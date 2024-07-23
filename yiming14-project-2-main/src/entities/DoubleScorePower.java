package entities;

import function.CollisionDetector;
import bagel.Image;
import bagel.Input;
import java.util.Properties;

/**
 * Class for the double score power entity.
 */
public class DoubleScorePower extends Entity{
    private final Image IMAGE;
    private final int MAX_FRAME;
    private final int COLLISION_SPEED = -10;
    private boolean isCollided = false;
    private boolean used = false;
    private int frameCount = 0;

    /**
     * Constructs a DoubleScorePower object with the specified position and properties.
     * @param x The x-coordinate of the DoubleScorePower.
     * @param y The y-coordinate of the DoubleScorePower.
     * @param props Properties object used to get the DoubleScorePower's properties.
     */
    public DoubleScorePower(int x, int y, Properties props){
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.doubleScore.radius")), Integer.parseInt(props.getProperty("gameObjects.doubleScore.speed")));
        this.IMAGE = new Image(props.getProperty("gameObjects.doubleScore.image"));
        this.MAX_FRAME = Integer.parseInt(props.getProperty("gameObjects.doubleScore.maxFrames"));
    }

    /**
     * Method that updates the Double Score Power object movement and draws it. Also checks for collisions with the player.
     * If collided with the player, the multiplier of the player score will become double.
     * After 500 frames, the multiplier of the player score will reset.
     * @param input detecting keyboard actions.
     * @param target The player to check for collisions.
     */
    public void updateWithTarget(Input input, Player target){
        moveWithPlayer(input);
        IMAGE.draw(getX(), getY());
        if (CollisionDetector.isCollided(target, getX(), getY(), getRadius()) && !isCollided) {
            isCollided = true;
            target.doubleScoreMultiplier();
        }

        if(isCollided){
            moveAfterCollision();
            if(!used) {
                frameCount++;
                if (frameCount > MAX_FRAME) {
                    //reset the score rate of all coins
                    used = true;
                    target.resetScoreMultiplier();
                }
            }
        }
    }
}
