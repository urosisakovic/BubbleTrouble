package gameobjects;

public abstract class MovingGameObject extends GameObject {
    
    protected float speedX, speedY;
    
    public MovingGameObject(float x, float y, float speedX, float speedY) {
        super(x, y);
        this.speedX = speedX;
        this.speedY = speedY;
    }
    
    public MovingGameObject(float x, float y) {
        this(x, y, 0, 0);
    }
    
    public MovingGameObject() {
        this(0, 0, 0, 0);
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
    
    public abstract void initializeInScene();
    protected abstract void handleCollisions();
}
