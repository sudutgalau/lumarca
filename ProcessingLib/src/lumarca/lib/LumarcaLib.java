package lumarca.lib;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import lumarca.lineMap.Line;
import lumarca.lineMap.LineMap;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import processing.core.PApplet;
import processing.opengl.PGraphicsOpenGL;
public class LumarcaLib {
	
	public PApplet pApplet;

	public int LINE = 256; // number of lines in contraption
	
	public LineMap lineMap;

	public static float DEFAULT_CAMERA_X = 0;
	public static float DEFAULT_CAMERA_Y = 0;
	public static float DEFAULT_CAMERA_Z = 0;

	private boolean moveCamera = false;
	public float cameraRot = 0.0f;
	
	private PGraphicsOpenGL pgl;
	public GL gl;
	
	private boolean FULL_SCREEN = false;
	
	public  static boolean LOGGING = false;
	
	private boolean NEW_MAP = false;

	int orginalLineNum;
	private List<Integer> depthList = new ArrayList<Integer>(); 
	private List<Line> newLines = new ArrayList<Line>();

	private List<Boolean> removedDepthPos =  new ArrayList<Boolean>();
	private List<Boolean> removedXPos =  new ArrayList<Boolean>();
	
	private float[] setupLines;

	public LumarcaLib(PApplet pApplet, float[] lines) {
		super();
		
		this.pApplet = pApplet;
		LINE = lines.length;
		
		NEW_MAP = false;
		
		setupLines = lines;
	}
	
	public LumarcaLib(PApplet pApplet, int numLines, boolean newMap) {
		super();
		
		this.pApplet = pApplet;
		LINE = numLines;
		
		NEW_MAP = newMap;
	}
	
	public void init() {
		
//		pApplet.frame.dispose(); 
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
//        GraphicsDevice displayDevice=ge.getDefaultScreenDevice(); 
//        pApplet.frame.setUndecorated(true); 
//
//        pApplet.frame.removeNotify();
//        
//		if (FULL_SCREEN) {
//			
//			displayDevice.setFullScreenWindow(pApplet.frame);
//			Dimension fullscreen = pApplet.frame.getSize();
//		}
//			
//		pApplet.init();
//		
//		pApplet.frame.setLocation(0, 0);//(int)-WIN_HEIGHT/2);

		DEFAULT_CAMERA_X = pApplet.getWidth() / 2.0f;
		DEFAULT_CAMERA_Y = 0;
		DEFAULT_CAMERA_Z = (pApplet.getHeight()/2.0f)/ PApplet.tan(PApplet.PI * 60.0f / 360.0f);
		
//		size((int)WIN_WIDTH, (int)WIN_HEIGHT, OPENGL);
		pApplet.hint(PApplet.DISABLE_OPENGL_2X_SMOOTH); 
		
		lineMap = new LineMap(LINE, DEFAULT_CAMERA_X, DEFAULT_CAMERA_Y, DEFAULT_CAMERA_Z, setupLines, "lineMap" + LINE +".txt", NEW_MAP);
		
		makeDescreeteDepthsAndCutCorners();
	}
	
	private void makeDescreeteDepthsAndCutCorners(){

		//make descreet depths
		makeDepthList();
		discreetDepths();
		
		orginalLineNum = lineMap.lines.length;
		
		cutOutSideBox();
		
		int lineCount = 0;
		
		if(true)
		for(Integer i: depthList){
			int xPos = depthList.get(i.intValue());
			
			if(!removedDepthPos.get(i.intValue()).booleanValue()){
				
				int realPos = 0;
				
				for(int spaces = 0; spaces < i; spaces++){
					if(!removedDepthPos.get(spaces).booleanValue()){
						realPos++;
					}
				}
				
//				realPos = depthList.get(realPos);
				
				System.out.println(lineMap.lines[realPos].bottom.x);
				
				float f = pApplet.getHeight() - (pApplet.getHeight()/2) * 
							(lineMap.mod + lineMap.mod * realPos/orginalLineNum);
				
				lineMap.map[lineCount] = f;
				
				lineMap.calculateLine(lineCount, xPos, f);
				System.out.println(lineMap.lines[lineCount].bottom.x);
				System.out.println("---------");
				lineCount++;
			}
		}
		
		//recalculate positions
		lineMap.maxPosition = new Coord();
		lineMap.minPosition = new Coord(10000, 10000, 10000);
		
		for(Line line: lineMap.lines){
			
			pApplet.println(line.bottom.x + "," + line.bottom.z);
			
			if(line.bottom.x > lineMap.maxPosition.x){
				lineMap.maxPosition.x = line.bottom.x;
			}
			if(line.bottom.x < lineMap.minPosition.x){
				lineMap.minPosition.x = line.bottom.x;
			}
			if(line.top.y > lineMap.maxPosition.y){
				lineMap.maxPosition.y = line.top.y;
			}
			if(line.bottom.y < lineMap.minPosition.y){
				lineMap.minPosition.y = line.bottom.y;
			}
			if(line.bottom.z > lineMap.maxPosition.z){
				lineMap.maxPosition.z = line.bottom.z;
			}
			if(line.bottom.z < lineMap.minPosition.z){
				lineMap.minPosition.z = line.bottom.z;
			}
		}

		lineMap.midPosition = new Coord((lineMap.maxPosition.x + lineMap.minPosition.x)/2,
				(lineMap.maxPosition.y + lineMap.minPosition.y)/2,
				(lineMap.maxPosition.z +lineMap. minPosition.z)/2);
		
	}
	
	private void moveCamera() {
//		if (moveCamera){

			pApplet.camera(DEFAULT_CAMERA_X * PApplet.sin(PApplet.PI/2f + cameraRot),
					DEFAULT_CAMERA_Y + -100 * cameraRot,
					DEFAULT_CAMERA_Z + cameraRot, 
					DEFAULT_CAMERA_X, 
					DEFAULT_CAMERA_Y, 
					0, 
					0, -1, 0);
	}
	
	private void makeDepthList(){
		depthList = new ArrayList<Integer>(); 
		depthList.add(0);
		
		for(int i = 1; i < lineMap.lines.length; i++){
			
			Line current = lineMap.lines[i];
			boolean inserted = false;
			
//			orgXPos.add(i);
			
			for(int c = 0; c < i; c++){
				Line prev = lineMap.lines[depthList.get(c).intValue()];
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
	
	private void discreetDepths(){

		for(int i = 0; i < lineMap.lines.length; i++){
			
			removedDepthPos.add(new Boolean(false));			
			removedXPos.add(new Boolean(false));
			
			int whichString = depthList.get(i).intValue();
			
			
			float f = pApplet.getHeight() - (pApplet.getHeight()/2) * 
						(lineMap.mod + lineMap.mod * i/lineMap.lines.length);
			
			lineMap.map[i] = f;
			
			lineMap.calculateLine(i, whichString, f);
		}
	}
	
	private void cutOutSideBox(){
		newLines = new ArrayList<Line>();

		ArrayList<Integer> cutNum = new ArrayList<Integer>();

		float largestDiff = 0;
		float averageDiff = 0;
		
		for(int i = 0; i < lineMap.lines.length; i++){
			
			int whichString = depthList.get(i).intValue();
			
			//FIXME THIS IS BROKEN!
			if(lineMap.lines[whichString].bottom.x > 227.55548 && lineMap.lines[whichString].bottom.x < 794.2223){

				
				Line line = lineMap.lines[whichString];

				newLines.add(line);
			} else {
				cutNum.add(new Integer(whichString));
				removedXPos.set(new Integer(i), new Boolean(true));
				removedDepthPos.set(whichString, new Boolean(true));
			}
		}
		
		System.out.println("Largest Diff:"  + largestDiff + " average: " + averageDiff/lineMap.lines.length);
		
		lineMap.lines = (Line[])newLines.toArray(new Line[newLines.size()]);
	}
	
	
	public void pre() {
		pApplet.background(0);
		
//		pApplet.noCursor();
		
		moveCamera();
		
		pgl = (PGraphicsOpenGL) pApplet.g;

		gl = pgl.beginGL();
	}
	
	public void draw() {}
	
	public void calibration() {

		
		for (int lineNum = 0; lineNum < lineMap.lines.length; lineNum++) {
			
			//line from Camera to Y points
			lineMap.drawIntersectLine(gl, lineNum);

			//Z Points
			lineMap.draw3dPointOnZ(gl, lineNum);

			//Y Points
			lineMap.draw3dPointOnY(gl, lineNum);
//			
//
			lineMap.drawVertLines(gl, lineNum);
			
		}

		lineMap.drawFloorBox(gl);

	}
	
	public void post() {
		if(pgl != null)
			pgl.endGL();
	}

	public void mouseEvent(MouseEvent e) {
	}


	public void keyEvent(KeyEvent e) {
	}

	public void size(int width, int height) {
		
		pApplet.hint(PApplet.DISABLE_OPENGL_2X_SMOOTH); 
	}
	
	public void stop() {
	}

	public void dispose() {
	}
}
