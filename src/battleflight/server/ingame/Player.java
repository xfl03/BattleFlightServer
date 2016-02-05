package battleflight.server.ingame;

import java.util.ArrayList;

import battleflight.server.map.way.Position;

public class Player {
	public String hashID=null;
	public String clientID;
	public Chess[] chesses=new Chess[4];
	public int mode=0;//1:Lost 2:Win
	public int lastNum=0;
	public String lastSelectChess=null;
	public String[] lastTargets=null;
	public boolean isInLastTargets(String position){
		for(int i=0;i<lastTargets.length;i++){
			if(lastTargets[i].equals(position))
				return true;
		}
		return false;
	}
	public Chess getChess(String position){
		for(int i=0;i<chesses.length;i++){
			if(chesses[i].position.equals(position))
				return chesses[i];
		}
		return null;
	}
	public ArrayList<Integer> getChessIndexes(Position position){
		ArrayList<Integer> c=new ArrayList<Integer>();
		for(int i=0;i<chesses.length;i++){
			if(chesses[i].position.equals(position))
				c.add(i);
		}
		return c;
	}
	public void removeChessByIndexes(ArrayList<Integer> indexes){
		for(int i=0;i<indexes.size();i++){
			chesses[indexes.get(i)]=null;
		}
	}
	public Chess getLastSelectChess(){
		return getChess(lastSelectChess);
	}
	public boolean ifChessRemains(){
		for(int i=0;i<chesses.length;i++){
			if(chesses[i]!=null)
				return true;
		}
		return false;
	}
}
