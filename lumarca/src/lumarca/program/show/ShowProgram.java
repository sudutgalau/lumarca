package lumarca.program.show;

import lumarca.Lumarca;
import lumarca.program.WaterProgram;
import lumarca.program.hear.DarkWaveProgram;
import lumarca.program.hear.LightWaveProgram;
import lumarca.program.story.StoryProgram;
import lumarca.timer.ProgramTimer;
import processing.core.PApplet;


public class ShowProgram extends StoryProgram{

	
	public ShowProgram(Lumarca lumarca) {
		super(lumarca);
		
		timers.add(new ProgramTimer(10, 50000, new WaterProgram(lumarca, 0.98f, 200)));
		timers.add(new ProgramTimer(10, 5000, new WaterProgram(lumarca, 1.011f, 400)));
		
		timers.get(0).trigger();
	}
	
}
