package message;

import function.*;

/**
 * Abstract class representing a text entity in the game.
 * Provides a font file property for derived classes.
 */
abstract class Text {
    public static final String FONT_FILE =  IOUtils.readPropertiesFile("res/app.properties").getProperty("font");
}
