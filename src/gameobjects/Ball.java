package gameobjects;

import gameobjects.bonuses.DollarSignBonus;
import game.GameModel;
import gameobjects.bonuses.ShieldBonus;
import gameobjects.bonuses.TimerBonus;
import gameobjects.weapons.Weapon;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball extends MovingGameObject {

    protected float radius;
    protected Color color;
    
    private float sceneHeight;
    private float sceneWidth;
    private float maxX;
    private float minX;
    private float maxY;
    private float minY;
    
    private int size;
    
    protected boolean canKill = false;
        
// CONSTRUCTORS-----------------------------------------------------------------
    public Ball(float x, float y, float speedX, float speedY, float radius, Color color, int size) {
        super(x, y, speedX, speedY);
        this.radius = radius;
        this.color = color;
        this.size = size;
                
        draw();
    }
    
    public Ball(float x, float y, float speedX, float speedY, float radius, Color color) {
        this(x, y, speedX, speedY, radius, color, GameModel.getInstance().getStartBallSize());
    }
    
    public Ball(float x, float y, float radius, Color color) {
        this(x, y, 0, 0, radius, color);
    }
    
    
    public Ball(float radius, Color color) {
        this(0, 0, 0, 0, radius, color);
    }
//------------------------------------------------------------------------------
    
// INIT, DRAW, UPDATE-----------------------------------------------------------
    @Override
    protected void draw() {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        
        this.getChildren().add(circle);
    }
    
    @Override
    public void initializeInScene() {
        sceneHeight = GameModel.getInstance().getSceneHeight();
        sceneWidth = GameModel.getInstance().getSceneWidth();
        minX = radius;
        
        maxX = sceneWidth - radius;
        minY = radius;
        maxY = sceneHeight - radius;
    }
    
    @Override
    public void update() {
        if (canKill) {
            super.update();
            speedY += GameModel.getInstance().getGravity();
        }
        else {
            super.update();
            speedY += GameModel.getInstance().getGravity();
            if (speedY >= 0) {
                canKill = true;
            }
        }
    }
//------------------------------------------------------------------------------
    
// PRIVATE COLLISION HANDLING HELPER METHODS------------------------------------
    protected boolean intersects(Shape shape) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setCenterX(getX());
        circle.setCenterY(getY());
        
        Shape intersection = Shape.intersect(shape, circle);
        return intersection.getBoundsInLocal().getWidth() > 0;
    }
    
    private Color differentColor(Color color) {
        Color[] colors = new Color[] {
            Color.RED,
            Color.AQUAMARINE,
            Color.AQUA,
            Color.CHOCOLATE,
            Color.GREENYELLOW,
            Color.BLUE,
            Color.PURPLE,
            Color.PINK,
            Color.LIGHTGREY
        };
        
        Random random = new Random();
        Color diffColor;
        while (true) {
            diffColor = colors[Math.abs(random.nextInt()) % colors.length];
            if (diffColor != color)
                return diffColor;
        }
    }
    
    protected void divide(boolean countPoints) {
        GameModel.getInstance().removeBall(this);
        
        if (size == 1) {
            if (countPoints)
                GameModel.getInstance().incrementPoints(10);
            if (GameModel.getInstance().noMoreBalls())
                GameModel.getInstance().gameWon();
        }
        else {
            if (countPoints)
                GameModel.getInstance().incrementPoints(5);
            Color newColor = differentColor(this.color);

            float newSpeedY = calculateNewSpeedY();

           
            double creationProbability = Math.random();
            if (creationProbability > 0.05) {
                GameModel.getInstance().addBall(
                    new Ball(
                        getX(), getY(),
                        getSpeedX(), newSpeedY,
                        this.radius / 2, newColor,
                        size - 1
                    )
                );
            }
            else {
                GameModel.getInstance().addBall(
                    new FakeBall(
                        getX(), getY(),
                        getSpeedX(), newSpeedY,
                        this.radius / 2, newColor,
                        size - 1
                    )
                );
            }
            

            creationProbability = Math.random();
            if (creationProbability > 0.05) {
                GameModel.getInstance().addBall(
                    new Ball(
                        getX(), getY(),
                        -getSpeedX(), newSpeedY,
                        this.radius / 2, newColor,
                        size - 1
                    )
                );
            }
            else {
                GameModel.getInstance().addBall(
                    new FakeBall(
                        getX(), getY(),
                        -getSpeedX(), newSpeedY,
                        this.radius / 2, newColor,
                        size - 1
                    )
                );
            }
        }
    }
    
    private void createFallingBonus() {
        double creationProbability = Math.random();
        if (creationProbability > GameModel.getInstance().getFallingBonusProb())
            return;
        
        DollarSignBonus dsb = new DollarSignBonus(
            getX(),
            getY(),
            GameModel.getInstance().getDollarSignSpeed(),
            GameModel.getInstance().getDollarSignWidth(),
            GameModel.getInstance().getDollarSignHeight()
        );
        
        TimerBonus tb = new TimerBonus(
            getX(),
            getY(),
            GameModel.getInstance().getDollarSignSpeed(),
            GameModel.getInstance().getDollarSignWidth(),
            GameModel.getInstance().getDollarSignHeight()
        );
        
        ShieldBonus sb = new ShieldBonus(
            getX(),
            getY(),
            GameModel.getInstance().getDollarSignSpeed(),
            GameModel.getInstance().getDollarSignWidth(),
            GameModel.getInstance().getDollarSignHeight()
        );
        
        creationProbability = Math.random();
        if (creationProbability <= 0.33) {
            GameModel.getInstance().addFallingBonus(dsb);
        }
        else if (creationProbability <= 0.66) {
            GameModel.getInstance().addFallingBonus(tb);
        }
        else
            GameModel.getInstance().addFallingBonus(sb);
    }
    
    private float calculateNewSpeedY() {
        float H = getY() - GameModel.getInstance().getMaxBallHeight(size - 1);
        float g = GameModel.getInstance().getGravity();
        
        float v0 = (float) -Math.sqrt(2 * g * H);
        
        return v0;
    }
//------------------------------------------------------------------------------
    
// COLLISION HANDLING-----------------------------------------------------------
    @Override
    protected void handleCollisions() {
        handleBorderCollisions();
        handlePlayerCollisions();
        handleWeaponCollisions();
    }
    
    protected void handleBorderCollisions() {
        if (getX() > maxX) {
            setX(maxX);
            setSpeedX(-getSpeedX());
        }
        else if (getX() < minX) {
            setX(minX);
            setSpeedX(-getSpeedX());
        }
        
        if (getY() > maxY) {
            setY(maxY);
            setSpeedY(-getSpeedY());
        }
        else if (getY() < minY) {
            setY(minY);
            setSpeedY(-getSpeedY());
        }
    }
    
    protected void handlePlayerCollisions() {
        if (!canKill) {
            return;
        }
        
        Player player = GameModel.getInstance().getPlayer();
        
        if (player != null)
            if (this.intersects(player.getShape())) {
                if (player.isShielded()) {
                    player.unshieldPlayer();
                }
                else {
                    GameModel.getInstance().loseLife();
                }
                
                canKill = false;
                divide(false);
            }
    }
    
    protected void handleWeaponCollisions() {
        Weapon weapon = GameModel.getInstance().getWeapon();
        
        if (weapon != null) {
            if (this.getBoundsInParent().intersects(weapon.getBoundsInParent())) {
                divide(true);
                
                createFallingBonus();
                
                GameModel.getInstance().setWeapon(null);
            }
        }
    }
//------------------------------------------------------------------------------
    
}
