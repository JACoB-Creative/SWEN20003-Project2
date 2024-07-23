package message;

import java.util.Properties;
import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;
import entities.*;

/**
 * Represents the boss health display in the game.
 * Handles rendering the health of the boss on the screen.
 */
public class BossHealth extends Text{
    private final String BOSS_HEALTH;
    private final Font BOSS_HEALTH_FONT;
    private final int BOSS_HEALTH_X;
    private final int BOSS_HEALTH_Y;

    /**
     * Constructs a BossHealth object with the specified game and message properties.
     * @param game_props Properties object used to get the game's settings.
     * @param message_props Properties object used to get the message settings.
     */
    public BossHealth(Properties game_props, Properties message_props){
        BOSS_HEALTH = message_props.getProperty("health");
        BOSS_HEALTH_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize")));
        BOSS_HEALTH_X = Integer.parseInt(game_props.getProperty("enemyBossHealth.x"));
        BOSS_HEALTH_Y = Integer.parseInt(game_props.getProperty("enemyBossHealth.y"));
    }

    /**
     * Renders the boss's health on the screen.
     * @param boss EnemyBoss object
     */
    public void render(EnemyBoss boss){
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setBlendColour(Colour.RED);
        BOSS_HEALTH_FONT.drawString( BOSS_HEALTH + Math.round(boss.getHealth()*100),
                BOSS_HEALTH_X, BOSS_HEALTH_Y, drawOptions);
    }
}
