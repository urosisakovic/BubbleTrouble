package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Background extends GameObject {
    
    private final float width;
    private final float height;
    
    public Background(float width, float height) {
        super();
        
        this.height = height;
        this.width = width;
        
        draw();
    }

    @Override
    protected final void draw() {
        Rectangle background = new Rectangle();
        background.setWidth(width);
        background.setHeight(height);
        background.setFill(Color.rgb(255, 0, 0, 0.3));
        
        this.getChildren().add(background);
    }
}
