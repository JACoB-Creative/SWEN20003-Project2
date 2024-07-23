package entities;

import function.CollisionDetector;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;

/**
 * Class for the enemy entity.
 */
public class Enemy extends Entity{
    private final double DAMAGE_SIZE;
    private final int MAX_RAN_X;
    private final int RAN_SPEED_X;
    private final Image IMAGE;
    private boolean killedTarget = false;
    private boolean hitPlayer = false;
    private boolean randomBoolean = new Random().nextBoolean();
    private int randomMovingPixel = 0;

    /**
     * Constructs an Enemy object with the specified position and properties.
     * @param x The x-coordinate of the Enemy.
     * @param y The y-coordinate of the Enemy.
     * @param props Properties object used to get the Enemy's properties.
     */
    public Enemy(int x, int y, Properties props) {
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.enemy.radius")), Integer.parseInt(props.getProperty("gameObjects.enemy.speed")));
        this.DAMAGE_SIZE = Double.parseDouble(props.getProperty("gameObjects.enemy.damageSize"));
        this.MAX_RAN_X = Integer.parseInt(props.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        this.RAN_SPEED_X = Integer.parseInt(props.getProperty("gameObjects.enemy.randomSpeed"));
        this.IMAGE = new Image(props.getProperty("gameObjects.enemy.image"));
    }

    /***
     * Method that updates the enemy movement and draws it. Also checks for collisions with the player.
     * @param input detecting keyboard actions.
     * @param target The player to check for collisions.
     */
    public void updateWithTarget(Input input, Player target) {
        moveWithPlayer(input);
        randomMove();
        IMAGE.draw(getX(), getY());

        if (target != null && CollisionDetector.isCollided(target, getX(), getY(), getRadius()) && !hitPlayer && !target.isInvincible()) {
            hitPlayer = true;
            damageTarget(target);
        }
    }

    /***
     * Method that damages the player. If the health of the player is less than or equal to 0,
     * the player will be marked as dead.
     * @param player The player to be damaged.
     */
    private void damageTarget(Player player) {
        double newHealth = player.getHealth() - DAMAGE_SIZE;
        player.setHealth(newHealth);

        if (newHealth <= 0 && !killedTarget) {
            player.dead();
            killedTarget = true;
        }
    }

    /**
     * Randomly moves the enemy in the horizontal direction.
     */
    public void randomMove(){
        randomMovingPixel++;
        if(randomBoolean){
            setX(getX() - RAN_SPEED_X);
        }else{
            setX(getX() + RAN_SPEED_X);
        }
        if(randomMovingPixel >= MAX_RAN_X){
            randomMovingPixel = 0;
            randomBoolean = !randomBoolean;
        }
    }
}
