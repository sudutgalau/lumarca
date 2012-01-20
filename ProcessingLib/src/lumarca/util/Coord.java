package lumarca.util;

import lumarca.lib.LumarcaLib;
import processing.core.PVector;

public class Coord extends PVector {

	public float yIntersect = 0;
	public float camDiff = 0;
	
	public Coord() {
		super();
	}

	public Coord(float x, float y, float z) {
		super(x, y, z);
	}
	
	public Coord(PVector vert){
		super(vert.x, vert.y, vert.z);
	}

	public Coord(float x, float y, float z, float intersect, float camDiff) {
		super(x, y, z);
		yIntersect = intersect;
		this.camDiff = camDiff;
	}
	
	public Coord getYIntersect(){
		return new Coord(LumarcaLib.DEFAULT_CAMERA_X - x * yIntersect, 
				LumarcaLib.DEFAULT_CAMERA_Y - y * yIntersect, 
				LumarcaLib.DEFAULT_CAMERA_Z - z * yIntersect);
	}
	
//	public void normalize(){
//		float distance = distance(new Coord(0,0,0));
//
//		this.x = x/distance;
//		this.y = y/distance;
//		this.z = z/distance;
//	}
//	
//	public static Coord add(Coord coord1, Coord coord2) {
//		Coord coord = new Coord(
//				coord1.x + coord2.x,
//				coord1.y + coord2.y,
//				coord1.z + coord2.z);
//		return coord;
//	}
//	
//	public static Coord minus(Coord coord1, Coord coord2) {
//		Coord coord = new Coord(
//				coord1.x - coord2.x,
//				coord1.y - coord2.y,
//				coord1.z - coord2.z);
//		return coord;
//	}
//	
//	public void add(Coord coord) {
//		this.x += coord.x;
//		this.y += coord.y;
//		this.z += coord.z;
//	}
//	
	public static Coord mult(Coord coord, float f){
		return new Coord(coord.x * f, coord.y * f, coord.z * f);
	}
	
	public float distance(Coord coord){
		return (int)(Math.sqrt(Math.pow(coord.x - x, 2) + Math.pow(coord.y - y, 2) + Math.pow(coord.z - z, 2)));
	}

	public String toString(){
		return x + "x" + y + "x" + z;
	}
	
	public Coord clone(){
		return new Coord(x, y, z);
	}
}
