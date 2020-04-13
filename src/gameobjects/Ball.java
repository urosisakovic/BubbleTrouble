package gameobjects;

import game.GameModel;
import gameobjects.weapons.Weapon;
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
        
        System.out.println("Ball constructor - radius: " + radius);
        
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
            if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
                System.out.println("Ball: handlePlayerCollision(): BALL HIT PLAYER");
                System.out.println("Player position: " + player.getX() + "  " + player.getY());
                System.out.println("Player bounds: " + player.getBoundsInParent().toString());
                System.out.println("Ball position and radius: " + getX() + "  " + getY() + "  " + radius);
                System.out.println("Ball bounds: " + this.getBoundsInParent().toString());
                GameModel.getInstance().gameLost();
            }
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
                    System.out.println("Createing ball of radius: " + radius / 2);
                    GameModel.getInstance().addBall(
                        new Ball(
                            getX(), getY(),
                            getSpeedX(), getSpeedY(),
                            this.radius / 2, color,
                            size - 1
                        )
                    );
                    
                    System.out.println("Createing ball of radius: " + radius / 2);
                    GameModel.getInstance().addBall(
                        new Ball(
                            getX(), getY(),
                            -getSpeedX(), getSpeedY(),
                            this.radius / 2, color,
                            size - 1
                        )
                    );
                }
                
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
