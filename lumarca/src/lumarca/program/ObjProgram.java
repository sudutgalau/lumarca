package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Diamond;
import lumarca.obj.ObjFile;
import lumarca.obj.Shape;
import lumarca.util.Coord;

import processing.core.PApplet;


public class ObjProgram extends LineProgram {

	private Shape shape;
	
	public ObjProgram(Lumarca lumarca) {
		super(lumarca);

		shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1f,1f,0f), "obj/Smile.obj", 0.125f);

		shape.rotateOnX(PApplet.PI/2);
	}

	@Override
	public void update() {
		shape.rotateOnY(0.1f);
	}

	float angle = 0f;

	@Override
	public void display(GL gl) {
		
		lumarca.lineMap.drawShape(gl, shape.color, shape);
		
	}

	@Override
	public void keyPressed() {

		System.out.println("KeyCode: " + lumarca.keyCode);
		
		if(lumarca.key == '.'){
			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(0,0,1), "obj/HiveB.obj", 90.125f);
		} else if(lumarca.key == 'S'){
			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1f,1f,0f), "obj/Smile.obj", 0.125f);
			
			shape.rotateOnX(PApplet.PI/2);
		} else if(lumarca.key == ','){
			shape = new Diamond(lumarca.lineMap.midPosition, 300f);
			shape.setColor(new Coord(1,0,0));
		}
		
//		if(lineMapMain.key != ' '){
		else if((lumarca.keyCode >= 65) && (lumarca.keyCode <= 90)){
			String letter = lumarca.key + "";

			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1,1,1),
					"obj/" + letter.toUpperCase() + ".obj", 500);

			shape.rotateOnX(PApplet.PI + PApplet.PI/2);
		}
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
