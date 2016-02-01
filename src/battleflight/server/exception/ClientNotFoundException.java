package battleflight.server.exception;

public class ClientNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public ClientNotFoundException(String clientID){
		super("Client '"+clientID+"' not found.");
	}
}
