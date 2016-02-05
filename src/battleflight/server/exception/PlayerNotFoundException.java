package battleflight.server.exception;

public class PlayerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public PlayerNotFoundException(String clientID){
		super("Player by client '"+clientID+"' not found.");
	}
	public PlayerNotFoundException(String clientID,String moreReason){
		super("Player by client '"+clientID+"' not found: "+moreReason);
	}
}
