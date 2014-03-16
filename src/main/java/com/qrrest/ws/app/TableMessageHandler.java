package com.qrrest.ws.app;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.tomcat.util.digester.ObjectParamRule;

import com.google.gson.Gson;
import com.qrrest.model.Book;
import com.qrrest.model.Order;
import com.qrrest.model.Table;
import com.qrrest.model.Table.TableStatusEnum;
import com.qrrest.service.BookService;
import com.qrrest.service.OrderService;
import com.qrrest.service.TableService;
import com.qrrest.service.UserService;
import com.qrrest.vo.OrderItemVo;

@SuppressWarnings({ "unused", "deprecation" })
class TableMessageHandler {

	private static final String TAG = TableMessageHandler.class.getSimpleName();

	private int tableId;
	private Integer orderId;
	private Set<Integer> userIdSet = new HashSet<Integer>(); // 考虑可能有用户中途离线，保存所有参与过的用户
	private Map<Integer, OrderItemVo> orderItemMap = new HashMap<Integer, OrderItemVo>();
	private List<UserMessageInbound> userMessageInboundList = new LinkedList<UserMessageInbound>();

	private TableService tableService = new TableService();
	private OrderService orderService = new OrderService();
	private BookService bookService = new BookService();

	public int getTableId() {
		return tableId;
	}

	public TableMessageHandler(int tableId) {
		this.tableId = tableId;
	}

	private void onWSOpen(UserMessageInbound sender) {
		userMessageInboundList.add(sender);
		userIdSet.add(sender.getUserId());
	}

	public MessageInbound createMessageInbound(int userId) {
		return new UserMessageInbound(userId);
	}

	@SuppressWarnings("incomplete-switch")
	private void onWSMessage(UserMessageInbound sender, ActionTypeEnum action,
			Object[] params) {
		switch (action) {
		case InitCheck: {
			Table table = tableService.getTableById(tableId);
			if (table == null) {
				WSLog.e(TAG, "WS-错误的二维码");
				sender.sendMessage(ActionTypeEnum.StatusError, new Object[] {},
						null);
				return;
			}
			if (table.getTableStatus() != TableStatusEnum.free
					&& table.getTableStatus() != TableStatusEnum.serving) {
				WSLog.e(TAG, "WS-操作不允许的餐桌");
				sender.sendMessage(ActionTypeEnum.StatusError, new Object[] {},
						null);
				return;
			}
			// 订单发起，创建者第一次扫描二维码时发生
			if (table.getTableStatus() == TableStatusEnum.free) {
				// 标记餐桌已被绑定
				table.setTableStatus(TableStatusEnum.serving);
				tableService.modifyTableStatus(table);
				// 创建订单
				if (!orderService.createOrderForServing(table,
						sender.getUserId())) {
					WSLog.e(TAG, "WS-创建订单失败");
					sender.sendMessage(ActionTypeEnum.StatusError,
							new Object[] {}, null);
					return;
				}
				Order order = orderService.getOrderByOrderId(orderService
						.getLastInsertId());
				// 为handler保留orderId引用
				orderId = order.getOrderId();
				// 通知同步订单号
				sender.sendMessage(ActionTypeEnum.SyncOrderId,
						new Object[] { orderId.intValue() }, null);
				// 检查预定，若存在预定，绑定并填充至临时菜单
				Book book = bookService
						.getLastestUnorderedBookByUserIdAndRestId(
								sender.getUserId(), table.getRestId());
				if (book != null) {
					// 标记预定绑定至订单
					book.setOrderIdNullabled(order.getOrderId());
					bookService.modifyBookOrderId(book);
					// 填充预定菜单
					List<OrderItemVo> orderItems = bookService
							.getBookDishRelationship(book.getBookId());
					for (OrderItemVo item : orderItems) {
						orderItemMap.put(item.getDishId(), item);
					}
					sender.sendMessage(ActionTypeEnum.InitBookFlush,
							new Object[] { new Gson().toJson(orderItemMap
									.values()) }, null);
					return;
				}
			}
			// 参与协同点餐
			else if (table.getTableStatus() == TableStatusEnum.serving) {
				// 通知同步订单号
				if(orderId == null) {
					orderId = new OrderService().getServingOrderIdByTableId(tableId);
				}
				sender.sendMessage(ActionTypeEnum.SyncOrderId,
						new Object[] { orderId.intValue() }, null);
			}
			if (orderItemMap.size() != 0) {
				sender.sendMessage(
						ActionTypeEnum.InitFlush,
						new Object[] { new Gson().toJson(orderItemMap.values()) },
						null);
			}
			break;
		}
		case InsertItem: {
			int dishId = ((Double) params[0]).intValue();
			if (!orderItemMap.containsKey(dishId)) {
				OrderItemVo item = new OrderItemVo();
				item.setDishId(dishId);
				item.setDishCount(1);
				orderItemMap.put(dishId, item);
				sender.sendMessage(ActionTypeEnum.InsertItem,
						new Object[] { dishId }, null);
				broadcastWSMessage(sender, ActionTypeEnum.InsertItem,
						new Object[] { dishId });
			} else {
				WSLog.w(TAG, "WS-InsertItem dishId not found");
			}
			break;
		}
		case RemoveItem: {
			int dishId = ((Double) params[0]).intValue();
			if (orderItemMap.containsKey(dishId)) {
				orderItemMap.remove(dishId);
				sender.sendMessage(ActionTypeEnum.RemoveItem,
						new Object[] { dishId }, null);
				broadcastWSMessage(sender, ActionTypeEnum.RemoveItem,
						new Object[] { dishId });
			} else {
				sender.sendMessage(ActionTypeEnum.StatusError, new Object[] {},
						null);
				WSLog.w(TAG, "WS-RemoveItem dishId not found");
			}
			break;
		}
		case ChangeItemNum: {
			int dishId = ((Double) params[0]).intValue();
			int num = ((Double) params[1]).intValue();
			if (orderItemMap.containsKey(dishId)) {
				orderItemMap.get(dishId).setDishCount(num);
				sender.sendMessage(ActionTypeEnum.ChangeItemNum, new Object[] {
						dishId, num }, null);
				broadcastWSMessage(sender, ActionTypeEnum.ChangeItemNum,
						new Object[] { dishId, num });
			} else {
				WSLog.w(TAG, "WS-ChangeItemNum dishId not found");
			}
			// TODO: 标记菜品的被使用状态
			break;
		}
		case CommitOrder: {
			int orderId = orderService.getServingOrderIdByTableId(tableId);
			if (orderService.fillOrderDishAndUserRelationship(orderId,
					new LinkedList<Integer>(userIdSet),
					new LinkedList<OrderItemVo>(orderItemMap.values()))) {
				sender.sendMessage(ActionTypeEnum.CommitOrder, new Object[] {
						true, orderId }, null);
				broadcastWSMessage(sender, ActionTypeEnum.CommitOrder,
						new Object[] { true, orderId });
				userIdSet.clear();
				orderItemMap.clear();
				userMessageInboundList.clear();
			} else {
				sender.sendMessage(ActionTypeEnum.CommitOrder, new Object[] {
						false, 0 }, null);
				WSLog.w(TAG, "WS-CommitOrder error");
			}
		}
		case Undefined: {
			break;
		}
		}
	}

	private void broadcastWSMessage(UserMessageInbound sender,
			ActionTypeEnum action, Object[] params) {
		String nickname = new UserService().getUserByUserId(sender.getUserId())
				.getUserNickName();
		if (nickname == null || nickname.equals("")) {
			nickname = "小伙伴";
		}
		String message = buildMessage(action, params, nickname);
		for (UserMessageInbound inbound : userMessageInboundList) {
			if (inbound != sender) {
				inbound.sendMessage(message);
			}
		}
	}

	private void onWSClose(UserMessageInbound sender) {
		userMessageInboundList.remove(sender);
	}

	public static enum ActionTypeEnum {
		InitCheck, SyncOrderId, StatusError, InitFlush, InitBookFlush, InsertItem, RemoveItem, ChangeItemNum, CommitOrder, Undefined;

		public static ActionTypeEnum parse(String action) {
			try {
				return ActionTypeEnum.valueOf(action);
			} catch (Exception e) {
				WSLog.e(TAG, "WS-未知命令:" + action);
				return Undefined;
			}
		}
	}

	private class UserMessageInbound extends MessageInbound {

		private WsOutbound outbound; // init in onOpen
		private int userId;

		public int getUserId() {
			return userId;
		}

		public UserMessageInbound(int userId) {
			super();
			this.userId = userId;
		}

		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			WSLog.i(TAG, "WS - ON binary message");
		}

		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			WSLog.d(TAG, "WS - ON text message: " + message);
			String payload = message.toString();
			int sharp1 = payload.indexOf('#');
			String action = payload.substring(0, sharp1);
			Object[] params = new Gson().fromJson(
					payload.substring(sharp1 + 1, payload.length()),
					Object[].class);
			onWSMessage(this, ActionTypeEnum.parse(action), params);
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			this.outbound = outbound;
			WSLog.d(TAG, "WS-已连接");
			onWSOpen(this);
			super.onOpen(outbound);
		}

		@Override
		protected void onClose(int status) {
			WSLog.d(TAG, "WS-已关闭");
			onWSClose(this);
			super.onClose(status);
		}

		public void sendMessage(ActionTypeEnum action, Object[] params,
				String operator) {
			sendMessage(buildMessage(action, params, operator));
		}

		public void sendMessage(String message) {
			try {
				WSLog.d(TAG, "WS - SEND：" + message);
				outbound.writeTextMessage(CharBuffer.wrap(message));
				outbound.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private String buildMessage(ActionTypeEnum action, Object[] params,
			String operator) {
		return action.toString() + "#" + new Gson().toJson(params) + "#"
				+ operator;
	}
}
