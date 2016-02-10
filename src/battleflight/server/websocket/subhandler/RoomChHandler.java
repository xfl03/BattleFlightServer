package battleflight.server.websocket.subhandler;

import java.util.ArrayList;

import com.google.gson.Gson;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.ingame.RoomInfo;
import battleflight.server.pool.MainPool;

public class RoomRequestHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
	    Player p = getPlayerByClientID(sender);
		Room r = mainPool.getRoom(text);
		ArrayList<String> rs=new ArrayList();
		rs.add(r.roomID);
		rs.add(r.status);
		rs.add(r.name);
		rs.add(r.player[0].name);
		rs.add(r.player[1].name);
		for (int i=0;i<r.player.length){
		    if(p.clientID==sender){
		        rs.add(i);
		        break;
		    }
		}
		rs.add(r.player[0].prepared);
		rs.add(r.player[1].prepared);
		String info=new Gson().toJson(rs);
		try {
			mainPool.webSocketPool.sendMessage(sender, "roomch",info);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}
	}
}
