package entities;

import bagel.Image;
import bagel.Input;
import function.CollisionDetector;
import java.util.Properties;

/**
 * Class for the fireball entity.
 */
public class Fireball extends Entity{
    private final double DAMAGE_SIZE;
    private final Image IMAGE;
    private final String POSITION;
    private final String ROLE;
    private boolean killedPlayer = false;
    private boolean killedBoss = false;
    private boolean isCollided = false;
    private final int SPEED;

    /**
     * Constructs an Fireball object with the specified position and properties.
     * @param x The x-coordinate of the Fireball.
     * @param y The y-coordinate of the Fireball.
     * @param position The position of the Fireball.
     * @param ROLE The Role that shoot the Fireball.
     * @param props Properties object used to get the Fireball's properties.
     */
    public Fireball(int x, int y, String position, String ROLE, Properties props){
        super(x, y, Double.parseDouble(props.getProperty("gameObjects.fireball.radius")), 5);
        this.POSITION = position;
        this.DAMAGE_SIZE = Double.parseDouble(props.getProperty("gameObjects.fireball.damageSize"));
        this.IMAGE = new Image(props.getProperty("gameObjects.fireball.image"));
        this.SPEED = Integer.parseInt(props.getProperty("gameObjects.fireball.speed"));
        this.ROLE = ROLE;
    }
    /***
     * Method that updates the Fireball movement and draws it.
     * The fireball will damage enemy base on the Role.
     * @param input detecting keyboard actions.
     * @param player
     * @param boss
     */
    public void update(Input input, Player player, EnemyBoss boss){
        moveFireball();
        moveWithPlayer(input);
        IMAGE.draw(getX(), getY());

        if(CollisionDetector.isCollided(player, getX(), getY(), getRadius()) && ROLE.equals("BOSS") && !player.isInvincible()){
            isCollided = true;
            damagePlayer(player);
        }else if(CollisionDetector.isCollided(boss, getX(), getY(), getRadius()) && ROLE.equals("PLAYER")){
            isCollided = true;
            damageBoss(boss);
        }

    }

    /**
     * Checks if the fireball has collided.
     * @return true if the fireball has collided, false otherwise.
     */
    public boolean isCollided(){
        return isCollided;
    }

    /**
     * Damages the player. Marks the player as dead if its health falls to or below zero.
     * @param player The player to be damaged.
     */
    private void damagePlayer(Player player) {
            double newHealth = player.getHealth() - DAMAGE_SIZE;
            player.setHealth(newHealth);

            if (newHealth <= 0 && !killedPlayer) {
                player.dead();
                killedPlayer = true;
            }
    }

    /**
     * Damages the enemy boss. Marks the boss as dead if its health falls to or below zero.
     * @param boss The enemy boss to be damaged.
     */
    private void damageBoss(EnemyBoss boss) {
        double newHealth = boss.getHealth() - DAMAGE_SIZE;
        boss.setHealth(newHealth);

        if (newHealth <= 0 && !killedBoss) {
            boss.dead();
            killedBoss = true;
        }
    }

    /**
     * Moves the fireball based on its position.
     * If the position is "LEFT", it moves left. If the position is "RIGHT", it moves right.
     */
    private void moveFireball(){
        if(this.POSITION.equals("LEFT")){
            setX(getX() - this.SPEED);
        } else if (this.POSITION.equals("RIGHT")) {
            setX(getX() + this.SPEED);
        }
    }


}
