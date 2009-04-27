package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;

import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;


public class CamProgram extends LineProgram {

	private Capture cam;
	private PImage pimg;
	private PImage pimg2;
	
	public CamProgram(Lumarca lumarca, Capture camera) {
		super(lumarca);
		
		cam = camera;
		pimg = new PImage(cam.width, cam.height);
		pimg2 = new PImage(cam.width, cam.height);
	}
	
	public int getIndex(float x, int y){
//		System.out.println(x);
		return (cam.height - y - 1) * cam.width + (cam.width - (int)x/4 - 1);
//		return y * cam.width + (cam.width - (int)x/4 - 1);
//		return y * cam.width + (int)x/4;
	}
	
	public boolean inTolerance(int color1, int color2){
		
		return PApplet.dist(pApplet.red(color1), pApplet.green(color1), pApplet.blue(color1),
				pApplet.red(color2), pApplet.green(color2), pApplet.blue(color2)) > 40f;
	}

	public void update() {
		if (cam.available()) {
			cam.read();
		}
	}
	
	public void display(GL gl) {
		
		for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
			Line line = lumarca.lineMap.lines[lineNum];

			int maxY = 1000;
			int minY = -1000;
			
			for(int y = 0; y < cam.height; y++){
				if(inTolerance(
						cam.pixels[getIndex(line.bottom.x, y)], 
						pimg.pixels[getIndex(line.bottom.x, y)])){
					pimg2.pixels[getIndex(line.bottom.x, y)] = pApplet.color(255);
					if(maxY > y){
						maxY = y;
					}
					if(minY < y){
						minY = y;
					}
					
				} else {
					pimg2.pixels[getIndex(line.bottom.x, y)] = 0;
				}
			}
			
			Coord top = line.top.clone();
			Coord bottom = line.bottom.clone();

			top.y = maxY * 3;
			bottom.y = minY * 3;
			
			if(maxY != 1000)
			{
				Util.drawLine(gl, new Coord(1,0,0), top, bottom);
			}
			
		}
		
		pimg2.updatePixels();
		
		pApplet.image(pimg2, 0, 0);
	}

	@Override
	public void keyPressed() {
		
		pimg.pixels = cam.pixels.clone();
		pimg.updatePixels();
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
