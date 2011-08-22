package lumarca.util;

import java.util.Comparator;

public class HeightCoordComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		Coord coord1 = (Coord)o1;
		Coord coord2 = (Coord)o2;
		
		if(coord1.y > coord2.y){
			return -1;
		} else {
			return 1;	
		}
	}

}
