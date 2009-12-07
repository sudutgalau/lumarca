package lumarca.program.addons;

/**
 * This file is part of the Snake program for the Lumarca project.
 * By Elie Zananiri, 2009 - http://www.silentlycrashing.net/
 */

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.program.LineProgram;
import lumarca.util.Coord;
import lumarca.util.Util;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

public class FFTVis extends LineProgram{
    
    //public static final int MAX_SECTIONS = 20;

/*    private Section[] sections;
    private int numSections;
*/
	public static OSCPortIn receiver;
	public float[] fftData = new float[16];
    public static Thread thread;
	
    public FFTVis(Lumarca lumarca) {
    	super(lumarca);

//		if (thread == null) {
//			try {
//				receiver = new OSCPortIn(9001);
//
//				thread = new Thread() {
//
//					public void run() {
//						try {
//							OSCListener listener = new OSCListener() {
//								public void acceptMessage(java.util.Date time,
//										OSCMessage message) {
//									Object[] args = message.getArguments();
//									for (int i = 0; i < 16; i++) {
//										fftData[i] = ((Float) args[i])
//												.floatValue();
//									}
//								}
//							};
//
//							receiver.addListener("/fft", listener);
//							receiver.startListening();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				};
//
//				thread.run();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} 
////		else {
////			receiver.startListening();
////		}
	} 
    
	public void update(){

	
	}
	public void display(GL gl){
    	
    	for(int lineNum = 0; lineNum < lumarca.lineMap.lines.length; lineNum++){
    		Line line = lumarca.lineMap.lines[lineNum];
    		
    		float percent = lineNum/256f;
    		
    		int position = lumarca.floor(percent * fftData.length);
    		
    		// for x in 0,1024
    		//  
    		
    		float val = fftData[position];
    		
    		Coord modCord = line.top.clone();
    		modCord.y = 5000 * val;
    		
//    		System.out.println(modCord.y + " = " + val + " : " + percent * 16 + " : " + percent);
    		
    		Util.drawLine(gl, new Coord(1,0,0), 
    				line.top, modCord);
    		
    		// fftData[i] * ymax
    	}
		
		
	}
	public void keyPressed(){
		
	}
	public void mousePressed(){
		
		
	}
	
	public void exit(){
		if(thread != null){
			thread.stop(new Throwable());
			thread = null;
//			receiver
		}
//		
		if(receiver != null){
			receiver.stopListening();
			receiver = null;
		}
//		if(receiver != null){
//			receiver.stopListening();
//		}
	}
}
