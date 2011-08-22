package lumarca.program.show;

import lumarca.Lumarca;
import lumarca.obj.Pyramid;
import lumarca.program.AnimTextProgram;
import lumarca.program.ColorBounceProgram;
import lumarca.program.GameProgram;
import lumarca.program.ObjProgram;
import lumarca.program.OtherSnakeProgram;
import lumarca.program.WaterProgram;
import lumarca.program.WaveProgram;
import lumarca.program.WaveProgram2;
import lumarca.program.hear.HearProgram;
import lumarca.program.story.StoryProgram;
import lumarca.program.volcano.VolcanoProgram;
import lumarca.timer.ProgramTimer;

public class Siggraph extends StoryProgram{

	
	public Siggraph(Lumarca lumarca) {
		super(lumarca);

//		timers.add(new ProgramTimer(10, 30000, new AnimTextProgram(lumarca)));
		timers.add(new ProgramTimer(1, 30 * 10, new VolcanoProgram(lumarca)));
		timers.add(new ProgramTimer(1, 30 * 25, new ColorBounceProgram(lumarca)));
		timers.add(new ProgramTimer(1, 30 * 5, new WaveProgram2(lumarca)));
		timers.add(new ProgramTimer(1, 30 * 10, new WaterProgram(lumarca, 0.98f, 200)));
		timers.add(new ProgramTimer(1, 30 * 5, new HearProgram(lumarca)));
		timers.add(new ProgramTimer(1, 30 * 5, new WaveProgram(lumarca)));
		timers.add(new ProgramTimer(1, 30 * 25, new GameProgram(lumarca)));
		
		timers.get(0).trigger();
	}
	
}
