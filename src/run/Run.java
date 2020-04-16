package run;

import game.GameModel;
import gameobjects.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Run extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
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
        
        GameModel.getInstance().setWrapperBackground(wrapperBackground);
        GameModel.getInstance().setBackground(background);
        GameModel.getInstance().setPlayer(player);
        GameModel.getInstance().setScoreSemaphore(scoreSemaphore);
        GameModel.getInstance().addBall(ball1);
        GameModel.getInstance().addBall(ball2);
        GameModel.getInstance().setGameTimer(gameTimer);
        
        stage.setScene(GameModel.getInstance().getScene());
        stage.setTitle(GameModel.getInstance().getGameName());
        stage.show();
        
        GameModel.getInstance().start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
