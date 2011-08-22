package windowtest;

import processing.core.PApplet;


public class WindowTest extends PApplet {

	public void setup() {
		size(100,100, OPENGL);
	}

	public void draw() {
		ellipse(10, 10, 10, 10);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { windowtest.WindowTest.class.getName() });
	}
}
