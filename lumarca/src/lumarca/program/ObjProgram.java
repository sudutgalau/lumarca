package lumarca.program;

import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.obj.ObjFile;
import lumarca.util.Coord;
import lumarca.util.Util;

import processing.core.PApplet;


public class ObjProgram extends LineProgram {

	private float waveRot = 0.0f;

	private ObjFile model;
	private ObjFile modelT;
	
	public ObjProgram(Lumarca lumarca) {
		super(lumarca);
		model = new ObjFile(lumarca, new Coord(
				Lumarca.WIN_WIDTH/2 + 0,
				Lumarca.WIN_HEIGHT/3 - 200,0), new Coord(1,0,0), "obj/Box.obj", 200000);
		modelT = new ObjFile(lumarca, new Coord(
				Lumarca.WIN_WIDTH/2 + 0,
				Lumarca.WIN_HEIGHT/2 - 500, 100), new Coord(0,0,1), "obj/Hand.obj", 0.125f);

//		modelT.rotateOnX(PApplet.PI/2);
		modelT.rotateOnX(-PApplet.PI/2);
	}

	@Override
	public void update() {
		waveRot += 0.001f;
	}

	float angle = 0f;

	@Override
	public void display(GL gl) {

//		model.drawlineFrame(gl);
//		model.rotateOnZ(0.1f);
		
//		modelT.drawlineFrame(gl);
		
		modelT.rotateOnY(0.1f);

//		modelT.center.z++;
//		modelT.center.y++;
		
		for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
			System.out.println(modelT.color);
			modelT.drawIntersect(gl, modelT.color, lumarca.lineMap.lines[lineNum]);
		}
	}

	@Override
	public void keyPressed() {

		System.out.println("KeyCode: " + lumarca.keyCode);
		
		if(lumarca.key == 'S'){
			modelT = new ObjFile(lumarca, new Coord(
					Lumarca.WIN_WIDTH/2 + 0,
					Lumarca.WIN_HEIGHT/2 - 500, 200), new Coord(1f,1f,0f), "obj/Smile.obj", 0.125f);
			
			modelT.rotateOnX(PApplet.PI/2);
		}
		
//		if(lineMapMain.key != ' '){
		else if((lumarca.keyCode >= 65) && (lumarca.keyCode <= 90)){
			String letter = lumarca.key + "";

			modelT = new ObjFile(lumarca, new Coord(
					Lumarca.WIN_WIDTH / 2 + 0, Lumarca.WIN_HEIGHT / 2 - 500, 200), new Coord(1,1,1),
					"obj/" + letter.toUpperCase() + ".obj", 500);

			modelT.rotateOnX(PApplet.PI + PApplet.PI/2);
		}
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
