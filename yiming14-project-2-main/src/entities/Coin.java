package entities;

import function.CollisionDetector;
import bagel.Image;
import bagel.Input;
import java.util.Properties;

/**
 * Class for the coin entity.
 */
public class Coin extends Entity{
    private final int VALUE;
    private final Image IMAGE;
    private boolean isCollided = false;

    /**
     * Constructs a Coin object with the specified position and properties.
     * @param x The x-coordinate of the coin.
     * @param y The y-coordinate of the coin.
     * @param props Properties object used to get the coin's properties.
     */
    public Coin(int x, int y, Properties props) {
        super(x ,
              y ,
              Double.parseDouble(props.getProperty("gameObjects.coin.radius")),
              Integer.parseInt(props.getProperty("gameObjects.coin.speed")));
        this.VALUE = Integer.parseInt(props.getProperty("gameObjects.coin.value"));
        this.IMAGE = new Image(props.getProperty("gameObjects.coin.image"));
    }

    /***
     * Method that updates the coin movement and draws it. Also checks for collisions with the player.
     * @param input detecting keyboard actions.
     * @param target The player to check for collisions.
     */
    public void updateWithTarget(Input input, Player target) {
        moveWithPlayer(input);
        IMAGE.draw(getX(), getY());

        if (CollisionDetector.isCollided(target, getX(), getY(), getRadius()) && !isCollided) {
            isCollided = true;
            increaseScore(target);
        }
        if(isCollided){
            moveAfterCollision();
        }
    }

    /**
     * Increases the player's score based on the coin's value and the player's score multiplier.
     * @param target The player whose score is to be increased.
     */
    private void increaseScore(Player target){
            target.setScore(target.getScore() + (this.VALUE * target.getScoreMultiplier()));
    }
}
