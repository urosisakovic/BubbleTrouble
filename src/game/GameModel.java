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
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameModel {
// GAME OBJECTS-----------------------------------------------------------------
    private final Group root = new Group();
    private Background background = null;
    private Player player = null;
    private Weapon weapon = null;
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
    private int points = 0;
//------------------------------------------------------------------------------
    
// SINGLETON--------------------------------------------------------------------
    private static GameModel instance = null; 
    
    private GameModel() {
        root.setScaleX(0.8);
        root.setScaleY(0.8);
        wrapper.getChildren().add(root);
        scene = new Scene(wrapper, Config.SCENE_WIDTH, Config.SCENE_HEIGHT);
        
        createAnimationTimer();
    }
    
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        
        return instance;
    }
//------------------------------------------------------------------------------
  
// TIMER------------------------------------------------------------------------
    private void createAnimationTimer() {
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
 
// ADD AND REMOVE GAME OBJECTS---------------------------------------------------
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
        wrapper.getChildren().add(0, this.wrapperBackground);
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        root.getChildren().add(this.player);
        player.initializeInScene(Config.PLAYER_START_X);
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
//------------------------------------------------------------------------------
    
// GAME CONTROLS----------------------------------------------------------------
    public void gameWon() {
        Text text = new Text();
        text.setText("You won!");
        
        text.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setFill(Color.GREENYELLOW);
        
        text.setX(0.35 * Config.SCENE_WIDTH); 
        text.setY(0.6 * Config.SCENE_HEIGHT); 
        root.getChildren().add(text);
                
        running = false;
    }
    
    public void gameLost() {
        Text text = new Text();
        text.setText("You lost!");
        
        text.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 80));
        text.setFill(Color.RED);
        
        text.setX(0.35 * Config.SCENE_WIDTH); 
        text.setY(0.6 * Config.SCENE_HEIGHT); 
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
            LifeIcon newLifeIcon = new LifeIcon(offset, Config.LIFE_ICON_Y, Config.LIFE_ICON_WIDTH);
            offset += Config.LIFE_ICON_WIDTH * 1.4;
            
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
    
    public void setGameTimer(Timer gameTimer) {
        this.gameTimer = gameTimer;
        wrapper.getChildren().add(this.gameTimer);
    }
    
    public void addMoreTime(int sec) {
        gameTimer.addMoreTime(sec);
    }

    public void shieldPlayer() {
        if (player != null)
            player.shieldPlayer();
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
//------------------------------------------------------------------------------
    
    
// GETTERS----------------------------------------------------------------------
    public float getMaxBallHeight(int size) {
        return Config.FIX_BALL_OFFSET + size * Config.BALL_SIZE_OFFSET;
    }
    
    public float getBallSizeOffset() {
        return Config.BALL_SIZE_OFFSET;
    }

    public float getGravity() {
        return Config.GRAVITY;
    }
    
    public int getPoints() {
        return points;
    }
    
    public String getGameName() {
        return Config.GAME_NAME;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public float getSceneHeight() {
        return Config.SCENE_HEIGHT;
    }

    public float getSceneWidth() {
        return Config.SCENE_WIDTH;
    }

    public float getPlayerHeight() {
        return Config.PLAYER_HEIGHT;
    }

    public float getPlayerStartX() {
        return Config.PLAYER_START_X;
    }

    public float getPlayerSpeed() {
        return Config.PLAYER_SPEED;
    }
    
    public Color getStartBallColor() {
        return Config.START_BALL_COLOR;
    }
    
    public float getStartBallX() {
        return Config.START_BALL_X;
    }
    
    public float getStartBallSpeedX() {
        return Config.START_BALL_SPEED_X;
    }

    public float getStartBallSpeedY() {
        return Config.START_BALL_SPEED_Y;
    }

    public float getStartBallRadius() {
        return Config.START_BALL_RADIUS;
    }   
    
    public float getWeaponSpeed() {
        return Config.WEAPON_SPEED;
    }

    public Player getPlayer() {
        return player;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    public LinearGradient getBackgroundColor() {
        return Config.backgroundColor;
    }
    
    public LinearGradient getWrapperBackgroundColor() {
        return Config.wrapperBackgroundColor;
    }
   
    public int getStartBallSize() {
        return Config.START_BALL_SIZE;
    }
    
    public float getFallingBonusProb() {
        return Config.FALLING_BONUS_PROB;
    }

    public float getDollarSignWidth() {
        return Config.BONUS_WIDTH;
    }

    public float getDollarSignHeight() {
        return Config.BONUS_HEIGHT;
    }

    public float getDollarSignSpeed() {
        return Config.BONUS_SPEED;
    }
 
    public float getScoreSemaphoreX() {
        return Config.SCORE_SEMAPHORE_X;
    }

    public float getScoreSemaphoreY() {
        return Config.SCORE_SEMAPHORE_Y;
    }
    
    public float getBallAcceleration() {
        return Config.BALL_ACCELERATION;
    }
//------------------------------------------------------------------------------
    
}
