package lumarca.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.media.opengl.GL;

import lumarca.lib.LumarcaLib;
import lumarca.lineMap.Line;
import lumarca.lineMap.LineMap;
import lumarca.util.Coord;
import lumarca.util.HeightCoordComparator;
import lumarca.util.Util;
import objloader.ModelElement;
import objloader.ModelSegment;
import objloader.OBJModel;
import processing.core.PApplet;
import processing.core.PVector;


public class ObjFile extends Shape {

	private OBJModel model;
	public float size;
	public float scale;
	
	private static final Coord vec = new Coord(0, -1, 0);
	
	public ObjFile(PApplet lumarca, PVector center, PVector color, String fileName, float size){
		  model = new OBJModel(lumarca);
		  model.load(fileName);
		  this.center = new Coord(center);
		  makeTrueCenter();
		  this.color = color;
		  this.size = size;
	}
	
	public void makeTrueCenter(){

		Coord max = new Coord(-1000000, -1000000, -1000000);
		Coord min = new Coord(1000000, 1000000, 1000000);
		
		for (int i = 0; i < model.vertexes.size(); i++) {
			PVector coord = Util.clonePVector(model.vertexes.get(i));
			
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
		
		scale = max.z - min.z;
		
		Coord trueCenter = new Coord((min.x + max.x)/2, (min.y + max.y)/2, (min.z + max.z)/2);
		
		for (int i = 0; i < model.vertexes.size(); i++) {
			PVector coord = Util.clonePVector(model.vertexes.get(i));
			
			coord = PVector.sub(coord, trueCenter);
			
			model.vertexes.set(i, coord);
		}
	}
	
	public List<PVector> getIntersection(Line line) {

		List<PVector> result = new ArrayList<PVector>();

		Coord inter = null;
		
		Coord testCoord = new Coord();
		
		for (int s = 0; s < model.modelSegments.size(); s++) {

			ModelSegment tmpModelSegment = (ModelSegment) model.modelSegments.elementAt(s);

			for (int f = 0; f < tmpModelSegment.elements.size(); f += 1) {

				ModelElement tmpf = (ModelElement) (tmpModelSegment.elements.elementAt(f));

				if (tmpf.indexes.size() > 0) {
					
					for (int fp = 0; fp < tmpf.indexes.size() - 2; fp += 1) {

						int vidx1 = ((Integer) (tmpf.indexes.elementAt(0))).intValue();
						int vidx2 = ((Integer) (tmpf.indexes.elementAt(fp + 1))).intValue();
						int vidx3 = ((Integer) (tmpf.indexes.elementAt(fp + 2))).intValue();

						PVector v1 = getCoord(model.vertexes.elementAt(vidx1 - 1));
						PVector v2 = getCoord(model.vertexes.elementAt(vidx2 - 1));
						PVector v3 = getCoord(model.vertexes.elementAt(vidx3 - 1));
						
						TrianglePlane tri = new TrianglePlane(center,v1,v2,v3);

						testCoord.x = line.bottom.x;
						testCoord.y = line.bottom.y + 10000;
						testCoord.z = line.bottom.z;
						
						inter = Util.checkIntersectTri(tri, 
								testCoord, 
								vec);
						

						if(inter != null){
							
//							Coord bottom = inter.clone();
//							
//							bottom.y -= 10;
							
							result.add(inter);
						}
					}
				
				} else {

					System.out.println("HUH?");
				}
			}
		}
		
//		if(result.size() != 0){
//			System.out.println("result.size(): " + result.size());
//		}
		
		return result;
	}
	
	public void drawIntersect(GL gl, PVector color, Line fullLine) {
		drawIntersect(gl, color, fullLine, true);
	}
	
	public void drawIntersect(GL gl, PVector color, Line fullLine, boolean dots) {
		
		List<Line> lines = getIntersect(color, fullLine);
		
		for(Line line: lines){

			if(line.top.y > fullLine.bottom.y){
				line.top.y = fullLine.bottom.y;
			}
			
			if(line.top.y < fullLine.top.y){
				line.top.y = fullLine.top.y;
			}
			
			if(line.bottom.y < fullLine.top.y){
				line.bottom.y = fullLine.top.y;
			} 
			 
			if (line.bottom.y > fullLine.bottom.y){
				line.bottom.y = fullLine.bottom.y;
			}

			LineMap.getInstance().drawLineNoDots(gl, color, line.bottom, line.top);
			
			if(dots){
				if(line.bottom.y != fullLine.bottom.y && line.bottom.y != fullLine.top.y){
					LineMap.getInstance().drawLine(gl, color, line.bottom, line.bottom);
				} 
				if(line.top.y != fullLine.bottom.y && line.top.y != fullLine.top.y){
					LineMap.getInstance().drawLine(gl, color, line.top, line.top);
				} 
			}
			
		}
	}
	
	public List<Line> getIntersect(PVector color, Line line) {
		List<Line> interLines = new ArrayList<Line>();
		List<PVector> result = getIntersection(line);

//		if(result.size() != 2 && result.size() != 0)
//			System.out.println("result: " + result.size());
		
		if(result.size() > 2){
			SortedSet<PVector> set = new TreeSet<PVector>(new HeightCoordComparator());
			set.addAll(result);
			
			result = new ArrayList<PVector>(set);
		}
		
		if (result.size() == 1) {
			PVector coord1 = result.get(0);
			PVector coord2 = line.bottom.clone();
//			coord2.y += 100;
			
//			if (coord2.y < line.bottom.y) {
				interLines.add(new Line(new Coord(coord1), new Coord(coord2), color));
//			}
		}
		
		if (result.size() == 2) {
			Coord coord1 = new Coord(result.get(0));
			Coord coord2 = new Coord(result.get(1));

			if (coord1.y > line.bottom.y) {
				coord1 = line.bottom;
			}
//			if (coord2.y < line.bottom.y) {
				interLines.add(new Line(coord1, coord2, color));
//			}
		}

		if (result.size() > 2) {
			for(int i = 0; i < result.size() - 1; i+=2){
				Coord coord1 = new Coord(result.get(i));
				Coord coord2 = new Coord(result.get(i+1));
				
				if(coord2.y > line.bottom.y){
					coord2 = line.bottom;
				}
//				if(coord1.y < line.bottom.y){
					interLines.add(new Line(coord1, coord2, color));
//				}
				
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

						PVector v1 = getCoord(model.vertexes.elementAt(vidx1 - 1));
						PVector v2 = getCoord(model.vertexes.elementAt(vidx2 - 1));
						PVector v3 = getCoord(model.vertexes.elementAt(vidx3 - 1));
						
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
			PVector coord = model.vertexes.get(i);
			model.vertexes.set(i, Util.rotateX(Util.zeroCoord, coord, rot));
//			model.vertexes.set(i, Util.rotateX(objCenter, coord, rot));
		}
	}

	@Override
	public void rotateOnY(float rot) {
		for (int i = 0; i < model.vertexes.size(); i++) {
			PVector coord = model.vertexes.get(i);
			model.vertexes.set(i, Util.rotateY(Util.zeroCoord, coord, rot));
//			model.vertexes.set(i, Util.rotateY(objCenter, coord, rot));
		}
		
	}

	@Override
	public void rotateOnZ(float rot) {
		for (int i = 0; i < model.vertexes.size(); i++) {
			PVector coord = model.vertexes.get(i);
			model.vertexes.set(i, Util.rotateZ(Util.zeroCoord, coord, rot));
		}
	}

	public void setSize(float size){
		this.size = size;
	}
	
	private PVector getCoord(PVector vert){
		PVector result = new Coord(vert);
		
		result.x *= size;
		result.y *= size;
		result.z *= size;
		
		return result;
	}
}
