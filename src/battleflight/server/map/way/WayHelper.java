package battleflight.server.map.way;

import java.util.ArrayList;

public class WayHelper {
	public static String[][] generate(int[][] parsedMap, String from, int step){
		return parseWays(generate(parsedMap,new Position(from),step),step);
	}
	private static String[][] parseWays(ArrayList<Way> w,int step){
		String[][] t=new String[w.size()][step+1];
		for(int i=0;i<w.size();i++){
			Way t2=w.get(i);
			for(int j=0;j<t2.position.size();j++){
				t[i][j]=t2.position.get(j).toString();
			}
		}
		return t;
	}
	public static ArrayList<Way> generate(int[][] parsedMap, Position from, int step){
		ArrayList<Way> ways=new ArrayList<Way>();
		Way w=new Way(from);
		ways.add(w);
		for(int i=1;i<=step;i++){
			int t=ways.size();
			for(int j=0;j<t;j++){
				Way wt=ways.get(j).clone();
				ArrayList<Position> pt=nextStop(parsedMap,step>1?wt.position.get(i-2):null,wt.position.get(i-1));
				ways.get(j).position.add(pt.get(0));
				for(int k=1;k<pt.size();k++){
					Way wtt=wt.clone();
					wtt.position.add(pt.get(k));
					ways.add(wtt);
				}
			}
		}
		return ways;
	}
	private static ArrayList<Position> nextStop(int[][] parsedMap, Position from, Position now){
		ArrayList<Position> t=new ArrayList<Position>();
		Position[] t2={
				new Position(from.x-1,from.y),
				new Position(from.x+1,from.y),
				new Position(from.x,from.y-1),
				new Position(from.x,from.y+1)};
		for(int i=0;i<4;i++){
			Position t3=t2[i];
			if(parsedMap[t3.x][t3.y]!=0&&!t3.equals(from))
				t.add(t3);
		}
		return t;
	}
}
