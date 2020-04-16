package run;

import game.GameModel;
import gameobjects.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Run extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // Create game objects
        Background wrapperBackground = new Background(
            GameModel.getInstance().getSceneWidth(), 
            GameModel.getInstance().getSceneHeight(),
            GameModel.getInstance().getWrapperBackgroundColor()
        );
        
        Background background = new Background(
            GameModel.getInstance().getSceneWidth(), 
            GameModel.getInstance().getSceneHeight(),
            GameModel.getInstance().getBackgroundColor()
        );
        
        Player player = new Player(
            GameModel.getInstance().getPlayerHeight(),
            GameModel.getInstance().getPlayerSpeed()
        );
        
        ScoreSemaphore scoreSemaphore = new ScoreSemaphore(
            GameModel.getInstance().getScoreSemaphoreX(),
            GameModel.getInstance().getScoreSemaphoreY()
        );
        
        Ball ball1 = new Ball(
            GameModel.getInstance().getStartBallX(),
            GameModel.getInstance().getMaxBallHeight(GameModel.getInstance().getStartBallSize()),
            -GameModel.getInstance().getStartBallSpeedX(),
            GameModel.getInstance().getStartBallSpeedY(),
            GameModel.getInstance().getStartBallRadius(),
            GameModel.getInstance().getStartBallColor()
        );
        
        Ball ball2 = new Ball(
            GameModel.getInstance().getStartBallX(),
            GameModel.getInstance().getMaxBallHeight(GameModel.getInstance().getStartBallSize()),
            GameModel.getInstance().getStartBallSpeedX(),
            GameModel.getInstance().getStartBallSpeedY(),
            GameModel.getInstance().getStartBallRadius(),
            GameModel.getInstance().getStartBallColor()
        );
        
        Timer gameTimer = new Timer(
            0, GameModel.getInstance().getSceneHeight() - 50,
            50, GameModel.getInstance().getSceneWidth()
        );
        
        // Add object to the game
        GameModel.getInstance().setWrapperBackground(wrapperBackground);
        GameModel.getInstance().setBackground(background);
        GameModel.getInstance().setPlayer(player);
        GameModel.getInstance().setScoreSemaphore(scoreSemaphore);
        GameModel.getInstance().addBall(ball1);
        GameModel.getInstance().addBall(ball2);
        GameModel.getInstance().setGameTimer(gameTimer);
        GameModel.getInstance().setLifeCount(
                GameModel.getInstance().getStartingLifeCount()
        );
        
        // Set stage
        stage.setScene(GameModel.getInstance().getScene());
        stage.setTitle(GameModel.getInstance().getGameName());
        stage.show();
        
        // Start the game
        GameModel.getInstance().start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
