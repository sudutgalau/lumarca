package lumarca.program.volcano;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Pyramid;
import lumarca.obj.Shape;
import lumarca.program.LineProgram;
import lumarca.util.Coord;

public class VolcanoProgram extends LineProgram {

	public Shape base;
	public Shape base2;
	public List<LavaParticle> particleList = new ArrayList<LavaParticle>();
	public List<Shape> lavaList = new ArrayList<Shape>();
	
	public VolcanoProgram(Lumarca lumarca) {
		super(lumarca);
		
		Coord basePos = lumarca.lineMap.midPosition.clone();
//		basePos.y -= 50f;

		base = new Pyramid(basePos, 300f);
		base2 = new Pyramid(basePos, 300f);
		base2.rotateOnY(Lumarca.PI/4);
	}


	@Override
	public void update() {
		if(lumarca.frameCount % 2 == 0 ){
			particleList.add(new LavaParticle(lumarca));
			lavaList.add(particleList.get(particleList.size() - 1).getLava());
		}
	}
	
	@Override
	public void display(GL gl){ 

		List<LavaParticle> removeParticle = new ArrayList<LavaParticle>();
		List<Shape> removeLava = new ArrayList<Shape>();
		
		for(LavaParticle lava: particleList){
			lava.update();
			if(!lava.active){
				removeParticle.add(lava);
				removeLava.add(lava.getLava());
			}
		}

		particleList.removeAll(removeParticle);
		lavaList.removeAll(removeLava);
		
		lumarca.lineMap.drawShapes(gl, lavaList);
		
		lumarca.lineMap.drawShape(gl, new Coord(1f, .5f, 0), base);
		
//		base.drawWireFrame(gl);
//		lumarca.lineMap.drawShape(gl, new Coord(1f, .5f, 0), base2);
		
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

}
