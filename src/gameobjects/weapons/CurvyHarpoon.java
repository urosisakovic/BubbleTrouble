package gameobjects.weapons;

import game.GameModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public final class CurvyHarpoon extends Weapon {
    
    private Arc[] left = new Arc[50];
    private Arc[] right = new Arc[50];
    private int lastAdded = 0;
    
    public CurvyHarpoon(float x, float y, float speedY) {
        super(x, y, speedY);
        
        draw();
    }
    
    @Override
    public void update() {
        super.update();
        
        float length = GameModel.getInstance().getSceneHeight() 
            - getY();
        
        for (; lastAdded < (length - 20) / 28 - 1; lastAdded++) {
            this.getChildren().addAll(left[lastAdded], right[lastAdded]);
        }
        
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
            left[i] = new Arc(0, 0, 5, 7, 90, 180);
            left[i].setFill(Color.rgb(0, 0, 0, 0));
            left[i].setStroke(Color.GRAY);
            left[i].setTranslateY(27 + 28 *i);

            right[i] = new Arc(0, 0, 5, 7, -90, 180);
            right[i].setFill(Color.rgb(0, 0, 0, 0));
            right[i].setStroke(Color.GRAY);
            right[i].setTranslateY(41  + 28 * i);
        }
    }
    
}
