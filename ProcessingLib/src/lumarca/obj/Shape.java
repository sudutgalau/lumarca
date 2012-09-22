package lumarca.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.lineMap.LineMap;
import lumarca.util.Coord;
import lumarca.util.LumarcaObject;
import lumarca.util.ProcessingObject;
import processing.core.PVector;


public abstract class Shape extends LumarcaObject {

	public PVector center;
	public PVector color;
	
	public abstract void drawIntersect(GL gl, PVector color, Line line);
	public abstract void drawIntersect(GL gl, PVector color, Line line, boolean dots);
	
	public abstract List<Line> getIntersect(PVector color, Line line);

	public abstract void drawWireFrame(GL gl);
	
	public abstract void drawShape(GL gl);

	public abstract void rotateOnX(float f);
	public abstract void rotateOnY(float f);
	public abstract void rotateOnZ(float f);
	
	public List<Line> getIntersections(PVector color){
		List<Line> interLines = new ArrayList<Line>();
		
		for(Line line: lineMap.lines){
			interLines.addAll(getIntersect(color, line));
		}
		
		return interLines;
	}

	public PVector getCenter() {
		return center;
	}

	public void setCenter(PVector center) {
		this.center = center;
	}

	public PVector getColor() {
		return color;
	}

	public void setColor(PVector color) {
		this.color = color;
	}
}
