package gameobjects.bonuses;

import game.GameModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public final class DollarSignBonus extends FallingBonus {
    
    private final Color color;
        
    public DollarSignBonus(float x, float y, float speedY, float width, float height, Color color) {
        super(x, y, speedY, width, height);
        
        this.color = color;
        
        draw();
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

    @Override
    protected void applyBonus() {
        GameModel.getInstance().doublePoints();
    }
    
}
