package battleflight.server.websocket.subhandler;

import com.google.gson.Gson;

import battleflight.server.ingame.Chess;
import battleflight.server.ingame.Player;
import battleflight.server.map.way.WayHelper;
import battleflight.server.pool.MainPool;

public class WayGeneratorHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try {
			Player p=mainPool.getPlayer(sender,true);
			if(p.lastNum==0)
				mainPool.webSocketPool.sendMessage(sender, "err", "No Random Num");
			Chess c=p.getChess(text);
			if(c==null){
				mainPool.webSocketPool.sendMessage(sender, "err", "Chess Not Found");
				return;
			}
			p.lastSelectChess=text;
			String[] targets=WayHelper.generateTargets(mainPool.parsedMap, text, p.lastNum);
			p.lastTargets=targets;
			p.lastNum=0;
			Gson gson=new Gson();
			mainPool.webSocketPool.sendMessage(sender, "way", gson.toJson(targets));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
