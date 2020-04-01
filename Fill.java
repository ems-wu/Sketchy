package Sketchy;

import javafx.scene.paint.Color;

/**
 * This is my Fill Command class that inherits the command interface. It has undo and redo
 * methods that undoes and redoes the filling of a shape's color.
 */
public class Fill implements Command {
	private Color _color;
	private Color _newColor;
	private SketchyShape _shape;
	
	public Fill(Color color, SketchyShape shape, Color newColor) {
		_color = color;
		_shape = shape;
		_newColor = newColor;
	}

	@Override
	public void undo() {
		// resets color of shape to original color
		_shape.setFill(_color);
	}

	@Override
	public void redo() {
		// undoes the resetting of a shape's color
		_shape.setFill(_newColor);
	}

}
