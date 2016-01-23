package battleflight.server.map;

public class MapHelper {
	public static int[][] parseMap(int[][] unparsedMap){
		int xs=unparsedMap[0].length+2;
		int ys=unparsedMap.length+2;
		int[][] temp=new int[xs][ys];
		for(int x=0;x<xs-2;x++){
			for(int y=0;y<ys-2;y++){
				temp[x+1][y+1]=unparsedMap[y][x];
			}
		}
		return temp;
	}
}
