package gameobjects;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends MovingGameObject {
     
    private final float playerHeight;
    private final float playerWidth;
    private final float playerSpeed;
    
    private float sceneHeight;
    private float sceneWidth;
    private float maxX;
    private float minX;
    
    public Player(float playerHeight, float playerSpeed) {
        super();
        this.playerHeight = playerHeight;
        this.playerWidth = 0.6f * playerHeight;
        this.playerSpeed = playerSpeed;
        
        drawPlayer();
        
        addControls();
    }
    
    private void drawPlayer() {
        Rectangle player = new Rectangle();
        player.setHeight(playerHeight);
        player.setWidth(playerWidth);
        player.setFill(Color.BLUE);
        
        this.getChildren().addAll(player);
    }
    
    private void addControls() {
        this.setFocusTraversable(true);
        
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            System.out.println("Player class: Key Pressed");
            
            switch (event.getCode()) {
                case RIGHT:
                    this.setSpeedX(playerSpeed);
                    break;
                case LEFT:
                    this.setSpeedX(-playerSpeed);
                    break;
            }
        });
        
        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            System.out.println("Player class: Key Released");
            
            if (event.getCode() == KeyCode.LEFT && getSpeedX() == -playerSpeed)
                this.setSpeedX(0);
            
            if (event.getCode() == KeyCode.RIGHT && getSpeedX() == playerSpeed)
                this.setSpeedX(0);
        });
    }
    
    public void initializeInScene() {
        sceneHeight = (float) this.getScene().getHeight();
        sceneWidth = (float) this.getScene().getWidth();
        minX = 0;
        maxX = sceneWidth - playerWidth;
        this.setY(sceneHeight - playerHeight);
    }
    
    public void initializeInScene(float x) {
        this.setX(x);
        initializeInScene();
    }

    @Override
    protected void handleBorderCollisions() {
        if (getX() > maxX)
            setX(maxX);
        else if (getX() < minX)
            setX(minX);
    }

    @Override
    protected void handleObjectCollisions() {

    }    
    
}
