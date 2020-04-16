package gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public final class LifeIcon extends GameObject {

    private final float width;
    
    public LifeIcon(float x, float y, float width) {
        super(x, y);
        
        this.width = width;
        
        draw();
    }
    
    @Override
    protected void draw() {
        final float headRadius = width / 3;
        final Point2D headCenter = new Point2D(width / 2, width / 2 - headRadius / 5);        
        Group head = drawHead();
        head.setTranslateX(headCenter.getX());
        head.setTranslateY(headCenter.getY());
        
        // Hat
        final float hatX = (float) headCenter.getX();
        final float hatY = (float) (headCenter.getY() - 4 * headRadius / 5);
        Group hat = drawHat();
        hat.setTranslateX(hatX);
        hat.setTranslateY(hatY);
        
        this.getChildren().addAll(head, hat);
    }
    
    private Group drawHead() {
        final float headRadius = width / 3;
        final Color faceColor = Color.rgb(255, 220, 177);
        final double eyeWidth = headRadius;
        final Color eyeColor = Color.BLACK;
        final double mouthWidth = headRadius / 4;
        final Color mouthStrokeColor = Color.BLACK;
        final Color mouthFillColor = Color.rgb(148, 10, 0);
        
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
        final Color hatColor = Color.BLACK;
        
        hatVerticalWidth = 2 * width / 3;
        final float hatVerticalHeight = width / 2.5f;
        final float hatVerticalX = -hatVerticalWidth / 2;
        final float hatVerticalY = -hatVerticalHeight;
        
        hatHorizontalWidth = width;
        final float hatHorizontalHeight = width / 12;
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
    
}
