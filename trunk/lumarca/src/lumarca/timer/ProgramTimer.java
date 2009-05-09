package lumarca.timer;

import javax.media.opengl.GL;

import lumarca.program.LineProgram;
import lumarca.util.ProcessingObject;


public class ProgramTimer extends ProcessingObject{

	protected long baseEnd;
	protected long interval;
	
	protected long begin;
	protected long end;
	
	protected long lastCheck;
	protected long lastInterval;
	
	public boolean active = false;
	
	public LineProgram program;

	public ProgramTimer(long interval, long length, LineProgram program) {
		this.interval = interval;
		this.baseEnd = length;
		this.program = program;
	}
	
	public void trigger() {
		active = true;
		begin = pApplet.millis();
		end = baseEnd + begin;
	}
	
	public void update(GL gl) {
		long currentTime = pApplet.millis();
		

//		System.out.println("currentTime: " + currentTime + " end: " + end);
		
		if (currentTime > begin && lastCheck < begin) {
			executeBegin();
		}
		else if (currentTime > end){// && lastCheck < end) {
			active = false;
			executeEnd();
		}
		
		if (active && (currentTime > begin) && (currentTime >= lastInterval + interval)) {
			executeStep(gl);
			lastInterval = currentTime;
		}
		lastCheck = currentTime;
	}

	protected void executeBegin() {
	}
	
	protected void executeEnd() {
	}
	
	protected void executeStep(GL gl) {
		program.update();
		program.display(gl);
	}
}
