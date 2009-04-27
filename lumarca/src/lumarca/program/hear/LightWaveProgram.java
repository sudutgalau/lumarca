package lumarca.program.hear;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.program.LineProgram;
import lumarca.program.story.StepLineProgram;
import lumarca.util.Coord;
import lumarca.util.Util;

import processing.core.PApplet;


public class LightWaveProgram extends StepLineProgram {

	private float waveRot = 0.0f;
	
	public LightWaveProgram(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void timedUpdate(int i) {
		waveRot += 0.0025f * i;
	}

	@Override
	public void display(GL gl) {

		for (int lineNum = 0; lineNum < lumarca.LINE - 25; lineNum++) {

			Coord coord = lumarca.lineMap.lines[lineNum].bottom;

			Util.drawLine(
						gl,
						new Coord(0,0.5f,1),
						new Coord(coord.x, 
								coord.y - PApplet.sin(lumarca.lineMap.projectorX- coord.x / 50 + waveRot) * 50 - 250,
								coord.z),
						new Coord(coord.x, 

								coord.y - PApplet.sin(lumarca.lineMap.projectorX- coord.x / 50 + waveRot) * 50 - 200,
								
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
