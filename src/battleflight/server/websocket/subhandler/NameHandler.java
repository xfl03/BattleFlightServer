package battleflight.server.websocket.subhandler;

import java.util.regex.Pattern;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.pool.MainPool;
import battleflight.server.websocket.WebSocketHandler;

public class NameHandler implements IWebSocketSubHandler {
	private final static Pattern userNamePattern = Pattern.compile("^[\u4e00-\u9fa5\\w]+$");
	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		WebSocketHandler w=mainPool.webSocketPool.clientList.get(sender);
		if(w==null)
			return;
		if(w.name!=null){
			send(mainPool,sender,2);
			return;
		}
		if(!userNamePattern.matcher(text).matches()){
			send(mainPool,sender,1);
			return;
		}
		if(mainPool.webSocketPool.getClientByName(text)!=null){
			send(mainPool,sender,3);
			return;
		}
		w.name=text;
		send(mainPool,sender,0);
	}
	/**
	 * @param status: 0-SUCCESS 1-WRONG_PATTERN 2-ALREADY_HAD_NAME 3-NAME_EXIST
	 * */
	public void send(MainPool mainPool,String sender,int status){
		try {
			mainPool.webSocketPool.sendMessage(sender, "nameable", ""+status);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}
	}

}
