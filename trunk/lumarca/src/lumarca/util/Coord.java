package lumarca.util;

import lumarca.Lumarca;



public class Coord {

	public float x = 0;
	public float y = 0;
	public float z = 0;

	public float yIntersect = 0;
	public float camDiff = 0;
	
	public Coord() {
		super();
	}

	public Coord(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coord(Coord vert){
		super();
		this.x = vert.x;
		this.y = vert.y;
		this.z = vert.z;
	}

	public Coord(float x, float y, float z, float intersect, float camDiff) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		yIntersect = intersect;
		this.camDiff = camDiff;
	}
	
	public Coord getYIntersect(){
		return new Coord(Lumarca.DEFAULT_CAMERA_X - x * yIntersect, 
				Lumarca.DEFAULT_CAMERA_Y - y * yIntersect, 
				Lumarca.DEFAULT_CAMERA_Z - z * yIntersect);
	}
	
	public void normalize(){
		float distance = distance(new Coord(0,0,0));

		this.x = x/distance;
		this.y = y/distance;
		this.z = z/distance;
	}
	
	public static Coord add(Coord coord1, Coord coord2) {
		Coord coord = new Coord(
				coord1.x + coord2.x,
				coord1.y + coord2.y,
				coord1.z + coord2.z);
		return coord;
	}
	
	public static Coord minus(Coord coord1, Coord coord2) {
		Coord coord = new Coord(
				coord1.x - coord2.x,
				coord1.y - coord2.y,
				coord1.z - coord2.z);
		return coord;
	}
	
	public void add(Coord coord) {
		this.x += coord.x;
		this.y += coord.y;
		this.z += coord.z;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void addZ(float z) {
		this.z += z;
	}
	
	public static Coord mult(Coord coord, float f){
		return new Coord(coord.x * f, coord.y * f, coord.z * f);
	}
	
//	
//	public void minus(Coord coord) {
//		if (coord != null) {
//			this.x -= coord.x;
//			this.y -= coord.y;
//		}
//	}
//	
//	public float getX() {
//		return x;
//	}
//
//	public void setX(float x) {
//		this.x = x;
//	}
//
//	public float getY() {
//		return y;
//	}
//
//	public void setY(float y) {
//		this.y = y;
//	}
//	
//	public void set(float x, float y){
//		this.x = x;
//		this.y = y;
//	}
//	
//	public void addX(float x){
//		this.x += x;
//	}
//	
//	public void addY(float y){
//		this.y += y;
//	}
//	
	
	public float distance(Coord coord){
		return (int)(Math.sqrt(Math.pow(coord.x - x, 2) + Math.pow(coord.y - y, 2) + Math.pow(coord.z - z, 2)));
	}
//	
	public String toString(){
		return x + "x" + y + "x" + z;
	}
	
	public Coord clone(){
		return new Coord(x, y, z);
	}
//
//	@Override
//	public int hashCode() {
//		// TODO Auto-generated method stub
//		return super.hashCode();
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		final Coord other = (Coord) obj;
//		if (x != other.x)
//			return false;
//		if (y != other.y)
//			return false;
//		return true;
//	}
}
