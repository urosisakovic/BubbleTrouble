package gameobjects;

import game.GameModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FakeBall extends Ball {
    
    public FakeBall(float x, float y, float speedX, float speedY, float radius, Color color, int size) {
        super(x, y, speedX, speedY, radius, color, size);
    }
    
    @Override
    protected void draw() {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.08));
        
        this.getChildren().addAll(circle);
    }
    
    @Override
    protected void handleCollisions() {
        handleBorderCollisions();
        handlePlayerCollisions();
        handleWeaponCollisions();
    }
    
    @Override
    protected void handlePlayerCollisions() {
        if (!canKill) {
            return;
        }
        
        Player player = GameModel.getInstance().getPlayer();
        
        if (player != null)
            if (this.intersects(player.getShape())) {
                canKill = false;
                divide(false);
            }
    }
}
