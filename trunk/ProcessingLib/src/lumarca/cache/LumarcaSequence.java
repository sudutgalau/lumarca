package lumarca.cache;

import java.util.ArrayList;
import java.util.List;

import lumarca.lineMap.Line;
import lumarca.util.LumarcaObject;

public class LumarcaSequence extends LumarcaObject{

//	public static int MODE_LOOP;
//	public static int MODE_PING_PONG;
	
	public List<LumarcaFrame> frames = new ArrayList<LumarcaFrame>();
	public int frameNum;

	public int startFrame;
	public int endFrame;

	public void addFrame(LumarcaFrame frame){
		frames.add(frame);
	}
	
	public void drawFrame(int frameNum){
		LumarcaFrame frame = frames.get(frameNum);
		for(Line line: frame.lines){
			lumarca.drawLine(line.color, line.top, line.bottom);
		}
	}
	
	public void draw(){
		drawFrame(frameNum);
		frameNum++;
		if(frameNum == frames.size()){
			frameNum = 0;
		}
	}
	
}
