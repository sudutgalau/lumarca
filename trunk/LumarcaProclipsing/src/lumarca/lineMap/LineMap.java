package lumarca.lineMap;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Shape;
import lumarca.obj.TrianglePlane;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import lumarca.util.Util;
import processing.core.PApplet;

public class LineMap extends ProcessingObject{
	
	private int lineNum; // number of lines in contraption


	public final static int DOT_HEIGHT = 5; // height of dots in pxs

	private float[] map;  // distance from mapline to point of intersection along hypot

	public float projectorX;
	public float projectorY;
	public float projectorZ;

	public Coord maxPosition = new Coord(-1000,-1000,-1000);
	public Coord minPosition = new Coord(20000,20000,20000);
	public Coord midPosition;
	
	float maxY = 0;
	float minY = 1000;

	float maxZ = 0;
	float minZ = 1000;
	

	public Line[] lines;
//	Coord[] zCoords = new Coord[255];
	
	public LineMap(int lineNum, float projectorX, float projectorY, float projectorZ, String fileName) {
		super();
		this.lineNum = lineNum;
		
		if(!Lumarca.DIY){
			lineNum *= 3;
		}
		
		this.lines = new Line[lineNum];
		this.map  = new float[lineNum];

		this.projectorX = projectorX;
		this.projectorY = projectorY;
		this.projectorZ = projectorZ;
		
		
		if(!loadFile(fileName)){
			makeMap(fileName);
		}

//			minY = Lumarca.WIN_HEIGHT/5.5f;
//			maxY = Lumarca.WIN_HEIGHT/6.0f;

		for (int i = 0; i < lineNum; i++) {

			if (Lumarca.WIN_HEIGHT - map[i] > maxY) {
				maxY = Lumarca.WIN_HEIGHT - map[i];
			}

			if (Lumarca.WIN_HEIGHT - map[i] < minY) {
				minY = Lumarca.WIN_HEIGHT - map[i];
			}
		}
		
		
		

//		minY = 10;//-Lumarca.WIN_HEIGHT;
//		maxY = Lumarca.WIN_HEIGHT/2.0f;
		
		System.out.println("---MIN: " + minY);
		System.out.println("---MAX: " + maxY);
		
		for (int lineCount = 0; lineCount < lineNum; lineCount++) {
			
			
			float camDiff = PApplet.dist(
					projectorX, 
					projectorY,
					projectorZ, 
					lineCount * (Lumarca.WIN_WIDTH/lineNum), 
					minY,
					Lumarca.WIN_HEIGHT);

//			System.out.println("camDiff: " + camDiff);
			
			float yIntersect = 
				(projectorY - minY) / 
				((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff);
			

//			float yIntersect = PApplet.sqrt(
//				PApplet.pow(projectorY - ((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff), 2) +
//				PApplet.pow(projectorY - ((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff), 2));
				
			
//			float f = (projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum)) / camDiff) * yIntersect;
			
//			coords[lineNum] = new Coord(
//					(projector_x - (lineNum * 4 + 0)) / camDiff,
//					(projector_y - Lumarca.WIN_HEIGHT + map[lineNum]) / camDiff,
//					(projector_z - 0f) / camDiff, yIntersect, camDiff);

			
			lines[lineCount] = new Line(
					new Coord(
							projectorX - ((projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum))) / camDiff) * yIntersect,
							minY,
							projectorZ - (projectorZ / camDiff) * yIntersect, 
							yIntersect, camDiff), 
					new Coord(
							projectorX - ((projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum))) / camDiff) * yIntersect,
							minY - Lumarca.WIN_HEIGHT/3,
							projectorZ - (projectorZ / camDiff) * yIntersect));
			
		}
		
		
		for(Line line: lines){
			
			pApplet.println(line.bottom.x + "," + line.bottom.z);
			
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

		pApplet.println("PRO" + projectorX + "x" + projectorY + "x" + projectorZ);
		pApplet.println("MAX" + maxPosition.x + "x" + maxPosition.y + "x" + maxPosition.z);
		pApplet.println("MIN" + minPosition.x + "x" + minPosition.y + "x" + minPosition.z);
	}

	public void drawShape(GL gl, Coord color, Shape shape){
		for(Line line: lines){
			shape.drawIntersect(gl, color, line);
		}
	}

	public List<Line> getShape(Coord color, Shape shape){
		List<Line> interLines = new ArrayList<Line>();
		
		for(Line line: lines){
			interLines.addAll(shape.getIntersect(color, line));
		}
		
		return interLines;
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
	
	private boolean loadFile(String fileName){

		String lines[] = pApplet.loadStrings(fileName);
		
		if(lines == null)
			return false;
		
		if (Lumarca.LOGGING) {
			pApplet.println("there are " + lines.length + " lines");
			for (int i = 0; i < lines.length; i++) {
				pApplet.println(lines[i]);
			}
		}

		int i = 0;
		for (String line: lines) {
			map[i] = Float.parseFloat(line);
			i++;
		}

		makelines();
		
		return true;
		
	}

	private void makeline(int lineCount){

		float camDiff = PApplet.dist(
				projectorX, 
				projectorY,
				projectorZ, 
				lineCount * (Lumarca.WIN_WIDTH/lineNum), 
				minY,
				Lumarca.WIN_HEIGHT);
		
		float yIntersect = 
			(projectorY - minY) / 
			((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff);

		
		lines[lineCount] = new Line(
				new Coord(
						projectorX - ((projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum))) / camDiff) * yIntersect,
						minY,
						projectorZ - (projectorZ / camDiff) * yIntersect, 
						yIntersect, camDiff), 
				new Coord(
						projectorX - ((projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum))) / camDiff) * yIntersect,
						minY - 900,
						projectorZ - (projectorZ / camDiff) * yIntersect));
	}
	
	private void makelines(){
		
		for (int lineCount = 0; lineCount < lineNum; lineCount++) {
			
			makeline(lineCount);
		}
	}
	
	private final static float tolerance = 68f;
	
	private boolean tooClose(int line1, int line2){
		Coord coord1 = lines[line1].bottom;
		Coord coord2 = lines[line2].bottom;
		
		if(PApplet.dist(coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z) < tolerance){
			System.out.println(PApplet.dist(coord1.x, coord1.y, coord1.z, coord2.x, coord2.y, coord2.z) + " : " + line2);

			return true;
		}
		
		return false;
	}
	
	private boolean validline(int lineNum){
		boolean result = true;
		
		makeline(lineNum);
		
		for(int i = 0 ; i < lineNum; i++){
			if(tooClose(i, lineNum)){
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	private void makeMap(String fileName) {
		
		Util.log("NEW FILE!");
		
		for (int i = 0; i < map.length; i++) {
			map[i] = Lumarca.WIN_HEIGHT - pApplet.random((Lumarca.WIN_HEIGHT/2) * 2/3, 
					Lumarca.WIN_HEIGHT/2);
				

			System.out.println("map[i]: " + map[i]);
			
			while(!validline(i)){
				
				map[i] = Lumarca.WIN_HEIGHT - pApplet.random((Lumarca.WIN_HEIGHT/2) * 2/3, 
						Lumarca.WIN_HEIGHT/2);
			}
			
		}
		
		PrintWriter output = pApplet.createWriter(fileName);
		

		for (int i = 0; i < map.length; i++) {
			output.println(map[i]);
		}

		output.flush(); // Write the remaining data
		output.close(); // Finish the file

	}
	
	private void makeMap2(String fileName) {

		int divNum = 3;
		int lineDepths = 128 * (divNum - 1)/divNum;
		
		float[] mins = new float[divNum];
		
		for(int i = 0; i < divNum; i++){
			mins[i] = 0.0f + (lineDepths/divNum) * (i%divNum);
		}
		
		List<Integer> temp = new ArrayList<Integer>();
		
		for(int i = 0; i < divNum; i++){
			for(int c = 0; c < divNum + (i * i); c++){
				temp.add(new Integer(i));
			}
		}
		
		Integer[] weight = temp.toArray(new Integer[]{});

		float prevMin1 =  mins[0];
		float prevMin2 =  mins[mins.length - 1];
		
		for (int i = 0; i < map.length; i++) {
			int minIndex = (int)pApplet.random(0f, ((float)(weight.length - 1)));
			float min = mins[weight[minIndex]];
			
			while((min == prevMin1) || (min == prevMin2)){
				minIndex = (int)pApplet.random(0f, ((float)(weight.length - 1)));
				min = mins[weight[minIndex]];
			}

			prevMin2 = prevMin1;
			prevMin1 = min;
			
			float max = min + (lineDepths/divNum) - 1.0f;
			
			System.out.println("min: " + min + " max: " + max);
			
//			map[i] = Util.distribute(4, min, max);
			map[i] = PApplet.map(Util.distribute(5, min, max) + 256, 0, lineDepths -1, 0, 255);
				

//			System.out.println(map[i]);
			
			while(!validline(i)){
				map[i] = PApplet.map(Util.distribute(5, min, max) + 256, 0, lineDepths -1, 0, 255);
			}
			
		}

		
//		for (int i = 0; i < map.length; i++) {
//
//			map[i] = PApplet.map(Util.distribute(4, min, max) + 256, 0, lineDepths -1, 0, 255);
//			while (!validline(i)) {
//				map[i] = PApplet.map(Util.distribute(4, min, max) + 256, 0, lineDepths -1, 0, 255);
//			}
//		}
		
		PrintWriter output = pApplet.createWriter(fileName);
		

		for (int i = 0; i < map.length; i++) {
			output.println(map[i]);
		}

		output.flush(); // Write the remaining data
		output.close(); // Finish the file

	}
	


	public void drawCalebrate() {

		for (int i = 0; i < lineNum; i++) {

			if (map[i] > 63 && map[i] < 126) {
				pApplet.fill(0, 255, 0);
				pApplet.stroke(0, 255, 0);
			} else if (map[i] > 126) {
				pApplet.fill(255, 0, 0);
				pApplet.stroke(255, 0, 0);
			} else {
				pApplet.fill(0, 0, 255);
				pApplet.stroke(0, 0, 255);
			}

			pApplet.strokeWeight(2);

			// config lines
			pApplet.line(i * 4 + 0, 0, i * 4 + 0, Lumarca.WIN_HEIGHT);

			pApplet.noStroke();

			// albert's points
			pApplet.ellipse(i * 4 + 0, Lumarca.WIN_HEIGHT - map[i], 8, 8);

		}

		pApplet.strokeWeight(1);

		pApplet.stroke(255, 255, 255);

		pApplet.line(0, minY, Lumarca.WIN_WIDTH, minY);
		pApplet.line(0, maxY, Lumarca.WIN_WIDTH, maxY);
	}
	


	public void drawFloorBox(GL gl) {
		gl.glColor3f(1.0f, 1.0f, 1.0f);

		//floor box
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex3f(0, minY, 0f); //X: 1/4, Y: 577, Z: 0
		gl.glVertex3f(1024, minY, 0f); //X: 3/4, Y: 577, Z: 0
		gl.glVertex3f(1024, minY, (1024f / 3.0f) * 2.0f); //X: 3/4, Y: 577, Z: 330
		gl.glVertex3f(0, minY, (1024f / 3.0f) * 2.0f); //X: 1/4, Y: 577, Z: 330
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

	public void draw3dPointOnZ(GL gl, int lineNum) {

		Coord coord = lines[lineNum].bottom;
		
		
		//	    Y Points
//		gl.glColor3f(0.0f, 0.0f, 1.0f);
//		gl.glPointSize(15.0f);
		
//		gl.glBegin(GL.GL_POINTS);
//		gl.glVertex3f(
//				
//				 + ((coord.x - projectorX)/coord.yIntersect) * coord.camDiff + projectorX,
//				 + ((coord.y - projectorY)/coord.yIntersect) * coord.camDiff + projectorY,
//				 + ((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
//		gl.glEnd();
		

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(
				 + ((coord.x - projectorX)/coord.yIntersect) * coord.camDiff + projectorX - 8000f,
				 + ((coord.y - projectorY)/coord.yIntersect) * coord.camDiff + projectorY,
				 + ((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
		gl.glVertex3f(
				 + ((coord.x - projectorX)/coord.yIntersect) * coord.camDiff + projectorX + 8000f,
				 + ((coord.y - projectorY)/coord.yIntersect) * coord.camDiff + projectorY,
				 + ((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
		gl.glEnd();
		
		
		gl.glLineWidth(1f);
		
		//Comment in/out for X
		gl.glBegin(GL.GL_LINE_STRIP);
		
		gl.glVertex3f(1000f, minY, 0f);
		gl.glVertex3f(Lumarca.WIN_WIDTH - 10f, minY, 
				((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
		
		gl.glVertex3f(10f, maxY, 0f);
		gl.glVertex3f(Lumarca.WIN_WIDTH - 10f, maxY,  
				((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
		gl.glVertex3f(10f, minY, 0f);
		gl.glVertex3f(Lumarca.WIN_WIDTH - 10f, minY, 
				((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);

		gl.glVertex3f(10f, minY, 0f);
		gl.glVertex3f(10f, maxY, 0f);
		
		gl.glVertex3f(Lumarca.WIN_WIDTH - 10f, minY, 
				((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
		gl.glVertex3f(Lumarca.WIN_WIDTH - 10f, maxY,  
				((coord.z - projectorZ)/coord.yIntersect) * coord.camDiff + projectorZ);
		
		gl.glEnd();
		

//		System.out.println("------------");
//		System.out.println(coord.x/coord.camDiff * 1);
//		System.out.println(coord.y/coord.camDiff * 1);
//		System.out.println(coord.z/coord.camDiff * 1);
//		System.out.println((coord.z/coord.camDiff)*coord.yIntersect);
	}

	public void drawVertLines(GL gl, int lineNum) {

		Coord coord = lines[lineNum].bottom;

		
		Util.drawLine(gl, 
				new Coord(0,1,0),
				new Coord(
						coord.x,
						coord.y - Lumarca.WIN_HEIGHT/3,
						coord.z),
				new Coord(
						coord.x,
						coord.y,
						coord.z)
				);
		
//		gl.glColor3f(coord.x/coord.yIntersect, 
//				coord.y/coord.yIntersect, 
//				coord.z/coord.yIntersect);
		

//
//		
//		gl.glColor3f(coord.x/coord.yIntersect, 
//				coord.y/coord.yIntersect, 
//				coord.z/coord.yIntersect);
//		
//		gl.glBegin(GL.GL_LINE_STRIP);
//		gl.glVertex3f(coord.x,
//					  coord.y +
//						100 - PApplet.sin(projectorX - coord.x / 100 + cameraRot
//								* 10) * 100 - 200,
//
//					   coord.z);
//		
//		
//		gl.glVertex3f(coord.x,
//					coord.y
//				+
//				PApplet.sin(projectorZ - coord.z/100 + cameraRot * 10)*100 +
//				PApplet.sin(projectorX - coord.x / 100 + cameraRot
//						* 10) * 100 - 200,
//
//						coord.z);
//		gl.glEnd();
		
		
		//	    
		//
		//	    
		////	    //Vertical other linesZ lines
		//
		//	    gl.glColor3f(0.1f,0.1f,0.1f); 
		//	    gl.glBegin(GL.GL_LINE_STRIP);
		//	    gl.glVertex3f(
		//	    		DEFAULT_CAMERA_X - coord.x * yInterect,
		//	    		
		//	    		projectorY - coord.y * yInterect + 
		//	    		//sin(projectorZ - coord.z * yInterect/50 + cameraRot * 10)*100 +
		//	    		sin(DEFAULT_CAMERA_X - coord.x * yInterect/100 + cameraRot * 10)*100 - 200,
		//	    		
		//	    		projectorZ - coord.z * yInterect); 
		//	    gl.glVertex3f(
		//	    		DEFAULT_CAMERA_X - coord.x * yInterect,
		//	    		
		//	    		projectorY - coord.y * yInterect - 300,
		//	    		
		//	    		projectorZ - coord.z * yInterect); 
		//	    gl.glEnd();
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

	public void drawFace(GL gl, List<Coord> coords, List<TrianglePlane> tris) {

		gl.glColor3f(0.0f, 0.0f, 1.1f);
		gl.glPointSize(5.0f);

		
		
//		gl.glBegin(GL.GL_TRIANGLE_STRIP);
//		for (int x = 1; x < objCoords.length - 1; x++) {
//			for (int y = 1; y < objCoords.length - 1; y++) {
//
//				Coord coord1 = objCoords[x][y];
//				Coord coord2 = objCoords[x - 1][y];
//				Coord coord3 = objCoords[x][y - 1];
//				Coord coord4 = objCoords[x - 1][y - 1];
//				
//				gl.glVertex3f(coord1.x, coord1.y, coord1.z);
//				gl.glVertex3f(coord2.x, coord2.y, coord2.z);
//				gl.glVertex3f(coord3.x, coord3.y, coord3.z);
//				gl.glVertex3f(coord4.x, coord4.y, coord4.z);
//			}
//		}
//		gl.glEnd();		
		
		gl.glColor3f(0f, 1.0f, 0f);

//		println("------------------------------");
		
		Coord vec = new Coord(0, -1, 0);
		
		for(Coord coord: coords){

			Coord zCoord = coord.getYIntersect();
			
			List<Coord> intersects = new ArrayList<Coord>();
			
			for (TrianglePlane tri : tris) {
				
				Coord pt = Util.checkIntersectTri(tri, zCoord, vec);
				
//				gl.glColor3f(1.0f, 1.0f, 0f);
//				gl.glBegin(GL.GL_TRIANGLE_STRIP);
//				gl.glVertex3f(tri.one.x, tri.one.y, tri.one.z);
//				gl.glVertex3f(tri.two.x, tri.two.y, tri.two.z);
//				gl.glVertex3f(tri.three.x, tri.three.y, tri.three.z);
//				gl.glEnd();

//				gl.glBegin(GL.GL_LINE_STRIP);
//				gl.glVertex3f(zCoord.x, zCoord.y, zCoord.z);
//				gl.glVertex3f(zCoord.x + vec.x * -100, 
//						zCoord.y + vec.y * -100, 
//						zCoord.z + vec.z * -100);
//				gl.glEnd();

				gl.glColor3f(0.0f, 0.0f, 1.0f);
				if (pt != null) {
					intersects.add(pt);
					
//					System.out.println(pt);
//					
//					gl.glBegin(GL.GL_LINE_STRIP);
//					gl.glVertex3f(zCoord.x, zCoord.y, zCoord.z);
//					gl.glVertex3f(pt.x, pt.y, pt.z);
//					gl.glEnd();
				}
			}
			
			if(intersects.size() > 0){
				if(intersects.size() == 1){
					gl.glBegin(GL.GL_LINE_STRIP);
					gl.glVertex3f(zCoord.x, zCoord.y - 500, zCoord.z);
					gl.glVertex3f(intersects.get(0).x,
							intersects.get(0).y, 
							intersects.get(0).z);
					gl.glEnd();
				} else {
					gl.glBegin(GL.GL_LINE_STRIP);
					gl.glVertex3f(intersects.get(1).x,
							intersects.get(1).y, 
							intersects.get(1).z);
					gl.glVertex3f(intersects.get(0).x,
							intersects.get(0).y, 
							intersects.get(0).z);
					gl.glEnd();
				}
			}
			
		}
		
//		for(Coord coord: lines){
//			
//			float x = (DEFAULT_CAMERA_X - coord.x * coord.yIntersect)/20;
//			float y = projectorY - coord.y * coord.yIntersect;
//			float z = (projectorZ - coord.z * coord.yIntersect)/20;
//
//			gl.glBegin(GL.GL_POINTS);
//			gl.glVertex3f(DEFAULT_CAMERA_X - coord.x * coord.yIntersect,
//					projectorY - coord.y * coord.yIntersect, 
//					projectorZ - coord.z * coord.yIntersect);
//			gl.glEnd();
//			
////			int locate_x = 
//			
//			if((x < newDepthGrid.size()) && (z + 10< newDepthGrid.size())){
//				y  = newDepthGrid.get((int)x).get((int)z + 10).floatValue() * -600.0f + 800;
////				println(coord);
////			println(x + ":" + z);
//			
//			if(y > projectorY - coord.y * coord.yIntersect){
//				y = projectorY - coord.y * coord.yIntersect;
//			}
//			
//			gl.glBegin(GL.GL_LINE_STRIP);
//			gl.glVertex3f(x* 20, y, projectorZ - coord.z * coord.yIntersect);
//			gl.glVertex3f(DEFAULT_CAMERA_X - coord.x * coord.yIntersect,
//					projectorY - coord.y * coord.yIntersect, 
//					projectorZ - coord.z * coord.yIntersect);
//			gl.glEnd();
//			}
//			//
////			println((((coord.x))));
////			println((((int)(coord.x))));
////			println(z);
//		}
	}
	
	public Line makeNewLine(int lineN, int yMod){
		
		Line oldLine = lines[lineN];
		
		map[lineN] += yMod;
		
		for (int lineCount = 0; lineCount < lineNum; lineCount++) {
			
			
			float camDiff = PApplet.dist(
					projectorX, 
					projectorY,
					projectorZ, 
					lineCount * (Lumarca.WIN_WIDTH/lineNum), 
					minY,
					Lumarca.WIN_HEIGHT);

//			System.out.println("camDiff: " + camDiff);
			
			float yIntersect = 
				(projectorY - minY) / 
				((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff);
			

//			float yIntersect = PApplet.sqrt(
//				PApplet.pow(projectorY - ((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff), 2) +
//				PApplet.pow(projectorY - ((projectorY - Lumarca.WIN_HEIGHT + map[lineCount]) / camDiff), 2));
				
			
//			float f = (projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum)) / camDiff) * yIntersect;
			
//			coords[lineNum] = new Coord(
//					(projector_x - (lineNum * 4 + 0)) / camDiff,
//					(projector_y - Lumarca.WIN_HEIGHT + map[lineNum]) / camDiff,
//					(projector_z - 0f) / camDiff, yIntersect, camDiff);

			
			lines[lineCount] = new Line(
					new Coord(
							projectorX - ((projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum))) / camDiff) * yIntersect,
							minY,
							projectorZ - (projectorZ / camDiff) * yIntersect, 
							yIntersect, camDiff), 
					new Coord(
							projectorX - ((projectorX - (lineCount * (Lumarca.WIN_WIDTH/lineNum))) / camDiff) * yIntersect,
							minY - Lumarca.WIN_HEIGHT/3,
							projectorZ - (projectorZ / camDiff) * yIntersect));
			
		}
		
		return lines[lineN];
	}
}
