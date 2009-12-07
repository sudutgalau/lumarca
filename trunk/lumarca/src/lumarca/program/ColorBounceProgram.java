package lumarca.program;

import javax.media.opengl.GL;

import processing.core.PApplet;

import lumarca.Lumarca;
import lumarca.util.Coord;
import lumarca.util.Util;

public class ColorBounceProgram extends LineProgram {

	private float waveRot = 0.0f;
	private float dest = 0.75f;
	private float tol = .1f;
	private float lerpVal = 0.005f;
	
	public ColorBounceProgram(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		waveRot = PApplet.lerp(waveRot, dest, lerpVal);
		
		if(PApplet.abs(waveRot) > PApplet.abs(dest) - tol){
			dest *= -1;
		}
		
//		System.out.println(waveRot);
	}

	@Override
	public void display(GL gl) {
		
		for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
			
			Coord coord = lumarca.lineMap.lines[lineNum].bottom;
			
			Util.drawLine(
						gl,
						new Coord(1- waveRot, 0, waveRot),
						new Coord(coord.x, 
								lumarca.lineMap.midPosition.y - lumarca.dist(coord.x, coord.z, 
									 lumarca.lineMap.midPosition.x, lumarca.lineMap.midPosition.z) * 1 * waveRot,
								coord.z),
						new Coord(coord.x, 
								lumarca.lineMap.midPosition.y + lumarca.dist(coord.x, coord.z, 
										 lumarca.lineMap.midPosition.x, lumarca.lineMap.midPosition.z) * 1 * waveRot,
								
								coord.z));
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

}
