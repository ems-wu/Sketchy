package Sketchy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is my main App class that calls the start method and 
  * instantiates my game. I create the stage and instantiate my
  * top level object/class, PaneOrganizer. 
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        	PaneOrganizer organizer = new PaneOrganizer();
    		Scene scene = new Scene(organizer.getRoot(), Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    		stage.setScene(scene);
    		stage.setTitle("Sketchy <3");
    		stage.setResizable(false); // keeps screen same size
    		stage.show();
    }
   
    public static void main(String[] argv) {
        launch(argv);
    }
}
