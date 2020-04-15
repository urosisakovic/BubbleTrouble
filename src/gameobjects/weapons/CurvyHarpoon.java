package gameobjects.weapons;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class CurvyHarpoon extends Weapon {
    
    public CurvyHarpoon(float x, float y, float speedY) {
        super(x, y, speedY);
    }
    
    @Override
    protected void draw() {
        Polygon top = new Polygon();
        top.getPoints().addAll(
            new Double[] {
               -10.0,  10.0,
                 0.0,   0.0,
                10.0,  10.0
            }
        );
        top.setFill(Color.GRAY);
        
        Line line = new Line();
        line.setStartX(0);
        line.setStartY(10);
        line.setEndX(0);
        line.setEndY(20);
        line.setStroke(Color.GRAY);
        
        this.getChildren().addAll(top, line);
        
        for (int i = 0; i < 50; i++) {
            Arc left = new Arc(0, 0, 5, 7, 90, 180);
            left.setFill(Color.rgb(0, 0, 0, 0));
            left.setStroke(Color.GRAY);
            left.setTranslateY(27 + 28 *i);

            Arc right = new Arc(0, 0, 5, 7, -90, 180);
            right.setFill(Color.rgb(0, 0, 0, 0));
            right.setStroke(Color.GRAY);
            right.setTranslateY(41 + 28 * i);

            this.getChildren().addAll(left, right);
        }
    }
    
}
