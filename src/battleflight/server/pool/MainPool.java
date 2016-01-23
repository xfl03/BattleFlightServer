package battleflight.server.pool;

import battleflight.server.map.MapHelper;

public class MainPool {
	public WebSocketPool webSocketPool=new WebSocketPool();
	public int[][] unparsedMap={
			{1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,1},
			{1,0,0,0,1,0,0,0,1},
			{1,0,0,0,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1}};
	public int[][] parsedMap=MapHelper.parseMap(unparsedMap);
}
