package gameobjects;

import javafx.scene.Group;

public class GameObject extends Group {
    
    protected float x, y;
    
    public GameObject() {
        x = 0;
        y = 0;
        
        this.setTranslateX(0);
        this.setTranslateY(0);
    }
    
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
        
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        this.setTranslateX(x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.setTranslateY(y);
    }
    
}
