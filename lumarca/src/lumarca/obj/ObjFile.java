package lumarca.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.util.Coord;
import lumarca.util.HeightCoordComparator;
import lumarca.util.Util;
import objloader.ModelElement;
import objloader.ModelSegment;
import objloader.OBJModel;


public class ObjFile extends Shape {

	private OBJModel model;
	private float size;
	
	public ObjFile(Lumarca lumarca, Coord center, Coord color, String fileName, float size){
		  model = new OBJModel(lumarca);
		  model.load(fileName);
		  this.center = center;
		  makeTrueCenter();
		  this.color = color;
		  this.size = size;
	}
	
	public void makeTrueCenter(){

		Coord max = new Coord(-1000000, -1000000, -1000000);
		Coord min = new Coord(1000000, 1000000, 1000000);
		
		for (int i = 0; i < model.vertexes.size(); i++) {
			Coord coord = model.vertexes.get(i).clone();
			
			if(coord.x > max.x){
				max.x = coord.x;
			}
			if(coord.y > max.y){
				max.y = coord.y;
			}
			if(coord.z > max.z){
				max.z = coord.z;
			}
			if(coord.x < min.x){
				min.x = coord.x;
			}
			if(coord.y < min.y){
				min.y = coord.y;
			}
			if(coord.z < min.z){
				min.z = coord.z;
			}
		}
		
		Coord trueCenter = new Coord((min.x + max.x)/2, (min.y + max.y)/2, (min.z + max.z)/2);
		
		for (int i = 0; i < model.vertexes.size(); i++) {
			Coord coord = model.vertexes.get(i).clone();
			
			coord = Coord.minus(coord, trueCenter);
			
			model.vertexes.set(i, coord);
		}
	}
	
	public List<Coord> getIntersection(Line line) {

		List<Coord> result = new ArrayList<Coord>();

		Coord inter = null;
		
		for (int s = 0; s < model.modelSegments.size(); s++) {

			ModelSegment tmpModelSegment = (ModelSegment) model.modelSegments.elementAt(s);

			for (int f = 0; f < tmpModelSegment.elements.size(); f += 1) {

				ModelElement tmpf = (ModelElement) (tmpModelSegment.elements.elementAt(f));

				if (tmpf.indexes.size() > 0) {
					
					for (int fp = 0; fp < tmpf.indexes.size() - 2; fp += 1) {

						int vidx1 = ((Integer) (tmpf.indexes.elementAt(0))).intValue();
						int vidx2 = ((Integer) (tmpf.indexes.elementAt(fp + 1))).intValue();
						int vidx3 = ((Integer) (tmpf.indexes.elementAt(fp + 2))).intValue();

						Coord v1 = getCoord(model.vertexes.elementAt(vidx1 - 1));
						Coord v2 = getCoord(model.vertexes.elementAt(vidx2 - 1));
						Coord v3 = getCoord(model.vertexes.elementAt(vidx3 - 1));
						
						TrianglePlane tri = new TrianglePlane(center,v1,v2,v3);
						
						inter = Util.checkIntersectTri(tri, 
								line.interSectBottom, 
								new Coord(0,-1,0));
						

						if(inter != null){
							
							Coord bottom = inter.clone();
							
							bottom.y -= 10;
							
							result.add(inter);
						}
					}
				
				} else {

					System.out.println("HUH?");
				}
			}
		}
		
		return result;
	}
	
	public void drawIntersect(GL gl, Coord color, Line line) {
		List<Coord> result = getIntersection(line);
		
//		System.out.println("result: " + result.size());
	
		if(result.size() > 2){
			SortedSet<Coord> set = new TreeSet<Coord>(new HeightCoordComparator());
			set.addAll(result);
			
			result = new ArrayList<Coord>(set);
			
//			System.out.println("1----------");
//			for(Coord coord: result){
//				System.out.println(coord.y);
//			}
//			System.out.println("2----------");
		}
		
		if (result.size() == 2) {
			Coord coord1 = result.get(0);
			Coord coord2 = result.get(1);

//			if (coord1.y > line.bottom.y) {
//				coord1 = line.bottom;
//			}
//			if (coord2.y < line.bottom.y) {
//				Util.drawLine(gl, color, coord1, coord2);
//			}
//			if (coord2.y < line.top.y) {
//				Util.drawLine(gl, color, coord1, line.top);
//			}

			Util.drawLine(gl, color, coord1, coord2);
		}

		if (result.size() > 2) {
			for(int i = 0; i < result.size() - 1; i+=2){
				Coord coord1 = result.get(i);
				Coord coord2 = result.get(i+1);
				
				if(coord2.y > line.bottom.y){
					coord2 = line.bottom;
				}
				if(coord1.y < line.bottom.y){
					Util.drawLine(gl, color, coord1, coord2);
				}
				
			}
		}
	}
	
	public List<Line> getIntersect(Coord color, Line line) {
		List<Line> interLines = new ArrayList<Line>();
		List<Coord> result = getIntersection(line);
	
		if(result.size() > 2){
			SortedSet<Coord> set = new TreeSet<Coord>(new HeightCoordComparator());
			set.addAll(result);
			
			result = new ArrayList<Coord>(set);
		}
		
		if (result.size() == 2) {
			Coord coord1 = result.get(0);
			Coord coord2 = result.get(1);

			if (coord1.y > line.bottom.y) {
				coord1 = line.bottom;
			}
			if (coord2.y < line.bottom.y) {
				interLines.add(new Line(coord1, coord2, color));
			}
		}

		if (result.size() > 2) {
			for(int i = 0; i < result.size() - 1; i+=2){
				Coord coord1 = result.get(i);
				Coord coord2 = result.get(i+1);
				
				if(coord2.y > line.bottom.y){
					coord2 = line.bottom;
				}
				if(coord1.y < line.bottom.y){
					interLines.add(new Line(coord1, coord2, color));
				}
				
			}
		}
		
		return interLines;
	}

	@Override
	public void drawShape(GL gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawWireFrame(GL gl) {
		
		for (int s = 0; s < model.modelSegments.size(); s++) {

			ModelSegment tmpModelSegment = (ModelSegment) model.modelSegments.elementAt(s);

			for (int f = 0; f < tmpModelSegment.elements.size(); f += 1) {

				ModelElement tmpf = (ModelElement) (tmpModelSegment.elements.elementAt(f));

				if (tmpf.indexes.size() > 0) {
					
					for (int fp = 0; fp < tmpf.indexes.size() - 2; fp += 1) {

						int vidx1 = ((Integer) (tmpf.indexes.elementAt(0))).intValue();
						int vidx2 = ((Integer) (tmpf.indexes.elementAt(fp + 1))).intValue();
						int vidx3 = ((Integer) (tmpf.indexes.elementAt(fp + 2))).intValue();

						Coord v1 = getCoord(model.vertexes.elementAt(vidx1 - 1));
						Coord v2 = getCoord(model.vertexes.elementAt(vidx2 - 1));
						Coord v3 = getCoord(model.vertexes.elementAt(vidx3 - 1));
						
						TrianglePlane tri = new TrianglePlane(center, v1,v2,v3);

						tri.drawWireFrame(gl);
					}
				
				} else {

					System.out.println("HUH?");
				}
			}
		}
		
	}

	@Override
	public void rotateOnX(float rot) {

		for (int i = 0; i < model.vertexes.size(); i++) {
			Coord coord = model.vertexes.get(i);
			model.vertexes.set(i, Util.rotateX(Util.zeroCoord, coord, rot));
//			model.vertexes.set(i, Util.rotateX(objCenter, coord, rot));
		}
	}

	@Override
	public void rotateOnY(float rot) {
		for (int i = 0; i < model.vertexes.size(); i++) {
			Coord coord = model.vertexes.get(i);
			model.vertexes.set(i, Util.rotateY(Util.zeroCoord, coord, rot));
//			model.vertexes.set(i, Util.rotateY(objCenter, coord, rot));
		}
		
	}

	@Override
	public void rotateOnZ(float rot) {
		for (int i = 0; i < model.vertexes.size(); i++) {
			Coord coord = model.vertexes.get(i);
			model.vertexes.set(i, Util.rotateZ(Util.zeroCoord, coord, rot));
		}
	}

	public void setSize(float size){
		this.size = size;
	}
	
	private Coord getCoord(Coord vert){
		Coord result = new Coord(vert);
		
		result.x *= size;
		result.y *= size;
		result.z *= size;
		
		return result;
	}
	
}
