package lumarca.obj;

import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;

public class Sphere extends ObjFile {

	public Sphere(Lumarca lumarca, Coord center, Coord color, float size) {
		super(lumarca, center, color, "obj/sphere.obj", size);
	}

	

}
