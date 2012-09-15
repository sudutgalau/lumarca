package lumarca.cache;

import java.util.ArrayList;
import java.util.List;

import lumarca.lineMap.Line;
import lumarca.util.LumarcaObject;

public class LumarcaFrame extends LumarcaObject {
	
	public List<Line> lines = new ArrayList<Line>();

	public LumarcaFrame(){
	}
	
	public LumarcaFrame(List<Line> lines){
		this.lines = lines;
	}
	
	public void addLine(Line line){
		lines.add(line);
	}
	
	public void addLines(List<Line> lines){
		this.lines.addAll(lines);
	}
	
	public void addFrame(LumarcaFrame frame){
		lines.addAll(frame.lines);
	}
}
