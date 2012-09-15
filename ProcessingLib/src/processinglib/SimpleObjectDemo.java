package processinglib;


import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.cache.LumarcaCacher;
import lumarca.lineMap.Line;
import lumarca.obj.ObjFile;
import lumarca.obj.Shape;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import processing.core.PApplet;
import processing.core.PVector;
import template.library.LumarcaLibrary;

public class SimpleObjectDemo extends PApplet {
	
	float farPlaneDepth   = 8400.0f;
	float structureWidth  = 3000.0f;
	float structureDepth  = 3000.0f;
	float structureHeight = 3000.0f;
	int   projWidthRes    = 1024;
	int   projHeightRes   = 768;
	float pxpmm             = (float)(projWidthRes) / structureWidth; // px per millimeter
	
	float px(float mm) {
	  return mm * pxpmm;
	}
	
	LumarcaLibrary lumarca;

	private float waveRot = 0.0f;
	
	Shape shape;
	
	List<List<Line>> frames = new ArrayList<List<Line>>();

	public void setup() {


		size(1024, 768 * 2, OPENGL);

		ProcessingObject.setPApplet(this);

		lumarca = new LumarcaLibrary(this, 256, false);

		shape = new ObjFile(this, lumarca.lib.lineMap.midPosition.clone(),
				new Coord(1, 0, 0), "sphere.obj", 1.5f);
	}

	public void draw() {
		lumarca.calibration();
		// lumarca.drawWireFramce(shape);
		lumarca.drawShape(new PVector(1, 1, 0), shape);
		

		  fill(255, 0, 0);
//		  translate(width/2, height/2, 0); 
		  sphere(100);  
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { SimpleObjectDemo.class.getName() });
	}
}
