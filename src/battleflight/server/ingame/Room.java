package battleflight.server.ingame;

import java.util.ArrayList;

import battleflight.server.map.way.Position;
import battleflight.server.pool.MainPool;

public class Room {
	public String roomID=null;
	public String name=null;
	public Player[] players=new Player[2];
	public int status=0;//0:Preparing 1:Playing
	
	public Player getPlayerByClientID(String clientID){
		for(int i=0;i<players.length;i++){
			if(players[i].clientID==clientID)
				return players[i];
		}
		return null;
	}
	public boolean isFull(){
		for(Player p:players){
			if(p==null)
				return false;
		}
		return true;
	}
	public boolean isEmpty(){
		for(Player p:players){
			if(p!=null)
				return false;
		}
		return true;
	}
	public int joinRoom(Player p){
		for(int i=0;i<players.length;i++){
			if(players[i]==null){
				players[i]=p;
				return i;
			}
		}
		return -1;
	}
	public boolean exitRoom(Player p){
		for(int i=0;i<players.length;i++){
			if(players[i]==p){
				players[i]=null;
			}
		}
		return isEmpty();
	}	
	public void sendToAll(MainPool mainPool,String message) throws Exception{
		for(int i=0;i<players.length;i++){
			String c=players[i].clientID;
			if(c!=null)
				mainPool.webSocketPool.sendMessage(c, message);
		}
	}
	public ArrayList<String> setChess(Player player, Chess chess, Position targetPosition){
		ArrayList<String> t=new ArrayList<String>();
		chess.position=targetPosition;
		for(int i=0;i<players.length;i++){
			Player p=players[i];
			if(p==player)
				continue;
			ArrayList<Integer> c=p.getChessIndexes(targetPosition);
			if(c.size()==0)
				continue;
			p.removeChessByIndexes(c);
			t.add("kill,,"+p.clientID+","+targetPosition.toString());
			if(!p.ifChessRemains()){
				p.mode=1;
				t.add("lost,,"+p.clientID);
			}
		}
		boolean win=false;
		for(int i=0;i<players.length;i++){
			Player p=players[i];
			if(p==player)
				continue;
			if(p.ifChessRemains()){
				win=false;
				break;
			}
			win=true;
		}
		if(win){
			player.mode=2;
			t.add("win,,"+player.clientID);
		}
		return t;
	}
	public String[] getRoomInfo(){
		ArrayList<String> s=new ArrayList<String>();
		s.add(roomID);
		s.add(status+"");
		s.add(name);
		s.add(players[0]==null?"":players[0].name);
		s.add(players[1]==null?"":players[1].name);
		return (String[]) s.toArray();
	}
}
