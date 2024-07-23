package message;

import bagel.Font;
import entities.Player;
import java.util.Properties;

/**
 * Represents the score display in the game.
 * Handles rendering the player's score on the screen.
 */
public class Score extends Text{
    private final String SCORE;
    private final Font SCORE_FONT;
    private final int SCORE_X;
    private final int SCORE_Y;

    /**
     * Constructs a Score object with the specified game and message properties.
     * @param game_props Properties object used to get the game's settings.
     * @param message_props Properties object used to get the message settings.
     */
    public Score(Properties game_props, Properties message_props){
        SCORE = message_props.getProperty("score");
        SCORE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("score.fontSize")));
        SCORE_X = Integer.parseInt(game_props.getProperty("score.x"));
        SCORE_Y = Integer.parseInt(game_props.getProperty("score.y"));
    }

    /**
     * Renders the player's score on the screen.
     * @param player The Player whose score is to be displayed.
     */
    public void render(Player player){
        SCORE_FONT.drawString( SCORE + player.getScore(), SCORE_X, SCORE_Y);
    }
}
