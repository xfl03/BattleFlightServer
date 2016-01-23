package battleflight.server.websocket.subhandler;

import battleflight.server.pool.MainPool;

public interface IWebSocketSubHandler {
	public void handle(MainPool mainPool, String text, String sender);
}
