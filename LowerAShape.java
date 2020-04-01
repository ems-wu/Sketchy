package Sketchy;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * This is my LowerAShape Command class that inherits the Command interface. 
 */
public class LowerAShape implements Command{
	private SketchyShape _sketchyShape;
	private Pane _sketchyPane;
	private ArrayList<Saveable> _saveable;
	private ArrayList<SketchyShape> _sketchyShapeList;
	private Shape _shape;
	private Layer _layer;
	public LowerAShape(SketchyShape sketchyShape , Pane sketchyPane, ArrayList<Saveable> saveable, ArrayList<SketchyShape> sketchyShapeList, Layer layer) {
		_sketchyShape = sketchyShape;
		_sketchyPane = sketchyPane;
		_saveable = saveable;
		_sketchyShapeList = sketchyShapeList;
		_layer = layer;
	}

	@Override
	public void undo() {
		
		/** Removes the Saveable object selected from where it is, adds 
		 * it to the proper index, which is a increment of one index in the Observable
		 * list of children, Saveable ArrayList, and SketchyShape ArrayList.
		 */
		_sketchyPane.getChildren().remove(_sketchyShape.getNode());
		_sketchyPane.getChildren().add(_layer.getPaneIndex()+1, _sketchyShape.getNode());
		_saveable.remove(_sketchyShape);
		_saveable.add(_layer.getSaveablesIndex()+1, _sketchyShape);
		_sketchyShapeList.remove(_sketchyShape);
		_sketchyShapeList.add(_layer.getShapesIndex()+1, _sketchyShape);
	}

	@Override
	public void redo() {
		
		/** Removes the Saveable object selected from where it is, adds 
		 * it to the proper index, which is a decrement of one index in the Observable
		 * list of children, Saveable ArrayList, and SketchyShape ArrayList.
		 */
		_sketchyPane.getChildren().remove(_sketchyShape.getNode());
		_sketchyPane.getChildren().add(_layer.getPaneIndex(), _sketchyShape.getNode());
		_saveable.remove(_sketchyShape);
		_saveable.add(_layer.getSaveablesIndex(), _sketchyShape);
		_sketchyShapeList.remove(_sketchyShape);
		_sketchyShapeList.add(_layer.getShapesIndex(), _sketchyShape);
	}

}
