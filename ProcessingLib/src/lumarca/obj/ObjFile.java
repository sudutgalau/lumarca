package lumarca.obj;

import java.sql.ResultSet;
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
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import saito.objloader.Face;
import saito.objloader.OBJModel;
import saito.objloader.Segment;


public class ObjFile extends Shape {

	public OBJModel model;
	public float size;
	public float scale;
	
	public PVector[] orgVerts;
	
	private PVector white = new PVector(1, 1, 1);
	
	private static final Coord vec = new Coord(0, -1, 0);
	
	public ObjFile(PApplet lumarca, PVector center, PVector color, String fileName, float size){
		  model = new OBJModel(lumarca);
		  model.load(fileName);
		  this.center = new Coord(center);
		  this.color = color;
		  this.size = size;
		  
		  getOrgVerts();
		  
		  resize();
		  makeTrueCenter();
	}
	
	public PVector[] getOrgVerts(){
		if(orgVerts == null){
			orgVerts = new PVector[model.getVertexCount()];
		}
		
		for(int i = 0; i < model.getVertexCount(); i++){
			orgVerts[i] = Util.clonePVector(model.getVertex(i));
		}
		
		return orgVerts;
	}
	
	public void makeTrueCenter(){

		Coord max = new Coord(-1000000, -1000000, -1000000);
		Coord min = new Coord(1000000, 1000000, 1000000);
		
		for (int i = 0; i < model.getVertexCount(); i++) {
			
			PVector coord = model.getVertex(i);
			
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
		
		for (int i = 0; i < model.getVertexCount(); i++) {
			PVector coord = Util.clonePVector(model.getVertex(i));

			coord = PVector.sub(coord, trueCenter);
			
			model.setVertex(i, coord);
		}
	}
	
	public void resize() {
		for(int i = 0; i < model.getVertexCount(); i++){
			model.setVertex(i, PVector.mult(orgVerts[i], size));
		}
		makeTrueCenter();
	}
	
	public List<PVector> getIntersection(Line line) {

		List<PVector> result = new ArrayList<PVector>();

		Coord inter = null;

		Coord testCoord = new Coord();

		Segment tmpModelSegment;
		Face tmpModelElement;
		PVector v = null, vt = null, vn = null;

		// render all triangles
		for (int s = 0; s < model.getSegmentCount(); s++) {

			tmpModelSegment = model.getSegment(s);

			for (int f = 0; f < tmpModelSegment.getFaceCount(); f++) {
				tmpModelElement = (tmpModelSegment.getFace(f));

				if (tmpModelElement.getVertIndexCount() > 0) {

					for (int fp = 1; fp < tmpModelElement.getVertIndexCount() - 1; fp++) {

						int vidx1 = tmpModelElement.getVertexIndex(0);
						int vidx2 = tmpModelElement.getVertexIndex(fp);
						int vidx3 = tmpModelElement.getVertexIndex(fp + 1);

						PVector v1 = model.getVertex(vidx1);
						PVector v2 = model.getVertex(vidx2);
						PVector v3 = model.getVertex(vidx3);

						TrianglePlane tri = new TrianglePlane(center, v1, v2, v3);

						testCoord.x = line.bottom.x;
						testCoord.y = line.bottom.y + 10000;
						testCoord.z = line.bottom.z;

						inter = Util.checkIntersectTri(tri, testCoord, vec);

						if (inter != null) {
							result.add(inter);
						}

					}
				}

			}
		}

		return result;
	}

	
	public void drawIntersect(GL gl, PVector color, Line fullLine) {
		drawIntersect(gl, color, fullLine, true);
	}
	
	public void drawIntersect(GL gl, PVector color, Line fullLine, boolean dots) {
		
		List<Line> lines = getIntersect(color, fullLine);

		PVector offset = new PVector();
		
		for(Line line: lines){

			LineMap.getInstance().drawLineNoDots(gl, color, line.bottom, line.top);
			
			if(dots){
				if(line.bottom.y != fullLine.bottom.y && line.bottom.y != fullLine.top.y){
					offset.set(line.bottom.x, line.bottom.y + Util.DOT_HEIGHT, line.bottom.z);
					LineMap.getInstance().drawLineNoDots(gl, white, line.bottom, offset);
				} 
				if(line.top.y != fullLine.bottom.y && line.top.y != fullLine.top.y){
					offset.set(line.top.x, line.top.y - Util.DOT_HEIGHT, line.top.z);
					LineMap.getInstance().drawLineNoDots(gl, white, line.top, offset);
				} 
			}
			
		}
	}
	
	public List<Line> getIntersect(PVector color, Line fullLine) {
		List<Line> interLines = new ArrayList<Line>();
		List<PVector> result = getIntersection(fullLine);

//		if(result.size() != 2 && result.size() != 0)
//			System.out.println("result: " + result.size());
		
		if(result.size() > 2){
			SortedSet<PVector> set = new TreeSet<PVector>(new HeightCoordComparator());
			set.addAll(result);
			
			result = new ArrayList<PVector>(set);
		}
		
		if (result.size() == 1) {
			PVector coord1 = result.get(0);
			PVector coord2 = fullLine.bottom.clone();
//			coord2.y += 100;
			
//			if (coord2.y < line.bottom.y) {
				interLines.add(new Line(new Coord(coord1), new Coord(coord2), color));
//			}
		}
		
		if (result.size() == 2) {
			Coord coord1 = new Coord(result.get(0));
			Coord coord2 = new Coord(result.get(1));

			if (coord1.y > fullLine.bottom.y) {
				coord1 = fullLine.bottom;
			}
//			if (coord2.y < line.bottom.y) {
				interLines.add(new Line(coord1, coord2, color));
//			}
		}

		if (result.size() > 2) {
			for(int i = 0; i < result.size() - 1; i+=2){
				Coord coord1 = new Coord(result.get(i));
				Coord coord2 = new Coord(result.get(i+1));
				
				if(coord2.y > fullLine.bottom.y){
					coord2 = fullLine.bottom;
				}
//				if(coord1.y < line.bottom.y){
					interLines.add(new Line(coord1, coord2, color));
//				}
				
			}
		}
		
		for(Line line: interLines){

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
		}
		
		return interLines;
	}

	@Override
	public void drawShape(GL gl) {
		// TODO Auto-generated method stub
		
	}
	

	public void drawOBJ() {
		pApplet.pushMatrix();
		
		pApplet.translate(center.x, center.y, center.z);
	    model.draw();

	    pApplet.popMatrix();
	}

	@Override
	public void drawWireFrame(GL gl) {

		for (int faceNum = 0; faceNum < model.getIndexCountInSegment(0); faceNum++) {
			int[] vertIndex = model.getVertexIndicesInSegment(0, faceNum);
			for (int i = 0; i < vertIndex.length - 2; i += 3) {
				PVector v1 = model.getVertex(vertIndex[i]);
				PVector v2 = model.getVertex(vertIndex[i + 1]);
				PVector v3 = model.getVertex(vertIndex[i + 2]);

				TrianglePlane tri = new TrianglePlane(center, v1, v2, v3);

				tri.drawWireFrame(gl);
			}
		}
	}
	

	@Override
	public void rotateOnX(float rot) {

		for (int i = 0; i < model.getVertexCount(); i++) {
			PVector coord = model.getVertex(i);
			model.setVertex(i, Util.rotateX(Util.zeroCoord, coord, rot));
		}
	}

	@Override
	public void rotateOnY(float rot) {
		for (int i = 0; i < model.getVertexCount(); i++) {
			PVector coord = model.getVertex(i);
			model.setVertex(i, Util.rotateY(Util.zeroCoord, coord, rot));
		}
	}

	@Override
	public void rotateOnZ(float rot) {
		for (int i = 0; i < model.getVertexCount(); i++) {
			PVector coord = model.getVertex(i);
			model.setVertex(i, Util.rotateZ(Util.zeroCoord, coord, rot));
		}
	}

	public void setSize(float size){
		this.size = size;
		resize();
	}
	
	private PVector getCoord(PVector vert){
		PVector result = new Coord(vert);
		
		result.x *= size;
		result.y *= size;
		result.z *= size;
		
		return result;
	}
}
