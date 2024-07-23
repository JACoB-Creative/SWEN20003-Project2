package entities;

import function.CollisionDetector;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Class for the end flag entity.
 */
public class EndFlag extends Entity{
    private final Image IMAGE;
    private boolean isCollided = false;

    /**
     * Constructs a EndFlag object with the specified position and properties.
     * @param x The x-coordinate of the EndFlag.
     * @param y The y-coordinate of the EndFlag.
     * @param props Properties object used to get the EndFlag's properties.
     */
    public EndFlag(int x, int y, Properties props) {
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.endFlag.radius")), Integer.parseInt(props.getProperty("gameObjects.endFlag.speed")));
        this.IMAGE = new Image(props.getProperty("gameObjects.endFlag.image"));
    }

    /***
     * Method that updates the end flag movement and draws it. Also checks for collisions with the player.
     * @param input detecting keyboard actions.
     * @param target The player to check for collisions.
     */
    public void updateWithTarget(Input input, Player target) {
        moveWithPlayer(input);
        IMAGE.draw(getX(), getY());

        if (CollisionDetector.isCollided(target, getX(), getY(), getRadius()) && !isCollided) {
            isCollided = true;
        }
    }
    /***
     * Check if the end flag is collided.
     * @return true if the end flag is collided.
     */
    public boolean isCollided() {
        return isCollided;
    }
}
