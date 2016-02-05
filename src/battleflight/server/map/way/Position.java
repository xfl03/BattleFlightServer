package battleflight.server.map.way;

public class Position {
	public int x;
	public int y;
	
	public Position(int x,int y){
		this.x=x;
		this.y=y;
	}
	public Position(String p){
		String x=p.substring(0, 2);
		this.x=Integer.parseInt(x);
		String y=p.substring(2, 4);
		this.y=Integer.parseInt(y);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(obj instanceof String)
			return toString().equals(obj);
		if(obj instanceof Position)
			return equals(((Position)obj).toString());
		return false;
	}
	
	@Override
	public String toString(){
		return (x<10?"0"+x:x)+""+(y<10?"0"+y:y);
	}
}
