package lumarca.obj;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import lumarca.util.Util;
import processing.core.PVector;


public class TrianglePlane extends ProcessingObject {

	private float rot = 0f;
	
	public PVector center;
	public PVector one;
	public PVector two;
	public PVector three;
	
	public TrianglePlane(PVector one, PVector two, PVector three) {
		super();
		
		this.one = one;
		this.two = two;
		this.three = three;
		
		center = new Coord(
				(one.x + two.x + three.x)/3,
				(one.y + two.y + three.y)/3,
				(one.z + two.z + three.z)/3);
	}
	
	public TrianglePlane(PVector center, PVector one, PVector two, PVector three) {
		super();
		this.center = center;
		this.one = one;
		this.two = two;
		this.three = three;
		
//		center = new Coord((one.x + two.x + three.x)/3,
//				(one.y + two.y + three.y)/3,
//				(one.z + two.z + three.z)/3);
	}
	
	public List<PVector> getAbsCoord(){
		List<PVector> absCoords= new ArrayList<PVector>();

		absCoords.add(Coord.add(center, one));
		absCoords.add(Coord.add(center, two));
		absCoords.add(Coord.add(center, three));
		
		return absCoords;
	}
	
	public void drawWireFrame(GL gl){
		Util.drawPoint(gl, center);
		
		for(PVector coord: getAbsCoord()){
			Util.drawPoint(gl, coord);
		}
		
		pApplet.noFill();
		pApplet.beginShape();
		Util.makeVertex(gl, center, one);
		Util.makeVertex(gl, center, two);
		Util.makeVertex(gl, center, three);
		Util.makeVertex(gl, center, one);
		pApplet.endShape();
	}
	
	public void draw(GL gl){

		pApplet.beginShape();
		Util.makeVertex(gl, center, one);
		Util.makeVertex(gl, center, two);
		Util.makeVertex(gl, center, three);
		pApplet.endShape();
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

	public PVector getCenter() {
		return center;
	}

	public void setCenter(Coord center) {
		this.center = center;
	}
}
