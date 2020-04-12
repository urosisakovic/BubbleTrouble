package game;

import gameobjects.Background;
import gameobjects.Ball;
import gameobjects.Player;
import gameobjects.Weapon;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;

public class GameModel {

    // Singleton fields and methods
    private static GameModel instance = null; 
    
    private GameModel() {
        root = new Group();
        scene = new Scene(root, sceneWidth, sceneHeight);
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                player.update();
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
    
    private final String gameName = "Bubble Trouble";

    // Game model logic
    private final AnimationTimer timer;
    private final Group root;
    private final Scene scene;
    private Background background;
    private Player player;
    
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
}
