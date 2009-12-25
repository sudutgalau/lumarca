package lumarca.program.show;

import lumarca.LumarcaPRO;
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

public class Gizmodo extends StoryProgram{

	
	public Gizmodo(LumarcaPRO lumarca) {
		super(lumarca);

//		timers.add(new ProgramTimer(10, 30000, new AnimTextProgram(lumarca)));
		timers.add(new ProgramTimer(10, 30000, new VolcanoProgram(lumarca)));
		timers.add(new ProgramTimer(10, 30000, new ColorBounceProgram(lumarca)));
		timers.add(new ProgramTimer(10, 10000, new WaterProgram(lumarca, 0.98f, 200)));
		timers.add(new ProgramTimer(10, 10000, new HearProgram(lumarca)));
		
		timers.get(0).trigger();
	}
	
}
