package Sketchy;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Here in my Control class, I set up all the buttons and their key handlers. 
 * It maintains all the logic for the buttons on the left column and 
 * communicates with the Sketchy Class whenever a button or color is picked.
 */
	public class Control {
		private Sketchy _sketchy;
		private Pane _controlPane;
		private RadioButton _selectShape;
		private RadioButton _drawWithPen;
		private RadioButton _drawRectangle;
		private RadioButton _drawEllipse;
		private ColorPicker _colorPicker;
		private Button _fill;
		private Button _delete;
		private Button _raise;
		private Button _lower;
		private Button _undo;
		private Button _redo;
		private Button _save;
		private Button _load;
		
		public Control (Pane controlPane, Sketchy sketchy) {
			_controlPane = controlPane;
			_sketchy = sketchy;
			this.createShapeandOperations();
		}

	private void createShapeandOperations() {
		
		/**
		 * This method creates all the buttons, labels, and the color picker
		 * for the left hand column of Sketchy. It assigns each button to the 
		 * ClickHandler. 
		 */
		VBox holder = new VBox(Constants.BUTTON_SPACING);
		_controlPane.getChildren().add(holder);
		Label drawingOptions = new Label("Drawing Options");
		_selectShape = new RadioButton("Select Shape");
		_drawWithPen = new RadioButton("Draw With Pen");
		_drawRectangle = new RadioButton("Draw Rectangle");
		_drawEllipse = new RadioButton("Draw Ellipse");
		_raise = new Button("Raise");
		_lower = new Button("Lower");
		_fill = new Button ("Fill");
		_delete = new Button ("Delete");
		_undo = new Button("Undo");
		_redo = new Button("Redo");
		_save = new Button ("Save");
		_load = new Button ("Load");
		_colorPicker = new ColorPicker(Color.LIGHTBLUE);
		
		// creates labels
		Label colorPickerLabel = new Label("Set The Color");
		Label shapeActions = new Label("Shape Actions");
		Label operations = new Label("Operations");
	
		
		ToggleGroup group = new ToggleGroup();
		_selectShape.setToggleGroup(group);
		_drawWithPen.setToggleGroup(group);
		_drawRectangle.setToggleGroup(group);
		_drawEllipse.setToggleGroup(group);
		
		// assigns each button to the click handler
		_selectShape.setOnAction(new ClickHandlerButtons());
		_drawWithPen.setOnAction(new ClickHandlerButtons());
		_drawRectangle.setOnAction(new ClickHandlerButtons());
		_drawEllipse.setOnAction(new ClickHandlerButtons());
		_colorPicker.setOnAction(new ClickHandlerButtons());
		_raise.setOnAction(new ClickHandlerButtons());
		_lower.setOnAction(new ClickHandlerButtons());
		_fill.setOnAction(new ClickHandlerButtons());
		_delete.setOnAction(new ClickHandlerButtons());		
		_undo.setOnAction(new ClickHandlerButtons());
		_redo.setOnAction(new ClickHandlerButtons());
		_save.setOnAction(new ClickHandlerButtons());
		_load.setOnAction(new ClickHandlerButtons());
		
		// adds buttons to pane
		holder.getChildren().addAll(drawingOptions, 
				_selectShape, 
				_drawWithPen, 
				_drawRectangle, 
				_drawEllipse, 
				colorPickerLabel, 
				_colorPicker, 
				shapeActions,
				_raise, 
				_lower, 
				_fill, 
				_delete, 
				operations, 
				_undo, 
				_redo, 
				_save, 
				_load);
		holder.setAlignment(Pos.CENTER);
		
	
	
	}
		
	
	private class ClickHandlerButtons implements EventHandler<ActionEvent> {
		
		/** Here I have a EventHandler for all the buttons on my button pane which checks which button is 
		* pressed and communicates with the Sketchy class accordingly. 
		**/
		@Override
		public void handle(ActionEvent event) {
			if(event.getSource().equals(_selectShape)){
			_sketchy.setAction(SketchyAction.SELECT_SHAPE);
			}
			
			else if(event.getSource().equals(_drawWithPen)) {
			_sketchy.setAction(SketchyAction.DRAW_LINE);
			}
			
			else if(event.getSource().equals(_drawRectangle)) {
	
				_sketchy.setAction(SketchyAction.DRAW_RECT);				
			}
			
			else if(event.getSource().equals(_drawEllipse)) {
				_sketchy.setAction(SketchyAction.DRAW_ELLIPSE);
			}
			
			else if(event.getSource().equals(_raise)) {
				_sketchy.raise();
			}
			
			else if(event.getSource().equals(_lower)) {
				_sketchy.lower();
			}
			
			else if(event.getSource().equals(_fill)) {
				_sketchy.fillShape(_colorPicker.getValue());
			}
			
			else if(event.getSource().equals(_colorPicker)) {	
			_sketchy.setColor(_colorPicker.getValue());
				
			}
			
			else if(event.getSource().equals(_delete)) {
				_sketchy.delete();
			}
			
			else if(event.getSource().equals(_undo)) {
				_sketchy.undo();
			}
			
			else if(event.getSource().equals(_redo)) {
				_sketchy.redo();
			}
			
			else if(event.getSource().equals(_save)) {
				_sketchy.save();
			}
			
			else if(event.getSource().equals(_load)) {
				_sketchy.load();
			}
		}	
	}
}

