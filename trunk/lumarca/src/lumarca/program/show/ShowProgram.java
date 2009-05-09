package lumarca.program.show;

import lumarca.Lumarca;
import lumarca.program.GameProgram;
import lumarca.program.ObjProgram;
import lumarca.program.OtherSnakeProgram;
import lumarca.program.WaterProgram;
import lumarca.program.WaveProgram;
import lumarca.program.WaveProgram2;
import lumarca.program.hear.DarkWaveProgram;
import lumarca.program.hear.HearProgram;
import lumarca.program.hear.LightWaveProgram;
import lumarca.program.story.StoryProgram;
import lumarca.timer.ProgramTimer;
import processing.core.PApplet;


public class ShowProgram extends StoryProgram{

	
	public ShowProgram(Lumarca lumarca) {
		super(lumarca);
		
//		timers.add(new ProgramTimer(10, 50000, new WaterProgram(lumarca, 0.98f, 200)));
//		timers.add(new ProgramTimer(10, 5000, new WaterProgram(lumarca, 1.011f, 400)));
		

		timers.add(new ProgramTimer(10, 10000, new WaterProgram(lumarca, 0.98f, 200)));
		timers.add(new ProgramTimer(10, 10000, new WaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 10000, new GameProgram(lumarca)));
		timers.add(new ProgramTimer(10, 10000, new HearProgram(lumarca)));
		timers.add(new ProgramTimer(10, 10000, new WaveProgram2(lumarca)));
		timers.add(new ProgramTimer(10, 10000, new ObjProgram(lumarca)));
		timers.add(new ProgramTimer(10, 10000, new OtherSnakeProgram(lumarca)));
	
		
		timers.get(0).trigger();
	}
	
}
