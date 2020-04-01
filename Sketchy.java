package Sketchy;

import java.util.ArrayList;
import java.util.Stack;
import cs015.fnl.SketchySupport.FileIO;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * This is my Sketchy class. This is where I have my Mouse Handler that takes care of all
 * of the methods relating to the buttons. Here, I create an instance variable for my SketchyAction
 * enums, create an instance of the undo and redo command stacks, and instantiate the SketchyShape
 * and Saveable array lists.
 */
public class Sketchy {
	private Pane _sketchyPane;
	private ArrayList<SketchyShape> _sketchyShapeArrayList;
	private ArrayList<Saveable> _saveableArrayList;
	private SketchyAction _action;
	private SketchyRectangle _sketchyRectangle;
	private SketchyEllipse _sketchyEllipse;    
	private Color _currentColor = Color.LIGHTBLUE;
	private Color _newColor;
	private Point2D _prev;
	private Point2D _prev1;
	private Point2D _prev2;
	private Point2D _curr;
	private double _oldX;
	private double _oldY;
	private double _oldRotation;
	private CurvedLine _polyline;
	private SketchyShape _selected;
	private Stack<Command> _undoStack;
	private Stack<Command> _redoStack;
	
	public Sketchy(Pane sketchyPane) {
		_action = SketchyAction.NULL; //sets enum 
		_sketchyPane = sketchyPane;
		
		//listens to any type of mouse event
		_sketchyPane.addEventHandler(MouseEvent.ANY, new MouseHandler()); 
	
		// array list that can store a possibly infinite amount of SketchyShapes
		_sketchyShapeArrayList = new ArrayList<SketchyShape>();
		// array list for saveable SketchyRects, SketchyEllipses, and CurvedLines
		_saveableArrayList = new ArrayList<Saveable>();
		
		// stacks that model how actions are done/undone
		_undoStack = new Stack<Command>();
		_redoStack = new Stack<Command>();
		
		// creates canvas and adds to pane
		Rectangle canvas = new Rectangle((3*Constants.WINDOW_WIDTH)/4, Constants.WINDOW_HEIGHT, Color.WHITE);
		_sketchyPane.getChildren().add(canvas);
	}
	
	public void setAction(SketchyAction action) {
		
		// helper method that sets action enum in Control class
		_action = action;
	}
	
	public void setColor(Color c) {
		
		//store currently selected color
		_currentColor = c;
	}
	
	public void fillShape(Color c) {
		
		// helper method that sets color to what color is passed in for shape in SketchyShapes arraylist
		if(_selected != null) {
			Color temp = (Color) _selected.getNode().getFill();
			_selected.setFill(c);
			_newColor = c;
			
			// creates instance of fill class, pushes it onto undo stack and clears redo stack
			Fill fill = new Fill(temp, _selected, _newColor);
			_undoStack.push(fill);
			_redoStack.clear();
		}
	}
	
	public void undo() {
		
		/**
		 * This method undoes a command. It creates an instance of each command
		 * and sets it equal to the object returned from calling pop on the instance
		 * of the undo stack. Function undo is called on instance of command and command
		 * is pushed onto undo stack. Each individual command inherits undo command from 
		 * Command interface and defines its own command. 
		 */
		 if(!_undoStack.empty()) { //makes sure undo stack is not empty
			int prevSize = _saveableArrayList.size();
			Command command = _undoStack.pop();
			command.undo();
			int afterSize = _saveableArrayList.size();
			/**
			 * If the amount of objects in the array list decreased after the undo, 
			 * we have to make sure that selected is null because we deleted something!
			 */
			if (prevSize > afterSize) {
				_selected = null;
			}
			_redoStack.push(command);
		 }
	}
	
	public void redo() {
		
		/**
		 * This method resets the undo method. It creates an instance of each command
		 * and sets it equal to the object returned from calling pop on the instance
		 * of the redo stack. Function redo is called on instance of command and command
		 * is pushed onto undo stack. Each individual command inherits redo command from 
		 * Command interface and defines its own command. 
		 */
		 if(!_redoStack.empty()) { //makes sure redo stack is not empty
		Command command = _redoStack.pop();
		command.redo();
		_undoStack.push(command);  
		 }
	}
	
	public void raise() {
		
		/**
		 * This method raises the SketchyShape selected up one index in the Observable
		 * List of children, the Saveable ArrayList, and the SketchyShape ArrayList. 
		 */
		if(_selected!=null) { //check to make sure _selected is not null
		int paneIndex = _sketchyPane.getChildren().indexOf(_selected.getNode());
		// if this is NOT the topmost layer
		if(paneIndex < _sketchyPane.getChildren().size() - 1) {		
			_sketchyPane.getChildren().remove(_selected.getNode()); //removes node from pane
			_sketchyPane.getChildren().add(paneIndex + 1, _selected.getNode()); //adds node one higher in index
			
			/**
			 * Check to make sure saveable index index is always at least 0 and makes sure
			 * that if we increase index by one, it is still less than size of saveable
			 * ArrayList!
			 */
			int saveableIndex = _saveableArrayList.indexOf(_selected);
			if (saveableIndex >= 0 && saveableIndex + 1 < _saveableArrayList.size()) {
				_saveableArrayList.remove(_selected);
				_saveableArrayList.add(saveableIndex + 1, _selected);
			}
		
			int shapeIndex = _sketchyShapeArrayList.indexOf(_selected);
			
			/**
			 * Check to make sure it was found (it always will be found), and to make sure that 
			 * it's not the topmost layer :D. Function indexOf() returns -1 if element not in list, 
			 * so this confirms whether or not this is SketchyShape or line
			 */
			if (shapeIndex >= 0 && shapeIndex + 1 < _sketchyShapeArrayList.size()) {
				_sketchyShapeArrayList.remove(_selected);
				// Shapeindex + 1 IS ALLOWED TO BE == Size
				_sketchyShapeArrayList.add(shapeIndex + 1, _selected);
			}
			
			//new instance of layer created and assigned to new instance of command
			Layer layer = createLayer(_selected);
			RaiseAShape raiseShape = new RaiseAShape(_selected, _sketchyPane, _saveableArrayList, _sketchyShapeArrayList, layer);
			_undoStack.push(raiseShape); // adds raise command to undo stack
			_redoStack.clear(); // clears redo stack
			}
		}
	}
	
	public void lower() {
		
		/**
		 * This method removes the Saveable object selected from where it is, adds 
		 * it to the proper index, which is a decrement of one index in the Observable
		 * list of children, Saveable ArrayList, and SketchyShape ArrayList.
		 */
		// remove from index where it is, add to proper index
		//decrement one index for observable, saveable, and sketchyshape list
		if(_selected!=null) { // check to make sure _selected is not null!
		int paneIndex = _sketchyPane.getChildren().indexOf(_selected.getNode());
		
		// absolute lowest layer has index 1
		if(paneIndex > 1) {		
			_sketchyPane.getChildren().remove(_selected.getNode());
			_sketchyPane.getChildren().add(paneIndex - 1, _selected.getNode());
			
			int saveableIndex = _saveableArrayList.indexOf(_selected);
			if (saveableIndex - 1 >= 0) {
				_saveableArrayList.remove(_selected);
				_saveableArrayList.add(saveableIndex - 1, _selected);
			}
			
			int shapeIndex = _sketchyShapeArrayList.indexOf(_selected);
			// check to make sure shape index is at least 0 AFTER it has been lowered
			if (shapeIndex - 1 >= 0) {
				_sketchyShapeArrayList.remove(_selected);
				_sketchyShapeArrayList.add(shapeIndex - 1, _selected);
			}
			
			//new instance of layer created and assigned to new instance of command
			Layer layer = createLayer(_selected);
			LowerAShape lowerShape = new LowerAShape(_selected, _sketchyPane, _saveableArrayList, _sketchyShapeArrayList, layer);
			_undoStack.push(lowerShape); //adds command to undo stack
			_redoStack.clear(); //clears undo stack
			}
		}
	}
	
	public void save() {
		
		// this method saves each shape and line into a file. 
		FileIO io = new FileIO();
		String filepath = FileIO.getFileName(true, _sketchyPane.getScene().getWindow());
		// Make sure the person actually clicked save and typed in something valid
		if (filepath != null) {
			io.openWrite(filepath);
			
			// iterating through all of saveables in ArrayList
			for(int i = 0; i < _saveableArrayList.size(); i++) {
				_saveableArrayList.get(i).save(io);
			}
			io.closeWrite();
		}
	}
	
	public void load() {
		
		// this method loads the file of a previous drawing	
		FileIO io = new FileIO();
		String filepath = FileIO.getFileName(false, _sketchyPane.getScene().getWindow());
		// Make sure the person actually clicked load and confirm on something valid
		if (filepath != null) {
			io.openRead(filepath);
			_sketchyPane.getChildren().clear();
			_sketchyShapeArrayList.clear();
			_saveableArrayList.clear();
			_undoStack.clear();
			_redoStack.clear();
			_selected = null;
			Rectangle canvas = new Rectangle((3*Constants.WINDOW_WIDTH)/4, Constants.WINDOW_HEIGHT, Color.WHITE);
			_sketchyPane.getChildren().add(canvas);
			while(io.hasMoreData()) {
				// Step 1 -- Get the string name, representing the type of object
				String type = io.readString().toLowerCase();
				
				// Step 2 -- SWITCH based on each type of object
				// Step 3 -- create a new ShetchyShape according to the switch
				if (type.equals("rectangle")) {
					SketchyShape temp = new SketchyRectangle(_sketchyPane); 
					temp.load(io);
					_sketchyShapeArrayList.add(temp); 
					_saveableArrayList.add(temp);
					_prev1 = new Point2D(temp.getX(), temp.getY());
				} else if (type.equals("curvedline")) {
					Saveable temp = new CurvedLine(_sketchyPane);
					temp.load(io);
					_saveableArrayList.add(temp);
				} else if (type.equals("ellipse")){
					SketchyShape temp = new SketchyEllipse(_sketchyPane);
					temp.load(io);
					_sketchyShapeArrayList.add(temp); 
					_saveableArrayList.add(temp);
				} else {
					// This is bad data :(
					System.out.println("file bad :(");
					break;
				}
				// Add this new shape to the arraylist of shapes (saveableArrayList)
				
			}
			
			io.closeRead();
		}
	}
	
	public void delete() {
		
		// deletes selected shape from pane and arraylists when delete butotn is clicked on
		if(_selected!=null) { // makes sure selected is not null
			Layer layer = createLayer(_selected);
			_sketchyPane.getChildren().remove(_selected.getNode());
			_sketchyShapeArrayList.remove(_selected);
			_saveableArrayList.remove(_selected);
			_selected.setBorder(Color.TRANSPARENT);
			DeleteAShape deletedShape = new DeleteAShape(_selected, _sketchyPane, _saveableArrayList, _sketchyShapeArrayList, layer);
			_undoStack.push(deletedShape);
			_redoStack.clear();
			_selected = null; // sets selected to null so new instances of command won't be called
		}
	}
	
	private Layer createLayer(SketchyShape shape) {
		
		/**
		 * This method assigns a layer to each shape that keeps track of the Shape's
		 * indicies in the ArrayList of children, Saveables, and SketchyShapes. 
		 */
		int paneIndex = _sketchyPane.getChildren().indexOf(_selected.getNode());
		int saveableIndex = _saveableArrayList.indexOf(_selected);
		int shapesIndex = _sketchyShapeArrayList.indexOf(_selected);
		return new Layer(paneIndex, saveableIndex, shapesIndex);
	}
	
	private class MouseHandler implements EventHandler<MouseEvent> {
		
		//automatically triggers whenever any MouseEvent occurs
		@Override
		public void handle(MouseEvent event) {
			
			switch(_action) { 
			//switch based on _action enum
			case DRAW_RECT:
				if(event.getEventType()==MouseEvent.MOUSE_PRESSED) { //filters event type
					if (_selected != null) { // makes non-selected shapes lose the outline
						_selected.setBorder(Color.TRANSPARENT);
					}
					
					//creating new SketchyRectangle then adding to arraylist
					_sketchyRectangle = new SketchyRectangle(_sketchyPane); 
					_sketchyShapeArrayList.add(_sketchyRectangle); 
					_saveableArrayList.add(_sketchyRectangle);
					_selected = _sketchyRectangle;
					_selected.setBorder(Color.BLACK);
					_prev1 = new Point2D(event.getX(),event.getY());	
					_selected.setFill(_currentColor);
					//gets location of the mouse click and sets location of rectangle
					_selected.setLocation(event.getX(),event.getY());
				}
				
				// if mouse is dragged, resize shape and reset prev and curr point
				if(event.getEventType()==MouseEvent.MOUSE_DRAGGED) {
					Point2D curr1 = new Point2D(event.getX(),event.getY());
					_selected.resizeShape(_prev1, curr1);	
					_prev1 = curr1;
				} 
				
				// if mouse released, create new instance of CreateAShape and add to undo stack
				if(event.getEventType()==MouseEvent.MOUSE_RELEASED) {
					CreateAShape createdShape = new CreateAShape(_selected, _sketchyPane, _saveableArrayList, _sketchyShapeArrayList);
					_undoStack.push(createdShape);
					_redoStack.clear();
				}
				break;
				
			case DRAW_ELLIPSE:
				if(event.getEventType()==MouseEvent.MOUSE_PRESSED) { //filters event type
					if (_selected != null) {
						_selected.setBorder(Color.TRANSPARENT);
					}
					_prev2 = new Point2D(event.getX(),event.getY());
					//creating new SketchyRectangle then adding to arraylist
					_sketchyEllipse = new SketchyEllipse(_sketchyPane);  
					_selected = _sketchyEllipse;
					_selected.setFill(_currentColor);
					_selected.setBorder(Color.BLACK);
					//get the location of the mouse click and sets location of rectangle to it
					_selected.setLocation(event.getX()-((1/2) * _selected.getRadius()),event.getY());
					_sketchyShapeArrayList.add(_selected); 
					_saveableArrayList.add(_selected);
				}
				
				if(event.getEventType()==MouseEvent.MOUSE_DRAGGED) {
				// if mouse is dragged, resizes shape and resets current mouse point to prev	
					Point2D curr1 = new Point2D(event.getX(),event.getY());
					_selected.resizeShape(_prev2, curr1);	
					_prev2 = curr1;
				}
				
				// if mouse released, create new instance of CreateAShape and add to undo stack
				if(event.getEventType()==MouseEvent.MOUSE_RELEASED) {
					CreateAShape createdShape = new CreateAShape(_selected, _sketchyPane, _saveableArrayList, _sketchyShapeArrayList);
					_undoStack.push(createdShape);
					_redoStack.clear();
				}
				break;
				
			case SELECT_SHAPE:
				if(event.getEventType()==MouseEvent.MOUSE_PRESSED) { 
				/** if mouse is clicked, loop through array list and if the node contains the mouse point,
				* set _selected equal to the element on the array list and make the border black
				*/
					Point2D mousePoint = new Point2D(event.getX(),event.getY());
					//get current location of point and sets it to _prev so mousepoint constantly updates
					_prev = mousePoint; 
					_oldX = event.getX();
					_oldY = event.getY();			
					
					// We can only set border of prev selected shape if it exists
					if (_selected != null) {
						_selected.setBorder(Color.TRANSPARENT);
					}
		
					// We now have nothing selected!
					_selected = null;
					for (int i = _sketchyShapeArrayList.size() - 1; i >= 0; i--) {
						
						SketchyShape shape = _sketchyShapeArrayList.get(i);
						Point2D rotateAround = new Point2D(shape.getCenterX(), shape.getCenterY());
						Point2D rotatedClick = shape.rotatePoint(mousePoint, rotateAround, shape.getRotate());
						// CHECK CONTAINS WITH ROTATED CLICK
						if( _sketchyShapeArrayList.get(i).getNode().contains(rotatedClick)) {
							_selected = _sketchyShapeArrayList.get(i);
							_selected.setBorder(Color.BLACK);
							break;
						}
					}
				}
				
				else if(event.getEventType()==MouseEvent.MOUSE_DRAGGED) { 
					//if mouse drags shape, shape location updates according to mouse location
					if (_selected != null) {
						_oldRotation = _selected.getRotate();
						_curr = new Point2D(event.getX(),event.getY());
							
						// if control is pressed, rotates selected shape
							if(event.isControlDown()) {
								_selected.rotate(_prev, _curr);
							}
							
							// if shift pressed, resizes selected shape
							else if(event.isShiftDown()) {
								_selected.resizeShape(_prev, _curr);
							}
							
							else {
								_selected.move(_prev, _curr);
							}
							_prev = _curr; //resets curr mouse point to prev
						}
					
				/**
				 *  Filtering out mouse events for specific command and creates instance of command
				 *  to push onto undo stack. Clears redo stack after. 
				 */
				} else if(event.getEventType()==MouseEvent.MOUSE_RELEASED && !event.isShiftDown() && !event.isControlDown()) {
					if (_selected != null) { // check to make sure _selected is not null
						MoveAShape movedShape = new MoveAShape(_selected);
						_undoStack.push(movedShape);
						_redoStack.clear();
					}
					
				} else if(event.getEventType()==MouseEvent.MOUSE_RELEASED && event.isShiftDown() && !event.isControlDown()) { 
					if (_selected != null) {
						ResizeAShape resizedShape = new ResizeAShape(_selected, _oldX, _oldY);
						_undoStack.push(resizedShape);
						_redoStack.clear();
					}
					
				} else if(event.getEventType()==MouseEvent.MOUSE_RELEASED && !event.isShiftDown()&& event.isControlDown()) {
					if (_selected != null) {
						RotateAShape rotatedShape = new RotateAShape(_selected, _oldRotation);
						_undoStack.push(rotatedShape);
						_redoStack.clear();
					}
				}
				break;
				
			case DRAW_LINE:
					if(event.getEventType()==MouseEvent.MOUSE_PRESSED) {
						// when mouse clicked, new instance of CurvedLine created
						_polyline = new CurvedLine(_sketchyPane);
						_saveableArrayList.add(_polyline);
						_polyline.setStroke(_currentColor);
						DrawALine drawLine = new DrawALine(_polyline, _sketchyPane, _saveableArrayList);
						_undoStack.push(drawLine);
						_redoStack.clear();
					}
					
					else if(event.getEventType()==MouseEvent.MOUSE_DRAGGED) {
						// when mouse dragged, all points added to _polyline array
						_polyline.returnPoints().addAll(event.getX(), event.getY()); 
					}
				break;				
			default:
				break;
			}
			event.consume();
			}
	}
}
		
	


