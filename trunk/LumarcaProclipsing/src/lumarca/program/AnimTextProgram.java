package lumarca.program;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.obj.Diamond;
import lumarca.obj.ObjFile;
import lumarca.obj.Shape;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PApplet;

public class AnimTextProgram extends LineProgram {

	private Shape shape;
	private Shape smile;
	private String str = "SIGGRAPHASIA";
	private int frameNum = 0;
	private int index = 0;
	private List<List<Line>> frames = new ArrayList<List<Line>>();

	public AnimTextProgram(Lumarca lumarca) {
		super(lumarca);

		smile = new ObjFile(lumarca, lumarca.lineMap.midPosition, new Coord(1f,
				1f, 0f), "obj/Smile.obj", 0.125f);

		smile.rotateOnX(PApplet.PI);
		
		shape = smile;
	}

	@Override
	public void start() {
		index = 0;
		frameNum = 0;
		
	}

	private void change(){
		index++;
		
		if(index == str.length()){
			shape = smile;
		} else if(shape == smile){
			index = 0;
			setLetter();
			if(frameNum > 0){
				frameNum = 0;
			}
		} else {
			setLetter();
		}
	}

	private void setLetter(){
		shape = new ObjFile(lumarca, lumarca.lineMap.midPosition,
				new Coord(0.25f, 0.25f, 1), "obj/" + str.charAt(index) + ".obj",
				500);
		shape.rotateOnX(PApplet.PI * 2 * 3/4);
	}
	
	List<Line> frame;

	@Override
	public void update() {
		shape.rotateOnY(0.1f);

		if(frameNum % 70 == 0) {
			change();
		}
		
		if(frames.size() <= frameNum){
			System.out.println("NEW FRAME");
			frame = lumarca.lineMap.getShape(shape.color, shape);
			frames.add(frameNum, frame);
		} else {
			frame = frames.get(frameNum);
		}
		
		frameNum++;
	}
	
	@Override
	public void display(GL gl) {
		
		for(Line line: frame){
			Util.drawLine(gl, line.color, line.top, line.bottom);
		}
		
//		lumarca.lineMap.drawShape(gl, shape.color, shape);

	}

	@Override
	public void keyPressed() {

		System.out.println("KeyCode: " + lumarca.keyCode);

		if (lumarca.key == '.') {
			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition,
					new Coord(0, 0, 1), "obj/HiveB.obj", 90.125f);
		} else if (lumarca.key == 'S') {
			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition,
					new Coord(1f, 1f, 0f), "obj/Smile.obj", 0.125f);

			shape.rotateOnX(PApplet.PI + PApplet.PI / 2);
		} else if (lumarca.key == ',') {
			shape = new Diamond(lumarca.lineMap.midPosition, 300f);
			shape.setColor(new Coord(1, 0, 0));
		}

		// if(lineMapMain.key != ' '){
		else if ((lumarca.keyCode >= 65) && (lumarca.keyCode <= 90)) {
			String letter = lumarca.key + "";

			shape = new ObjFile(lumarca, lumarca.lineMap.midPosition,
					new Coord(1, 1, 1), "obj/" + letter.toUpperCase() + ".obj",
					500);

			shape.rotateOnX(PApplet.PI / 2);
		}
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
