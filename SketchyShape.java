package Sketchy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * This is my SketchyShape interface. SketchyRectangle and SketchyEllipse implement this interface.
 * This interface takes care of many of the shapes' methods such as move(), rotate, resizeShape(), etc. 
 */

public interface SketchyShape extends Saveable {
	
	public void move(Point2D prev, Point2D curr);
	
	public void rotate(Point2D prev, Point2D curr);
	
	public Point2D rotatePoint(Point2D pointToRotate, Point2D rotateAround, double angle);
	
	public void resizeShape(Point2D prev, Point2D curr);

	public void setLocation(double x, double y);
	
	public void setBorder(Color c);
	
	public Shape getNode();
	
	public double getRadius();
	
	public void setFill(Color c);
	
	public double getX();
	
	public double getY();
	
	public double getRotate();
	
	public double getCenterX();
	
	public double getCenterY();
	
	public double getWidth();
	
	public double getHeight();
	
	public void setWidth(double width);
	
	public void setLength(double length);

	public void setRotate(double d);
	
}
