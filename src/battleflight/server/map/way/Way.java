package battleflight.server.map.way;

import java.util.ArrayList;

public class Way {
	public ArrayList<Position> position=new ArrayList<Position>();
	
	public Way(Position p0){
		position.add(p0);
	}
	public Way(ArrayList<Position> position){
		this.position=position;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Way clone(){
		return new Way((ArrayList<Position>)position.clone());
	}
}
