package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public final class ScoreSemaphore extends GameObject {

    private Text score;
    private int points = 0;
    
    public ScoreSemaphore(float x, float y) {
        super(x, y);
        
        draw();
    }
    
    public void setPoints(int points) {
        this.points = points;
        score.setText("Score: " + points); 
    }
    
    @Override
    protected void draw() {
        score = new Text();
        score.setText("Score: " + points); 
        score.setFont(Font.font("Comic Sans", FontWeight.NORMAL, FontPosture.REGULAR, 40));
        score.setFill(Color.RED);
        
        this.getChildren().add(score);        
                
        
        
    }
    
}
