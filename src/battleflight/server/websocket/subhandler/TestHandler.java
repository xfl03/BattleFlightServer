package battleflight.server.websocket.subhandler;

import battleflight.server.pool.MainPool;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TestHandler implements IWebSocketSubHandler {

	@Override
	public void handle(MainPool mainPool, String text, String sender) {
		mainPool.webSocketPool.clientList.get(sender).ctx0.write(new TextWebSocketFrame(text));
	}

}
