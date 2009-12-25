package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.util.ProcessingObject;


public abstract class LineProgram extends ProcessingObject{

	protected Lumarca lumarca;

	public LineProgram(Lumarca lumarca) {
		super();
		this.lumarca = lumarca;
	}

	public abstract void update();
	public abstract void display(GL gl);
	public abstract void keyPressed();
	public abstract void mousePressed();
	
	public Lumarca getLumarca() {
		return lumarca;
	}
	
	public void setLumarca(Lumarca lumarca) {
		this.lumarca = lumarca;
	}

	public void start(){}
	public void exit(){}
}
