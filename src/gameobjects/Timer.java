package gameobjects;

import game.GameModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Timer extends GameObject {
    private final float width, height;
    private final long startTime;
    private Rectangle bar;
    private Text score;
    
    public Timer(float x, float y, float height, float width) {
        super(x, y);
        
        this.width = width;
        this.height = height;
        this.startTime = System.nanoTime();
        
        draw();
    }
    
    @Override
    protected void draw() {
        bar = new Rectangle();
        bar.setHeight(height);
        bar.setWidth(width);
        bar.setFill(Color.RED);
        
        score = new Text();
        score.setText("60"); 
        score.setTranslateX(width / 2 - 10); 
        score.setTranslateY(height - 10); 
        score.setFont(Font.font("Comic Sans", FontWeight.NORMAL, FontPosture.REGULAR, 40));
        score.setFill(Color.BLUE);
        
        this.getChildren().addAll(bar, score);
    }
    
    public void update() {
        long timeElapsed = System.nanoTime() - startTime;
        
        if (timeElapsed > 60000000000l)
            GameModel.getInstance().gameLost();
        
        float secondsElapsed = (float) (timeElapsed / 1000000000);
        
        int intSec = 60 - (int) secondsElapsed;
        
        score.setText("" + intSec);
        
        float ratio = 1 - (secondsElapsed / 60);
        float translation = width * (1 - ratio);
        bar.setScaleX(ratio);
        bar.setTranslateX(-translation/2);
    }    
}
