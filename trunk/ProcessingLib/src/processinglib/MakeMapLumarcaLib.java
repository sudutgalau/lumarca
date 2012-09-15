package processinglib;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.obj.Shape;
import lumarca.util.Coord;
import processing.core.PApplet;
import processing.core.PVector;
import template.library.LumarcaLibrary;

public class MakeMapLumarcaLib extends PApplet {

	LumarcaLibrary lumarca;

	private float waveRot = 0.0f;
	
	Shape shape;
	
	List<List<Line>> frames = new ArrayList<List<Line>>();
	
	Line[] lines;

	public void setup() {
		
//		848x480 for SHOW WX PICO
		size(848 + 20, 848 + 20,  PDF, "miniMap.pdf");

		
//		lumarca = new LumarcaLibrary(this, "lineMap106.txt");
//		lumarca = new LumarcaLibrary(this, width/8, true);

		lines = makeMap(loadStrings("lineMap106.txt"));
		
		System.out.println("LINES: " + lines.length);
		
		frameRate(90);
	}

	boolean rot = false;
	
	public void draw() {

//		lumarca.calibration();
//
//		if(rot)
//			waveRot += 0.0001f;
//		
//		lumarca.moveCamera(waveRot * 300);

		for (int lineNum = 0; lineNum < lines.length; lineNum++) {

			PVector coord = lines[lineNum].bottom;

			noFill();
			point(coord.x + 10, -coord.z + 10);
//			ellipse(coord.x + 10, -coord.z + 10, 10, 10);
		}
		
		exit();
		
//		fill(255, 0, 0);
//		ellipse(width/2, height/2, 30, 30);
	}
	
	public void keyPressed(){
		rot = !rot;
		waveRot = 0.000f;
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { MakeMapLumarcaLib.class.getName() });
	}
	
	private Line[] makeMap(String[] map) {
		
		Line[] lines = new Line[map.length];
		
		for(int i = 0; i < map.length; i++){
			String s = map[i];
			
			Coord bottom = new Coord();
			
			String[] cStr = s.split("x");
			bottom.x = Float.parseFloat(cStr[0]);
			bottom.y = Float.parseFloat(cStr[1]);
			bottom.z = Float.parseFloat(cStr[2]);
			
			Coord top = new Coord(bottom.x, 0, bottom.z);
			
			lines[i] = new Line(bottom, top, 0);
		}

		return lines;
	}
}