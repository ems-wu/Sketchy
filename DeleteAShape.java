package Sketchy;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * This is my DeleteAShape Command class that inherits the Command interface. I
 * pass in an instance of layer so I can keep track of all of the indicies of my
 * three different ArrayLists.
 */
public class DeleteAShape implements Command{
	private SketchyShape _sketchyShape;
	private Pane _sketchyPane;
	private ArrayList<Saveable> _saveable;
	private ArrayList<SketchyShape> _sketchyShapeList;
	private Shape _shape;
	private Layer _layer;
	
	public DeleteAShape(SketchyShape sketchyShape , Pane sketchyPane, ArrayList<Saveable> saveable, ArrayList<SketchyShape> sketchyShapeList, Layer layer){
		_sketchyShape = sketchyShape;
		_sketchyPane = sketchyPane;
		_saveable = saveable;
		_sketchyShapeList = sketchyShapeList;
		_layer = layer;
	}
	
	
	@Override
	public void undo() {
		 // This method adds the object from the pane and saveable and sketchyshape arraylist
		_sketchyPane.getChildren().add(_layer.getPaneIndex(), _sketchyShape.getNode());
		_saveable.add(_layer.getSaveablesIndex(), _sketchyShape);
		_sketchyShapeList.add(_layer.getShapesIndex(), _sketchyShape);
		_shape = _sketchyShape.getNode();
	}

	@Override
	public void redo() {
		 // This method removes the object from the pane and saveable and sketchyshape arraylist
		_sketchyPane.getChildren().remove(_shape);
		_sketchyShapeList.remove(_sketchyShape);
		_saveable.remove(_sketchyShape);
	}
	

}
