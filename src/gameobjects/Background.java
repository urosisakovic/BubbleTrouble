package gameobjects;

import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;

public class Background extends GameObject {
    
    private final float width;
    private final float height;
    private final LinearGradient lg;
    
    public Background(float width, float height, LinearGradient lg) {
        super();
        
        this.height = height;
        this.width = width;
        this.lg = lg;
        
        draw();
    }

    @Override
    protected final void draw() {
        Rectangle background = new Rectangle();
        background.setWidth(width);
        background.setHeight(height);
        background.setFill(lg);
        
        this.getChildren().add(background);
    }
}
