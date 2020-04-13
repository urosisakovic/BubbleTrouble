package gameobjects;

import javafx.scene.Group;

public abstract class GameObject extends Group {
    
    protected float x, y;
    
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
        
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    
    public GameObject() {
        this(0, 0);
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
    
    protected abstract void draw();
    
}
