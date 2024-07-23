package entities;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Class for the player entity.
 */
public class Player extends Entity{
    private int GROUND_Y;
    private int initialY;
    private final Properties PROPS;
    private final String ROLE = "PLAYER";
    private final int INITIAL_JUMP_SPEED = -20;
    private final int FALL_SPEED = 2;
    private int speedY = 0;
    private Image image;
    private String position = "RIGHT";
    private double health;
    private int score = 0;
    private boolean isJump = false;
    private boolean isInvincible = false;
    private int scoreMultiplier = 1;
    private boolean isDead = false;

    /**
     * Constructs a player object with the specified position and properties.
     * @param x The x-coordinate of the player.
     * @param y The y-coordinate of the player.
     * @param props Properties object used to get the player's properties.
     */
    public Player(int x, int y, Properties props){
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.player.radius")), 0);
        this.GROUND_Y = y;
        this.initialY = y;
        this.PROPS = props;
        this.image = new Image(props.getProperty("gameObjects.player.imageRight"));
        this.health = Double.parseDouble(props.getProperty("gameObjects.player.health"));
    }

    /**
     * Updates the player's state based on input.
     * @param input detecting keyboard actions.
     */
    public void update(Input input){
        //if the player is not dead
        if(!isDead){
            updateImageDirection(input);
            jump(input);
            if(!isJump){
                fall();
            }
        } else {
            setY(getY() + FALL_SPEED);
        }
        image.draw(getX(), getY());
    }

    /**
     * Handles the player's jump based on input.
     * Initiates the jump if the up arrow key is pressed, updates the vertical speed during the jump,
     * and resets the player's position when the jump is finished.
     * @param input detecting keyboard actions.
     */
    public void jump(Input input) {

        // on platform and up arrow key is pressed
        if (input.wasPressed(Keys.UP) && getY() == initialY) {
            isJump = true;
            speedY = INITIAL_JUMP_SPEED;
        }

        // mid jump
        if (getY() < initialY) {
            speedY += 1;
        }

        // finishing jump
        if (speedY > 0 && getY() >= initialY && !isDead()) {
            isJump = false;
            speedY = 0;
            setY(initialY);
        }

        setY(getY() + speedY);
    }

    /**
     * Handles the player's falling motion.
     * Increases the vertical speed if the player is above the initial Y position and updates the Y position.
     * Stops the fall when the player reaches the ground.
     */
    public void fall(){
        if (getY() < initialY) {
            speedY += 1;
            int newY = getY() + speedY;

            if (newY > GROUND_Y) {
                setY(GROUND_Y);
                speedY = 0;
            } else {
                setY(newY);
            }
        }
    }

    /**
     * Updates the player's image direction based on input.
     * Changes the image and position when the left or right arrow key is pressed.
     * @param input detecting keyboard actions.
     */
    public void updateImageDirection(Input input){
        if (input.wasPressed(Keys.LEFT)) {
            image = new Image(this.PROPS.getProperty("gameObjects.player.imageLeft"));
            position = "LEFT";
        }
        if (input.wasPressed(Keys.RIGHT)) {
            image = new Image(this.PROPS.getProperty("gameObjects.player.imageRight"));
            position = "RIGHT";
        }
    }

    /**
     * Sets the player to be on a flying platform.
     * Updates the initial Y position to the current Y position and resets the vertical speed.
     */
    public void setOnFlyingPlatform(){
        initialY = getY();
        speedY = 0;
    }

    /**
     * Resets the player's state when not on a flying platform.
     * Sets the initial Y position back to the ground level.
     */
    public void resetOnFlyingPlatform(){
        initialY = GROUND_Y;
    }

    /**
     * Marks the player as dead.
     */
    public void dead() {
        isDead = true;
    }

    /**
     * Checks if the player is dead.
     * @return true if the player's health is less than or equal to 0, false otherwise.
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Activates the player's invincibility.
     */
    public void activeInvincible(){
        this.isInvincible = true;
    }

    /**
     * Deactivates the player's invincibility.
     */
    public void deactivateInvincible(){
        this.isInvincible = false;
    }

    /**
     * Checks if the player is invincible.
     * @return true if the player is invincible, false otherwise.
     */
    public boolean isInvincible(){
        return isInvincible;
    }

    /**
     * Doubles the player's score multiplier.
     */
    public void doubleScoreMultiplier(){
        scoreMultiplier = 2;
    }

    /**
     * Resets the player's score multiplier to 1.
     */
    public void resetScoreMultiplier(){
        scoreMultiplier = 1;
    }

    /**
     * Gets the player's score multiplier.
     * @return The player's score multiplier.
     */
    public int getScoreMultiplier(){
        return scoreMultiplier;
    }

    /**
     * Gets the player's position.
     * @return The player's current position.
     */
    public String getPosition(){
        return position;
    }

    /**
     * Gets the player's health.
     * @return The current health of the player.
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the player's health.
     * @param health The new health value to set.
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets the player's score.
     * @return The current score of the player.
     */
    public int getScore(){
        return score;
    }

    /**
     * Sets the player's score.
     * @param points The new score to set.
     */
    public void setScore(int points){
        this.score = points;
    }

    /**
     * Gets the player's role.
     * @return The role of the player.
     */
    public String getROLE(){
        return ROLE;
    }

    /**
     * Gets the player's initial Y position.
     * @return The initial Y position of the player.
     */
    public int getInitialY(){
        return initialY;
    }

    /**
     * Gets the ground Y position.
     * @return The ground Y position.
     */
    public int getGROUND_Y(){
        return GROUND_Y;
    }

}
