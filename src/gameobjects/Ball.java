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
    
    public Ball(float x, float y, float speedX, float speedY, float radius, Color color) {
        super(x, y, speedX, speedY);
        this.radius = radius;
        this.color = color;
        
        draw();
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
                GameModel.getInstance().gameEnd();
    }
    
    private void handleWeaponCollisions() {
        Weapon weapon = GameModel.getInstance().getWeapon();
        
        if (weapon != null)
            if (this.getBoundsInParent().intersects(weapon.getBoundsInParent()))
                GameModel.getInstance().gameEnd();
    }

}
