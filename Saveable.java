package Sketchy;

import cs015.fnl.SketchySupport.FileIO;
import javafx.scene.Node;

/**
 * This is my Saveable interface that is inherited by CurvedLine, SketchyRectangle, and Sketchy Ellipse. 
 */
public interface Saveable {
	
	void save(FileIO io); // saves file
	
	void load(FileIO io); // loads file

	Node getNode();

}
