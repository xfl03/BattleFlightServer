package battleflight.server.pool;

import java.util.HashMap;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.exception.TargetNotFoundException;
import battleflight.server.websocket.WebSocketHandler;
import battleflight.server.websocket.subhandler.IWebSocketSubHandler;
import battleflight.server.websocket.subhandler.TestHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketPool {
	public static final String SPLIT=",,";
	
	public HashMap<String,IWebSocketSubHandler> handlerList= new HashMap<String,IWebSocketSubHandler>();
	public HashMap<String,WebSocketHandler> clientList= new HashMap<String,WebSocketHandler>();
	
	public WebSocketPool(){
		//Init Handler
		handlerList.put("test", new TestHandler());
	}
	
	public void addHandler(String target,IWebSocketSubHandler handler){
		handlerList.put(target, handler);
	}
	public IWebSocketSubHandler getHandler(String target) throws TargetNotFoundException{
		IWebSocketSubHandler handler=handlerList.get(target);
		if(handler==null)
			throw new TargetNotFoundException(target);
        return handler;
	}
	
	public void sendMessage(String clientID, String fullMessage) throws ClientNotFoundException{
		WebSocketHandler client=clientList.get(clientID);
		if(client==null)
			throw new ClientNotFoundException(clientID);
		client.ctx0.write(new TextWebSocketFrame(fullMessage));
	}
	public void sendMessage(String clientID, String target, String message) throws ClientNotFoundException{
		sendMessage(clientID,target+SPLIT+message);
	}
}
