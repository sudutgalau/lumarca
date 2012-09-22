package lumarca.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PVector;


public class Rectangle extends Shape {

	private List<SimpleShape> parts = new ArrayList<SimpleShape>();

	public float width;
	public float height;
	public float depth;
	
	public Rectangle(Coord center, float width, float height, float depth) {

		this.center = center;
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		List<Coord> coords = new ArrayList<Coord>();
		List<Coord> coords2 = new ArrayList<Coord>();
		
		coords.add(new Coord(width/2f, 	height/2f,	-depth/2f));
		coords.add(new Coord(-width/2f, height/2f,	depth/2f));
		coords.add(new Coord(-width/2f, height/2f,	-depth/2f));

		coords.add(new Coord(width/2f, 	-height/2f,	-depth/2f));
		coords.add(new Coord(-width/2f, -height/2f,	depth/2f));
		coords.add(new Coord(-width/2f, -height/2f,	-depth/2f));
		
		coords2.add(new Coord(width/2f, 	height/2f,	-depth/2f));
		coords2.add(new Coord(width/2f, height/2f,	depth/2f));
		coords2.add(new Coord(-width/2f, height/2f,	depth/2f));

		coords2.add(new Coord(width/2f, 	-height/2f,	-depth/2f));
		coords2.add(new Coord(width/2f, -height/2f,	depth/2f));
		coords2.add(new Coord(-width/2f, -height/2f,	depth/2f));
		
		parts.add(new SimpleShape(center, coords));
		parts.add(new SimpleShape(center, coords2));
	}

	@Override
	public void drawIntersect(GL gl, PVector color, Line line){
		drawIntersect(gl, color, line, true);
	}

	@Override
	public void drawIntersect(GL gl, PVector color, Line line, boolean dots) {
		for(Shape shape: parts){
			shape.drawIntersect(gl, color, line, dots);
		}
	}

	@Override
	public List<Line> getIntersect(PVector color, Line line) {
		List<Line> lines = new ArrayList<Line>();
		
		for(Shape shape: parts){
			List<Line> interLines = shape.getIntersect(color, line);
			
			if(interLines.size() > 0){
				lines.addAll(interLines);
			}
		}
		
		return lines;
	}

	@Override
	public void setCenter(PVector center) {
		for(SimpleShape shape: parts){
			shape.setCenter(center);
		}
	}

	@Override
	public void drawShape(GL gl) {
		for(Shape shape: parts){
			shape.drawShape(gl);
		}
	}

	@Override
	public void drawWireFrame(GL gl) {
		Util.drawPoint(gl, center);
		for(Shape shape: parts){
			shape.drawWireFrame(gl);
		}
	}

	@Override
	public void rotateOnX(float f) {
		for(Shape shape: parts){
			shape.rotateOnX(f);
		}
	}

	@Override
	public void rotateOnY(float f) {
		for(Shape shape: parts){
			shape.rotateOnY(f);
		}
	}

	@Override
	public void rotateOnZ(float f) {
		for(Shape shape: parts){
			shape.rotateOnZ(f);
		}
	}

}
