package message;

import bagel.Font;
import bagel.Window;
import java.util.Properties;

/**
 * Represents game messages for winning or losing.
 * Handles rendering these messages on the screen.
 */
public class Message extends Text{
    private final String GAME_WON;
    private final String GAME_OVER;
    private final Font MESSAGE_FONT;
    private final int MESSAGE_Y;

    /**
     * Constructs a Message object with the specified game and message properties.
     * @param game_props Properties object used to get the game's settings.
     * @param message_props Properties object used to get the message settings.
     */
    public Message(Properties game_props, Properties message_props){
        GAME_WON = message_props.getProperty("gameWon");
       GAME_OVER = message_props.getProperty("gameOver");
        MESSAGE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("message.fontSize")));
        MESSAGE_Y = Integer.parseInt(game_props.getProperty("message.y"));
    }

    /**
     * Renders Game Won message on the screen.
     */
    public void renderWon(){
        MESSAGE_FONT.drawString(GAME_WON,
                Window.getWidth() / 2 - MESSAGE_FONT.getWidth(GAME_WON)/2,
                MESSAGE_Y);
    }

    /**
     * Renders Game Over message on the screen.
     */
    public void renderOver(){
        MESSAGE_FONT.drawString(GAME_OVER,
                Window.getWidth() / 2 - MESSAGE_FONT.getWidth(GAME_OVER)/2,
                MESSAGE_Y);
    }
}
