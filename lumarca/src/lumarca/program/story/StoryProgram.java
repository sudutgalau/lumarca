package lumarca.program.story;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.program.LineProgram;
import lumarca.timer.ProgramTimer;


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
					timers.get(i + 1).program.start();
				} else if(!timer.active){
					timers.get(0).trigger();
					timers.get(0).program.start();
				}
			} else {
				timer.program.exit();
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
