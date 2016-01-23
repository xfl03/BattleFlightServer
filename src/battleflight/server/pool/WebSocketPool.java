package battleflight.server.pool;

import java.util.HashMap;

import battleflight.server.websocket.WebSocketHandler;
import battleflight.server.websocket.subhandler.IWebSocketSubHandler;
import battleflight.server.websocket.subhandler.TestHandler;

public class WebSocketPool {
	public HashMap<String,IWebSocketSubHandler> handlerList= new HashMap<String,IWebSocketSubHandler>();
	public HashMap<String,WebSocketHandler> clientList= new HashMap<String,WebSocketHandler>();
	
	public WebSocketPool(){
		//Init Handler
		handlerList.put("test", new TestHandler());
	}
	
	public void addHandler(String target,IWebSocketSubHandler handler){
		handlerList.put(target, handler);
	}
}
