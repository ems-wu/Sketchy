package Sketchy;

import java.util.ArrayList;

import javafx.scene.layout.Pane;

/**
 * This is my Draw a Line Command class that inherits the command interface. 
 */
public class DrawALine implements Command {
	private CurvedLine _curvedLine;
	private Pane _sketchyPane;
	private ArrayList<Saveable> _saveable;
	public DrawALine(CurvedLine curvedLine, Pane sketchyPane, ArrayList<Saveable> saveable) {
		//take in line you drew, sketchyPane, saveables
		_curvedLine = curvedLine;
		_sketchyPane = sketchyPane;
		_saveable = saveable;
	}
	
	@Override
	public void undo() {
		//removes line from sketchy pane saveables
		_sketchyPane.getChildren().remove(_curvedLine.getPolyline());
		_saveable.remove(_curvedLine);
		
	}

	@Override
	public void redo() {
		//add to pane, add back to saveables
		_sketchyPane.getChildren().add(_curvedLine.getPolyline());
		_saveable.add(_curvedLine);
	}

}
