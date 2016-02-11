package battleflight.server.websocket.subhandler;

import battleflight.server.ingame.Player;
import battleflight.server.ingame.Room;
import battleflight.server.pool.MainPool;
import battleflight.server.websocket.WebSocketHandler;

public class WaitingExitHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try{
			WebSocketHandler w=mainPool.webSocketPool.clientList.get(sender);
			Room r=mainPool.getRoom(sender, false, true);
			if(r.status==1){
				mainPool.webSocketPool.sendMessage(sender, "err","Playing.");
				return;
			}
			Player p=r.getPlayerByClientID(sender);
			if(p.prepared==true){
				mainPool.webSocketPool.sendMessage(sender, "err","Prepared.");
				return;
			}
			if(r.exitRoom(p)){
				mainPool.rooms.remove(r.roomID);
			}
			w.roomNow=null;
				
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
