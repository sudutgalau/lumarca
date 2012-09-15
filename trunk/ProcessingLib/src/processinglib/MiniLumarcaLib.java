package processinglib;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.cache.LumarcaCacher;
import lumarca.lineMap.Line;
import lumarca.obj.ObjFile;
import lumarca.obj.Shape;
import lumarca.util.ProcessingObject;
import processing.core.PApplet;
import processing.core.PVector;
import template.library.LumarcaLibrary;

public class MiniLumarcaLib extends PApplet {

	LumarcaLibrary lumarca;

	private float waveRot = 0.0f;
	
	Shape shape;
	
	List<List<Line>> frames = new ArrayList<List<Line>>();

	public void setup() {

//		ProcessingObject.setPApplet(this);
		
//		848x480
		
//		10.5 inches wide at 1ft away
//		4 3/4 from lens to bolt
//		4 16x32x(3/16)
//		BLICK: 32 x 40 (cut in half)
//		Get chip board from blick

		size(848, 480*2, OPENGL);

		
		lumarca = new LumarcaLibrary(this, "lineMap106.txt");
//		lumarca = new LumarcaLibrary(this, width/8, true);
		
		System.out.println("LINES: " + lumarca.getLines().length);

		PVector center = new PVector();
		
		lumarca.getCenterPosition(center);
//		
//		PVector max = new PVector();
//		lumarca.getMaxPosition(max);
//
		shape = new ObjFile(this, center,
				new PVector(1, 0, 0), "obj/hand-free.obj",
				30f);

//		shape.center.z = lumarca.getMidZ();
//		shape.center.y = lumarca.getMidY();
//
		shape.rotateOnZ(PI/2);
//		shape.rotateOnY(PI/2);
//		
//		for(float f = 0; shape.center.x < max.x; f+=0.1f){
//			shape.center.x+=10f;
//			frames.add(0,lumarca.lib.lineMap.getShape(shape.color, shape));
//		}
//		
//		Cacher.saveFrames(frames, "test.txt");
		
//		frames = Cacher.loadFrames("test.txt");
//		
//		System.out.println("frames: " + frames.size());
		
		frameRate(90);
	}

	boolean rot = false;
	
	int lineNum = 0;
	
	public void draw() {
		
		Line[] lines = lumarca.getLines();
		
		lineNum++; 
		
		if(lineNum == lines.length){
			lineNum = 0;
		}

		PVector coord1 = lines[lineNum].bottom;
		PVector coord2 = lines[lineNum].top;
		
		lumarca.drawLine(
						new PVector(0, 1, 0),
						coord2,
						coord1);

		
//		for(Line line: frames.get(frameCount%frames.size())){
//			lumarca.drawLine(line.color, line.top, line.bottom);
//		}
		

//		lumarca.calibration();

//		if(rot)
//			waveRot += 0.001f;
//		
//		lumarca.moveCamera(waveRot * 30);
//		
//		Line[] lines = lumarca.getLines();
//
//		for (int lineNum = 0; lineNum < lines.length; lineNum++) {
//
//			PVector coord = lines[lineNum].bottom;
//
//			lumarca.drawLine(
//							new PVector(coord.x / 1024f, 1f,
//									1024f - coord.x / 1024f),
//							new PVector(coord.x, coord.y
//									- PApplet.sin(lumarca.lib.lineMap.projectorX
//											- coord.x / 100 + waveRot * 50)
//									* 150 - 200, coord.z),
//							new PVector(coord.x, coord.y
//									- PApplet.sin(lumarca.lib.lineMap.projectorX
//											- coord.x / 100 + waveRot * 50)
//									* 150 - 150, coord.z));
//
//		}

//		lumarca.drawWireFrame(shape);
//		lumarca.drawShape(new PVector(1, 1, 0), shape);

//		GL gl = lumarca.lib.gl;
//		
//		gl.glColor3f(1f,1f,1f);
//
//		gl.glBegin(GL.GL_LINE_STRIP);
//		
//		gl.glVertex3f(width/2, 0, -100);
//		gl.glVertex3f(width/2, height, -100);
//		
//		gl.glEnd();
//		
//		fill(255, 0, 0);
//		ellipse(0, 0, 50, 50);
//		ellipse(width, height, 50, 50);
//		ellipse(0, height*2, 50, 50);
	}
	
	public void keyPressed(){
		rot = !rot;
		waveRot = 0.000f;
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { MiniLumarcaLib.class.getName() });
	}
}