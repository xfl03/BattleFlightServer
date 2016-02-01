package battleflight.server.exception;

public class TargetNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public TargetNotFoundException(String target){
		super("Target '"+target+"' not found.");
	}
}
