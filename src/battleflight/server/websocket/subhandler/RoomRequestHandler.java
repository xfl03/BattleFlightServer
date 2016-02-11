package battleflight.server.websocket.subhandler;

import java.util.ArrayList;
import java.util.Set;

import com.google.gson.Gson;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.pool.MainPool;

public class RoomRequestHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try {
			if(mainPool.webSocketPool.clientList.get(sender).roomNow!=null){
				mainPool.webSocketPool.sendMessage(sender, "err","Already in a room.");
				return;
			}
			ArrayList<String[]> rs=new ArrayList<String[]>(); 
			Set<String> s=mainPool.rooms.keySet();
			for(String ss: s){
				rs.add(mainPool.rooms.get(ss).getRoomInfo());
			}
			String info=new Gson().toJson(rs);
			mainPool.webSocketPool.sendMessage(sender, "rooms",info);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}
	}

}
