package battleflight.server.websocket.subhandler;

import battleflight.server.ingame.Room;
import battleflight.server.pool.MainPool;

public class WaitingReadyHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try{
			Room r=mainPool.getRoom(sender, false, true);
			if(r.ready(sender)){
				r.players[0].mode=10;
				r.sendToAll(mainPool, "start,,"+r.players[0].clientID);
			}else{
				r.sendToAll(mainPool, "ready,,"+sender);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
