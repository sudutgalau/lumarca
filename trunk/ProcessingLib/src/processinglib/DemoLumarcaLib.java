package processinglib;

import lumarca.lineMap.Line;
import lumarca.obj.ObjFile;
import lumarca.obj.Shape;
import lumarca.util.ProcessingObject;
import processing.core.PApplet;
import processing.core.PVector;
import template.library.LumarcaLibrary;

public class DemoLumarcaLib extends PApplet {

	LumarcaLibrary lumarca;

	private float waveRot = 0.0f;
	
	Shape shape;

	public void setup() {

		ProcessingObject.setPApplet(this);

		size(1024, 768 * 2, OPENGL);

		lumarca = new LumarcaLibrary(this, "test256.txt");
//		lumarca = new LumarcaLibrary(this, 1440, true);

		PVector center = new PVector();
		
		lumarca.getCenterPosition(center);

		shape = new ObjFile(this, center,
				new PVector(1, 0, 0), "sphere.obj",
				1.5f);
//		shape = new Rectangle(center, width/2, width/2, width/2);
	}

	public void draw() {

//		lumarca.calibration();
		
		waveRot += 0.001f;
		
//		lumarca.moveCamera(waveRot * 20);
//		
		Line[] lines = lumarca.getLines();

		for (int lineNum = 0; lineNum < lines.length; lineNum++) {

			PVector coord = lines[lineNum].bottom;

			lumarca.drawLine(
							new PVector(coord.x / 1024f, 1f,
									1024f - coord.x / 1024f),
							new PVector(coord.x, coord.y
									- PApplet.sin(lumarca.lib.lineMap.projectorX
											- coord.x / 100 + waveRot * 50)
									* 150 - 200, coord.z),
							new PVector(coord.x, coord.y
									- PApplet.sin(lumarca.lib.lineMap.projectorX
											- coord.x / 100 + waveRot * 50)
									* 150 - 150, coord.z));

		}

//		lumarca.drawWireFramce(shape);
		lumarca.drawShape(new PVector(1, 1, 0), shape);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { DemoLumarcaLib.class.getName() });
	}
}
