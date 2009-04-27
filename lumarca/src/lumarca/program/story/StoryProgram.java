package lumarca.program.story;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import objloader.OBJModel;

import lumarca.Lumarca;
import lumarca.obj.Diamond;
import lumarca.obj.Shape;
import lumarca.program.CalabProgram;
import lumarca.program.LineProgram;
import lumarca.program.WaveProgram;
import lumarca.program.WaveProgram2;
import lumarca.program.hear.Boat;
import lumarca.timer.ProgramTimer;
import lumarca.timer.Timer;
import lumarca.util.Coord;

import processing.core.PApplet;


public class StoryProgram extends LineProgram {
	
	protected List<ProgramTimer> timers = new ArrayList<ProgramTimer>();
	
	public StoryProgram(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void display(GL gl) {
		
		for(int i = 0; i < timers.size(); i++){
			ProgramTimer timer = timers.get(i);
			if(timer.active){
				timer.update(gl);
				if((!timer.active) && (timers.size() - 1 != i)){
					timers.get(i + 1).trigger();
				} else if(!timer.active){
					timers.get(0).trigger();
				}
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
