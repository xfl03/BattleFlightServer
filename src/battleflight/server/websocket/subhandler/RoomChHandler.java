package battleflight.server.websocket.subhandler;

import java.util.ArrayList;

import com.google.gson.Gson;

import battleflight.server.ingame.Player;
import battleflight.server.ingame.Room;
import battleflight.server.pool.MainPool;

public class RoomChHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try {
			Room r = mainPool.getRoom(sender,false,true);
			ArrayList<String> rs=new ArrayList<String>();
			rs.add(r.roomID);
			rs.add(r.status+"");
			rs.add(r.name);
			rs.add(r.players[0].name);
			rs.add(r.players[1].name);
			for (int i=0;i<r.players.length;i++){
				Player p=r.players[i];
		    	if(p.clientID==sender){
		        	rs.add(i+"");
		        	break;
		    	}
			}
			rs.add(r.players[0].prepared?"1":"0");
			rs.add(r.players[1].prepared?"1":"0");
			String info=new Gson().toJson(rs);
			mainPool.webSocketPool.sendMessage(sender, "roomch",info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
