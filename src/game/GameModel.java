package game;

import gameobjects.Background;
import gameobjects.Ball;
import gameobjects.Player;
import gameobjects.Weapon;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class GameModel {

    // Singleton fields and methods
    private static GameModel instance = null; 
    
    private GameModel() {
        running = true;
        balls = new CopyOnWriteArrayList<>();
        
        root = new Group();
        scene = new Scene(root, sceneWidth, sceneHeight);
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    if (player != null)
                        player.update();

                    balls.forEach((ball) -> {
                        ball.update();
                    });
                }
            }
        };
    }
    
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        
        return instance;
    }
    
    // Game settings
    private final float sceneHeight = 600;
    private final float sceneWidth = 1000;
    
    private final float playerHeight = 100;
    private final float playerStartX = 100;
    private final float playerSpeed = 2;
    
    private final String gameName = "Bubble Trouble";
    
    // Starting ball settins
    private final float startBallX = 100;
    private final float startBallY = 100;
    private final float startBallSpeedX = 2;
    private final float startBallSpeedY = 2;
    private final float startBallRadius = 100;
    private final Color startBallColor = Color.GREEN;

    // Game model logic
    private boolean running;
    private final AnimationTimer timer;
    private final Group root;
    private final Scene scene;
    private Background background;
    private Player player = null;
    private CopyOnWriteArrayList<Ball> balls;
    
    public void addBackground(Background background) {
        this.background = background;
        root.getChildren().add(this.background);
    }
    
    public void addPlayer(Player player) {
        this.player = player;
        root.getChildren().add(this.player);
        player.initializeInScene(playerStartX);
    }
    
    public void addWeapon(Weapon weapon) {
        
    }
    
    public void addBall(Ball ball) {
        balls.add(ball);
        root.getChildren().add(ball);
        ball.initializeInScene();
    }
    
    public void gameEnd() {
        running = false;
    }
    
    public void reset() {
        // reset state
        running = true;
    }
    
    public String getGameName() {
        return gameName;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public void start() {
        timer.start();
    }
    
    public float getSceneHeight() {
        return sceneHeight;
    }

    public float getSceneWidth() {
        return sceneWidth;
    }

    public float getPlayerHeight() {
        return playerHeight;
    }

    public float getPlayerStartX() {
        return playerStartX;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }
    
    public Color getStartBallColor() {
        return startBallColor;
    }
    
    public float getStartBallX() {
        return startBallX;
    }

    public float getStartBallY() {
        return startBallY;
    }

    public float getStartBallSpeedX() {
        return startBallSpeedX;
    }

    public float getStartBallSpeedY() {
        return startBallSpeedY;
    }

    public float getStartBallRadius() {
        return startBallRadius;
    }
   
}
