package game;

import gameobjects.Background;
import gameobjects.Ball;
import gameobjects.Player;
import gameobjects.weapons.Weapon;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

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
                    
                    if (weapon != null)
                        weapon.update();

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
    
    private final float playerHeight = 130;
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

    private final float bulletSpeed = -5;
    
    private final LinearGradient backgroundColor;
    {
        Stop[] stops = new Stop[] {
            new Stop(0, Color.YELLOW),
            new Stop(1, Color.BLACK)
        };
        
        backgroundColor = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    }
    
    // Game model logic
    private boolean running;
    private final AnimationTimer timer;
    private final Group root;
    private final Scene scene;
    private Background background;
    private Player player = null;
    private Weapon weapon = null;
    private CopyOnWriteArrayList<Ball> balls;
    
    public void setBackground(Background background) {
        this.background = background;
        root.getChildren().add(this.background);
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        root.getChildren().add(this.player);
        player.initializeInScene(playerStartX);
    }
    
    public void setWeapon(Weapon weapon) {
        if (weapon == null) {
            if (this.weapon != null) {
                root.getChildren().remove(this.weapon);
                this.weapon = null;
            }
        }
        else {
            if (this.weapon != null)
                root.getChildren().remove(this.weapon);
            
            this.weapon = weapon;
            root.getChildren().add(this.weapon);
            this.weapon.initializeInScene();
        }
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
    
    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public Player getPlayer() {
        return player;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    public LinearGradient getBackgroundColor() {
        return backgroundColor;
    }
   
}
