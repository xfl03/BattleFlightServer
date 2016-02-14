package battleflight.server.websocket.subhandler;

import java.util.ArrayList;

import com.google.gson.Gson;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.hash.MD5Helper;
import battleflight.server.ingame.Player;
import battleflight.server.ingame.Room;
import battleflight.server.pool.MainPool;
import battleflight.server.websocket.WebSocketHandler;

public class CreateRoomHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		WebSocketHandler w=mainPool.webSocketPool.clientList.get(sender);
		String roomID="ROOM"+MD5Helper.generateShortMD5(System.currentTimeMillis()+sender);
		Room r=new Room();
		w.roomNow=roomID;
		r.name=text;
		r.roomID=roomID;
		Player p=new Player();
		p.clientID=sender;
		p.name=w.name;
		r.players[0]=p;
		try {
			ArrayList<String> s=new ArrayList<String>();
			s.add(r.roomID);
			s.add(r.status+"");
			s.add(r.name);
			s.add(w.name);
			s.add("");
			s.add("0");
			mainPool.webSocketPool.sendMessage(sender, "roomable",new Gson().toJson(s));
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}
	}

}
