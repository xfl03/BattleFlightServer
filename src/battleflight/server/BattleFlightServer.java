package battleflight.server;

import battleflight.server.pool.MainPool;
import battleflight.server.websocket.WebSocket;

public class BattleFlightServer {
	private MainPool mainPool;
	private Thread webSocketThread;
	public static void main(String[] args) {
		new BattleFlightServer().start();
	}
	public void start(){
		System.out.println("Start");
		mainPool=new MainPool();
		webSocketThread=new Thread(new WebSocket(mainPool));
		webSocketThread.start();
	}
}
