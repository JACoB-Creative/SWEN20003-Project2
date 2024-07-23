package entities;

import bagel.Image;
import bagel.Input;
import function.CollisionDetector;
import java.util.Properties;

/**
 * Class for the invincible power entity.
 */
public class InvinciblePower extends Entity{
    private final Image IMAGE;
    private final int MAX_FRAME;
    private final int COLLISION_SPEED = -10;
    private boolean isCollided = false;
    private boolean used = false;
    private int frameCount = 0;
    private boolean isInvincible = false;

    /**
     * Constructs a InvinciblePower object with the specified position and properties.
     * @param x The x-coordinate of the InvinciblePower.
     * @param y The y-coordinate of the InvinciblePower.
     * @param props Properties object used to get the InvinciblePower's properties.
     */
    public InvinciblePower(int x, int y, Properties props){
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.invinciblePower.radius")), Integer.parseInt(props.getProperty("gameObjects.invinciblePower.speed")));
        this.IMAGE = new Image(props.getProperty("gameObjects.invinciblePower.image"));
        this.MAX_FRAME = Integer.parseInt(props.getProperty("gameObjects.invinciblePower.maxFrames"));
    }

    /**
     * Method that updates the Invincible Power object movement and draws it. Also checks for collisions with the player.
     * If collided with the player, the player will become invincible.
     * After 500 frames, the player's invincible ability will deactivate.
     * @param input detecting keyboard actions.
     * @param target The player to check for collisions.
     */
    public void updateWithTarget(Input input, Player target) {
        moveWithPlayer(input);
        IMAGE.draw(getX(), getY());
        if (CollisionDetector.isCollided(target, getX(), getY(), getRadius()) && !isCollided) {
            isCollided = true;
            target.activeInvincible();
        }

        if (isCollided) {
            moveAfterCollision();
            if (!used) {
                frameCount++;
                if (frameCount > MAX_FRAME) {
                    used = true;
                    target.deactivateInvincible();
                }
            }
        }
    }

}
