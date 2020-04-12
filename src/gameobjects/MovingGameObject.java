package gameobjects;

public abstract class MovingGameObject extends GameObject {
    
    protected float speedX, speedY;
    
    public MovingGameObject() {
        super();
        this.speedX = 0;
        this.speedY = 0;
    }
    
    public MovingGameObject(float x, float y) {
        super(x, y);
        this.speedX = 0;
        this.speedY = 0;
    }
    
    public MovingGameObject(float x, float y, float speedX, float speedY) {
        super(x, y);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    
    public void update() {
        updatePosition();
        handleCollisions();
    }
    
    public void updatePosition() {
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
    }
    
    public void handleCollisions() {
        handleBorderCollisions();
        handleObjectCollisions();
    }
    
    protected abstract void handleBorderCollisions();
    protected abstract void handleObjectCollisions();
}
