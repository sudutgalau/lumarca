package lumarca.program;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;


public class CamProgram extends LineProgram {

	private Capture cam;
	private PImage bg;
	private PImage pimgs[];
	private int ratio;
	
	public CamProgram(LumarcaPRO lumarca, Capture camera) {
		super(lumarca);
		
		cam = camera;
		bg = new PImage(cam.width, cam.height);
		pimgs = new PImage[]{new PImage(cam.width, cam.height),
				new PImage(cam.width, cam.height),
				new PImage(cam.width, cam.height),
				new PImage(cam.width, cam.height),
				new PImage(cam.width, cam.height),
				new PImage(cam.width, cam.height)};
		
		ratio = 1024/cam.width;
	}
	
	public int getIndex(float x, int y){
//		System.out.println(x);
		return (cam.height - y - 1) * cam.width + (cam.width - (int)x - 1);
//		return y * cam.width + (cam.width - ((int)(x/1.1f)) - 1);
//		return y * cam.width + (int)x/4;
	}
	
	public boolean inTolerance(int color1, int color2){
		
		return PApplet.dist(pApplet.red(color1), pApplet.green(color1), pApplet.blue(color1),
				pApplet.red(color2), pApplet.green(color2), pApplet.blue(color2)) > 70f;
	}

	public void update() {
		if (cam.available()) {
			cam.read();

//			pApplet.image(cam, 0, lumarca.height/2);
		}
	}
	
	public void display(GL gl) {
		
		if(lumarca.frameCount%3 == 0){
			for(int i = pimgs.length - 1; i > 0 ; i--){
				pimgs[i].pixels = pimgs[i - 1].pixels.clone();
				pimgs[i].updatePixels();
			}
		}
		
		for(int x = 0; x < cam.width; x++){
			for(int y = 0; y < cam.height; y++){
				if(inTolerance(
						cam.pixels[getIndex(x, y)], 
						bg.pixels[getIndex(x, y)])){
//					System.out.println("DIFF");
					pimgs[0].pixels[getIndex(x, y)] = cam.pixels[getIndex(x, y)];
					
				} else {
					pimgs[0].pixels[getIndex(x, y)] = pApplet.color(0);
				}
			}	
		}
		
		pimgs[0].updatePixels();
		
		for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
			Line line = lumarca.lineMap.lines[lineNum];

			int maxY = 1000;
			int minY = -1000;
			
			for (int i = 0; i < pimgs.length - 1; i++) {
//				System.out.println("=============");
//				System.out.println(line.bottom.z);
//				System.out.println((line.bottom.z > (442 / pimgs.length * i)) && 
//						(line.bottom.z < (442 / pimgs.length * (i+1))));
//				System.out.println((442 / pimgs.length * i));
//				System.out.println((442 / pimgs.length * (i+1)));
//				System.out.println("=============");
				
				if ((line.bottom.z >= (442 / pimgs.length * i)) && 
						(line.bottom.z <= (442 / pimgs.length * (i+1)))){

					Coord bottom = line.bottom.clone();
					Coord top = line.bottom.clone();
					
					
					
					for (int y = 0; y < cam.height - 1; y++) {
						
//						if (maxY > y) {
//						maxY = y;
//					}
//					if (minY < y) {
//						minY = y;
//					}
						 
//						System.out.println("pixel: " + pimgs[0].pixels[getIndex(line.bottom.x/ratio, y/2)]);

//						 System.out.println("x: " + line.bottom.x/ratio + " Y: " +
//								 y + " = " + ratio);
						 
						if (pimgs[0].pixels[getIndex(line.bottom.x/ratio, y)]  != pApplet.color(0)) {

//							 System.out.println("x: " + line.bottom.x/ratio + " Y: " +
//									 y + " = " + i);

							float r = 1;//((float)(pApplet.red(pimgs[i].pixels[getIndex(line.bottom.x/ratio, y)])))/255f;
							float g = 0;//((float)(pApplet.green(pimgs[i].pixels[getIndex(line.bottom.x/ratio, y)])))/255f;
							float b = 0;//((float)(pApplet.blue(pimgs[i].pixels[getIndex(line.bottom.x/ratio, y)])))/255f;
							
							
							bottom.y = line.bottom.y - y * 2;
							top.y = bottom.y + 3;

							Util.drawLineNoDots(gl, new Coord(r, g, b), bottom, top);
							
//							if (maxY > y) {
//								maxY = y;
//							}
//							if (minY < y) {
//								minY = y;
//							}
						}
//					}


//					 System.out.println("x: " + line.bottom.x + " Y: " +
//							 y);
//
//					 System.out.println(line.bottom.y);
//					 System.out.println(767);
					

//					Coord bottom = line.bottom.clone();
//					Coord top = line.bottom.clone();
//
//					if (maxY != 1000) {
//						bottom.y = minY;
//						top.y = maxY;
//
//						Util.drawLineNoDots(gl, new Coord(1, 1, 1), bottom, top);
					}

				}

//				 System.out.println("-------");
			}
		}
//			
//			for(int y = 0; y < cam.height; y++){
//				if(inTolerance(
//						cam.pixels[getIndex(line.bottom.x, y)], 
//						bg.pixels[getIndex(line.bottom.x, y)])){
//					pimgs[0].pixels[getIndex(line.bottom.x, y)] = pApplet.color(255);
//					if(maxY > y){
//						maxY = y;
//					}
//					if(minY < y){
//						minY = y;
//					}
//					
//				} else {
//					pimgs[0].pixels[getIndex(line.bottom.x, y)] = 0;
//				}
//			}
//			
//			Coord top = line.top.clone();
//			Coord bottom = line.bottom.clone();
//
//			top.y = maxY * 3;
//			bottom.y = minY * 3;
//			
//			if(maxY != 1000)
//			{
//				Util.drawLine(gl, new Coord(1,0,0), top, bottom);
//			}
//			
//		}
//		
//		for(int i = 0; i < pimgs.length; i++){
//			
//		}
		
		
//		for(int i = 0; i < pimgs.length; i++){
//			pApplet.image(pimgs[i], i * cam.width, lumarca.height/3);
//		}
	}

	@Override
	public void keyPressed() {
		
		bg.pixels = cam.pixels.clone();
		bg.updatePixels();
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
