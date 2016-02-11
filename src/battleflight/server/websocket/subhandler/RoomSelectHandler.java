package battleflight.server.websocket.subhandler;

import java.util.ArrayList;

import com.google.gson.Gson;

import battleflight.server.ingame.Player;
import battleflight.server.ingame.Room;
import battleflight.server.pool.MainPool;
import battleflight.server.websocket.WebSocketHandler;

public class RoomSelectHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try {
			WebSocketHandler w=mainPool.webSocketPool.clientList.get(sender);
			if(w.roomNow!=null){
				mainPool.webSocketPool.sendMessage(sender, "err","Already in a room");
				return;
			}
			Room r=mainPool.rooms.get(text);
			if(r==null){
				mainPool.webSocketPool.sendMessage(sender, "err","Room not found");
				return;
			}
			if(r.isFull()){
				mainPool.webSocketPool.sendMessage(sender, "err","Room is full");
				return;
			}
			Player p=new Player();
			p.clientID=sender;
			p.name=w.name;
			int po=r.joinRoom(p);
			w.roomNow=r.roomID;
			ArrayList<String> s=new ArrayList<String>();
			s.add(r.roomID);
			s.add(r.status+"");
			s.add(r.name);
			s.add(r.players[0]==null?"":r.players[0].name);
			s.add(r.players[1]==null?"":r.players[1].name);
			s.add(po+"");
			mainPool.webSocketPool.sendMessage(sender, "roomable",new Gson().toJson(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
