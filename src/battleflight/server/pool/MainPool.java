package battleflight.server.pool;

import java.util.HashMap;

import battleflight.server.exception.ClientNotFoundException;
import battleflight.server.exception.PlayerNotFoundException;
import battleflight.server.ingame.Player;
import battleflight.server.ingame.Room;
import battleflight.server.map.MapHelper;

public class MainPool {
	public WebSocketPool webSocketPool=new WebSocketPool();
	public int[][] unparsedMap={
			{1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,1},
			{1,0,0,0,1,0,0,0,1},
			{1,0,0,0,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1}};
	public int[][] parsedMap=MapHelper.parseMap(unparsedMap);
	
	public HashMap<String,Room> rooms=new HashMap<String,Room>();
	
	public Player getPlayer(String clientID,boolean sendErr) throws Exception{
		Room r=getRoom(clientID,true,sendErr);
		Player p=r.getPlayerByClientID(clientID);
		if(p==null)
			throw generatePlayerException(clientID, "User is not in the room",sendErr);
		return p;
	}
	public Room getRoom(String clientID,boolean checkActive,boolean sendErr) throws Exception{
		String room=webSocketPool.clientList.get(clientID).roomNow;
		if(room==null)
			throw generatePlayerException(clientID,"Not in a room",sendErr);
		Room r=rooms.get(room);
		if(r==null||(checkActive&&r.status==0))
			throw generatePlayerException(clientID,"Room is not avalible",sendErr);
		return r;
	}
	private Exception generatePlayerException(String clientID,String reason,boolean sendErr){
		try {
			if(sendErr)
				webSocketPool.sendMessage(clientID, "err", reason);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
			return e;
		}
		return new PlayerNotFoundException(clientID,reason);
	}
}
