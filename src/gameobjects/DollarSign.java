package gameobjects;

import game.GameModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public final class DollarSign extends MovingGameObject {
    
    private final float width;
    private final float height;
    private final Color color;
    
    private float maxY;
    
    public DollarSign(float x, float y, float speedY, float width, float height, Color color) {
        super(x, y, 0, speedY);
        
        this.width = width;
        this.height = height;
        this.color = color;
        
        draw();
    }

    @Override
    public void initializeInScene() {
        maxY = GameModel.getInstance().getSceneHeight() - height;
    }

    @Override
    protected void handleCollisions() {
        handleBorderCollisions();
        handlePlayerCollisions();
    }
    
    private void handleBorderCollisions() {
        if (getY() > maxY) {
            GameModel.getInstance().removeDollarSign(this);
        }
    }
    
    private void handlePlayerCollisions() {
        Player player = GameModel.getInstance().getPlayer();
        
        if (player != null)
            if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
                GameModel.getInstance().doublePoints();
                GameModel.getInstance().removeDollarSign(this);
            }
    }

    @Override
    protected void draw() {
        Rectangle dollarBill = new Rectangle();
        dollarBill.setWidth(width);
        dollarBill.setHeight(height);
        dollarBill.setFill(color);
        
        Text dollarText = new Text();
        dollarText.setText("$"); 
        dollarText.setFont(Font.font("Comic Sans", FontWeight.BOLD, FontPosture.REGULAR, 50));
        dollarText.setFill(Color.DARKGREEN);
        dollarText.setX(0.07 * width); 
        dollarText.setY(0.75 * height); 
        
        this.getChildren().addAll(dollarBill, dollarText);
    }
    
    
}
