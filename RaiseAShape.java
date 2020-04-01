package Sketchy;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * This is my RaiseAShape Command class that inherits the Command interface. 
 */
public class RaiseAShape implements Command{
	private SketchyShape _sketchyShape;
	private Pane _sketchyPane;
	private ArrayList<Saveable> _saveable;
	private ArrayList<SketchyShape> _sketchyShapeList;
	private Shape _shape;
	private Layer _layer;
	public RaiseAShape(SketchyShape sketchyShape , Pane sketchyPane, ArrayList<Saveable> saveable, ArrayList<SketchyShape> sketchyShapeList, Layer layer) {
		_sketchyShape = sketchyShape;
		_sketchyPane = sketchyPane;
		_saveable = saveable;
		_sketchyShapeList = sketchyShapeList;
		_layer = layer;
	}

	@Override
	public void undo() {
	
		/**
		 * This method lowers the SketchyShape selected up one index in the Observable
		 * List of children, the Saveable ArrayList, and the SketchyShape ArrayList. 
		 */
		_sketchyPane.getChildren().remove(_sketchyShape.getNode());
		_sketchyPane.getChildren().add(_layer.getPaneIndex()-1, _sketchyShape.getNode());
		_saveable.remove(_sketchyShape);
		_saveable.add(_layer.getSaveablesIndex()-1, _sketchyShape);
		_sketchyShapeList.remove(_sketchyShape);
		_sketchyShapeList.add(_layer.getShapesIndex()-1, _sketchyShape);
	}

	@Override
	public void redo() {
		
		/**
		 * This method raises the SketchyShape selected up one index in the Observable
		 * List of children, the Saveable ArrayList, and the SketchyShape ArrayList. 
		 */
		_sketchyPane.getChildren().remove(_sketchyShape.getNode());
		_sketchyPane.getChildren().add(_layer.getPaneIndex(), _sketchyShape.getNode());
		_saveable.remove(_sketchyShape);
		_saveable.add(_layer.getSaveablesIndex(), _sketchyShape);
		_sketchyShapeList.remove(_sketchyShape);
		_sketchyShapeList.add(_layer.getShapesIndex(), _sketchyShape);
	}

}
