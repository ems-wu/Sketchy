package Sketchy;

import cs015.fnl.SketchySupport.FileIO;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 * Here in the CurvedLine class, I create my instance of Polyline and add it to the pane.
 */

public class CurvedLine implements Saveable {
	private Polyline _polyline;
	private Color _c;
	public CurvedLine(Pane curvedLinePane) {
		_polyline = new Polyline();
		curvedLinePane.getChildren().add(_polyline);
		_c = Color.rgb(0, 0, 0);
	}
	
	public ObservableList<Double> returnPoints(){
		return _polyline.getPoints();	
	}
	
	public Polyline getPolyline(){
		return _polyline;
	}

	@Override
	public void save(FileIO io) {
		// saves attributes of polyline into a file
		io.writeString("CurvedLine");
		io.writeInt((int) (_c.getRed()*255));
		io.writeInt((int) (_c.getGreen()*255));
		io.writeInt((int) (_c.getBlue()*255));
		// Store the number of points we actually have 
		io.writeInt(_polyline.getPoints().size()/2);
		
		// Store each of the points
		for (Double point : _polyline.getPoints()) {
			io.writeDouble(point);
		}
	}

	@Override
	public void load(FileIO io) {
		
		//loads all of the attributes of polyline
		_polyline.setStroke(Color.rgb(io.readInt(),io.readInt(),io.readInt()));
		int numPoints = io.readInt();
		// Load all of the points 
		for (int i = 0; i < numPoints; i++) {
			double xPos = io.readDouble();
			double yPos = io.readDouble();
			_polyline.getPoints().addAll(xPos, yPos);
		}
	}

	@Override
	public Node getNode() {
		// returns instance of polyline when called
		return _polyline;
	}

	public void setStroke(Color currentColor) {
		// sets color of line to currentColor
		_polyline.setStroke(currentColor);
		_c = currentColor;
	}
}
