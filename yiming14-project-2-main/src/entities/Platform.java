package entities;

import bagel.Image;
import bagel.Input;

import java.util.Properties;

/**
 * Class for the platform entity.
 */
public class Platform extends Entity{
    private final Image IMAGE;
    private final int MAX_COORDINATE = 3000;

    /**
     * Constructs a Platform object with the specified position and properties.
     * @param x The x-coordinate of the Platform.
     * @param y The y-coordinate of the Platform.
     * @param props Properties object used to get the Platform's properties.
     */
    public Platform(int x, int y, Properties props) {
        super(x, y, 0, Integer.parseInt(props.getProperty("gameObjects.platform.speed")));
        this.IMAGE = new Image(props.getProperty("gameObjects.platform.image"));
    }

    /***
     * Method that updates the platform movement and draws it.
     * @param input detecting keyboard actions.
     */
    public void updateWithTarget(Input input) {
        moveWithPlayer(input);
        IMAGE.draw(getX(), getY());
    }
}
