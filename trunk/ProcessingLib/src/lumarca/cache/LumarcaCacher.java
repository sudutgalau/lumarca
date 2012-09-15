package lumarca.cache;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;
import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.ProcessingObject;

public class LumarcaCacher extends ProcessingObject{
	
	private static final String SPLIT_CHAR = "_";
	private static final String END_CHAR  = "*";
	private static final String COORD_CHAR  = "x";
	
	private static boolean dirty;
	
	public static LumarcaSequence sequence;
	
	public static void addFrame(LumarcaFrame frame){
		dirty = true;
		
		if(sequence == null){
			sequence = new LumarcaSequence();
		}
		
		sequence.addFrame(frame);
		
	}
	
	public static void clearSequence(){
		sequence = null;
	}
	
	public static void saveFrames(String filename) {
		if (dirty) {
			dirty = false;

			List<String> result = new ArrayList<String>();

			result.add(END_CHAR);

			for (LumarcaFrame saveFrame : sequence.frames) {
				for (Line line : saveFrame.lines) {
					result.add(pvector2String(line.color) + SPLIT_CHAR
							+ line.top + SPLIT_CHAR + line.bottom);
				}
				result.add(END_CHAR);
			}

			pApplet.saveStrings(filename,
					result.toArray(new String[result.size()]));
		}
	}
	
	private static String pvector2String(PVector p){
		return p.x + COORD_CHAR + p.y + COORD_CHAR + p.z;
	}
	
	public static LumarcaSequence loadFrames(String filename){
		dirty = false;
		sequence = new LumarcaSequence();
		
		String[] frameList = pApplet.loadStrings(filename);
		
		List<Line> frame = null;
		
		for(String line: frameList){
			if(line.contains(END_CHAR)){
				if(frame != null){
					sequence.addFrame(new LumarcaFrame(frame));
				}
				
				frame = new ArrayList<Line>();
			} else{
				String[] lineSplit = line.split(SPLIT_CHAR);

				String color	= lineSplit[0];
				String top		= lineSplit[1];
				String bottom	= lineSplit[2];

				String[] colorSplit 	= color.split(COORD_CHAR);
				String[] topSplit		= top.split(COORD_CHAR);
				String[] bottomSplit	= bottom.split(COORD_CHAR);
				
				Line newLine = new Line(
						new Coord(Float.parseFloat(bottomSplit[0]),Float.parseFloat(bottomSplit[1]),Float.parseFloat(bottomSplit[2])), 
						new Coord(Float.parseFloat(topSplit[0]),Float.parseFloat(topSplit[1]),Float.parseFloat(topSplit[2])), 
						new PVector(Float.parseFloat(colorSplit[0]),Float.parseFloat(colorSplit[1]),Float.parseFloat(colorSplit[2])));

				frame.add(newLine);
			}
		}
		
		return sequence;
	}

}
