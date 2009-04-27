package lumarca.obj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;


public class Diamond extends Shape {

	private List<SimpleShape> parts = new ArrayList<SimpleShape>();
	public Coord center;
	
	public Diamond(Coord center, float size) {

		this.center = center;
		
		List<Coord> coords = new ArrayList<Coord>();
		List<Coord> coords2 = new ArrayList<Coord>();
		List<Coord> coords3 = new ArrayList<Coord>();
		List<Coord> coords4 = new ArrayList<Coord>();
		
		coords.add(new Coord(0, 		-size/2f,		0f));
		coords.add(new Coord(-size/2f, 	0f,			-size/2f));
		coords.add(new Coord(-size/2f, 	0f,			size/2f));
		
		coords.add(new Coord(0, 		size/2f,		0f));
		coords.add(new Coord(-size/2f, 	0f,			-size/2f));
		coords.add(new Coord(-size/2f, 	0f,			size/2f));

		coords2.add(new Coord(0, 		-size/2f,		0f));
		coords2.add(new Coord(size/2f, 	0f,	-size/2f));
		coords2.add(new Coord(size/2f, 	0f,	size/2f));

		coords2.add(new Coord(0, 		size/2f,		0f));
		coords2.add(new Coord(size/2f, 	0f,	-size/2f));
		coords2.add(new Coord(size/2f, 	0f,	size/2f));

		coords3.add(new Coord(0, 		-size/2f,		0f));
		coords3.add(new Coord(-size/2f, 0f,			size/2f));
		coords3.add(new Coord(size/2f, 	0f,			size/2f));

		coords3.add(new Coord(0, 		size/2f,		0f));
		coords3.add(new Coord(-size/2f, 0f,	size/2f));
		coords3.add(new Coord(size/2f, 	0f,	size/2f));

		coords4.add(new Coord(0, 		-size/2f,		0f));
		coords4.add(new Coord(-size/2f, 0f,	-size/2f));
		coords4.add(new Coord(size/2f, 	0f,	-size/2f));

		coords4.add(new Coord(0, 		size/2f,	0f));
		coords4.add(new Coord(-size/2f, 		0f,	-size/2f));
		coords4.add(new Coord(size/2f, 			0f,	-size/2f));
		
		parts.add(new SimpleShape(center, coords));
		parts.add(new SimpleShape(center, coords2));
		parts.add(new SimpleShape(center, coords3));
		parts.add(new SimpleShape(center, coords4));
	}

	@Override
	public void drawIntersect(GL gl, Coord color, Line line) {
		for(Shape shape: parts){
			shape.drawIntersect(gl, color, line);
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

	public Coord getCenter() {
		return center;
	}

	public void setCenter(Coord center) {
		this.center = center.clone();
		for(Shape shape: parts){
			shape.setCenter(center.clone());
		}
	}
	
}
