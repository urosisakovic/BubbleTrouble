package run;

import game.GameModel;
import gameobjects.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Run extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Background background = new Background(
            GameModel.getInstance().getSceneWidth(), 
            GameModel.getInstance().getSceneHeight()
        );
        
        Player player = new Player(
            GameModel.getInstance().getPlayerHeight(),
            GameModel.getInstance().getPlayerSpeed()
        );
        
        GameModel.getInstance().addBackground(background);
        GameModel.getInstance().addPlayer(player);
        
        stage.setScene(GameModel.getInstance().getScene());
        stage.setTitle(GameModel.getInstance().getGameName());
        stage.show();
        
        GameModel.getInstance().start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
