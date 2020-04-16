package gameobjects.bonuses;

import game.GameModel;
import gameobjects.MovingGameObject;
import gameobjects.Player;

public abstract class FallingBonus extends MovingGameObject {

    protected float Height;
    protected float maxY;
    protected final float width;
    protected final float height;
    
    public FallingBonus(float x, float y, float speedY, float width, float height) {
        super(x, y, 0, speedY);
        
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void initializeInScene() {
        maxY = GameModel.getInstance().getSceneHeight() - height;
    }

    @Override
    protected void handleCollisions() {
        handleBorderCollisions();
        handlePlayerCollisions();
    }
    
    private void handleBorderCollisions() {
        if (getY() > maxY) {
            removeBonus();
        }
    }
    
    private void handlePlayerCollisions() {
        Player player = GameModel.getInstance().getPlayer();
        
        if (player != null)
            if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
                applyBonus();
                removeBonus();
            }
    } 
    
    protected abstract void applyBonus();
    protected void removeBonus() {
        GameModel.getInstance().removeFallingBonus(this);
    }
}
