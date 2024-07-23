package message;

import bagel.Font;
import bagel.Window;
import java.util.Properties;

/**
 * Represents the start screen of the game.
 * Handles rendering the title and instructions on the screen.
 */
public class StartScreen extends Text{
    private final Font TITLE_FONT;
    private final String TITLE;
    private final int TITLE_X;
    private final int TITLE_Y;
    private final Font INSTRUCTION_FONT;
    private final String INSTRUCTION;
    private final int INS_Y;

    /**
     * Constructs a StartScreen object with the specified game and message properties.
     * @param game_props Properties object used to get the game's settings.
     * @param message_props Properties object used to get the message settings.
     */
    public StartScreen(Properties game_props, Properties message_props){
        TITLE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("title.fontSize")));
        TITLE = message_props.getProperty("title");
        TITLE_X = Integer.parseInt(game_props.getProperty("title.x"));
        TITLE_Y = Integer.parseInt(game_props.getProperty("title.y"));
        INSTRUCTION_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        INSTRUCTION = message_props.getProperty("instruction");
        INS_Y = Integer.parseInt(game_props.getProperty("instruction.y"));
    }

    /**
     * Renders the title and instructions on the screen.
     */
    public void render(){
        TITLE_FONT.drawString(TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION,
                Window.getWidth() / 2 - INSTRUCTION_FONT.getWidth(INSTRUCTION)/2, INS_Y);
    }
}