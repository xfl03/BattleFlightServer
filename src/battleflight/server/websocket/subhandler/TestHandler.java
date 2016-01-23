package battleflight.server.websocket.subhandler;

import battleflight.server.pool.MainPool;

public class TestHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		mainPool.webSocketPool.sendMessage(sender,text);//Send User¡®s Message Back
	}
}
