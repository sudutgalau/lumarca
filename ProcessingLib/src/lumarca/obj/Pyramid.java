package lumarca.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.util.Coord;
import processing.core.PVector;


public class Pyramid extends Shape {

	private List<SimpleShape> parts = new ArrayList<SimpleShape>();
	
	public Pyramid(Coord center, float size) {

		List<Coord> coords = new ArrayList<Coord>();
		List<Coord> coords2 = new ArrayList<Coord>();
		List<Coord> coords3 = new ArrayList<Coord>();
		List<Coord> coords4 = new ArrayList<Coord>();
		
		coords.add(new Coord(0, 	0f,		0f));
		coords.add(new Coord(-size, 	size,	-size));
		coords.add(new Coord(-size, 	size,	size));
		
		coords.add(new Coord(0, 	size,		0f));
		coords.add(new Coord(-size, 	size,	-size));
		coords.add(new Coord(-size, 	size,	size));

		coords2.add(new Coord(size, 	size,	-size));
		coords2.add(new Coord(0, 	0f,		0f));
		coords2.add(new Coord(size, 	size,	size));

		coords2.add(new Coord(size, 	size,	-size));
		coords2.add(new Coord(0, 	size,		0f));
		coords2.add(new Coord(size, 	size,	size));

		coords3.add(new Coord(0, 	0f,		0f));
		coords3.add(new Coord(-size, 	size,	size));
		coords3.add(new Coord(size, 	size,	size));

		coords3.add(new Coord(0, 	size,		0f));
		coords3.add(new Coord(-size, 	size,	size));
		coords3.add(new Coord(size, 	size,	size));

		coords4.add(new Coord(0, 	0f,		0f));
		coords4.add(new Coord(-size, 	size,	-size));
		coords4.add(new Coord(size, 	size,	-size));

		coords4.add(new Coord(0, 	size,	0f));
		coords4.add(new Coord(-size, 	size,	-size));
		coords4.add(new Coord(size, 	size,	-size));
		
		parts.add(new SimpleShape(center, coords));
		parts.add(new SimpleShape(center, coords2));
		parts.add(new SimpleShape(center, coords3));
		parts.add(new SimpleShape(center, coords4));
		
		this.center = center.clone();
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
		List<Line> interLines = new ArrayList<Line>();
		
		for(Shape shape: parts){
			List<Line> lines = shape.getIntersect(color, line);
			
			if(lines.size() > 0){
				interLines.addAll(lines);
			}
		}
		
		return interLines;
	}

	@Override
	public void drawShape(GL gl) {
		for(Shape shape: parts){
			shape.drawShape(gl);
		}
	}

	@Override
	public void drawWireFrame(GL gl) {
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

	public void setCenter(Coord center) {
		this.center = center.clone();
		for(Shape shape: parts){
			shape.setCenter(center.clone());
		}
	}

}
