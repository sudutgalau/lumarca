package lumarca.program;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.obj.Rectangle;
import lumarca.obj.Shape;
import lumarca.util.Coord;

public class ArtProgram extends LineProgram{

	private List<Shape> shapes = new ArrayList<Shape>();
	
	private Rectangle box;
	private float boxPercent = 0f;
	
	private int dir = 1;
	
	public ArtProgram(LumarcaPRO lumarca) {
		super(lumarca);
		
		box = new Rectangle(lumarca.lineMap.maxPosition.clone(), 400, 400, 400);
		box.color = new Coord(1,0,0);
		
		shapes.add(box);
	}
	

	@Override
	public void start() {
		boxPercent += 0.0f;
	}

	@Override
	public void update() {
		boxPercent += 0.01f;
		box.setCenter(new Coord(
				pApplet.lerp(lumarca.lineMap.maxPosition.x, lumarca.lineMap.minPosition.x, boxPercent), 
				pApplet.lerp(lumarca.lineMap.maxPosition.y, lumarca.lineMap.minPosition.y, boxPercent),
				pApplet.lerp(lumarca.lineMap.maxPosition.z, lumarca.lineMap.minPosition.z, boxPercent)));
		System.out.println(box.center.x);
		
		box.setColor(new Coord(
				pApplet.lerp(0,1, boxPercent), 
				pApplet.lerp(1,0, boxPercent),
				pApplet.lerp(0,0, boxPercent)));
	}
	
	
	@Override
	public void display(GL gl) {
		// TODO Auto-generated method stub
		lumarca.lineMap.drawShapes(gl, shapes);
		
//		box.center = new Coord(box.)
		
//		box.drawWireFrame(gl);
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
