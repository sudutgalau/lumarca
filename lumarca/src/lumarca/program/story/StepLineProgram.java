package lumarca.program.story;

import lumarca.Lumarca;
import lumarca.program.LineProgram;

public abstract class StepLineProgram extends LineProgram {

	protected int startMillis;
	protected int changeMillis;
	
	public StepLineProgram(Lumarca lumarca) {
		super(lumarca);
		
		startMillis = pApplet.millis();
	}

	public abstract void timedUpdate(int changeMillis);

	public void update() {
		changeMillis = startMillis - pApplet.millis();
		
		timedUpdate(changeMillis);
			
		startMillis = pApplet.millis();
	}

}
