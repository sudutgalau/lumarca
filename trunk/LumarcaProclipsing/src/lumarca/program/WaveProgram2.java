package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PApplet;


public class WaveProgram2 extends LineProgram {

	private float waveRot = 0.0f;
	
	public WaveProgram2(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		waveRot += 0.001f;
		
//		if(lumarca.frameCount% 90 == 0)
//			colorMode = ! colorMode;
	}
	
	boolean colorMode = true;

	@Override
	public void display(GL gl) {

		for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {

			Coord coord = lumarca.lineMap.lines[lineNum].bottom;
			
			Coord colorCoord = new Coord(coord.x, 
					coord.y + 
					(PApplet.sin(lumarca.lineMap.projectorZ- coord.z / 100 + waveRot * 50) * 150 - 250 +
					PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 350)/2f,
					coord.z);

			float r = pApplet.map(colorCoord.y,  lumarca.lineMap.minPosition.y, lumarca.lineMap.maxPosition.y,
					0f, 1f);
			float b = 1f - pApplet.map(colorCoord.y,  lumarca.lineMap.minPosition.y, lumarca.lineMap.maxPosition.y,
					0f, 1f);
			
			float g = 0;
			
			if(colorMode){
				r = 0;
				b = 0;
				g = 1;
			
			Util.drawLine(
					gl,
					new Coord(r, g, b),
					new Coord(coord.x, 
							coord.y + 
							(PApplet.sin(lumarca.lineMap.projectorZ- coord.z / 100 + waveRot * 50) * 150 - 250 +
							PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 350)/2f,
							coord.z),
					new Coord(coord.x, 
												
							coord.y + 
							(PApplet.sin(lumarca.lineMap.projectorZ- coord.z / 100 + waveRot * 50) * 150 - 200 +
							PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 300)/2f,
							
							coord.z));
			} else {
				Util.drawLine(
						gl,
						new Coord(r, g, b),
						new Coord(coord.x, 
								coord.y + 
								(PApplet.sin(lumarca.lineMap.projectorZ- coord.z / 100 + waveRot * 50) * 150 - 250 +
								PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 350)/2f,
								coord.z),
						new Coord(coord.x, 
													
								coord.y + 
								(PApplet.sin(lumarca.lineMap.projectorZ- coord.z / 100 + waveRot * 50) * 150 - 200 +
								PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 300)/2f,
								
								coord.z));
			}
			
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
