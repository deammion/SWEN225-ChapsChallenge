package nz.ac.vuw.ecs.swen225.gp30.maze;


public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
