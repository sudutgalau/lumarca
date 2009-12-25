package lumarca.program;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;

import processing.video.Movie;
import processing.video.MovieMaker;

//void setup() {
//	  size(320, 240);
//	  // Create MovieMaker object with size, filename,
//	  // compression codec and quality, framerate
//	  mm = new MovieMaker(this, width, height, "drawing.mov",
//	                       30, MovieMaker.H263, MovieMaker.HIGH);
//	  background(204);
//	}
//
//	void draw() {
//	  ellipse(mouseX, mouseY, 20, 20);
//	  mm.addFrame();  // Add window's pixels to movie
//	}
//
//	void keyPressed() {
//	  if (key == ' ') {
//	    mm.finish();  // Finish the movie if space bar is pressed!
//	  }
//	}

public class PlayMovieProgram extends LineProgram {
	

	private MovieMaker mm;  // Declare MovieMaker object
	
	private Movie mov;

	public PlayMovieProgram(LumarcaPRO lumarca) {
		super(lumarca);
		
		System.out.println("HUH?");
		
//		mov = new Movie(lumarca, "station.mov");
		start();
	}

	@Override
	public void start() {
//		  mov.loop();
		  pApplet.camera();

			System.out.println("HUH?");

		  mm = new MovieMaker(lumarca, ((int)LumarcaPRO.WIN_WIDTH), ((int)LumarcaPRO.WIN_HEIGHT)/2, "drawing.mov",
		                       30);
			System.out.println("HUH?");
	}

	@Override
	public void update() {
//		if(mov.available())
//		  mov.read();
		System.out.println("HUH?");
	}
	
	@Override
	public void display(GL gl) {
		  
		System.out.println("MovieState: " + myMovieState);
		
		  float width = pApplet.width;
		float height = pApplet.height;

		pApplet.pushMatrix();
		
		
		lumarca.lineMap.drawFloorBox(gl);
		lumarca.lineMap.drawCalebrate();
		
//		pApplet.translate(-1024, -768 * 2, 0);
//		pApplet.translate(0,0, +1024 * 2);

//		if (mov.available())
//			pApplet.image(mov, 0, 0, 1024, 768);
		pApplet.popMatrix();


		if(myMovieState)
		  mm.addFrame();  // Add window's pixels to movie		 
	}

	@Override
	public void keyPressed() {

	}
	
	boolean myMovieState = false;

	@Override
	public void mousePressed() {

		if(myMovieState)
		    mm.finish();  // Finish the movie if space bar is pressed!
		
		myMovieState = !myMovieState;
		

	}
}
