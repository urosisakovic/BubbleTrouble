package gameobjects;

import game.GameModel;
import gameobjects.weapons.CurvyHarpoon;
import gameobjects.weapons.Weapon;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public final class Player extends MovingGameObject {
     
    private final float playerHeight;
    private final float playerWidth;
    private final float playerSpeed;
    
    private float sceneHeight;
    private float sceneWidth;
    private float maxX;
    private float minX;
    
    private boolean shielded = false;
    private long shieldedTime;
    
    public Player(float playerHeight, float playerSpeed) {
        super();
        this.playerHeight = playerHeight;
        this.playerWidth = 0.7f * playerHeight;
        this.playerSpeed = playerSpeed;
        
        draw();
        
        addControls();
    }
        
    @Override
    protected final void draw() {
        // Head
        final float headRadius = playerWidth / 3;
        final Point2D headCenter = new Point2D(playerWidth / 2, playerHeight / 2 - headRadius / 5);        
        Group head = drawHead();
        head.setTranslateX(headCenter.getX());
        head.setTranslateY(headCenter.getY());
        
        // Hat
        final float hatX = (float) headCenter.getX();
        final float hatY = (float) (headCenter.getY() - 4 * headRadius / 5);
        Group hat = drawHat();
        hat.setTranslateX(hatX);
        hat.setTranslateY(hatY);
        
        // Cape
        final float capeX = (float) headCenter.getX();
        final float capeY = (float) (headCenter.getY() + headRadius);
        final float  capeHeight = playerHeight - capeY;
        cape = drawCape(capeHeight);
        cape.setTranslateX(capeX);
        cape.setTranslateY(capeY);
        
        this.getChildren().addAll(head, cape, hat);
    }
    
    private final Color faceColor = Color.rgb(255, 220, 177);
    private final Color eyeColor = Color.rgb(0, 0, 0);
    private final Color mouthStrokeColor = Color.rgb(0, 0, 0);
    private final Color mouthFillColor = Color.rgb(148, 10, 0);
    
    private final Color faceColorShielded = Color.rgb(255, 220, 177, 0.5);
    private final Color eyeColorShielded = Color.rgb(0, 0, 0, 0.5);
    private final Color mouthStrokeColorShielded = Color.rgb(0, 0, 0, 0.5);
    private final Color mouthFillColorShielded = Color.rgb(148, 10, 0, 0.5);
    
    private Circle face;
    private Polygon leftEye, rightEye;
    private Arc mouth;
    
    private Group drawHead() {
        final float headRadius = playerWidth / 3;
        final double eyeWidth = headRadius;
        final double mouthWidth = headRadius / 4;
        
        Group head = new Group();
        
        // Face
        face = new Circle();
        face.setRadius(headRadius);
        face.setFill(faceColor);
        
        // Eyes
        leftEye = new Polygon();
        leftEye.getPoints().addAll(
            new Double[] {
                0.0,            0.0,
                eyeWidth,       0.0,
                0.5 * eyeWidth, 0.5 * eyeWidth
            }
        );
        leftEye.setTranslateX(-headRadius);
        leftEye.setTranslateY(-0.25 * headRadius);
        leftEye.setFill(eyeColor);
        
        rightEye = new Polygon();
        rightEye.getPoints().addAll(
            new Double[] {
               0.0,            0.0,
               eyeWidth,       0.0,
               0.5 * eyeWidth, 0.5 * eyeWidth
            }
        );
        rightEye.setTranslateX(headRadius - eyeWidth);
        rightEye.setTranslateY(-0.25 * headRadius);
        rightEye.setFill(eyeColor);
        
        // Mouth
        mouth = new Arc(
            0, headRadius / 2,
            headRadius / 2, headRadius / 4,
            -180, 180
        );
        mouth.setFill(mouthFillColor);
        mouth.setStroke(mouthStrokeColor);
        
        head.getChildren().addAll(face, leftEye, rightEye, mouth);
        
        return head;
    }
    
    
    private float hatVerticalWidth;
    private float hatHorizontalWidth;

    private final Color hatColor = Color.rgb(0, 0, 0);
    private final Color hatColorShielded = Color.rgb(0, 0, 0);
    
    private Rectangle hatVertical, hatHorizontal;
    
    private Group drawHat() {
        hatVerticalWidth = 2 * playerWidth / 3;
        final float hatVerticalHeight = playerHeight / 4;
        final float hatVerticalX = -hatVerticalWidth / 2;
        final float hatVerticalY = -hatVerticalHeight;
        
        hatHorizontalWidth = playerWidth;
        final float hatHorizontalHeight = playerHeight / 12;
        final float hatHorizontalX = -hatHorizontalWidth / 2;
        final float hatHorizontalY = -2;
        
        Group hat = new Group();
        
        hatVertical = new Rectangle();
        hatVertical.setWidth(hatVerticalWidth);
        hatVertical.setHeight(hatVerticalHeight);
        hatVertical.setTranslateX(hatVerticalX);
        hatVertical.setTranslateY(hatVerticalY);
        hatVertical.setFill(hatColor);
        
        hatHorizontal = new Rectangle();
        hatHorizontal.setWidth(hatHorizontalWidth);
        hatHorizontal.setHeight(hatHorizontalHeight);
        hatHorizontal.setTranslateX(hatHorizontalX);
        hatHorizontal.setTranslateY(hatHorizontalY);
        hatHorizontal.setFill(hatColor);
        
        hat.getChildren().addAll(hatVertical, hatHorizontal);
        
        return hat;
    }
    
    private final Color capeFillColor = Color.rgb(255, 0, 0);
    private final Color capeStrokeColor = Color.rgb(0, 0, 0);
    
    private final Color capeFillColorShielded = Color.rgb(255, 0, 0, 0.5);
    private final Color capeStrokeColorShielded = Color.rgb(0, 0, 0, 0.5);
    
    private Path cape;
    
    private Path drawCape(float capeHeight) {
        final Point2D bottomLeft = new Point2D(-0.5 * playerWidth, capeHeight);
        final Point2D bottomRight = new Point2D(0.5 * playerWidth, 0.9 * capeHeight);
        final Point2D control1 = new Point2D(-0.2 * playerWidth, 0.5 * capeHeight);
        final Point2D control2 = new Point2D(0.4 * playerWidth,  capeHeight);
        
        
        cape = new Path();
        MoveTo startMove = new MoveTo(0, 0);
        LineTo leftLine = new LineTo(bottomLeft.getX(), bottomLeft.getY());
        CubicCurveTo bottomLine = new CubicCurveTo();
        bottomLine.setControlX1(control1.getX());
        bottomLine.setControlY1(control1.getY());
        bottomLine.setControlX2(control2.getX());
        bottomLine.setControlY2(control2.getY());
        bottomLine.setX(bottomRight.getX());
        bottomLine.setY(bottomRight.getY());
        ClosePath closePath = new ClosePath();
        
        cape.getElements().addAll(startMove, leftLine, bottomLine, closePath);
        cape.setFill(capeFillColor);
        cape.setStroke(capeStrokeColor);
        
        return cape;
    }
    
    private void addControls() {
        this.setFocusTraversable(true);
        
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {            
            switch (event.getCode()) {
                case RIGHT:
                    this.setSpeedX(playerSpeed);
                    break;
                case LEFT:
                    this.setSpeedX(-playerSpeed);
                    break;
                case SPACE:                    
                    Weapon weapon = new CurvyHarpoon(
                        getX() + playerWidth / 2,
                        getY(),
                        GameModel.getInstance().getWeaponSpeed()
                    );
                    GameModel.getInstance().setWeapon(weapon);
                    break;
            }
        });
        
        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {            
            if (event.getCode() == KeyCode.LEFT && getSpeedX() == -playerSpeed)
                this.setSpeedX(0);
            
            if (event.getCode() == KeyCode.RIGHT && getSpeedX() == playerSpeed)
                this.setSpeedX(0);
        });
    }
    
    @Override
    public void initializeInScene() {
        sceneHeight = GameModel.getInstance().getSceneHeight();
        sceneWidth = GameModel.getInstance().getSceneWidth();
        minX = 0;
        maxX = sceneWidth - playerWidth;
        this.setY(sceneHeight - playerHeight);
    }
    
    public void initializeInScene(float x) {
        this.setX(x);
        initializeInScene();
    }

    @Override
    protected void handleCollisions() {
        if (getX() > maxX)
            setX(maxX);
        else if (getX() < minX)
            setX(minX);
    }

    public Shape getShape() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(hatVerticalWidth);
        rectangle.setHeight(playerHeight);
        rectangle.setX(getX() + (hatHorizontalWidth - hatVerticalWidth) / 2);
        rectangle.setY(getY());
                
        return rectangle;
    }

    @Override
    public void update() {
        super.update();
        
        if (shielded) {
            long timeElapsed = System.nanoTime() - shieldedTime;

            if (timeElapsed > 10000000000l)
                unshieldPlayer();
        }
    }
    
    public void shieldPlayer() {        
        shielded = true;
        shieldedTime = System.nanoTime();
        
        face.setFill(faceColorShielded);
        leftEye.setFill(eyeColorShielded);
        rightEye.setFill(eyeColorShielded);
        mouth.setFill(mouthFillColorShielded);
        hatVertical.setFill(hatColorShielded);
        hatHorizontal.setFill(hatColorShielded);
        cape.setFill(capeFillColorShielded);
        cape.setStroke(capeStrokeColorShielded);
    }

    public boolean isShielded() {
        return shielded;
    }
    
    public void unshieldPlayer() {        
        shielded = false;
        
        face.setFill(faceColor);
        leftEye.setFill(eyeColor);
        rightEye.setFill(eyeColor);
        mouth.setFill(mouthFillColor);
        hatVertical.setFill(hatColor);
        hatHorizontal.setFill(hatColor);
        cape.setFill(capeFillColor);
        cape.setStroke(capeStrokeColor);
    }
}
