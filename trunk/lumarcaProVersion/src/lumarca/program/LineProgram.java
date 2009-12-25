package lumarca.program;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.util.ProcessingObject;


public abstract class LineProgram extends ProcessingObject{

	protected LumarcaPRO lumarca;

	public LineProgram(LumarcaPRO lumarca) {
		super();
		this.lumarca = lumarca;
	}

	public abstract void update();
	public abstract void display(GL gl);
	public abstract void keyPressed();
	public abstract void mousePressed();
	
	public LumarcaPRO getLumarca() {
		return lumarca;
	}
	
	public void setLumarca(LumarcaPRO lumarca) {
		this.lumarca = lumarca;
	}

	public void start(){}
	public void exit(){}
}
