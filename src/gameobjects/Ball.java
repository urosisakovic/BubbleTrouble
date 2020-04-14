package gameobjects;

import game.GameModel;
import gameobjects.weapons.Weapon;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends MovingGameObject {

    private float radius;
    private Color color;
    
    private float sceneHeight;
    private float sceneWidth;
    private float maxX;
    private float minX;
    private float maxY;
    private float minY;
    
    private int size;
    
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
    
    @Override
    protected final void draw() {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        
        this.getChildren().add(circle);
    }
    
    @Override
    public void initializeInScene() {
        sceneHeight = (float) this.getScene().getHeight();
        sceneWidth = (float) this.getScene().getWidth();
        minX = radius;
        
        maxX = sceneWidth - radius;
        minY = radius;
        maxY = sceneHeight - radius;
    }
    
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
    
    
    private void handlePlayerCollisions() {
        Player player = GameModel.getInstance().getPlayer();
        
        if (player != null)
            if (this.getBoundsInParent().intersects(player.getBoundsInParent()))
                GameModel.getInstance().gameLost();
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
    
    private void createDollarSign() {
        double creationProbability = Math.random();
        if (creationProbability > GameModel.getInstance().getDollarSignProb())
            return;
        
        GameModel.getInstance().addDollarSign(
            new DollarSign(
                getX(),
                getY(),
                GameModel.getInstance().getDollarSignSpeed(),
                GameModel.getInstance().getDollarSignWidth(),
                GameModel.getInstance().getDollarSignHeight(),
                GameModel.getInstance().getDollarSignColor()
            )
        );
    }
    
    private void handleWeaponCollisions() {
        Weapon weapon = GameModel.getInstance().getWeapon();
        
        if (weapon != null) {
            if (this.getBoundsInParent().intersects(weapon.getBoundsInParent())) {
                GameModel.getInstance().removeBall(this);
                
                if (size == 1) {    
                    if (GameModel.getInstance().noMoreBalls())
                        GameModel.getInstance().gameWon();
                }
                else {     
                    Color newColor = differentColor(this.color);
                    
                    GameModel.getInstance().addBall(
                        new Ball(
                            getX(), getY(),
                            getSpeedX(), getSpeedY(),
                            this.radius / 2, newColor,
                            size - 1
                        )
                    );
                    
                    GameModel.getInstance().addBall(
                        new Ball(
                            getX(), getY(),
                            -getSpeedX(), getSpeedY(),
                            this.radius / 2, newColor,
                            size - 1
                        )
                    );
                }
                
                createDollarSign();
                
                GameModel.getInstance().setWeapon(null);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
}
