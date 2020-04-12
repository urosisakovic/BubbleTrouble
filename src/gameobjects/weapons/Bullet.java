package gameobjects.weapons;

import javafx.scene.shape.Rectangle;

public class Bullet extends Weapon {
    
    public Bullet(float x, float y, float speedY) {
        super(x, y, speedY);
    }

    @Override
    protected void drawWeapon() {
        Rectangle bullet = new Rectangle();
        bullet.setWidth(5);
        bullet.setHeight(5);
        
        this.getChildren().addAll(bullet);
    }
}
