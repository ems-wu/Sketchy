package Sketchy;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * In CreateAShape, I inherit the undo and redo method from the command interface
 * and determine the specifics for what those commands do. 
 */
public class CreateAShape implements Command{
	private SketchyShape _shape;
	private Pane _sketchyPane;
	private ArrayList<Saveable> _saveable;
	private ArrayList<SketchyShape> _sketchyShape;
	
	public CreateAShape(SketchyShape shape, Pane sketchyPane, ArrayList<Saveable> saveable, ArrayList<SketchyShape> sketchyShape) {
		_shape = shape;
		_sketchyPane = sketchyPane;
		_saveable = saveable;
		_sketchyShape = sketchyShape;
	}

	@Override
	public void undo() {
		 // This method removes the object from the pane and saveable and sketchyshape arraylist
		_sketchyPane.getChildren().remove(_shape.getNode());
		_saveable.remove(_shape);
		_sketchyShape.remove(_shape);
	}

	@Override
	public void redo() {
		 // This method adds the object to the pane and saveable and sketchyshape arraylist
		_shape.setBorder(Color.TRANSPARENT); // make sure object is not selected
		_sketchyPane.getChildren().add(_shape.getNode());
		_saveable.add(_shape);
		_sketchyShape.add(_shape);
	}

}
