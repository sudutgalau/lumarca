package lumarca.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;


public class SimpleShape extends Shape {

	private List<TrianglePlane> tris = new ArrayList<TrianglePlane>();

	private static final Coord vec = new Coord(0, -1, 0);

	//Assumes lines list divisible by 3
	public SimpleShape(Coord center, List<Coord> coords) {
		this.center = center;
		
		for(int i = 0; i < coords.size(); i += 3){
			tris.add(new TrianglePlane(center, coords.get(i), coords.get(i+1), coords.get(i+2)));
		}
	}
	
	public void drawShape(GL gl){
		for(TrianglePlane tri: tris){
			tri.draw(gl);
		}
	}
	
	public void drawWireFrame(GL gl){
		for(TrianglePlane tri: tris){
			tri.drawWireFrame(gl);
		}
	}
	
	public void rotateOnX(float f){
		for(TrianglePlane tri: tris){
			tri.rotateOnX(f);
		}
	}
	
	public void rotateOnY(float f){
		for(TrianglePlane tri: tris){
			tri.rotateOnY(f);
		}
	}
	
	public void rotateOnZ(float f){
		for(TrianglePlane tri: tris){
			tri.rotateOnZ(f);
		}
	}
	
	//Assumes 2 pts on intersection
	public void drawIntersect(GL gl, Coord color, Line line){
		
		List<Coord> coords = new ArrayList<Coord>();
		
		for(TrianglePlane tri: tris){
			coords.add(Util.checkIntersectTri(tri, line.bottom, vec));
		}
		
		
		if(coords.get(0) != null){
			Coord pt1 = coords.get(0);
			Coord pt2 = coords.get(1);
			
			if(pt2 == null){
				pt2 = line.bottom;
			} else if(pt2.y < line.top.y){
				pt2 = line.top;
			}
			
			Util.drawLine(gl, color, pt1, pt2);
		}
	}
	
	public void setCenter(Coord center) {
		this.center = center;
		for(TrianglePlane tri: tris){
			tri.setCenter(center);
		}
	}

	public List<TrianglePlane> getTris() {
		return tris;
	}

	public void setTris(List<TrianglePlane> tris) {
		this.tris = tris;
	}
	
}
