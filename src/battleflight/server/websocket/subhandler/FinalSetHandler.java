package battleflight.server.websocket.subhandler;

import java.util.ArrayList;

import battleflight.server.ingame.Chess;
import battleflight.server.ingame.Player;
import battleflight.server.ingame.Room;
import battleflight.server.map.way.Position;
import battleflight.server.pool.MainPool;

public class FinalSetHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try{
			Player p=mainPool.getPlayer(sender);
			if(p.lastSelectChess==null||p.lastTargets==null)
				mainPool.webSocketPool.sendMessage(sender, "err", "Last Chess or Last Targets Not Found");
			Chess c=p.getLastSelectChess();
			if(c==null)
				mainPool.webSocketPool.sendMessage(sender, "err", "Last Chess Not Found");
			Room r=mainPool.getRoom(sender, true);
			if(!p.isInLastTargets(text))
				mainPool.webSocketPool.sendMessage(sender, "err", "Target Not Avaiable");
			ArrayList<String> co=r.setChess(p, p.getLastSelectChess(), new Position(text));
			for(int i=0;i<co.size();i++)
				r.sendToAll(mainPool,co.get(i));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
