package game;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class Config {
// GENERAL SETTINGS-------------------------------------------------------------
    static final float SCENE_HEIGHT = 700;
    static final float SCENE_WIDTH = 1500;
    static final String GAME_NAME = "Bubble Trouble";
//------------------------------------------------------------------------------
    
// PLAYER SETTINGS--------------------------------------------------------------
    static final float PLAYER_HEIGHT = SCENE_HEIGHT / 7;
    static final float PLAYER_START_X = 100;
    static final float PLAYER_SPEED = SCENE_WIDTH / 200;
    static final float WEAPON_SPEED = -5;
    static final int STARTING_LIFE_COUNT = 5;
//------------------------------------------------------------------------------
    
// BALL SETTINGS----------------------------------------------------------------
    static final float FIX_BALL_OFFSET = SCENE_HEIGHT / 3;
    static final float BALL_SIZE_OFFSET = SCENE_HEIGHT / 15;
    static final int START_BALL_SIZE = 4;
    static final float START_BALL_X = SCENE_WIDTH / 2;
    static final float START_BALL_SPEED_X = SCENE_WIDTH / 300;
    static final float START_BALL_SPEED_Y = 0;
    static final float START_BALL_RADIUS = 80;
    static final Color START_BALL_COLOR = Color.GREEN;
    static final float BALL_ACCELERATION = SCENE_HEIGHT / 100;
    static final float GRAVITY = 0.2f;
//------------------------------------------------------------------------------
    
// BONUSES SETTINGS-------------------------------------------------------------
    static final float SINGLE_FALLING_BONUS_PROB = 0.16f;
    static final float FALLING_BONUS_PROB = (float) (1 - Math.pow(1 - SINGLE_FALLING_BONUS_PROB, 3));
    static final float BONUS_WIDTH = 40;
    static final float BONUS_HEIGHT = 60;
    static final float BONUS_SPEED = 0.5f;
//------------------------------------------------------------------------------
    
// WRAPPER SETTINGS-------------------------------------------------------------
    static final float SCORE_SEMAPHORE_X = (float) (0.83 * Config.SCENE_WIDTH);
    static final float SCORE_SEMAPHORE_Y = (float) (0.06 * Config.SCENE_HEIGHT);
    
    static final float LIFE_ICON_WIDTH = 50;
    static final float LIFE_ICON_Y = 20;
//------------------------------------------------------------------------------
    
// BACKGRUND SETTINGS----------------------------------------------------------
    static final LinearGradient backgroundColor;
    static final LinearGradient wrapperBackgroundColor;
    
    static {
        Stop[] stops = new Stop[] {
            new Stop(0, Color.YELLOW),
            new Stop(1, Color.BLACK)
        };
        
        backgroundColor = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    }
    static {
        Stop[] stops = new Stop[] {
            new Stop(0, Color.GRAY),
            new Stop(0.2, Color.BLACK),
            new Stop(0.4, Color.GRAY),
            new Stop(0.6, Color.BLACK),
            new Stop(0.8, Color.GRAY),
            new Stop(1, Color.BLACK)
        };
        
        wrapperBackgroundColor = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REPEAT, stops);
    }
//------------------------------------------------------------------------------

}
