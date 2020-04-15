package game;

import gameobjects.Background;
import gameobjects.Ball;
import gameobjects.DollarSign;
import gameobjects.Player;
import gameobjects.ScoreSemaphore;
import gameobjects.weapons.Weapon;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameModel {

    // Singleton fields and methods
    private static GameModel instance = null; 
    
    private GameModel() {
        running = true;
        balls = new CopyOnWriteArrayList<>();
        dollarSigns = new CopyOnWriteArrayList<>();
        
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
                    
                    dollarSigns.forEach((dollarSign) -> {
                        dollarSign.update();
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
    private final float sceneHeight = 700;
    private final float sceneWidth = 1500;
    
    private final float playerHeight = sceneHeight / 7;
    private final float playerStartX = 100;
    private final float playerSpeed = sceneWidth / 200;
    
    private final String gameName = "Bubble Trouble";
    
    // Ball settings
    private final float fixBallOffset = sceneHeight / 3;
    private final float ballSizeOffset = sceneHeight / 15;
    private final int startBallSize = 4;
    private final float startBallX = sceneWidth / 2;
    private final float startBallSpeedX = sceneWidth / 300;
    private final float startBallSpeedY = 0;
    private final float startBallRadius = 80;
    private final Color startBallColor = Color.GREEN;
    private final float ballAcceleration = sceneHeight / 100;
    private final float bulletSpeed = -5;
    private final LinearGradient backgroundColor;
    private final float gravity = 0.2f;

    public float getMaxBallHeight(int size) {
        return fixBallOffset + size * ballSizeOffset;
    }
    
    public float getBallSizeOffset() {
        return ballSizeOffset;
    }

    public float getGravity() {
        return gravity;
    }
    {
        Stop[] stops = new Stop[] {
            new Stop(0, Color.YELLOW),
            new Stop(1, Color.BLACK)
        };
        
        backgroundColor = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    }
    
    // Dollar signs settings
    private final float dollarSignProb = 0.16f;
    private final float dollarSignWidth = 40;
    private final float dollarSignHeight = 60;
    private final float dollarSignSpeed = 0.5f;
    private final Color dollarSignColor = Color.GREEN;
    
    // Score semaphore settings
    private final float scoreSemaphoreX = (float) (0.78 * sceneWidth);
    private final float scoreSemaphoreY = (float) (0.08 * sceneHeight);
    
    // Game model logic
    private boolean running;
    private final AnimationTimer timer;
    private final Group root;
    private final Scene scene;
    private Background background;
    private Player player = null;
    private Weapon weapon = null;
    private CopyOnWriteArrayList<Ball> balls;
    private CopyOnWriteArrayList<DollarSign> dollarSigns;
    private ScoreSemaphore scoreSemaphore = null;
    
    private int points = 0;
    public int getPoints() {
        return points;
    }
    
    public void incrementPoints(int inc) {
        points += inc;
        if (scoreSemaphore != null)
            scoreSemaphore.setPoints(points);
    }
    
    public void doublePoints() {
        points *= 2;
        if (scoreSemaphore != null)
            scoreSemaphore.setPoints(points);
    }
    
    public void setScoreSemaphore(ScoreSemaphore scoreSemaphore) {
        this.scoreSemaphore = scoreSemaphore;
        root.getChildren().add(this.scoreSemaphore);
    }
    
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
    
    public void removeBall(Ball ball) {
        root.getChildren().remove(ball);
        balls.remove(ball);
    }
    
    public void addDollarSign(DollarSign dollarSign) {
        dollarSigns.add(dollarSign);
        root.getChildren().add(dollarSign);
        dollarSign.initializeInScene();
    }
    
    public void removeDollarSign(DollarSign dollarSign) {
        root.getChildren().remove(dollarSign);
        dollarSigns.remove(dollarSign);
    }
    
    public boolean noMoreBalls() {
        return (balls.isEmpty());
    }
    
    public void gameWon() {
        Text text = new Text();
        text.setText("You won!");
        
        text.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setFill(Color.GREENYELLOW);
        
        text.setX(0.25 * sceneWidth); 
        text.setY(0.5 * sceneHeight); 
        root.getChildren().add(text);
                
        running = false;
    }
    
    public void gameLost() {
        Text text = new Text();
        text.setText("You lost!");
        
        text.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setFill(Color.RED);
        
        text.setX(0.25 * sceneWidth); 
        text.setY(0.5 * sceneHeight); 
        root.getChildren().add(text);
                
        running = false;
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
   
    public int getStartBallSize() {
        return startBallSize;
    }
    
    public float getDollarSignProb() {
        return dollarSignProb;
    }

    public float getDollarSignWidth() {
        return dollarSignWidth;
    }

    public float getDollarSignHeight() {
        return dollarSignHeight;
    }

    public float getDollarSignSpeed() {
        return dollarSignSpeed;
    }
    
    public Color getDollarSignColor() {
        return dollarSignColor;
    }
 
    public float getScoreSemaphoreX() {
        return scoreSemaphoreX;
    }

    public float getScoreSemaphoreY() {
        return scoreSemaphoreY;
    }
    
    public float getBallAcceleration() {
        return ballAcceleration;
    }
    
}
