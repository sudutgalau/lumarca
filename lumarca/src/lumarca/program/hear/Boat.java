package lumarca.program.hear;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.obj.Shape;
import lumarca.obj.SimpleShape;
import lumarca.obj.TrianglePlane;
import lumarca.util.Coord;
import lumarca.util.Util;


public class Boat extends Shape {

	private List<SimpleShape> parts = new ArrayList<SimpleShape>();

	public Boat(Coord center, float size) {

		this.center = center;
		
		List<Coord> coords = new ArrayList<Coord>();
		List<Coord> coords2 = new ArrayList<Coord>();
		List<Coord> coords3 = new ArrayList<Coord>();
		List<Coord> coords4 = new ArrayList<Coord>();
		List<Coord> coords5 = new ArrayList<Coord>();
		List<Coord> coords6 = new ArrayList<Coord>();
		List<Coord> coords7 = new ArrayList<Coord>();
		List<Coord> coords8 = new ArrayList<Coord>();
		
		coords.add(new Coord(0 		  + size, 	0f,		0f));
		coords.add(new Coord(-size/2f + size, 	0f,		-size/2f));
		coords.add(new Coord(-size/2f + size, 	0f,		0f));
		
		coords.add(new Coord(0 		  + size, 	0f,		0f));
		coords.add(new Coord(-size/2f + size, 	0f,		-size/2f));
		coords.add(new Coord(-size/2f + size, 	size/4f,		0f));
		
		coords2.add(new Coord(0 	   + size, 	0f,		0f));
		coords2.add(new Coord(-size/2f + size, 	0f,		size/2f));
		coords2.add(new Coord(-size/2f + size, 	0f,		0f));
		
		coords2.add(new Coord(0 	   + size, 	0f,		0f));
		coords2.add(new Coord(-size/2f + size, 	0f,		size/2f));
		coords2.add(new Coord(-size/2f + size, 	size/4f,		0f));
		
		coords3.add(new Coord(0 		  - size, 	0f,		0f));
		coords3.add(new Coord(size/2f - size, 	0f,		-size/2f));
		coords3.add(new Coord(size/2f - size, 	0f,		0f));
		
		coords3.add(new Coord(0 		  - size, 	0f,		0f));
		coords3.add(new Coord(size/2f - size, 	0f,		-size/2f));
		coords3.add(new Coord(size/2f - size, 	size/4f,		0f));
		
		coords4.add(new Coord(0 	   - size, 	0f,		0f));
		coords4.add(new Coord(size/2f - size, 	0f,		size/2f));
		coords4.add(new Coord(size/2f - size, 	0f,		0f));
		
		coords4.add(new Coord(0 	   - size, 	0f,		0f));
		coords4.add(new Coord(size/2f - size, 	0f,		size/2f));
		coords4.add(new Coord(size/2f - size, 	size/4f,		0f));

		coords5.add(new Coord(-size/2f + size, 	0f,		-size/2f));
		coords5.add(new Coord(size/2f  - size, 	0f,		-size/2f));
		coords5.add(new Coord(size/2f - size, 	size/4f,		0f));

		coords5.add(new Coord(-size/2f + size, 	0f,		-size/2f));
		coords5.add(new Coord(size/2f  - size, 	0f,		-size/2f));
		coords5.add(new Coord(size/2f - size, 	0f,		0f));

		coords6.add(new Coord(-size/2f + size, 	0f,		-size/2f));
		coords6.add(new Coord(-size/2f + size, 	size/4f,		0f));
		coords6.add(new Coord(size/2f - size, 	size/4f,		0f));

		coords6.add(new Coord(-size/2f + size, 	0f,		-size/2f));
		coords6.add(new Coord(-size/2f + size, 	0f,		0f));
		coords6.add(new Coord(size/2f - size, 	0f,		0f));

		coords7.add(new Coord(-size/2f + size, 	0f,		size/2f));
		coords7.add(new Coord(-size/2f + size, 	size/4f,		0f));
		coords7.add(new Coord(size/2f - size, 	size/4f,		0f));

		coords7.add(new Coord(-size/2f + size, 	0f,		size/2f));
		coords7.add(new Coord(-size/2f + size, 	0f,		0f));
		coords7.add(new Coord(size/2f - size, 	0f,		0f));

		coords8.add(new Coord(-size/2f + size, 	0f,		size/2f));
		coords8.add(new Coord(size/2f  - size, 	0f,		size/2f));
		coords8.add(new Coord(size/2f - size, 	size/4f,		0f));

		coords8.add(new Coord(-size/2f + size, 	0f,		size/2f));
		coords8.add(new Coord(size/2f  - size, 	0f,		size/2f));
		coords8.add(new Coord(size/2f - size, 	0f,		0f));
		
//		parts.add(new SimpleShape(center, coords));
//		parts.add(new SimpleShape(center, coords2));
		parts.add(new SimpleShape(center, coords3));
		parts.add(new SimpleShape(center, coords4));
		parts.add(new SimpleShape(center, coords5));
		parts.add(new SimpleShape(center, coords6));
		parts.add(new SimpleShape(center, coords7));
		parts.add(new SimpleShape(center, coords8));
	}

	@Override
	public void drawIntersect(GL gl, Coord color, Line line) {
		List<Coord> coords = new ArrayList<Coord>();

		for (SimpleShape shape : parts) {
			for (TrianglePlane tri : shape.getTris()) {
				Coord inter = Util.checkIntersectTri(tri, line.bottom,
						new Coord(0, -1, 0));
				if (inter != null) {
					coords.add(inter);
				}
			}
		}

		if (coords.size() > 1) {
			Coord pt1 = coords.get(0);
			Coord pt2 = coords.get(1);

			if (pt2 == null) {
				pt2 = line.bottom;
			} else if (pt2.y < line.top.y) {
				pt2 = line.top;
			}

			Util.drawLine(gl, color, pt1, pt2);
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