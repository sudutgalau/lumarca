package lumarca.obj;

import lumarca.util.Coord;
import processing.core.PApplet;

public class Sphere extends ObjFile {

	public Sphere(PApplet lumarca, Coord center, Coord color, float size) {
		super(lumarca, center, color, "obj/sphere.obj", size);
	}

}
