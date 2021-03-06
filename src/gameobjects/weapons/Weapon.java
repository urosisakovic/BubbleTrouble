package gameobjects.weapons;

import game.GameModel;
import gameobjects.MovingGameObject;

public abstract class Weapon extends MovingGameObject {

    private float maxY;
    
    public Weapon(float x, float y, float speedY) {
        super(x, y, 0, speedY);
    }
        
    @Override
    public void initializeInScene() {
        maxY = (float) this.getScene().getHeight();
    }
    
    @Override
    protected void handleCollisions() {
        if (getY() <= 0)
            GameModel.getInstance().setWeapon(null);
    }
    
}
