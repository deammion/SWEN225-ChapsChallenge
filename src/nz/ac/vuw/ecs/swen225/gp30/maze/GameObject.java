package nz.ac.vuw.ecs.swen225.gp30.maze;

public interface GameObject {
    /**
     * Returns the x position of the game object.
     * @return the x position of the object
     */
    int getX();

    /**
     * Returns the y position of the game object.
     * @return the y position of the object
     */
    int getY();

    /**
     * Returns the image string of the object.
     * @return the image string.
     */
    String getImageString();
}
