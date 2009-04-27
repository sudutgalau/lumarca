package lumarca.program;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.net.URL;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.Util;

import processing.core.PApplet;
import processing.core.PImage;
import processing.opengl.PGraphicsOpenGL;


public class WaterProgram extends LineProgram {

	private float sludgefactor;
	private float map1[];
	private float map2[];
	private float pt1[];
	private float pt2[];
	private float tmpt[];
	
	private float drag = 0.99f;
	private int waterHeight = 200;

	public WaterProgram(Lumarca lumarca, float drag, int waterHeight) {
		super(lumarca);

		this.drag = drag;
		this.waterHeight = waterHeight;
		
		sludgefactor = 6F;
		map1 = new float[443*lumarca.width];
		map2 = new float[443*lumarca.width];
		
		pt1 = map1;
		pt2 = map2;
	}
	
	private void splash(int x, int y, float splashDepth, int splashWidth) {
		if (x >= splashWidth && y >= splashWidth && x < lumarca.width - splashWidth && y < 443 - splashWidth) {
			for (int l = -splashWidth; l < splashWidth; l++) {
				for (int i1 = -splashWidth; i1 < splashWidth; i1++)
					pt2[(y + i1) *	lumarca.width + x + l] += splashDepth;
			}

		}
	}
	
	public void update() {
		
		System.out.println("SIZE:" + 443*lumarca.width);
		
//		splash(lumarca.mouseX, lumarca.mouseY, 0.5F, 5);
		if(lumarca.frameCount%3 == 0)
		 splash(PApplet.parseInt(lumarca.random(lumarca.width)),
		 PApplet.parseInt(lumarca.random(lumarca.width)), 0.003F, 75);

		for (int y = 1; y < 443 - 1; y++) {
			for (int x = 1; x < lumarca.width - 1; x++){

				pt1[x + y * lumarca.width] =
					drag * ((getValue(x + 1, y, pt2)
						+ getValue(x - 1, y, pt2) 
						+ getValue(x, y + 1, pt2) 
						+ getValue(x, y - 1, pt2)) / 2.0F 
						- getValue(x, y, pt1));
			}
		}

		tmpt = pt2;
		pt2 = pt1;
		pt1 = tmpt;
	}

	public int getIndex(int x, int y, int width) {
		return y * width + x;
	}

	public float getValue(int x, int y, float af[]) {
		return af[getIndex(x,y,lumarca.width)];
	}

	@Override
	public void display(GL gl) {	

		int minX = -100;
		int maxX = 10000;
		
		for(Line line: lumarca.lineMap.lines){
			int x = (int)line.bottom.x;
			int z = (int)line.bottom.z;

			Coord bottom = line.bottom.clone();
			bottom.y = waterHeight;
			Coord top = bottom.clone();
			
			if(z > 440){
				System.out.println("x: " + x + " z: " + z + " = " + getIndex(x, z, lumarca.width) + " " + pt1.length);
			}
			
			top.y -= pt1[getIndex(x, z, lumarca.width)] * 1000;
			bottom.y = top.y + 20;//pt1[getIndex(x, z, lumarca.width)] * 1000;
//			
			Util.drawLineNoDots(gl, new Coord(0,0,1), bottom, top);

			top.y = bottom.y - 2;
			
			Util.drawLineNoDots(gl, new Coord(0,1,1), bottom, top);
//				ellipse(x * 2, y * 2, 20 - pt1[x + y * width] * 20, 20 - pt1[x + y * width] * 20);
//			    popMatrix();

			if(minX < z)
				minX = z;
			if(maxX > z)
				maxX = z;
			}

		System.out.println(minX + "-----------------" + maxX);
	}

	@Override
	public void keyPressed() {
 		System.out.println("DIST");
	}

	@Override
	public void mousePressed() {
	}

}
