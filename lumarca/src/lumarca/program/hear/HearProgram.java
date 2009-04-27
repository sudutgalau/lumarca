package lumarca.program.hear;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Shape;
import lumarca.program.CalabProgram;
import lumarca.program.story.StoryProgram;
import lumarca.timer.ProgramTimer;
import lumarca.util.Coord;

import processing.core.PApplet;


public class HearProgram extends StoryProgram {
	
	Coord center  = new Coord(Lumarca.WIN_WIDTH/2 + 50f, 150f, 50 * 5);
	Boat boat = new Boat(center, 450f);
	
	float end = 0.3f;
	float rot = 0;
	int dir = -1;
	
	public HearProgram(Lumarca lumarca) {
		super(lumarca);


		boat.rotateOnX(PApplet.PI);
		
		timers.add(new ProgramTimer(10, 5000, new DarkWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new LightWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new DarkWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new LightWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 5000, new DarkWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new LightWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new DarkWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new LightWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 5000, new DarkWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new LightWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new DarkWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 100, new LightWaveProgram(lumarca)));
		timers.add(new ProgramTimer(10, 5000, new DarkWaveProgram(lumarca)));
		
//		timers.get(0).trigger();
	}
	
	public void display(GL gl){
		super.display(gl);

		rot += 0.01f * dir;
		System.out.println("ROT: " + rot);

		if(PApplet.abs(rot) > end){
			dir *= -1;
		}

		boat.rotateOnX(0.01f * dir);
//		boat.rotateOnZ(0.01f * dir);
		
//		boat.drawWireFrame(gl);
		
		List<Shape> shapes = new ArrayList<Shape>();
		shapes.add(boat);
		
		lumarca.lineMap.drawShapes(gl, new Coord(1,0,1), shapes);
	}
}
