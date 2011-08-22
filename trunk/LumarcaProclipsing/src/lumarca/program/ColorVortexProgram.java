package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.obj.Diamond;
import lumarca.util.Coord;
import lumarca.util.Util;

public class ColorVortexProgram extends LineProgram {

	//declare variables
	private boolean firstRun = true;
	private boolean doOnce = true;
	
	private float numLines = lumarca.LINE;
	private float lineLength = 10;
	private float segsPerLine = lumarca.floor((lumarca.lineMap.maxPosition.y)/ lineLength);
	private float totalSegments = numLines * segsPerLine; // total segements
	private float maxX = lumarca.lineMap.maxPosition.x;
	private float xLength, zLength;
	private float area[][];// stores the max and min position for the lines
	
	private float segmentColorArray[][][][]; // create nested array that holds the segments color values [line][segment][r,g,b][total value, vortex loc value, animation value, animation incr]
	private float colorMax = totalSegments - (totalSegments/2.3f);//private float colorMax = totalSegments * 2f;
	private float colorStep = colorMax/1000f;// one percent value of color max, will allow for precise color sequencing.
	private float vortexLocArray[][][] ;//[r,g,b][x,y,z][location,increment value]
	//private float centerX, centerY, centerZ;
	//private float centerXIncr = 0.3f;
	//private float centerYIncr = 0.2f;
	//private float centerZIncr = 0.1f;
	
	
	private float r = 0f;
	private float g = 0f;
	private float b = 0f;
	
	// create a series of arrays to deal with each stringSegment three color values
	// use code from ledTile series
	
	
	public void start(){
		doOnce = true;
		firstRun = true;
	}
	
	public ColorVortexProgram(Lumarca lumarca) {
		super(lumarca);
		lumarca.frameRate(60);
		// instantiate vars
		lumarca.println("totalSegments = "+totalSegments);
		segmentColorArray = new float[(int) numLines][(int) segsPerLine][3][4];
		area = new float[3][2];
		vortexLocArray = new float[3][3][2];//[r,g,b][x,y,z][loc,incr]
		
	}

	@Override
	public void update() {
		// nothing in here
		//lumarca.colorMode(lumarca.HSB);
	}

	@Override
	public void display(GL gl) {
		 Diamond diamond = new Diamond(new Coord(500, 200, 200), 200);
		 diamond.rotateOnX(10f);
		 diamond.drawWireFrame(gl);
		 lumarca.lineMap.drawShape(gl, new Coord(1,0,0), diamond);
		
		
		for (int lineNum = 0; lineNum < numLines; lineNum++) {

			Coord coord = lumarca.lineMap.lines[lineNum].bottom;
			if(doOnce){
				// set the min and max postions into the area array
				area[0][0] = lumarca.lineMap.minPosition.x;
				area[0][1] = lumarca.lineMap.maxPosition.x;
				
				area[1][0] = lumarca.lineMap.minPosition.y;
				area[1][1] = lumarca.lineMap.maxPosition.y;
				
				area[2][0] = lumarca.lineMap.minPosition.z;
				area[2][1] = lumarca.lineMap.maxPosition.z;
				doOnce = false;
			}
			
			// move vortex center
			//centerX -= 10;
			
			
			for (int segmentNum = 0; segmentNum < segsPerLine; segmentNum++) {
				
				float segmentLocBottom = segmentNum * lineLength;// lower extreme point of segment
				float segmentLocTop = segmentLocBottom + lineLength;// upper most point of segment
				
				if(firstRun){
					// calculate the xpos increment distance
					//lumarca.println("x room to move= "+(lumarca.lineMap.maxPosition.x - lumarca.lineMap.minPosition.x));
					xLength = (lumarca.lineMap.maxPosition.x - lumarca.lineMap.minPosition.x) / numLines;
					zLength = (lumarca.lineMap.maxPosition.z - lumarca.lineMap.minPosition.z) / numLines;
					
					vortexLocArray[0][0][0] = vortexLocArray[1][0][0] = vortexLocArray[2][0][0] = (numLines/2)*xLength;
					vortexLocArray[0][1][0] = vortexLocArray[1][1][0] = vortexLocArray[2][1][0] = (segsPerLine/2)*lineLength;
					vortexLocArray[0][2][0] = vortexLocArray[1][2][0] = vortexLocArray[2][2][0] = (numLines/2)*zLength;
					
					for(int color = 0; color < 3; color++){
						for(int axis = 0; axis < 3; axis++){
							vortexLocArray[color][axis][1] = lumarca.random(-5f, 5f);
							//vortexLocArray[color][axis][1] = 0;
						}
					}
					vortexLocArray[0][1][1] = vortexLocArray[1][1][1] = vortexLocArray[2][1][1] = 2f;
					vortexLocArray[0][2][1] = vortexLocArray[1][2][1] = vortexLocArray[2][2][1] = 3f;
					
					//lumarca.println("line "+lineNum+"'s x= "+coord.x+"  z= "+coord.z+"  y= "+segmentLocBottom);
					// r reflects x position
				      segmentColorArray[lineNum][segmentNum][0][1] = (lumarca.abs(coord.x-(vortexLocArray[0][0][0])) + lumarca.abs(segmentLocBottom-(vortexLocArray[0][1][0])) + lumarca.abs(coord.z-(vortexLocArray[0][2][0])))*1;
				      segmentColorArray[lineNum][segmentNum][0][2] = segmentLocBottom%600f - 400.0f;
				      segmentColorArray[lineNum][segmentNum][0][3] = 0.0f * colorStep;
				      // g reflects y position
				      segmentColorArray[lineNum][segmentNum][1][1] = (lumarca.abs(coord.x-(vortexLocArray[1][0][0])) + lumarca.abs(segmentLocBottom-(vortexLocArray[1][1][0])) + lumarca.abs(coord.z-(vortexLocArray[1][2][0])))*1;
				      segmentColorArray[lineNum][segmentNum][1][2] = segmentLocBottom%600f - 400.0f;
				      segmentColorArray[lineNum][segmentNum][1][3] = 0.0f * colorStep;
				      // b reflects z position*
				      //(lumarca.abs(lineNum-120) + lumarca.abs(segmentNum-20))*10
				      segmentColorArray[lineNum][segmentNum][2][1] = (lumarca.abs(coord.x-(vortexLocArray[2][0][0])) + lumarca.abs(segmentLocBottom-(vortexLocArray[2][1][0])) + lumarca.abs(coord.z-(vortexLocArray[2][2][0])))*1;
				      segmentColorArray[lineNum][segmentNum][2][2] = segmentLocBottom%600f - 400.0f;
				      segmentColorArray[lineNum][segmentNum][2][3] = 0.0f * colorStep;
				}
				
				// use the below to animate the position of the vortex
				
				segmentColorArray[lineNum][segmentNum][0][1] = (lumarca.abs(coord.x-(vortexLocArray[0][0][0])) + lumarca.abs(segmentLocBottom-(vortexLocArray[0][1][0])) + lumarca.abs(coord.z-(vortexLocArray[0][2][0])))*10;
				segmentColorArray[lineNum][segmentNum][1][1] = (lumarca.abs(coord.x-(vortexLocArray[1][0][0])) + lumarca.abs(segmentLocBottom-(vortexLocArray[1][1][0])) + lumarca.abs(coord.z-(vortexLocArray[1][2][0])))*10;
				segmentColorArray[lineNum][segmentNum][2][1] = (lumarca.abs(coord.x-(vortexLocArray[2][0][0])) + lumarca.abs(segmentLocBottom-(vortexLocArray[2][1][0])) + lumarca.abs(coord.z-(vortexLocArray[2][2][0])))*10;
				
				
				
				for(int color = 0; color < 3; color++){
					
					if(segmentColorArray[lineNum][segmentNum][color][2] > 0 && segmentColorArray[lineNum][segmentNum][color][2] < colorMax/2){
				        segmentColorArray[lineNum][segmentNum][color][2]+= segmentColorArray[lineNum][segmentNum][color][3];
				      } 
				      else {
				        segmentColorArray[lineNum][segmentNum][color][3] *= -1; 
				        segmentColorArray[lineNum][segmentNum][color][2]+= segmentColorArray[lineNum][segmentNum][color][3];
				      }
					//lumarca.println(segmentColorArray[lineNum][segmentNum][color][2]);
					// add the values up
					segmentColorArray[lineNum][segmentNum][color][0] = segmentColorArray[lineNum][segmentNum][color][1]+segmentColorArray[lineNum][segmentNum][color][2];
				}//+segmentColorArray[lineNum][segmentNum][color][2]
				
			      
			    // normalize all of the color values
				r = lumarca.norm(segmentColorArray[lineNum][segmentNum][0][0], colorMax, 0);
				g = lumarca.norm(segmentColorArray[lineNum][segmentNum][1][0], colorMax, 0);
				b = lumarca.norm(segmentColorArray[lineNum][segmentNum][2][0], colorMax, 0);
				
				Util.drawLineNoDots(gl, 
						new Coord(r, g, b),
						
						new Coord(coord.x,
								  coord.y - segmentLocBottom,
								  coord.z),
								  
						new Coord(coord.x,
								  coord.y - segmentLocTop,
								  coord.z));
			}
		}
		firstRun = false;
		
		/// move the vortex center
		for(int color = 0; color < 3; color++){
			for(int axis = 0; axis < 3; axis++){
				
				if(vortexLocArray[color][axis][0] > area[axis][0] && vortexLocArray[color][axis][0] < area[axis][1]){
					vortexLocArray[color][axis][0] += vortexLocArray[color][axis][1];
				} 
				else {
					vortexLocArray[color][axis][1] *= -1; 
					vortexLocArray[color][axis][0] += vortexLocArray[color][axis][1];
				}
			}
		}
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
