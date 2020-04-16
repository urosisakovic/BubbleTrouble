package gameobjects.bonuses;

import game.GameModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public final class TimerBonus extends FallingBonus {
    
    public TimerBonus(float x, float y, float speedY, float width, float height) {
        super(x, y, speedY, width, height);
        
        draw();
    }
    
    @Override
    protected void applyBonus() {
        GameModel.getInstance().addMoreTime(10);
    }

    @Override
    protected void draw() {
        Circle outter = new Circle();
        outter.setRadius(width / 2);
        outter.setTranslateX(width / 2);
        outter.setTranslateY(height / 2);
        outter.setFill(Color.RED);
        
        Circle inner = new Circle();
        inner.setRadius(0.8 * (width / 2));
        inner.setTranslateX(width / 2);
        inner.setTranslateY(height / 2);
        inner.setFill(Color.WHITE);
        
        Line vertical = new Line();
        vertical.setStroke(Color.BLACK);
        vertical.setStartX(width / 2);
        vertical.setStartY(height / 2);
        vertical.setEndX(width / 2);
        vertical.setEndY(0.3 * height);
        vertical.setStrokeWidth(3);
        
        Line horizontal = new Line();
        horizontal.setStroke(Color.BLACK);
        horizontal.setStartX(width / 2);
        horizontal.setStartY(height / 2);
        horizontal.setEndX(0.7 * width);
        horizontal.setEndY(height / 2);
        horizontal.setStrokeWidth(3);
        
        this.getChildren().addAll(outter, inner,vertical, horizontal);
    }
    
}
