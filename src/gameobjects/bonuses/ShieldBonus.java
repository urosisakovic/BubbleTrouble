package gameobjects.bonuses;

import game.GameModel;
import javafx.geometry.Point2D;
import javafx.scene.Group;
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

public final class ShieldBonus extends FallingBonus {

    public ShieldBonus(float x, float y, float speedY, float width, float height) {
        super(x, y, speedY, width, height);
        
        draw();
    }
    
    @Override
    protected void applyBonus() {
        GameModel.getInstance().shieldPlayer();
    }
 
     @Override
    protected final void draw() {
        // Head
        final float headRadius = width / 3;
        final Point2D headCenter = new Point2D(width / 2, height / 2 - headRadius / 5);        
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
        final float  capeHeight = height - capeY;
        Path cape = drawCape(capeHeight);
        cape.setTranslateX(capeX);
        cape.setTranslateY(capeY);
        
        this.getChildren().addAll(head, cape, hat);
    }
    
    private Group drawHead() {
        final float headRadius = width / 3;
        final Color faceColor = Color.rgb(255, 220, 177, 0.5);
        final double eyeWidth = headRadius;
        final Color eyeColor = Color.rgb(0, 0, 0, 0.5);
        final double mouthWidth = headRadius / 4;
        final Color mouthStrokeColor = Color.BLACK;
        final Color mouthFillColor = Color.rgb(148, 10, 0, 0.5);
        
        Group head = new Group();
        
        // Face
        Circle face = new Circle();
        face.setRadius(headRadius);
        face.setFill(faceColor);
        
        // Eyes
        Polygon leftEye = new Polygon();
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
        
        Polygon rightEye = new Polygon();
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
        Arc mouth = new Arc(
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

    private Group drawHat() {
        final Color hatColor = Color.rgb(0, 0, 0, 0.7);
        
        hatVerticalWidth = 2 * width / 3;
        final float hatVerticalHeight = height / 4;
        final float hatVerticalX = -hatVerticalWidth / 2;
        final float hatVerticalY = -hatVerticalHeight;
        
        hatHorizontalWidth = width;
        final float hatHorizontalHeight = height / 12;
        final float hatHorizontalX = -hatHorizontalWidth / 2;
        final float hatHorizontalY = -2;
        
        Group hat = new Group();
        
        Rectangle hatVertical = new Rectangle();
        hatVertical.setWidth(hatVerticalWidth);
        hatVertical.setHeight(hatVerticalHeight);
        hatVertical.setTranslateX(hatVerticalX);
        hatVertical.setTranslateY(hatVerticalY);
        hatVertical.setFill(hatColor);
        
        Rectangle hatHorizontal = new Rectangle();
        hatHorizontal.setWidth(hatHorizontalWidth);
        hatHorizontal.setHeight(hatHorizontalHeight);
        hatHorizontal.setTranslateX(hatHorizontalX);
        hatHorizontal.setTranslateY(hatHorizontalY);
        hatHorizontal.setFill(hatColor);
        
        hat.getChildren().addAll(hatVertical, hatHorizontal);
        
        return hat;
    }
    
    private Path drawCape(float capeHeight) {
        final Point2D bottomLeft = new Point2D(-0.5 * width, capeHeight);
        final Point2D bottomRight = new Point2D(0.5 * width, 0.9 * capeHeight);
        final Point2D control1 = new Point2D(-0.2 * width, 0.5 * capeHeight);
        final Point2D control2 = new Point2D(0.4 * width,  capeHeight);
        final Color fillColor = Color.rgb(255, 0, 0, 0.5);
        final Color strokeColor = Color.rgb(0, 0, 0, 0.5);
        
        Path cape = new Path();
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
        cape.setFill(fillColor);
        cape.setStroke(strokeColor);
        
        return cape;
    }
    
}
