package nz.ac.vuw.ecs.swen225.gp30.maze;

public abstract class GameObject {
    /**
     * Returns the x position of the game object.
     * @return the x position of the object
     */
    public abstract int getX();

    /**
     * Returns the y position of the game object.
     * @return the y position of the object
     */
    public abstract int getY();

    /**
     * Returns the image string of the object.
     * @return the image string.
     */
    public abstract String getImageString();
}
