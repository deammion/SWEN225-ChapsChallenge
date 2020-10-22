package nz.ac.vuw.ecs.swen225.gp30.maze;


/**
 * IllegalMoveException is custom runtime exception used for when moves are not permitted
 * 
 * @author campliosca 300489876
 */
public class IllegalMoveException extends RuntimeException {
	
	/**
	 * Constructs an IllegalMoveException with message.
	 * 
	 * @param message the message of the exception
	 */
    public IllegalMoveException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
