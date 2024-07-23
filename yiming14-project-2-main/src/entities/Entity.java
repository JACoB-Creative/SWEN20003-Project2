package entities;

import bagel.Input;
import bagel.Keys;

/**
 * Abstract class representing a generic entity in the game.
 * Handles basic movement and position properties.
 */
public abstract class Entity {
    private final int SPEED_X;
    private final double RADIUS;
    private int x;
    private int y;
    private final int COLLISION_SPEED = -10;
    protected Entity(int x, int y, double RADIUS, int SPEED_X){
        this.x = x;
        this.y = y;
        this.RADIUS = RADIUS;
        this.SPEED_X = SPEED_X;
    }

    /**
     * Moves the entity based on player input.
     * @param input detecting keyboard actions.
     */
    protected void moveWithPlayer(Input input){
        if (input.isDown(Keys.RIGHT)) {
            this.x -= SPEED_X;
        } else if (input.isDown(Keys.LEFT)) {
            this.x += SPEED_X;
        }
    }

    /**
     * Moves the entity after a collision.
     */
    public void moveAfterCollision(){
        this.y += COLLISION_SPEED;
    }

    /**
     * Sets the x-coordinate of the entity.
     * @param x The new x-coordinate.
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the entity.
     * @param y The new y-coordinate.
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the entity.
     * @return The current x-coordinate.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Gets the y-coordinate of the entity.
     * @return The current y-coordinate.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Gets the radius of the entity's collision boundary.
     * @return The radius of the collision boundary.
     */
    public double getRadius() {
        return this.RADIUS;
    }
}
