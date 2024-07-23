package function;
import entities.*;


/**
 * Class that handles the collision detection.
 */
public class CollisionDetector {

    /**
     * Checks for a collision between two entities.
     * @param entity The entity to check for collision.
     * @param x The x-coordinate of the given entity's position.
     * @param y The y-coordinate of the given entity's position.
     * @param radius The radius of the given entity's collision boundary.
     * @return true if there is a collision, false otherwise.
     */
    public static boolean isCollided(Entity entity, int x, int y, double radius) {
        return Math.sqrt(Math.pow(entity.getX() - x, 2) +
                Math.pow(entity.getY() - y, 2)) <= entity.getRadius() + radius;
    }
}

