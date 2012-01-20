package objloader;

import processing.core.PApplet;

public class Debug {
	public boolean enabled = true;
	PApplet parent;
	public Debug(PApplet parent){
		this.parent = parent;
	}
	public void println(String str){
		if(enabled)
			parent.println(str);
	}
	public void print(String str){
		if(enabled)
			parent.print(str);
	}
}
