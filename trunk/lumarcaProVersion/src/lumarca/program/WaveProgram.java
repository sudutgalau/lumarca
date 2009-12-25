package lumarca.program;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PApplet;


public class WaveProgram extends LineProgram {

	private float waveRot = 0.0f;
	
	public WaveProgram(LumarcaPRO lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		waveRot += 0.001f;
	}

	@Override
	public void display(GL gl) {
		
		for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
			
			Coord coord = lumarca.lineMap.lines[lineNum].bottom;
			
			Util.drawLine(
						gl,
						new Coord(coord.x/1024f, 1f, 1024f - coord.x/1024f),
						new Coord(coord.x, 
								coord.y - PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 250,
								coord.z),
						new Coord(coord.x, 
													
								coord.y - 
								PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 200,
								
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
