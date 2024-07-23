package message;

import entities.*;
import bagel.Font;
import java.util.Properties;

/**
 * This class represents the player health display in the game.
 * It handles rendering the health of the player on the screen.
 */
public class PlayerHealth extends Text{
    private final String PLAYER_HEALTH;
    private final Font PLAYER_HEALTH_FONT;
    private final int PLAYER_HEALTH_X;
    private final int PLAYER_HEALTH_Y;

    /**
     * Constructs a PlayerHealth object with the specified game and message properties.
     * @param game_props Properties object used to get the game's settings.
     * @param message_props Properties object used to get the message settings.
     */
    public PlayerHealth(Properties game_props, Properties message_props){
        PLAYER_HEALTH = message_props.getProperty("health");
        PLAYER_HEALTH_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("playerHealth.fontSize")));
        PLAYER_HEALTH_X = Integer.parseInt(game_props.getProperty("playerHealth.x"));
        PLAYER_HEALTH_Y = Integer.parseInt(game_props.getProperty("playerHealth.y"));
    }

    /**
     * Renders the boss's health on the screen.
     * @param player Player object
     */
    public void render(Player player){
        PLAYER_HEALTH_FONT.drawString(PLAYER_HEALTH + Math.round(player.getHealth()*100),
                PLAYER_HEALTH_X, PLAYER_HEALTH_Y);
    }
}
