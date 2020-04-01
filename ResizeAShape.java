package Sketchy;

/**
 * This is my ResizeAShape Command class that inherits the Command interface. 
 */
public class ResizeAShape implements Command {
	private SketchyShape _shape;
	private double _width;
	private double _height;
	private double _width2;
	private double _height2;
	public ResizeAShape(SketchyShape shape, double oldWidth, double oldHeight) {
		_width = oldWidth;
		_height = oldHeight;
		_shape = shape;
	}

	@Override
	public void undo() {
		// sets shape width and height to previous values
		_width2 = _shape.getWidth();
		_height2 = _shape.getHeight();
		_shape.setWidth(_width);
		_shape.setLength(_height);
	
	}

	@Override
	public void redo() {
		// resets shape to size before undo
		_shape.setWidth(_width2);
		_shape.setLength(_height2);
	}

}
