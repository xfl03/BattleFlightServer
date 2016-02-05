package battleflight.server.websocket.subhandler;

import battleflight.server.ingame.Player;
import battleflight.server.pool.MainPool;

public class GetRandomNumHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		try {
		Player p=mainPool.getPlayer(sender);
		int temp=getRandom(1,6);
		p.lastNum=temp;
		mainPool.webSocketPool.sendMessage(sender, "ran", temp+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getRandom(int min,int max){
		return (int)(Math.random()*(max-min+1)+min);
	}

}
