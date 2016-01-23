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
		System.out.println("BattleFlight Server Starting");
		mainPool=new MainPool();
		/*//Test for parsed map
		for(int x=0;x<=10;x++){
			for(int y=0;y<=6;y++){
				System.out.print(mainPool.parsedMap[x][y]+" ");
			}
			System.out.println();
		}*/
		webSocketThread=new Thread(new WebSocket(mainPool));
		webSocketThread.start();
	}
}
