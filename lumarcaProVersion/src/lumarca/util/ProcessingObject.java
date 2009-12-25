package lumarca.util;

import processing.core.PApplet;

public abstract class ProcessingObject {

	public static PApplet pApplet;

	public static PApplet getPApplet() {
		return pApplet;
	}

	public static void setPApplet(PApplet applet) {
		pApplet = applet;
	}	
}
