package lumarca.program;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.LineMap;
import lumarca.obj.Diamond;
import lumarca.obj.Shape;
import lumarca.util.Coord;


public class GameProgram extends LineProgram {

	private List<Ball> balls = new ArrayList<Ball>();
	
	public GameProgram(Lumarca lumarca) {
		super(lumarca);
		
		reset();
	}
	
	public void start(){
		reset();
		System.out.println("RESET!");
	}
	
	public void reset(){
		balls = new ArrayList<Ball>();

		for(int i = 0; i < 4; i++){
			balls.add(new Ball(i, new Diamond(null, 200), new Coord(0, 0.98f,0), 
					new Coord(lumarca.random(-10f,10f), 1, lumarca.random(-10f,10f))));
//					new Coord(lumarca.random(-50,50),1,1)));
//					lumarca.random(-50,50))));
		}
	}

	@Override
	public void update() {
		if(lumarca.frameCount%700 == 0){
			reset();
		}
		
		for(Ball ball: balls){
			ball.update();
		}
	}
	
	@Override
	public void display(GL gl) {
		for(Ball ball: balls){
			ball.display(gl);
		}
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}
	
	class Ball{
		
		Shape shape;
		Coord speed;
		Coord accel;
		Coord color;
		Coord vec;
		
		public Ball(float y, Shape shape, Coord accel, Coord vec) {
			super();
			this.shape = shape;
			this.speed = new Coord(1,1,1);
			this.accel = accel;
			this.vec = vec;
			
			this.color = new Coord(lumarca.random(1f), lumarca.random(1f), lumarca.random(1f));

//			vec.normalize();
			
			shape.center = new Coord(lumarca.random(200, Lumarca.WIN_WIDTH -200), 0, lumarca.random(100f, 300f));
		}
		
		public void update(){
			speed.add(accel);

			shape.center.addX(vec.x);
			shape.center.addY(vec.y * speed.y);
			shape.center.addZ(vec.z);
			
			shape.setCenter(shape.center);

			if((shape.center.x > 600) ||  (shape.center.x < 250) ){
				vec.x *= -1;
			}
			
			if(shape.center.y > lumarca.lineMap.minPosition.y && speed.y > 0){
//				System.out.println();
				speed.y *= -1;
			}

			if((shape.center.z > 420) || (shape.center.z < 0) ){
				vec.z *= -1;
			}
			
//			System.out.println(vec);
//			System.out.println(speed);
		}
		
		
		public void display(GL gl){
			lumarca.lineMap.drawShape(gl, color, shape);
		}
	}

}
