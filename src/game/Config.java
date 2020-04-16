package game;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class Config {
    // Game settings
    static final float sceneHeight = 700;
    static final float sceneWidth = 1500;
    
    static final float playerHeight = sceneHeight / 7;
    static final float playerStartX = 100;
    static final float playerSpeed = sceneWidth / 200;
    
    static final String gameName = "Bubble Trouble";
    
    static final float fixBallOffset = sceneHeight / 3;
    static final float ballSizeOffset = sceneHeight / 15;
    static final int startBallSize = 4;
    static final float startBallX = sceneWidth / 2;
    static final float startBallSpeedX = sceneWidth / 300;
    static final float startBallSpeedY = 0;
    static final float startBallRadius = 80;
    static final Color startBallColor = Color.GREEN;
    static final float ballAcceleration = sceneHeight / 100;
    static final float bulletSpeed = -5;
    
    static final float gravity = 0.2f;
    
    static final float singleFallingBonusProb = 0.16f;
    static final float fallingBonusProb = (float) (1 - Math.pow(1 - singleFallingBonusProb, 3));
    static final float dollarSignWidth = 40;
    static final float dollarSignHeight = 60;
    static final float dollarSignSpeed = 0.5f;
    static final Color dollarSignColor = Color.GREEN;
    
    // Score semaphore settings
    static final float scoreSemaphoreX = (float) (0.83 * Config.sceneWidth);
    static final float scoreSemaphoreY = (float) (0.06 * Config.sceneHeight);
    
    static final float lifeIconWidth = 50;
    static final float lifeIconY = 20;
}
