package lumarca.program.volcano;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.obj.Diamond;
import lumarca.obj.Shape;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;

public class LavaParticle extends ProcessingObject {

	private LumarcaPRO lumarca;
	private Shape lava;
	private Coord acel;
	public boolean active = true;
	public int timer = 0;
	
	public LavaParticle(LumarcaPRO lumarca) {
		super();
		this.lumarca = lumarca;
		
		Coord center = lumarca.lineMap.midPosition.clone();
		center.y -= 100f;
		
		lava = new Diamond(center, 150f);
		
		acel = new Coord(pApplet.random(-5, 5),pApplet.random(15, 25), pApplet.random(-5, 5));
		
		lava.color = new Coord(1, 0, 0);
	}
	
	
	public void update(){
	
		if(lava.getCenter().y < lumarca.lineMap.maxPosition.y){
			timer++;
		} else {
			acel.y--;
			
			Coord newCenter = Coord.add(lava.getCenter(), acel);
			
			lava.setCenter(newCenter);
		}
		
		if(timer > 30){
			active = false;
		}
	}

	public void display(GL gl){
		lumarca.lineMap.drawShape(gl, new Coord(1, pApplet.random(0.25f, 0.75f), 0), lava);
	}


	public Shape getLava() {
		return lava;
	}


	public void setLava(Shape lava) {
		this.lava = lava;
	}
}
