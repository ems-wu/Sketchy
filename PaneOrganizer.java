package Sketchy;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Here in the PaneOrganizer class, I handle setting the background. creating a getter
 * method for my pane, and creating the button pane for my quit button.
 */

public class PaneOrganizer {
	private BorderPane _root;
	public PaneOrganizer() {
		_root = new BorderPane();
		Pane sketchyPane = new Pane();
		VBox controlPane = new VBox();
		controlPane.setAlignment(Pos.CENTER);
		controlPane.setStyle("-fx-background-color: lightblue;");
		controlPane.setPrefSize(Constants.WINDOW_WIDTH/4, Constants.WINDOW_HEIGHT);
		
		// instantiating Sketchy and Control
		Sketchy sketchy = new Sketchy(sketchyPane);
		Control control = new Control(controlPane, sketchy);
		
		_root.setCenter(sketchyPane);
		_root.setLeft(controlPane);
	}
	
	public BorderPane getRoot() {
		return _root;
	}
}
