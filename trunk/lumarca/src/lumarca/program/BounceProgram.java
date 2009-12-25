package lumarca.program;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.obj.Diamond;
import lumarca.obj.Rectangle;
import lumarca.obj.Shape;
import lumarca.obj.TrianglePlane;
import lumarca.util.Coord;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;
import ddf.minim.AudioSnippet;

public class BounceProgram extends LineProgram {


	float speed = 20f;

	Coord center = new Coord(LumarcaPRO.WIN_WIDTH/2, 200f, 410);//snakeSize * 5);
	
	public List<Shape> parts;
	Diamond diamond;
	Rectangle rectangle;
	
	public float snakeSize = 30f;

	public Coord dir;
//	public Coord dir = new Coord(0,2.5f,0);
	public static float gravity = 0.98f;
	public static float velocity = 0;

	public final static int PLAY = 0;
	public final static int DEAD = 1;
	
	public static int mode;

	public AudioSnippet bounceSound1;
	public AudioSnippet bounceSound2;
	public AudioSnippet paddleSound;
	public AudioSnippet endSound;
	
	public Capture cam;
	private PImage pimg;
	private PImage pimg2;

	public BounceProgram(LumarcaPRO lumarca, Capture cam) {
		super(lumarca);

		init();
		
		this.cam = cam;
		this.pimg = new PImage(cam.width, cam.height);
		this.pimg2 = new PImage(cam.width, cam.height);
		
		parts.add(diamond);
		parts.add(rectangle);
	}

	public void init(){

		
		snakeSize = 30f;
		
		mode = PLAY;
		paddleSound = LumarcaPRO.minim.loadSnippet("aqua.mp3");
		bounceSound1 = LumarcaPRO.minim.loadSnippet("BD.mp3");
//		bounceSound1 = LumarcaPRO.minim.loadSnippet("BD.mp3");
		endSound = LumarcaPRO.minim.loadSnippet("aaah.mp3");
		
//		paddleSound.play();
		 
		center = new Coord(LumarcaPRO.WIN_WIDTH/2, 400f, 200);//snakeSize * 5);
		
		dir = new Coord(2.5f,2.5f,2.5f);
		
		parts = new ArrayList<Shape>();
		diamond = new Diamond(center, snakeSize * 5f);
		rectangle = new Rectangle(new Coord(LumarcaPRO.WIN_WIDTH/2, 90f, 200), snakeSize * 100, 35, snakeSize * 100);

		parts.add(diamond);
		parts.add(rectangle);
	}
	
	private boolean collision(){
		Coord dCenter = diamond.getCenter();
		Coord rCenter = rectangle.getCenter();

		return true;
		
//		return 
//		((diamond.getCenter().x < rCenter.x + rectangle.width/2) && 
//				(diamond.getCenter().x > rCenter.x - rectangle.width/2) && 
//				(diamond.getCenter().z < rCenter.z + rectangle.depth/2) &&
//				(diamond.getCenter().z > rCenter.z - rectangle.depth/2));

	}
	
	public boolean inTolerance(int color1, int color2){
		
		return PApplet.dist(pApplet.red(color1), pApplet.green(color1), pApplet.blue(color1),
				pApplet.red(color2), pApplet.green(color2), pApplet.blue(color2)) > 70f;
	}
	
	public void reverse(){
		bounceSound1.cue(0);
		bounceSound1.play();

		diamond.setCenter(Coord.add(diamond.getCenter(), dir));
		
		System.out.println("REVERSE!!!");
	}

	
	public int getIndex(float x, int y){
//		System.out.println(x);
		return (cam.height - y - 1) * cam.width + (cam.width - (int)x - 1);
//		return y * cam.width + (cam.width - (int)x/4 - 1);
//		return y * cam.width + (int)x/4;
	}
	
	public void update() {

		if (cam.available()) {
			cam.read();
		}
		
//		for(int x = 0; x < cam.width; x++){
//			
//			for(int y = 0; y < cam.height; y++){
//				if(inTolerance(
//						cam.pixels[getIndex(x, y)], 
//						pimg.pixels[getIndex(x, y)])){
//					pimg2.pixels[getIndex(x, y)] = pApplet.color(255);
////					if(maxY > y){
////						maxY = y;
////					}
////					if(minY < y){
////						minY = y;
////					}
//					
//				} else {
//					pimg2.pixels[getIndex(x, y)] = 0;
//				}
//			}
//		}
//
//		pimg2.updatePixels();
//
//		pApplet.image(pimg2, 500, 500);
//		pApplet.image(pimg, 500, 500);

		int tx = 0;
		int ty = 0;
		int numPts = 0;
		
		for(int x = 0; x < cam.width; x++){
			
			for(int y = 0; y < cam.height; y++){
				
				if(inTolerance(
						cam.pixels[getIndex(x, y)], 
						pimg.pixels[getIndex(x, y)])){
					
					tx += x;
					ty += y;
					numPts++;
					pimg2.pixels[getIndex(x, y)] = lumarca.color(255);
					
//					if(minY < y){
//						if(prev){
//							minY = y;
//							camLine++;
//						} else {
//							camLine = 0;
//							prev = true;
//						}
//					} else {
//						prev = false;
//					}
//					
//					if(camLine > 15){
////						System.out.println("CONTROL");
////						System.out.println(rectangle.getCenter());
//						rectangle.center.x = x;
//						rectangle.center.z = y;
////						System.out.println(rectangle.getCenter());
//						break;
//					}
					
				} else {
					pimg2.pixels[getIndex(x, y)] = lumarca.color(0);
				}
			}
			
		}
		
		pimg2.updatePixels();

		if(numPts > 0){
			rectangle.center.x = 4 * tx/numPts;
			rectangle.center.z = 4 * ty/numPts - 150f;
		}
		
		if (mode == PLAY) {
			diamond.setCenter(Coord.add(diamond.getCenter(), dir));

			if ((diamond.getCenter().x <= 250)
					|| (diamond.getCenter().x >= 770)) {
				dir.x *= -1;
				reverse();
			}

			if (diamond.getCenter().y >= 430) {
				dir.y *= -1;
				reverse();
			}

			if (diamond.getCenter().y <= 100) {

				if (collision()) {
					paddleSound.cue(0);
					paddleSound.play();
					System.out.println("contact");
					dir.y *= -1;
					reverse();
					
					snakeSize -= 0.5f;

//					diamond = new Diamond(diamond.getCenter(), snakeSize * 5f);
//					rectangle = new Rectangle(rectangle.getCenter(), snakeSize * 10, 35, snakeSize * 10);
					
				} else {
					System.out.println("miss");
					mode = DEAD;
					endSound.cue(0);
					endSound.play();
				}
			}

			if ((diamond.getCenter().z >= 420) || (diamond.getCenter().z <= 10)) {
				dir.z *= -1;
				reverse();
			}
		}
		
//		System.out.println(diamond.getCenter().z);
//
//		center.y += velocity;
//		velocity += gravity;
//		
//		
//		System.out.println("velocity: " + velocity);
		
//		if(center.z >= 420){
//			center.z = 10f;
//		} else if(center.z <= 10){
//			center.z = 420f;
//		}
		
//		if(center.y >= 530){
////			center.y = 170f;
//			velocity *= -1;
//		} else if(center.y <= 170){
//			center.y = 530;
//			gravity *= -1;
//		}
		
//		if(center.x <= 212){
//			center.x = 826;
//		} else if(center.x >= 826){
//			center.x = 212;
//		}
		
//		center.x++;
		

		
		
//		f += 0.0001f;
		

		diamond.rotateOnY(0.1f);
		
//		rectangle.rotateOnY(0.01f);
	}
	

	TrianglePlane tri = new TrianglePlane(
			new Coord(400,450,  0),
			new Coord(200f,  0, 100), 
			new Coord(-200f, 0, 100), 
			new Coord(0f,  	 0, -100));
	
	float f = 0.0f;
	
	public void display(GL gl) {
		
		if (mode == PLAY) {
//			for (Shape shape : parts) {
//				shape.drawWireFrame(gl);
//			}

			
			lumarca.lineMap.drawShape(gl, new Coord(0, 0, 1), diamond);
			lumarca.lineMap.drawShape(gl, new Coord(1, 0, 0), rectangle);
		}

//		pApplet.image(pimg2, 400, 1000);
//		pApplet.image(cam, 700, 1000);

//		rectangle.drawWireFrame(gl);
//		diamond.drawWireFrame(gl);
		
	}

	public void keyPressed() { 

		
		pimg.pixels = cam.pixels.clone();
		pimg.updatePixels();
		
		if(lumarca.key == 'p') {
			init();
		}
		
		if (lumarca.key == 'w') {
			rectangle.getCenter().addZ(-speed);
		} else if (lumarca.key == 's') {
			rectangle.getCenter().addZ(speed);
		}
		
		if (lumarca.key == 'a') {
			rectangle.getCenter().addX(-speed);
		} else if (lumarca.key == 'd') {
			rectangle.getCenter().addX(speed);
		}
	}

	@Override
	public void mousePressed() {
		
		pimg.pixels = cam.pixels.clone();
		pimg.updatePixels();
	}
}