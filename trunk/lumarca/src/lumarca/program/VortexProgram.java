package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.util.Coord;
import lumarca.util.Util;

public class VortexProgram extends LineProgram {

	//declare variables
	private boolean firstRun = true;
	
	private float numLines = lumarca.LINE;
	private float lineLength = 25;
	private float segsPerLine = lumarca.floor((lumarca.lineMap.minPosition.y - lumarca.lineMap.maxPosition.y)/ lineLength);
	private float totalSegments = numLines * segsPerLine; // total segements
	private float maxX = lumarca.lineMap.maxPosition.x;
	private float xLength, zLength;
	
	private float segmentColorArray[][][][]; // create nested array that holds the segments color values
	private float colorMax = totalSegments * 2f;
	private float centerX = lumarca.floor(numLines/2);
	private float centerY = lumarca.floor(segsPerLine/2);
	
	
	private float r = 0f;
	private float g = 0f;
	private float b = 0f;
	
	// create a series of arrays to deal with each stringSegment three color values
	// use code from ledTile series
	
	
	public VortexProgram(Lumarca lumarca) {
		super(lumarca);
		// instantiate vars
		lumarca.println("totalSegments = "+totalSegments);
		segmentColorArray = new float[(int) numLines][(int) segsPerLine][3][2];
	}

	@Override
	public void update() {
		// nothing in here
		//lumarca.colorMode(lumarca.HSB);
	}

	@Override
	public void display(GL gl) {
		
		// Diamond diamond = new Diamond(new Coord(500, 200, 200), 200);
		// diamond.rotateOnX(10f);
		// lumarca.lineMap.drawShape(gl, new Coord(1,0,0), diamond);
		
		for (int lineNum = 0; lineNum < numLines; lineNum++) {

			Coord coord = lumarca.lineMap.lines[lineNum].bottom;
			
			for (int segmentNum = 0; segmentNum < segsPerLine; segmentNum++) {

				float segmentLocBottom = segmentNum * lineLength;// lower extreme point of segment
				float segmentLocTop = segmentLocBottom + lineLength;// upper most point of segment
				
				if(firstRun){
					// calculate the xpos increment distance
					xLength = (lumarca.lineMap.maxPosition.x - lumarca.lineMap.minPosition.x) / numLines;
					zLength = (lumarca.lineMap.maxPosition.z - lumarca.lineMap.minPosition.z) / numLines;
					
					//lumarca.println("line "+lineNum+"'s x= "+coord.x+"  z= "+coord.z+"  y= "+segmentLocBottom);
					// r reflects x position
				      segmentColorArray[lineNum][segmentNum][0][0] = (lumarca.abs(coord.x-((numLines/2)*xLength)) + lumarca.abs(segmentLocBottom-((segsPerLine/2)*lineLength)) + lumarca.abs(coord.z-((numLines/2)*zLength)))*10;
				      segmentColorArray[lineNum][segmentNum][0][1] = -100;
				      // g reflects y position
				      segmentColorArray[lineNum][segmentNum][1][0] = (lumarca.abs(coord.x-((numLines/2)*xLength)) + lumarca.abs(segmentLocBottom-((segsPerLine/2)*lineLength)) + lumarca.abs(coord.z-((numLines/2)*zLength)))*10;
				      segmentColorArray[lineNum][segmentNum][1][1] = 90f;
				      // b reflects z position
				      //(lumarca.abs(lineNum-120) + lumarca.abs(segmentNum-20))*10
				      segmentColorArray[lineNum][segmentNum][2][0] = (lumarca.abs(coord.x-((numLines/2)*xLength)) + lumarca.abs(segmentLocBottom-((segsPerLine/2)*lineLength)) + lumarca.abs(coord.z-((numLines/2)*zLength))+500)*10;
				      segmentColorArray[lineNum][segmentNum][2][1] = 105f;
				}
				
				// use the below to animate the position of the vortex
				
//				segmentColorArray[lineNum][segmentNum][0][0] = (lumarca.abs(coord.x-((numLines/2)*xLength)) + lumarca.abs(segmentLocBottom-((segsPerLine/2)*lineLength)) + lumarca.abs(coord.z-((numLines/2)*zLength)))*10;
//				segmentColorArray[lineNum][segmentNum][1][0] = (lumarca.abs(coord.x-((numLines/2)*xLength)) + lumarca.abs(segmentLocBottom-((segsPerLine/2)*lineLength)) + lumarca.abs(coord.z-((numLines/2)*zLength)))*10;
//				segmentColorArray[lineNum][segmentNum][2][0] = (lumarca.abs(coord.x-((numLines/2)*xLength)) + lumarca.abs(segmentLocBottom-((segsPerLine/2)*lineLength)) + lumarca.abs(coord.z-((numLines/2)*zLength)))*10;
				
				
				
				if(segmentColorArray[lineNum][segmentNum][0][0] > 0 && segmentColorArray[lineNum][segmentNum][0][0] < colorMax){
			        segmentColorArray[lineNum][segmentNum][0][0]+= segmentColorArray[lineNum][segmentNum][0][1];
			      } 
			      else {
			        segmentColorArray[lineNum][segmentNum][0][1] *= -1; 
			        segmentColorArray[lineNum][segmentNum][0][0]+= segmentColorArray[lineNum][segmentNum][0][1]; 
			      }

			      if(segmentColorArray[lineNum][segmentNum][1][0] > 0 && segmentColorArray[lineNum][segmentNum][1][0] < colorMax){
			        segmentColorArray[lineNum][segmentNum][1][0]+= segmentColorArray[lineNum][segmentNum][1][1];
			      } 
			      else {
			        segmentColorArray[lineNum][segmentNum][1][1] *= -1; 
			        segmentColorArray[lineNum][segmentNum][1][0]+= segmentColorArray[lineNum][segmentNum][1][1];
			      }

			      if(segmentColorArray[lineNum][segmentNum][2][0] > 0 && segmentColorArray[lineNum][segmentNum][2][0] < colorMax){
			        segmentColorArray[lineNum][segmentNum][2][0]+= segmentColorArray[lineNum][segmentNum][2][1];
			      } 
			      else {
			        segmentColorArray[lineNum][segmentNum][2][1] *= -1; 
			        segmentColorArray[lineNum][segmentNum][2][0]+= segmentColorArray[lineNum][segmentNum][2][1];
			      }
				
				r = lumarca.norm(segmentColorArray[lineNum][segmentNum][0][0], 0, colorMax);
				g = lumarca.norm(segmentColorArray[lineNum][segmentNum][1][0], 0, colorMax);
				b = lumarca.norm(segmentColorArray[lineNum][segmentNum][2][0], 0, colorMax);
				
				Util.drawLineNoDots(gl, 
						new Coord(r,g,b),
									
									new Coord(coord.x,
											  coord.y - segmentLocBottom,
											  coord.z),
											  
									new Coord(coord.x,
											  coord.y - segmentLocTop,
											  coord.z));
			}
		}
		firstRun = false;
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
