package lumarca.program;

import java.util.List;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.lineMap.Line;
import lumarca.obj.Diamond;
import lumarca.obj.ObjFile;
import lumarca.obj.Shape;
import lumarca.obj.Sphere;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PApplet;


public class ObjProgram extends LineProgram {

	private Shape shape;
	
	private float size = 5f;
	
	public ObjProgram(LumarcaPRO lumarca) {
		super(lumarca);
		
		shape =  new Sphere(lumarca, lumarca.lineMap.midPosition, new Coord(0f,1f,0f), size);

//		shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1f,1f,0f), "obj/Smile.obj", 0.125f);
		shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1f,1f,0f), "obj/packman.obj", size);

		shape.rotateOnX(PApplet.PI + PApplet.PI/2);
	}

	@Override
	public void update() {
		shape.rotateOnY(0.1f);
		
//		size -= 0.01f;
	}

	float angle = 0f;
	List<Line> frame;

	@Override
	public void display(GL gl) {
		
		((ObjFile)shape).setSize(size);
		
//		for(Line line: lumarca.lineMap.lines){
//			Util.drawLineNoDots(gl, new Coord(1,1,0), line.top, line.bottom);
//		}
		
//		lumarca.lineMap.drawShape(gl, shape.color, shape);
		
		if(frame ==  null){
			frame = lumarca.lineMap.getShape(shape.color, shape);
		}
		
		for(Line line: frame){
			Util.drawLine(gl, line.color, line.top, line.bottom);
		}
		
//		shape.drawWireFrame(gl);
		
	}

	@Override
	public void keyPressed() {

		System.out.println("KeyCode: " + lumarca.keyCode);
		
		if(lumarca.key == '.'){
			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(0,0,1), "obj/HiveB.obj", 90.125f);
		} else if(lumarca.key == 'S'){
			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1f,1f,0f), "obj/Smile.obj", 0.525f);

			shape.rotateOnX(PApplet.PI + PApplet.PI/2);
		} else if(lumarca.key == ','){
			shape = new Diamond(lumarca.lineMap.midPosition, 300f);
			shape.setColor(new Coord(1,0,0));
		}
		
//		if(lineMapMain.key != ' '){
		else if((lumarca.keyCode >= 65) && (lumarca.keyCode <= 90)){
			String letter = lumarca.key + "";

			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1,1,1),
					"obj/" + letter.toUpperCase() + ".obj", 500);

			shape.rotateOnX(PApplet.PI/2);
		}
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
