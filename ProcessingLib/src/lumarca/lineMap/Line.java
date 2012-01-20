package lumarca.lineMap;

import lumarca.util.Coord;
import lumarca.util.ProcessingObject;
import lumarca.util.Util;
import processing.core.PVector;

public class Line extends ProcessingObject {

	public Coord interSectBottom;
	public Coord bottom;
	public Coord top;
	public PVector color; // optional color value
	
	public Line(Coord bottom, Coord top) {
		super();
		this.bottom = bottom;
		this.top = top;
		interSectBottom = bottom.clone();
		interSectBottom.y += 1000;
	}

	public Line(Coord bottom, Coord top, PVector color) {
		super();
		this.bottom = bottom;
		this.top = top;
		this.color = color;
		interSectBottom = bottom.clone();
		interSectBottom.y += 1000;
	}
	
	public Line clone(){
		if(color != null)
			return new Line(this.bottom.clone(), this.top.clone(), Util.clonePVector(color));
		else
			return new Line(this.bottom.clone(), this.top.clone());
	}
	
}
