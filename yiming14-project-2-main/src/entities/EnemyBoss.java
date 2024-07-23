package entities;

import bagel.Image;
import bagel.Input;
import function.CollisionDetector;

import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Class for the enemy boss entity.
 */
public class EnemyBoss extends Entity{
    private final double ACTIVATION_RADIUS;
    private final Image IMAGE;
    private final String ROLE = "BOSS";
    private final int FALL_SPEED = 2;
    private final String position = "LEFT";
    private final int MAX_FRAME;
    private boolean randomBoolean = new Random().nextBoolean();
    private double health;
    private int frameCount = 0;
    private boolean isDead = false;

    /**
     * Constructs an EnemyBoss object with the specified position and properties.
     * @param x The x-coordinate of the EnemyBoss.
     * @param y The y-coordinate of the EnemyBoss.
     * @param props Properties object used to get the EnemyBoss's properties.
     */
    public EnemyBoss(int x, int y, Properties props){
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.enemyBoss.radius")), Integer.parseInt(props.getProperty("gameObjects.enemyBoss.speed")));
        this.ACTIVATION_RADIUS = Double.parseDouble(props.getProperty("gameObjects.enemyBoss.activationRadius"));
        this.IMAGE = new Image(props.getProperty("gameObjects.enemyBoss.image"));
        this.health = Double.parseDouble(props.getProperty("gameObjects.enemyBoss.health"));
        this.MAX_FRAME = 100;
    }

    /***
     * Method that updates the Enemy boss movement and draws it.
     * Check if the player is within the attack range of the boss.
     * If within the attack range, every 100 frames will determine whether to shoot a fireball.
     * @param input detecting keyboard actions.
     * @param target The player to check for collisions.
     * @param props Properties object used to set the fireball's properties.
     * @param fireballs fireballs List to add new fireballs when the boss attacks.
     */
    public void updateWithTarget(Input input, Player target, Properties props, List<Fireball> fireballs) {
        moveWithPlayer(input);

        if(!isDead) {
            if (CollisionDetector.isCollided(target, getX(), getY(), this.ACTIVATION_RADIUS)) {
                frameCount++;
            }

            //Determine whether to shoot
            if (frameCount == 100) {
                frameCount = 0;
                randomBoolean = new Random().nextBoolean();
                if (randomBoolean) {
                    fireballs.add(new Fireball(getX(), getY(), position, ROLE, props));
                }
            }
        } else {

            //
            setY(getY() + FALL_SPEED);
        }
        IMAGE.draw(getX(), getY());
    }

    /**
     * Checks if the enemy boss is dead.
     * @return true if the boss's health is less than or equal to 0, false otherwise.
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Marks the enemy boss as dead.
     */
    public void dead() {
        isDead = true;
    }

    /**
     * Gets the health of the boss.
     * @return The current health of the boss.
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the health of the boss.
     * @param health The new health value to set.
     */
    public void setHealth(double health) {
        this.health = health;
    }
}
