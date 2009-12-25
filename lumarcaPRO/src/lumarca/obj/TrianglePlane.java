package lumarca.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import lumarca.util.Util;


public class TrianglePlane extends ProcessingObject {

	private float rot = 0f;
	
	public Coord center;
	public Coord one;
	public Coord two;
	public Coord three;
	
	public TrianglePlane(Coord one, Coord two, Coord three) {
		super();
		
		this.one = one;
		this.two = two;
		this.three = three;
		
		center = new Coord(
				(one.x + two.x + three.x)/3,
				(one.y + two.y + three.y)/3,
				(one.z + two.z + three.z)/3);
	}
	
	public TrianglePlane(Coord center, Coord one, Coord two, Coord three) {
		super();
		this.center = center;
		this.one = one;
		this.two = two;
		this.three = three;
		
//		center = new Coord((one.x + two.x + three.x)/3,
//				(one.y + two.y + three.y)/3,
//				(one.z + two.z + three.z)/3);
	}
	
	public List<Coord> getAbsCoord(){
		List<Coord> absCoords= new ArrayList<Coord>();

		absCoords.add(Coord.add(center, one));
		absCoords.add(Coord.add(center, two));
		absCoords.add(Coord.add(center, three));
		
		return absCoords;
	}
	
	public void drawWireFrame(GL gl){
		Util.drawPoint(gl, center);
		
		for(Coord coord: getAbsCoord()){
			Util.drawPoint(gl, coord);
		}
		
		gl.glBegin(GL.GL_LINE_STRIP);
		Util.makeVertex(gl, center, one);
		Util.makeVertex(gl, center, two);
		Util.makeVertex(gl, center, three);
		Util.makeVertex(gl, center, one);
		gl.glEnd();
	}
	
	public void draw(GL gl){
		
		gl.glBegin(GL.GL_POLYGON);
		Util.makeVertex(gl, center, one);
		Util.makeVertex(gl, center, two);
		Util.makeVertex(gl, center, three);
		gl.glEnd();
	}
	
	public void rotateOnX(float f){
		one = Util.rotateX(Util.zeroCoord, one, f);
		two = Util.rotateX(Util.zeroCoord, two, f);
		three = Util.rotateX(Util.zeroCoord, three, f);
	}
	
	public void rotateOnY(float f){
		one = Util.rotateY(Util.zeroCoord, one, f);
		two = Util.rotateY(Util.zeroCoord, two, f);
		three = Util.rotateY(Util.zeroCoord, three, f);
	}
	
	public void rotateOnZ(float f){
		one = Util.rotateZ(Util.zeroCoord, one, f);
		two = Util.rotateZ(Util.zeroCoord, two, f);
		three = Util.rotateZ(Util.zeroCoord, three, f);
	}

	public Coord getCenter() {
		return center;
	}

	public void setCenter(Coord center) {
		this.center = center;
	}
}
