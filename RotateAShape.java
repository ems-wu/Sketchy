package Sketchy;

/**
 * This is my RotateAShape Command class that inherits the Command interface. It has undo and redo
 * methods that set the shape's rotation to before and after it has been rotate respectively. 
 */
public class RotateAShape implements Command{
	private SketchyShape _shape;
	private double _oldRotation;
	private double _newRotation;
	private double _currentRotation;
	private double _firstRotation;
	public RotateAShape(SketchyShape shape, double oldRotation) {
		_shape = shape;
		_oldRotation = oldRotation;
		_firstRotation = _shape.getRotate();
	}

	@Override
	public void undo() {
		// undoes rotation for shape
		_newRotation = _shape.getRotate();
		_shape.setRotate(_firstRotation - _oldRotation);
		
	}

	@Override
	public void redo() {
		// sets rotation of shape to after it was rotated
		_currentRotation = _shape.getRotate();
		_shape.setRotate(_newRotation - _currentRotation);
	}

}
