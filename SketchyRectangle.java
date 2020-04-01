package Sketchy;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.lang.Math;
import java.util.ArrayList;

import cs015.fnl.SketchySupport.FileIO;

/**
 * Here in the SketchyRectangle class, I create my instance of Rectangle and inherit methods from
 * my SketchyShape interface, which I then define.
 */
public class SketchyRectangle implements SketchyShape {
	private Rectangle _rect;
	private Color _c;
	private Pane _sketchyRectanglePane;
	public SketchyRectangle(Pane sketchyRectanglePane){
		_rect = new Rectangle(0, 0);
		_sketchyRectanglePane = sketchyRectanglePane;
		_sketchyRectanglePane.getChildren().add(_rect);
		_c = Color.rgb(0, 0, 0);
	}
	
	@Override
	public double getCenterX() {
		// returns center x of rect
		return _rect.getX() + _rect.getWidth()/2;
	}
	
	@Override
	public double getCenterY() {
		// returns center y of rect
		return _rect.getY() + _rect.getHeight()/2;
	}
	
	@Override
	public Rectangle getNode() {
		// returns _rect
		return _rect;
	}
	
	@Override
	public void setFill(Color c) {
		// sets rect color
		_c = c;
		_rect.setFill(_c);	
		
	}
	
	@Override
	public void setLocation(double x, double y) {
		// sets x and y location of instance of SketchyRectangle
		_rect.setX(x);
		_rect.setY(y);
	}

	@Override
	public void move(Point2D prev, Point2D curr) {
		// translates selected shape according to previous and current mouse location
		double dX = curr.getX() - prev.getX();
		double dY = curr.getY() - prev.getY();
		_rect.setX(_rect.getX() + dX);
		_rect.setY(_rect.getY() + dY);
				
	}
	@Override
	public void rotate(Point2D prev, Point2D curr) {
		// takes in two points and rotates shape
		double centerX = this.getCenterX();
		double centerY = this.getCenterY();
		
		double angle =  Math.atan2(prev.getY() - centerY, prev.getX() -centerX) - Math.atan2(curr.getY()-centerY, curr.getX()-centerX);
		_rect.setRotate(_rect.getRotate()-Math.toDegrees(angle));
	}

	@Override
	public void resizeShape(Point2D prev, Point2D curr) {
//		// resizes the width and height of shape according to mouse drag
		double rotation = _rect.getRotate();
		Point2D oldCenter = new Point2D(this.getCenterX(), this.getCenterY());
		Point2D rotatedPrev = rotatePoint(prev, oldCenter, rotation);
		Point2D rotatedCurr = rotatePoint(curr, oldCenter, rotation);
		
		double dx = Math.abs(rotatedCurr.getX() - rotatedPrev.getX());
		double dy = Math.abs(rotatedCurr.getY() - rotatedPrev.getY());

			if((Math.abs(rotatedPrev.getX() - this.getCenterX()))>(Math.abs(rotatedCurr.getX() - this.getCenterX()))) {
				dx = -1 * dx;
			}
			
			if((Math.abs(rotatedPrev.getY() - this.getCenterY()))>(Math.abs(rotatedCurr.getY() - this.getCenterY()))) {
				dy = -1 *dy;
			}
			_rect.setWidth(_rect.getWidth() + 2*dx);		
			_rect.setHeight(_rect.getHeight() + 2*dy);
			
			Point2D newCenter = new Point2D(this.getCenterX(), this.getCenterY());
			if(oldCenter != newCenter) {
				dx = oldCenter.getX() - newCenter.getX();
				dy = oldCenter.getY() - newCenter.getY();

				    _rect.setX(_rect.getX() + dx);
					_rect.setY(_rect.getY() + dy);
			}
		}
	@Override
	public Point2D rotatePoint(Point2D pointToRotate, Point2D rotateAround, double angle) {
		// rotates point around another point
		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));
		// move our rotateAround to the "origin" so thatpointToRotate will rotate around (0,0)
				
		Point2D point1 = new Point2D(pointToRotate.getX() - rotateAround.getX(), pointToRotate.getY() -rotateAround.getY());
		// rotate the point by the input angle
//				
		Point2D point2 = new Point2D(point1.getX() *cosine + point1.getY() *sine, -point1.getX() *sine + point1.getY()*cosine);
		
			// return the point to its initial offset	
			return new Point2D(point2.getX() + rotateAround.getX(), point2.getY() + rotateAround.getY());
	}

	@Override
	public void setBorder(Color c) {
		// creates outline of shape
		_rect.setStroke(c);
			
		}

	@Override
	public double getRadius() {
		// method mainly for ellipses
		return 0;
	}
	
	@Override
	public double getX() {
		// returns x location of ellipse
		return _rect.getX();
	}

	@Override
	public double getY() {
		// returns y location of ellipse
		return _rect.getY();
	}

	@Override
	public double getWidth() {
		// returns rect width
		return _rect.getWidth();
	}

	@Override
	public double getHeight() {
		// returns rect height
		return _rect.getHeight();
	}

	@Override
	public void setWidth(double width) {
		// sets width of shape
		_rect.setWidth(width);
		
	}

	@Override
	public void setLength(double length) {
		// sets height of shape
		_rect.setHeight(length);
		
	}
	@Override
	public double getRotate() {
		// gets rotation
		return _rect.getRotate();
	}
	@Override
	public void setRotate(double d) {
		// sets rotations
		_rect.setRotate(d);
	}
	@Override
	public void save(FileIO io) {
		// saves the shape
		io.writeString("Rectangle");
		io.writeDouble(_rect.getX());
		io.writeDouble(_rect.getY());
		io.writeDouble(_rect.getWidth());
		io.writeDouble(_rect.getHeight());
		io.writeDouble(_rect.getRotate());
		io.writeInt((int) (_c.getRed()*255));
		io.writeInt((int) (_c.getGreen()*255));
		io.writeInt((int) (_c.getBlue()*255));
		
	}
	@Override
	public void load(FileIO io) {
		// loads the shape
		_rect.setX(io.readDouble());
		_rect.setY(io.readDouble());
		_rect.setWidth(io.readDouble());
		_rect.setHeight(io.readDouble());
		_rect.setRotate(io.readDouble());
		_rect.setFill(Color.rgb(io.readInt(),io.readInt(),io.readInt()));
		
	}


}
