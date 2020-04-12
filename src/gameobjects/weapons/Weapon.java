package gameobjects.weapons;

import game.GameModel;
import gameobjects.MovingGameObject;

public abstract class Weapon extends MovingGameObject {

    private float maxY;
    
    public Weapon(float x, float y, float speedY) {
        super(x, y, 0, speedY);
        
        drawWeapon();
    }
    
    protected abstract void drawWeapon();
    
    public void initializeInScene() {
        maxY = (float) this.getScene().getHeight();
    }
    
    @Override
    protected void handleBorderCollisions() {
        if (getY() > maxY)
            GameModel.getInstance().setWeapon(null);
    }

    @Override
    protected void handleObjectCollisions() {

    }
    
}
