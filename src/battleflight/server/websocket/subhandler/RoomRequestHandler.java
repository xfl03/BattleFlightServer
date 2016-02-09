package battleflight.server.websocket.subhandler;

import java.util.ArrayList;
import java.util.Set;

import com.google.gson.Gson;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.ingame.RoomInfo;
import battleflight.server.pool.MainPool;

public class RoomRequestHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		ArrayList<RoomInfo> rs=new ArrayList<RoomInfo>(); 
		Set<String> s=mainPool.rooms.keySet();
		for(String ss: s){
			rs.add(mainPool.rooms.get(ss).getRoomInfo());
		}
		String info=new Gson().toJson(rs);
		try {
			mainPool.webSocketPool.sendMessage(sender, "rooms",info);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}
	}

}
