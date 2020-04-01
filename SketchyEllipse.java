package Sketchy;

import cs015.fnl.SketchySupport.FileIO;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Here in the SketchyEllipse class, I create my instance of Ellipse and inherit methods from
 * my SketchyShape interface, which I then define.
 */
public class SketchyEllipse implements SketchyShape {
	private Ellipse _ellipse;
	private Pane _sketchyEllipse;
	private Color _c;
	public SketchyEllipse(Pane sketchyEllipse){
		_ellipse = new Ellipse(0,0);
		_sketchyEllipse = sketchyEllipse;
		_sketchyEllipse.getChildren().add(_ellipse);
		_c = Color.rgb(0, 0, 0);
	}
	public Color getColor() {
		// returns color of Ellipse
		return _c;
	}
	@Override
	public Ellipse getNode() {
		// returns ellipse
		return _ellipse;
	}
	
	@Override
	public void setFill(Color c) {
		// sets ellipse color
		_c = c;
		_ellipse.setFill(_c);
	}
	
	@Override
	public double getRadius() {
		// returns ellipse radius
		return _ellipse.getRadiusX();
	}

	@Override
	public void move(Point2D prev, Point2D curr) {
		// translates selected shape according to previous and current mouse location
		double dX = curr.getX() - prev.getX();
		double dY = curr.getY() - prev.getY();
		_ellipse.setCenterX(_ellipse.getCenterX() + dX);
		_ellipse.setCenterY(_ellipse.getCenterY() + dY);
	}

	@Override
	public void rotate(Point2D prev, Point2D curr) {
		// takes in two points and rotates shape
		double centerX = _ellipse.getCenterX();
		double centerY = _ellipse.getCenterY();
		double angle =  Math.atan2(prev.getY() - centerY, prev.getX() -centerX) - Math.atan2(curr.getY()-centerY, curr.getX()-centerX);
		
		_ellipse.setRotate(_ellipse.getRotate()-Math.toDegrees(angle));
	}

	@Override
	public void setLocation(double x, double y) {
		// sets x and y location of ellipse
		_ellipse.setCenterX(x);
		_ellipse.setCenterY(y);
		
	}

	@Override
	public void resizeShape(Point2D prev, Point2D curr) {
//		// resizes the width and height of shape according to mouse drag
		double rotation = _ellipse.getRotate();
		Point2D oldCenter = new Point2D(_ellipse.getCenterX(), _ellipse.getCenterY());
		Point2D rotatedPrev = rotatePoint(prev, oldCenter, rotation);
		Point2D rotatedCurr = rotatePoint(curr, oldCenter, rotation);
		
		double dx = Math.abs(rotatedCurr.getX() - rotatedPrev.getX());
		double dy = Math.abs(rotatedCurr.getY() - rotatedPrev.getY());

			if((Math.abs(rotatedPrev.getX() - _ellipse.getCenterX()))>(Math.abs(rotatedCurr.getX() - _ellipse.getCenterX()))) {
				dx = -1 * dx;
			}
			
			if((Math.abs(rotatedPrev.getY() - _ellipse.getCenterY()))>(Math.abs(rotatedCurr.getY() - _ellipse.getCenterY()))) {
				dy = -1 *dy;
			}
			_ellipse.setRadiusX((_ellipse.getRadiusX() + 2*dx));		
			_ellipse.setRadiusY((_ellipse.getRadiusY() + 2*dy));
			
			Point2D newCenter = new Point2D(_ellipse.getCenterX(), _ellipse.getCenterY());
			if(oldCenter != newCenter) {
				dx = oldCenter.getX() - newCenter.getX();
				dy = oldCenter.getY() - newCenter.getY();

				_ellipse.setCenterX(_ellipse.getCenterX());
				_ellipse.setCenterY(_ellipse.getCenterY());
			}
		
	}

	@Override
	public Point2D rotatePoint(Point2D pointToRotate, Point2D rotateAround, double angle) {
		// rotates point around another point
		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));
//				move our rotateAround to the "origin" so that
//				pointToRotate will rotate around (0,0)
//				
		Point2D point1 = new Point2D(pointToRotate.getX() - rotateAround.getX(), pointToRotate.getY() -rotateAround.getY());
//				rotate the point by the input angle
//				
		Point2D point2 = new Point2D(point1.getX() *cosine + point1.getY() *sine, -point1.getX() *sine + point1.getY()*cosine);
		
//				return the point to its initial offset
//						
				return new Point2D(point2.getX() + rotateAround.getX(), point2.getY() + rotateAround.getY());
//				return point
		//when you call this, make sure that the point you pass in is the mouse and rotateAround is center of the shape
	}

	@Override
	public void setBorder(Color c) {
		// sets border of ellipse to black 
		_ellipse.setStroke(c);
		
	}

	@Override
	public double getX() {
		// returns x location of ellipse
		return _ellipse.getCenterX();
	}

	@Override
	public double getY() {
		// returns y location of ellipse
		return _ellipse.getCenterY();
	}

	@Override
	public double getWidth() {
		// gets x radius of ellipse
		return _ellipse.getRadiusX();
	}

	@Override
	public double getHeight() {
		// gets y radius of ellipse
		return _ellipse.getRadiusY();
	}

	@Override
	public void setWidth(double width) {
		// sets width of shape
		_ellipse.setRadiusX(width);
	}

	@Override
	public void setLength(double length) {
		// sets length of shape
		_ellipse.setRadiusY(length);
	}

	@Override
	public double getRotate() {
		// gets rotation
		return _ellipse.getRotate();
	}

	@Override
	public void setRotate(double d) {
		// // sets rotations
		_ellipse.setRotate(d);
	}

	@Override
	public void save(FileIO io) {
		// saves the shape
		io.writeString("Ellipse");
		io.writeDouble(_ellipse.getCenterX());
		io.writeDouble(_ellipse.getCenterY());
		io.writeDouble(_ellipse.getRadiusX());
		io.writeDouble(_ellipse.getRadiusY());
		io.writeDouble(_ellipse.getRotate());
		System.out.println(_c.getRed() * 255);
		System.out.println(_c.getGreen() * 255);
		System.out.println(_c.getBlue() * 255);
		io.writeInt((int) (_c.getRed()*255));
		io.writeInt((int) (_c.getGreen()*255));
		io.writeInt((int) (_c.getBlue()*255));
	}

	@Override
	public void load(FileIO io) {
		// loads the shape
		_ellipse.setCenterX(io.readDouble());
		_ellipse.setCenterY(io.readDouble());
		_ellipse.setRadiusX(io.readDouble());
		_ellipse.setRadiusY(io.readDouble());
		_ellipse.setRotate(io.readDouble());
		int red =  io.readInt();
		int green = io.readInt();
		int blue = io.readInt();
		System.out.println(red);
		System.out.println(green);
		System.out.println(blue);
		_ellipse.setFill(Color.rgb(red,green,blue));

	}
	@Override
	public double getCenterX() {
		// gets ellipse center x
		return _ellipse.getCenterX();
	}
	@Override
	public double getCenterY() {
		// gets ellipse center y
		return _ellipse.getCenterY();
	}
}
