package game;

import gameobjects.Background;
import gameobjects.Ball;
import gameobjects.bonuses.FallingBonus;
import gameobjects.LifeIcon;
import gameobjects.Player;
import gameobjects.ScoreSemaphore;
import gameobjects.Timer;
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
// GAME OBJECTS-----------------------------------------------------------------
    private final Group root = new Group();
    private Player player = null;
    private Weapon weapon = null;
    private Background background;
    private Timer gameTimer = null;
    
    private final CopyOnWriteArrayList<Ball> balls = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<FallingBonus> fallingBonuses = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<LifeIcon> lifeIcons = new CopyOnWriteArrayList<>();
//------------------------------------------------------------------------------
    
// GAME WRAPPER-----------------------------------------------------------------
    private final Scene scene;
    private final Group wrapper = new Group();
    private Background wrapperBackground;
    private ScoreSemaphore scoreSemaphore = null;
//------------------------------------------------------------------------------
    
// GAME LOGIC-------------------------------------------------------------------
    private boolean running = true;
    private AnimationTimer timer;
    private int lifeCount;
//------------------------------------------------------------------------------
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private int points = 0;
    
// GRADIENTS - move to Config---------------------------------------------------
    private final LinearGradient backgroundColor;
    private final LinearGradient wrapperBackgroundColor;
    
    {
        Stop[] stops = new Stop[] {
            new Stop(0, Color.YELLOW),
            new Stop(1, Color.BLACK)
        };
        
        backgroundColor = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    }
    
    {
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
    
// SINGLETON--------------------------------------------------------------------
    private static GameModel instance = null; 
    
    private GameModel() {
        root.setScaleX(0.8);
        root.setScaleY(0.8);
        wrapper.getChildren().add(root);
        scene = new Scene(wrapper, Config.sceneWidth, Config.sceneHeight);
        
        createAnimaTimer();
    }
    
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        
        return instance;
    }
//------------------------------------------------------------------------------
  
// TIMER------------------------------------------------------------------------
    private void createAnimaTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    if (player != null)
                        player.update();
                    
                    if (weapon != null)
                        weapon.update();

                    if (gameTimer != null)
                        gameTimer.update();
                    
                    balls.forEach((ball) -> {
                        ball.update();
                    });
                    
                    fallingBonuses.forEach((fallingBonus) -> {
                        fallingBonus.update();
                    });
                }
            }
        };
    }
    
    public void start() {
        timer.start();
    }
//------------------------------------------------------------------------------   
    public void addMoreTime(int sec) {
        gameTimer.addMoreTime(sec);
    }

    public void shieldPlayer() {
        if (player != null)
            player.shieldPlayer();
    }
    
    public void setGameTimer(Timer gameTimer) {
        this.gameTimer = gameTimer;
        wrapper.getChildren().add(this.gameTimer);
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
        wrapper.getChildren().add(this.scoreSemaphore);
    }
    
    public void setBackground(Background background) {
        this.background = background;
        root.getChildren().add(this.background);
    }
    
    public void setWrapperBackground(Background wrapperBackground) {
        this.wrapperBackground = wrapperBackground;
        wrapper.getChildren().add(0, wrapperBackground);
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        root.getChildren().add(this.player);
        player.initializeInScene(Config.playerStartX);
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
    
    public void addFallingBonus(FallingBonus fallingBonus) {
        fallingBonuses.add(fallingBonus);
        root.getChildren().add(fallingBonus);
        fallingBonus.initializeInScene();
    }
    
    public void removeFallingBonus(FallingBonus fallingBonus) {
        root.getChildren().remove(fallingBonus);
        fallingBonuses.remove(fallingBonus);
    }
    
    
// GAME CONTROLS----------------------------------------------------------------
    public void gameWon() {
        Text text = new Text();
        text.setText("You won!");
        
        text.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setFill(Color.GREENYELLOW);
        
        text.setX(0.35 * Config.sceneWidth); 
        text.setY(0.6 * Config.sceneHeight); 
        root.getChildren().add(text);
                
        running = false;
    }
    
    public void gameLost() {
        Text text = new Text();
        text.setText("You lost!");
        
        text.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setFill(Color.RED);
        
        text.setX(0.35 * Config.sceneWidth); 
        text.setY(0.6 * Config.sceneHeight); 
        root.getChildren().add(text);
                
        running = false;
    }
    
    public boolean noMoreBalls() {
        return (balls.isEmpty());
    }
    
    public void setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
        
        float offset = 0;
        for (int i = 0; i < this.lifeCount; i++) {
            LifeIcon newLifeIcon = new LifeIcon(offset, Config.lifeIconY, Config.lifeIconWidth);
            offset += Config.lifeIconWidth * 1.4;
            
            lifeIcons.add(newLifeIcon);
            wrapper.getChildren().add(newLifeIcon);
        }
    }
    
    public void loseLife() {
        this.lifeCount--;
        if (this.lifeCount == 0)
            this.gameLost();
        
        LifeIcon toRemove = lifeIcons.get(this.lifeCount);
        lifeIcons.remove(toRemove);
        wrapper.getChildren().remove(toRemove);
    }
//------------------------------------------------------------------------------
    
    
// GETTERS----------------------------------------------------------------------
    public float getMaxBallHeight(int size) {
        return Config.fixBallOffset + size * Config.ballSizeOffset;
    }
    
    public float getBallSizeOffset() {
        return Config.ballSizeOffset;
    }

    public float getGravity() {
        return Config.gravity;
    }
    
    public int getPoints() {
        return points;
    }
    
    public String getGameName() {
        return Config.gameName;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public float getSceneHeight() {
        return Config.sceneHeight;
    }

    public float getSceneWidth() {
        return Config.sceneWidth;
    }

    public float getPlayerHeight() {
        return Config.playerHeight;
    }

    public float getPlayerStartX() {
        return Config.playerStartX;
    }

    public float getPlayerSpeed() {
        return Config.playerSpeed;
    }
    
    public Color getStartBallColor() {
        return Config.startBallColor;
    }
    
    public float getStartBallX() {
        return Config.startBallX;
    }
    
    public float getStartBallSpeedX() {
        return Config.startBallSpeedX;
    }

    public float getStartBallSpeedY() {
        return Config.startBallSpeedY;
    }

    public float getStartBallRadius() {
        return Config.startBallRadius;
    }   
    
    public float getBulletSpeed() {
        return Config.bulletSpeed;
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
    
    public LinearGradient getWrapperBackgroundColor() {
        return wrapperBackgroundColor;
    }
   
    public int getStartBallSize() {
        return Config.startBallSize;
    }
    
    public float getFallingBonusProb() {
        return Config.fallingBonusProb;
    }

    public float getDollarSignWidth() {
        return Config.dollarSignWidth;
    }

    public float getDollarSignHeight() {
        return Config.dollarSignHeight;
    }

    public float getDollarSignSpeed() {
        return Config.dollarSignSpeed;
    }
    
    public Color getDollarSignColor() {
        return Config.dollarSignColor;
    }
 
    public float getScoreSemaphoreX() {
        return Config.scoreSemaphoreX;
    }

    public float getScoreSemaphoreY() {
        return Config.scoreSemaphoreY;
    }
    
    public float getBallAcceleration() {
        return Config.ballAcceleration;
    }
//------------------------------------------------------------------------------
    
}
