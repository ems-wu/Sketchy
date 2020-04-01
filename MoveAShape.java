package Sketchy;

/**
 * This is my MoveAShape Command class that inherits the Command interface. 
 */
public class MoveAShape implements Command {
	private SketchyShape _shape;
	private double _xLocation;
	private double _yLocation;
	private double _newXLocation;
	private double _newYLocation;
	public MoveAShape(SketchyShape shape) {
		_shape = shape;
		_xLocation  = _shape.getX(); 
		_yLocation  = _shape.getY();
	}

	@Override
	public void undo() {
		// resets location of shape
		_shape.setLocation(_xLocation, _yLocation);
		_newXLocation = _shape.getX();
		_newYLocation = _shape.getY();
	}

	@Override
	public void redo() {
		// set location of target shape to after
		_shape.setLocation(_newXLocation,_newYLocation);	
	}
}
