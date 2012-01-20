package lumarca.obj;

import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import processing.core.PVector;


public abstract class Shape extends ProcessingObject {

	public Coord center;
	public PVector color;

	public abstract void drawIntersect(GL gl, PVector color, Line line);
	public abstract void drawIntersect(GL gl, PVector color, Line line, boolean dots);
	
	public abstract List<Line> getIntersect(PVector color, Line line);

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

	public PVector getColor() {
		return color;
	}

	public void setColor(PVector color) {
		this.color = color;
	}
}
