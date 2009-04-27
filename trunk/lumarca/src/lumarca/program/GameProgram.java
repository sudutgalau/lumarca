package lumarca.program;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Diamond;
import lumarca.obj.Shape;
import lumarca.util.Coord;

import processing.core.PApplet;


public class GameProgram extends LineProgram {

	private List<Shape> shapes = new ArrayList<Shape>();
	
	public GameProgram(Lumarca lumarca) {
		super(lumarca);
		
		for(int i = 0; i < 10; i++){
			Coord center = new Coord(lumarca.random(0f, Lumarca.WIN_WIDTH),
					i * -500,
					lumarca.random(200f, 400f));
			shapes.add(new Diamond(center, 200));
		}
	}

	@Override
	public void update() {
		for(int i = 0; i < 10; i++){
			Diamond shape = (Diamond)shapes.get(i);

//			shape.center.x += PApplet.sin(shape.center.y/100) * 10;
//			shape.center.z += PApplet.cos(shape.center.y/50) * 10;
			shape.center.y += 5;
			
			if(shape.center.y  > 500){			
				Coord center = new Coord(lumarca.random(0f, Lumarca.WIN_WIDTH),
					i * -500,
					lumarca.random(200f, 400f));

				shapes.set(i, new Diamond(center, 100));
			}
		}
	}
	
	@Override
	public void display(GL gl) {
		for(Shape shape: shapes){
//			shape.drawWireFrame(gl);
		}
		
		lumarca.lineMap.drawShapes(gl, new Coord(0,0,1f), shapes);
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
