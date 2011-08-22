package lumarca.program;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Diamond;
import lumarca.obj.Shape;
import lumarca.util.Coord;


public class SnakeProgram extends LineProgram {


	float snakeSize = 50f;

	Coord center;
	Coord center1;
	Coord center2;
	Coord center3;
	
	private Coord snakeDir = new Coord(0, 0, 1);
	
	public List<Shape> parts = new ArrayList<Shape>();
	
	public SnakeProgram(Lumarca lumarca) {
		super(lumarca);

		center  = new Coord(Lumarca.WIN_WIDTH/2, 400f, snakeSize * 5);
		center1 = new Coord(Lumarca.WIN_WIDTH/2, 400f, snakeSize * 4);
		center2 = new Coord(Lumarca.WIN_WIDTH/2, 400f, snakeSize * 3);
		center3 = new Coord(Lumarca.WIN_WIDTH/2, 400f, snakeSize * 2);
		
		Diamond diamond1 = new Diamond(center, 	snakeSize * 5);
		Diamond diamond2 = new Diamond(center1, snakeSize);
		Diamond diamond3 = new Diamond(center2, snakeSize);
		Diamond diamond4 = new Diamond(center3, snakeSize);
		
		parts.add(diamond1);
//		parts.add(diamond2);
//		parts.add(diamond3);
//		parts.add(diamond4);
	}

	public void update() {
		// TODO Auto-generated method stub

	}
	
	public void display(GL gl) {
		
		for(Shape shape: parts){
			shape.drawWireFrame(gl);
		}
		
		lumarca.lineMap.drawShapes(gl, new Coord(0,0,1), parts);
	}

	public void keyPressed() { 

		float speed = snakeSize;
		
		//front 512.0x400.0x420.0
		//back  512.0x400.0x10.0
		
		for(int i = parts.size() - 1; i > 0; i--){
			
			Diamond ball = (Diamond)(parts.get(i));
			Diamond prev = (Diamond)(parts.get(i - 1));
			
			ball.setCenter(prev.center);
		}
		
		if (lumarca.key == 'a') {
			center.z += speed;
		} else if (lumarca.key == 's') {
			center.z -= speed;
		}
		
		if (lumarca.key == 'q') {
			center.y += speed;
		} else if (lumarca.key == 'w') {
			center.y -= speed;
		}
		
		if (lumarca.key == 'x') {
			center.x += speed;
		} else if (lumarca.key == 'z') {
			center.x -= speed;
		}
		
		if(center.z >= 420){
			center.z = 10f;
		} else if(center.z <= 10){
			center.z = 420f;
		}
		
		if(center.y >= 530){
			center.y = 170f;
		} else if(center.y <= 170){
			center.y = 530;
		}
		
		if(center.x <= 212){
			center.x = 826;
		} else if(center.x >= 826){
			center.x = 212;
		}
	}

	@Override
	public void mousePressed() {
	}

}
