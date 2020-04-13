package gameobjects.weapons;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class StraightHarpoon extends Weapon {

    public StraightHarpoon(float x, float y, float speedY) {
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
        top.setFill(Color.BLUE);
        
        Line rope = new Line();
        rope.setStartX(0);
        rope.setStartY(0);
        rope.setEndX(0);
        rope.setEndY(1000);
        rope.setStroke(Color.RED);
     
        this.getChildren().addAll(top, rope);
    }
       
}
