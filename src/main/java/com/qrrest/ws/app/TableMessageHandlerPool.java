package com.qrrest.ws.app;

import java.util.HashMap;
import java.util.Map;

public class TableMessageHandlerPool {

	private static TableMessageHandlerPool instance = new TableMessageHandlerPool();
	public static TableMessageHandlerPool getInstance(){
		return instance;
	}
	
	protected TableMessageHandlerPool() {}
	
	private Map<Integer, TableMessageHandler> handlerMap = new HashMap<Integer, TableMessageHandler>();
	
	
	public TableMessageHandler getOrCreateHandler(int tableId) {
		if(!handlerMap.containsKey(tableId)) {
			handlerMap.put(tableId, new TableMessageHandler(tableId));
		}
		return handlerMap.get(tableId);
	}
	
}
