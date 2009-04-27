package lumarca.lineMap;

import lumarca.util.Coord;
import lumarca.util.ProcessingObject;

public class Line extends ProcessingObject {

	public Coord interSectBottom;
	public Coord bottom;
	public Coord top;
	
	public Line(Coord bottom, Coord top) {
		super();
		this.bottom = bottom;
		this.top = top;
		interSectBottom = bottom.clone();
		interSectBottom.y += 1000;
	}
	
}
