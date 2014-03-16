package com.qrrest.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qrrest.model.Book;
import com.qrrest.model.User;

import com.qrrest.service.BookService;
import com.qrrest.service.UserService;
import com.qrrest.vo.OrderItemVo;

@SuppressWarnings("serial")
public class SubmitBookOrderServlet extends HttpServlet {

	private BookService service = new BookService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("u_id"));
		int restId = Integer.parseInt(req.getParameter("r_id"));
		String orderStr = req.getParameter("order_list");
		if (AppDebug.IS_DEBUG) {
			AppDebug.log(getClass(), "commit book restId(" + restId
					+ ")|userId(" + userId + "):" + orderStr);
		}

		List<OrderItemVo> orderItems = new Gson().fromJson(orderStr,
				new TypeToken<List<OrderItemVo>>() {
				}.getType());

		List<User> users = new ArrayList<User>();
		users.add(new UserService().getUserByUserId(userId));

		Book book = new Book();
		book.setUserId(userId);
		book.setRestId(restId);
		book.setBookName("test");
		book.setBookTel("123");
		book.setBookMemo("...");

		String result;
		if (service.createBook(book, orderItems)) {
			result = service.getLastestInsertId() + "";
		} else {
			result = "";
		}
		PrintWriter out = resp.getWriter();
		out.append(result);
		out.close();
	}
}
