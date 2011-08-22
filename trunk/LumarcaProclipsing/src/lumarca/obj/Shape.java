package lumarca.obj;

import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;


public abstract class Shape extends ProcessingObject {

	public Coord center;
	public Coord color;

	public abstract void drawIntersect(GL gl, Coord color, Line line);
	
	public abstract List<Line> getIntersect(Coord color, Line line);

	public abstract void drawWireFrame(GL gl);
	
	public abstract void drawShape(GL gl);

	public abstract void rotateOnX(float f);
	public abstract void rotateOnY(float f);
	public abstract void rotateOnZ(float f);

	public Coord getCenter() {
		return center;
	}

	public void setCenter(Coord center) {
		this.center = center;
	}

	public Coord getColor() {
		return color;
	}

	public void setColor(Coord color) {
		this.color = color;
	}
}
