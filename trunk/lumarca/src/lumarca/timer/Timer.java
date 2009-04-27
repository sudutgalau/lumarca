package lumarca.timer;

import lumarca.util.ProcessingObject;
public class Timer extends ProcessingObject{

	protected long begin;
	protected long end;
	
	protected long baseBegin;
	protected long baseEnd;
	
	protected long interval;
	protected long lastCheck;
	protected long lastInterval;
	
	protected boolean active = false;
	
	public Timer(long interval, long length) {
		this.interval = interval;
		this.baseEnd = length;
	}
		
	public void trigger() {
		active = true;
		begin = pApplet.millis();
		end = baseEnd + begin;
	}
	
	public void update() {
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
			executeStep();
			lastInterval = currentTime;
		}
		lastCheck = currentTime;
	}
	
	public long getElapsed() {
		return lastCheck - begin;
	}
	
	public long getRemaining() {
		return end - lastCheck;
	}
	
	protected void executeBegin() {
	}
	
	protected void executeStep() {
	}
	
	protected void executeEnd() {
	}
	
	public long getBegin() {
		return begin;
	}
	public void setBegin(long begin) {
		this.baseBegin = begin;
		this.begin = begin;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.baseEnd = end;
		this.end = end;
	}
	public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
	public boolean isActive() {
		return active;
	}
	
	public void deactivate() {
		active = false;
	}
}
