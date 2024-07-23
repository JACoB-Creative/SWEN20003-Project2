import bagel.*;
import function.IOUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Iterator;

import message.*;
import entities.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 *
 * Please enter your name below
 * @YIMING LI
 */
public class ShadowMario extends AbstractGame {

    private final int WINDOW_HEIGHT;
    private final Image BACKGROUND_IMAGE;
    private final Properties PROPS;
    private boolean finished = false;
    private boolean started = false;

    //message
    private final StartScreen STARTSCREEN;
    private final Message MESSAGE;
    private final Score SCORE;
    private final PlayerHealth PLAYERHEALTH;
    private final BossHealth BOSSHEALTH;

    //entities
    private Player player;
    private EnemyBoss boss;
    private Platform platform;
    private EndFlag endFlag;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Coin> coins = new ArrayList<>();
    private List<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    private List<DoubleScorePower> doubleScorePowers = new ArrayList<>();
    private List<InvinciblePower> invinciblePowers = new ArrayList<>();
    private List<Fireball> fireballs = new ArrayList<>();

    /**
     * Constructs a ShadowMario object with the specified game and message properties.
     * @param game_props Properties object used to get the game's settings.
     * @param message_props Properties object used to get the message settings.
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        WINDOW_HEIGHT = Integer.parseInt(game_props.getProperty("windowHeight"));
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        this.PROPS = game_props;

        //message
        STARTSCREEN = new StartScreen(game_props, message_props);
        MESSAGE = new Message(game_props, message_props);
        SCORE = new Score(game_props, message_props);
        PLAYERHEALTH = new PlayerHealth(game_props, message_props);
        BOSSHEALTH = new BossHealth(game_props, message_props);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update of the selected level.
     * Allows the game to exit when the escape key is pressed.
     * Handle screen navigation between levels and instruction pages here.
     */
    @Override
    protected void update(Input input) {

        // close window
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        //game start
        if (!started) {
            //instruction part
            STARTSCREEN.render();

            if (input.wasPressed(Keys.NUM_1) ||
                input.wasPressed(Keys.NUM_2) ||
                input.wasPressed(Keys.NUM_3)) {
                started = true;
                finished = false;
                //initialize entities in selected level
                if(input.wasPressed(Keys.NUM_1)) {
                    String[][] lines = IOUtils.readCsv(this.PROPS.getProperty("level1File"));
                    populateGameObjects(lines);
                }else if(input.wasPressed(Keys.NUM_2)){
                    String[][] lines = IOUtils.readCsv(this.PROPS.getProperty("level2File"));
                    populateGameObjects(lines);
                }else if(input.wasPressed(Keys.NUM_3)) {
                    String[][] lines = IOUtils.readCsv(this.PROPS.getProperty("level3File"));
                    populateGameObjects(lines);
                }
            }
        //player dead
        } else if (player.isDead() && player.getY() > WINDOW_HEIGHT) {
            //lose the game
            MESSAGE.renderOver();
            if (input.wasPressed(Keys.SPACE)) {
                //restart the game
                started = false;
            }
        } else {
            if (finished) {
                //win the game
                MESSAGE.renderWon();
                if(input.wasPressed(Keys.SPACE)) {
                    //restart the game
                    started = false;
                }
            } else {
                // game is running
                SCORE.render(player);
                PLAYERHEALTH.render(player);

                //if the boss exist
                if(boss != null) {
                    BOSSHEALTH.render(boss);

                    //player able to shoot fireball
                    if(input.wasPressed(Keys.S)){
                        fireballs.add(new Fireball(player.getX(), player.getY(), player.getPosition(), player.getROLE(), this.PROPS));
                    }
                }
                updateGameObjects(input);
            }
        }
    }


    /**
     * Method that creates the game objects using the lines read from the CSV file.
     * @param lines A 2D array where each sub-array contains:
     *              - [0] The type of game object
     *              - [1] The x-coordinate
     *              - [2] The y-coordinate
     */
    private void populateGameObjects(String[][] lines) {
        resetObject();

        for(String[] lineElement: lines) {
            int x = Integer.parseInt(lineElement[1]);
            int y = Integer.parseInt(lineElement[2]);

            if (lineElement[0].equals("PLAYER")) {
                player = new Player(x, y, this.PROPS);
            } else if (lineElement[0].equals("PLATFORM")) {
                platform = new Platform(x, y, this.PROPS);
            } else if (lineElement[0].equals("ENEMY")) {
                enemies.add(new Enemy(x, y, this.PROPS));
            } else if (lineElement[0].equals("COIN")) {
                coins.add(new Coin(x, y, this.PROPS));
            } else if (lineElement[0].equals("END_FLAG")) {
                endFlag = new EndFlag(x, y, this.PROPS);
            } else if(lineElement[0].equals("FLYING_PLATFORM")){
                flyingPlatforms.add(new FlyingPlatform(x, y, this.PROPS));
            } else if(lineElement[0].equals("DOUBLE_SCORE")){
                doubleScorePowers.add(new DoubleScorePower(x, y, this.PROPS));
            } else if(lineElement[0].equals("INVINCIBLE_POWER")){
                invinciblePowers.add(new InvinciblePower(x, y, this.PROPS));
            } else if(lineElement[0].equals("ENEMY_BOSS")){
                boss = new EnemyBoss(x, y, this.PROPS);
            }
        }
    }

    /**
     * Method that updates the game objects each frame, when the game is running.
     * @param input detecting keyboard actions.
     */
    public void updateGameObjects(Input input) {

        platform.updateWithTarget(input);

        for(Enemy e: enemies) {
            e.updateWithTarget(input, player);
        }

        for(Coin c: coins) {
            c.updateWithTarget(input, player);
        }

        for(FlyingPlatform f: flyingPlatforms){
            f.updateWithTarget(input, player);
        }

        for(DoubleScorePower d: doubleScorePowers){
            d.updateWithTarget(input, player);
        }

        for(InvinciblePower i: invinciblePowers){
            i.updateWithTarget(input, player);
        }

        for(Fireball f: fireballs) {
            f.update(input, player, boss);
        }

        player.update(input);
        endFlag.updateWithTarget(input, player);

        if(boss != null) {
            boss.updateWithTarget(input, player, this.PROPS,fireballs);
        }

        //make collided fireball disappear
        if(fireballs != null) {
            removeCollidedFireballs(fireballs);
        }

        // Reset flying platforms status if player is on the ground
        if(player.getInitialY() == player.getGROUND_Y()){
            for(FlyingPlatform f: flyingPlatforms){
                f.resetIsRise();
            }
        }

        if(endFlag.isCollided()) {
            if(boss == null) {
                finished = true;
            } else {
                if(boss.isDead()){
                    finished = true;
                }
            }
        }
    }

    /**
     * Removes fireballs from the list that have collided.
     * @param fireballs The list of fireballs to check for collisions
     */
    public void removeCollidedFireballs(List<Fireball> fireballs){
        Iterator<Fireball> iterator = fireballs.iterator();
        while (iterator.hasNext()) {
            Fireball f = iterator.next();
            if (f.isCollided()) {
                iterator.remove();
            }
        }
    }

    /**
     * Resets all game objects.
     */
    public void resetObject(){
        player = null;
        platform = null;
        endFlag = null;
        boss = null;
        enemies.clear();
        coins.clear();
        flyingPlatforms.clear();
        invinciblePowers.clear();
        doubleScorePowers.clear();
        fireballs.clear();;
    }


}
