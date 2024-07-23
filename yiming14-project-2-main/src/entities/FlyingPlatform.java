package entities;

import bagel.Image;
import bagel.Input;

import java.util.Properties;
import java.util.Random;

/**
 * Class for the flying platform entity.
 */
public class FlyingPlatform extends Entity{
    private final int HALF_LENGTH;
    private final int HALF_HEIGHT;
    private final int MAX_RAN_X;
    private final int RAN_SPEED_X;
    private final Image IMAGE;
    private boolean randomBoolean = new Random().nextBoolean();
    private int randomMovingPixel = 0;
    private boolean isRise = false;

    /**
     * Constructs a FlyingPlatform object with the specified position and properties.
     * @param x The x-coordinate of the FlyingPlatform.
     * @param y The y-coordinate of the FlyingPlatform.
     * @param props Properties object used to get the FlyingPlatform's properties.
     */
    public FlyingPlatform(int x, int y, Properties props){
        super(x, y, 0, Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.speed")));
        this.HALF_LENGTH = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfLength"));
        this.HALF_HEIGHT = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfHeight"));
        this.MAX_RAN_X = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        this.RAN_SPEED_X = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        this.IMAGE = new Image(props.getProperty("gameObjects.flyingPlatform.image"));
    }

    /**
     * Updates the platform's movement and checks for interactions with the player.
     * @param input detecting keyboard actions.
     * @param target The player to check for interactions.
     */
    public void updateWithTarget(Input input, Player target) {
        moveWithPlayer(input);
        randomMove();
        IMAGE.draw(getX(), getY());

        if(Math.abs(getX() - target.getX()) <= HALF_LENGTH &&
            getY() - target.getY() <= HALF_HEIGHT &&
            getY() - target.getY() >= (HALF_HEIGHT-1)){
            isRise = true;
            target.setOnFlyingPlatform();
        } else if(isRise){
            target.resetOnFlyingPlatform();
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

    /**
     * Method that reset the status of the flying platform
     */
    public void resetIsRise(){
        isRise = false;
    }

}
