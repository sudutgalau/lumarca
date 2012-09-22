package lumarca.lineMap;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.obj.Shape;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import lumarca.util.Util;
import processing.core.PApplet;
import processing.core.PVector;

public class LineMap extends ProcessingObject{
	
	private int lineNum; // number of lines in contraption

	public float projectorX;
	public float projectorY;
	public float projectorZ;

	public Coord maxPosition = new Coord(-1000,-1000,-1000);
	public Coord minPosition = new Coord(20000,20000,20000);
	public Coord midPosition;
	
	public Line[] lines;
	
	public static LineMap lineMap;

	public float nearZ;
	public float farZ;
	
	ArrayList<Integer> depthList = null;
	
	public static LineMap getInstance(){
		return lineMap;
	}

	private static float tolerance;
	
	//Constructor for pre created map
	public LineMap(int lineNum, float projectorX, float projectorY, float projectorZ, String[] setUpLines){
		this.lineNum = lineNum;

		this.lines = new Line[lineNum];

		this.projectorX = projectorX;
		this.projectorY = projectorY;
		this.projectorZ = projectorZ;
		
		lineMap = this;
		
		lines = makeMap(setUpLines);
		
		makeMinMaxMidPos();
	}
	
	//Constructor for new map
	public LineMap(int lineNum, float projectorX, float projectorY, float projectorZ, float farZ, String fileName, boolean discreet, boolean cut){
		this.lineNum = lineNum;

		this.lines = new Line[lineNum];

		this.projectorX = projectorX;
		this.projectorY = projectorY;
		this.projectorZ = projectorZ;

		this.farZ = farZ;
		this.nearZ = 0;
		
		tolerance = (pApplet.width * pApplet.abs(farZ))/(lineNum * 10);
		
//		System.out.println("tolerance: " + tolerance);
		
		lineMap = this;
		
		makeMap();
		
		discreetCut(discreet, cut);
		
		makeMinMaxMidPos();
		
		saveMap(fileName);
	}
	
	private void discreetCut(boolean discreet, boolean cut){
		if(cut){
			if(discreet){
				
				int numLines = lines.length;
				descreetDepths();
				cutOutSideBox();
			
				while(numLines != lines.length){
					descreetDepths();
					numLines = lines.length;
					cutOutSideBox();
				}
			} else {
				cutOutSideBox();
			}
		} else if (discreet){
			descreetDepths();
		}
	}
	
	private void descreetDepths(){
		makeDepthList();
		
		for(int i = 0; i < lineMap.lines.length; i++){
			
			int whichString = depthList.get(i).intValue();
			
			Line line = lines[whichString];
			
			makeLine(whichString, line.originalX, pApplet.lerp(0, farZ, i/(float)lineMap.lines.length));
		}
	}
	
	private void cutOutSideBox(){
		List newLines = new ArrayList<Line>();
		
		for(Line line: lines){
			if(line.bottom.x > 0 && line.bottom.x < pApplet.width){
				newLines.add(line);
			}
		}
		
		lines = (Line[])newLines.toArray(new Line[newLines.size()]);
	}
	
	private void makeDepthList(){
		depthList = new ArrayList<Integer>(); 
		depthList.add(0);
		
		for(int i = 1; i < lines.length; i++){
			
			Line current = lines[i];
			boolean inserted = false;
			
			for(int c = 0; c < i; c++){
				Line prev = lines[depthList.get(c).intValue()];
				if(current.bottom.z > prev.bottom.z){
					depthList.add(c, new Integer(i));
					inserted = true;
					break;
				}
			}
			
			if(!inserted){
				depthList.add(new Integer(i));
			}
		}
	}
	
	private void cutOutSideOfBox(){

		List newLines = new ArrayList<Line>();
		
		for(Line line: lines){
			if(line.bottom.x > 0 && line.bottom.x < pApplet.width){
				newLines.add(line);
			}
		}
		
		lines = (Line[])newLines.toArray(new Line[newLines.size()]);
	}
	
	public void makeMinMaxMidPos(){
		
		for(Line line: lines){
			
			if(line.bottom.x > maxPosition.x){
				maxPosition.x = line.bottom.x;
			}
			if(line.bottom.x < minPosition.x){
				minPosition.x = line.bottom.x;
			}
			if(line.top.y > maxPosition.y){
				maxPosition.y = line.top.y;
			}
			if(line.bottom.y < minPosition.y){
				minPosition.y = line.bottom.y;
			}
			if(line.bottom.z > maxPosition.z){
				maxPosition.z = line.bottom.z;
			}
			if(line.bottom.z < minPosition.z){
				minPosition.z = line.bottom.z;
			}
		}

		midPosition = new Coord((maxPosition.x + minPosition.x)/2,
				(maxPosition.y + minPosition.y)/2,
				(maxPosition.z + minPosition.z)/2);
	}
	
	public void calculateLine(int lineCount){
		//get the distance between the vitrual gl camera and a line of tick marks at the vertical center of
		//the page
		float camDiff = PApplet.dist(
				projectorX, 
				projectorY,
				projectorZ, 
				lineCount * (pApplet.width/lineNum), 
				pApplet.height,
				pApplet.height);
		
	}
	
	public void drawShape(GL gl, PVector color, Shape shape){
		for(Line line: lines){
			shape.drawIntersect(gl, color, line);
		}
	}

	public void drawShapeNoDots(GL gl, PVector color, Shape shape){
		for(Line line: lines){
			shape.drawIntersect(gl, color, line, false);
		}
	}

	public List<Line> getShape(PVector color, Shape shape){
//		List<Line> interLines = new ArrayList<Line>();
//		
//		for(Line line: lines){
////			System.out.println("shape.getIntersect(color, line): " + shape.getIntersect(color, line).size());
//			interLines.addAll(shape.getIntersect(color, line));
//		}
		
		return shape.getIntersections(color);
	}

	public void drawShapes(GL gl, Coord color, List<Shape> shapes){
		for(Line line: lines){
			for(Shape shape: shapes){
				shape.drawIntersect(gl, color, line);
			}
		}
	}

	public void drawShapes(GL gl, List<Shape> shapes){
		for(Line line: lines){
			for(Shape shape: shapes){
				shape.drawIntersect(gl, shape.color, line);
			}
		}
	}
	
	private boolean tooClose(int line1, int line2){
		Coord coord1 = lines[line1].bottom;
		Coord coord2 = lines[line2].bottom;
		
		if(PApplet.dist(coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z) < tolerance){
//			System.out.println(PApplet.dist(coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z) + " : " + line2);

			return true;
		}
		
		return false;
	}
	
	private boolean validline(int lineNum){
		boolean result = true;
		
		for(int i = 0 ; i < lineNum; i++){
			if(tooClose(i, lineNum)){
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	
//	boolean depthIncr[] = new boolean[Lumarca.LINE];
	
//	public float getRandomSpot(){
//
//		return pApplet.random((pApplet.height/2) * mod, pApplet.height/2);
//	}
	
	private Line[] makeMap(String[] map) {
		
		Line[] lines = new Line[map.length];
		
		for(int i = 0; i < map.length; i++){
			String s = map[i];
			
			Coord bottom = new Coord();
			
			String[] cStr = s.split("x");
			bottom.x = Float.parseFloat(cStr[0]);
			bottom.y = Float.parseFloat(cStr[1]);
			bottom.z = Float.parseFloat(cStr[2]);
			
			Coord top = new Coord(bottom.x, 0, bottom.z);
			
			lines[i] = new Line(bottom, top, 0);
		}

		return lines;
	}
	

	
	private void makeMap() {
		int maxAttempts = 125;
		
		boolean reset = false;
		
		for (int i = 0; i < lineNum && !reset; i++) {
			float z = pApplet.random(farZ, nearZ);
			
			int attempts = 0;
			makeLine(i, i, z);
			
			while(!validline(i) && attempts < maxAttempts){
				attempts++;
				z = pApplet.random(farZ, nearZ);
				makeLine(i, i, z);
			}
			
			if(attempts == maxAttempts){
				reset = true;
				tolerance-=0.1f;

				System.out.println("tolerance: " + tolerance);
				break;
			}
		}
		
		if(reset){
			makeMap();
		}
	}
	
	public void saveMap(String fileName){	
		PrintWriter output = pApplet.createWriter(fileName);
		
		for (int i = 0; i < lines.length; i++) {
			output.println(lines[i].bottom);
		}

		output.flush(); // Write the remaining data
		output.close(); // Finish the file
	}
	
	public void makeLine(int i, float xPos, float z){

		float y = pApplet.height;
		
		PVector slope = new PVector(projectorX - (pApplet.width/(float)lineNum) * xPos, 0, projectorZ -nearZ);
		slope.normalize();

		float magnitude = (projectorZ - z)/slope.z;
		
		lines[i] = new Line(
				new Coord(projectorX - slope.x * magnitude, y, projectorZ - slope.z * magnitude), 
				new Coord(projectorX - slope.x * magnitude, 0, projectorZ - slope.z * magnitude),
				xPos);
	}

	public void drawFloorBox(GL gl) {
		gl.glColor3f(1.0f, 1.0f, 1.0f);

		//floor box
//		gl.glBegin(GL.GL_LINE_LOOP);
//		gl.glVertex3f(0, minY, 0f); //X: 1/4, Y: 577, Z: 0
//		gl.glVertex3f(1024, minY, 0f); //X: 3/4, Y: 577, Z: 0
//		gl.glVertex3f(1024, minY, (1024f / 3.0f) * 2.0f); //X: 3/4, Y: 577, Z: 330
//		gl.glVertex3f(0, minY, (1024f / 3.0f) * 2.0f); //X: 1/4, Y: 577, Z: 330
//		gl.glEnd();
		

		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex3f(minPosition.x, minPosition.y, minPosition.z); //X: 1/4, Y: 577, Z: 0
		gl.glVertex3f(maxPosition.x, minPosition.y, minPosition.z); //X: 1/4, Y: 577, Z: 0
		gl.glVertex3f(minPosition.x, minPosition.y, maxPosition.z); //X: 1/4, Y: 577, Z: 0
		gl.glVertex3f(maxPosition.x, minPosition.y, maxPosition.z); //X: 1/4, Y: 577, Z: 0
		
//		System.out.println( maxPosition.z + "x"+  minPosition.z);
		
//		gl.glVertex3f(1024, minY, 0f); //X: 3/4, Y: 577, Z: 0
//		gl.glVertex3f(1024, minY, (1024f / 3.0f) * 2.0f); //X: 3/4, Y: 577, Z: 330
//		gl.glVertex3f(0, minY, (1024f / 3.0f) * 2.0f); //X: 1/4, Y: 577, Z: 330
		gl.glEnd();
	}
	
	public void drawIntersectLine(GL gl, int lineNum) {
		//line from Camera to Y points


		Coord coord = lines[lineNum].bottom;

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		
		//Z Points
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(projectorX, projectorY, projectorZ);
		
		gl.glVertex3f(coord.x, 
					coord.y, 
					coord.z);
		gl.glEnd();
	}
	
	public void drawLine(GL gl, PVector color, PVector top, PVector bottom){
		Util.drawLine(gl, color, top, bottom, this);
	}
	
	public void drawLineNoDots(GL gl, PVector color, PVector top, PVector bottom){
		Util.drawLineNoDots(gl, color, top, bottom, this);
	}

	public void drawVertLines(GL gl, int lineNum) {

		Coord coord1 = lines[lineNum].bottom;
		Coord coord2 = lines[lineNum].top;
		
//		Util.drawLine(gl, 
//				new Coord(0,1,0),
//				coord1,
//				coord2,
//				this
//				);
	}


	public void draw3dPointOnY(GL gl, int lineNum) {

		Coord coord = lines[lineNum].bottom;
		
		gl.glColor3f(coord.x/coord.camDiff, 
				coord.y/coord.camDiff, 
				coord.z/coord.camDiff);

		gl.glPointSize(5.0f);
		
		//Z Points
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex3f(coord.x, 
					coord.y, 
					coord.z);
		gl.glEnd();
	}
}
