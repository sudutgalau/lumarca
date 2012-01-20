package lumarca.util;

import java.util.List;

import javax.media.opengl.GL;

import lumarca.lib.LumarcaLib;
import lumarca.lineMap.LineMap;
import lumarca.obj.TrianglePlane;
import processing.core.PApplet;
import processing.core.PVector;


public class Util extends ProcessingObject {

	public static final Coord zeroCoord = new Coord(0,0,0);
	
	static int[] depths = new int[256];//Lumarca.LINE];

	public static float distribute(int maxDupes, float min, float max) {
		int depth = (int) pApplet.random(min, max);

		float middle = min + ((max - min)/2);
		
		boolean hasDupe = false;

//		for (int i : depths) {
//			if (depths[i] == 0) {
//				hasDupe = true;
//			}
//		}

		while ((depths[depth] == maxDupes)
				|| ((hasDupe) && (depths[depth] != 0))) {
			depth = (int) pApplet.random(middle, max);
			
			System.out.println("MIN: " + min + " MID: " + middle + " MAX: " + max + " " + maxDupes + " = " + depths[depth]);
		}

		depths[depth]++;

		return depth;
	}

	private static boolean checkSameClockDir(PVector pt1, PVector pt2,
			PVector pt3, PVector norm) {
		float testi, testj, testk;
		float dotprod;
		// normal of trinagle
		testi = (((pt2.y - pt1.y) * (pt3.z - pt1.z)) - ((pt3.y - pt1.y) * (pt2.z - pt1.z)));
		testj = (((pt2.z - pt1.z) * (pt3.x - pt1.x)) - ((pt3.z - pt1.z) * (pt2.x - pt1.x)));
		testk = (((pt2.x - pt1.x) * (pt3.y - pt1.y)) - ((pt3.x - pt1.x) * (pt2.y - pt1.y)));

		// Dot product with triangle normal
		dotprod = testi * norm.x + testj * norm.y + testk * norm.z;

		// answer
		if (dotprod < 0)
			return false;
		else
			return true;
	}

	private static PVector getNormal(PVector pt1, PVector pt2, PVector pt3){
		
		float V1x, V1y, V1z;
		float V2x, V2y, V2z;
		Coord norm = new Coord();
		float t;

		// vector form triangle pt1 to pt2
		V1x = pt2.x - pt1.x;
		V1y = pt2.y - pt1.y;
		V1z = pt2.z - pt1.z;

		// vector form triangle pt2 to pt3
		V2x = pt3.x - pt2.x;
		V2y = pt3.y - pt2.y;
		V2z = pt3.z - pt2.z;

		// vector normal of triangle
		norm.x = (V1y * V2z - V1z * V2y);
		norm.y = (V1z * V2x - V1x * V2z);
		norm.z = (V1x * V2y - V1y * V2x);
		
		return norm;
	}
	
	public static Coord checkIntersectTri(TrianglePlane tri,
			Coord linept, Coord vect) {

		List<PVector> absCoords = tri.getAbsCoord();
		
		PVector pt1 = absCoords.get(0);
		PVector pt2 = absCoords.get(1);
		PVector pt3 = absCoords.get(2);
//		
//		float V1x, V1y, V1z;
//		float V2x, V2y, V2z;
		PVector norm = getNormal(pt1, pt2, pt3);
		float dotprod;
		float t;
//
//		// vector form triangle pt1 to pt2
//		V1x = pt2.x - pt1.x;
//		V1y = pt2.y - pt1.y;
//		V1z = pt2.z - pt1.z;
//
//		// vector form triangle pt2 to pt3
//		V2x = pt3.x - pt2.x;
//		V2y = pt3.y - pt2.y;
//		V2z = pt3.z - pt2.z;
//
//		// vector normal of triangle
//		norm.x = (V1y * V2z - V1z * V2y);
//		norm.y = (V1z * V2x - V1x * V2z);
//		norm.z = (V1x * V2y - V1y * V2x);

		if(norm.y < 0){
			pt2 = absCoords.get(0);
			pt1 = absCoords.get(1);
			pt3 = absCoords.get(2);
			
			norm = getNormal(pt1, pt2, pt3);
		}
		
		// dot product of normal and line's vector if zero line is parallel to
		// triangle
		dotprod = norm.x * vect.x + norm.y * vect.y + norm.z * vect.z;

		if (dotprod < 0) {
			// Find point of intersect to triangle plane.
			// find t to intersect point
			t = -(norm.x * (linept.x - pt1.x) + norm.y * (linept.y - pt1.y) + norm.z
					* (linept.z - pt1.z))
					/ (norm.x * vect.x + norm.y * vect.y + norm.z * vect.z);

			// if ds is neg line started past triangle so can't hit triangle.
			if (t < 0)
				return null;

			Coord result = new Coord(linept.x + vect.x * t, linept.y + vect.y
					* t, linept.z + vect.z * t);

			if (checkSameClockDir(pt1, pt2, result, norm)) {
				if (checkSameClockDir(pt2, pt3, result, norm)) {
					if (checkSameClockDir(pt3, pt1, result, norm)) {
						// answer in pt_int is insde triangle
						return result; // ANSWER
					}
				}
			}
		}
		return null;
	}
	
	public static void makeVertex(GL gl, PVector center, PVector coord){
		gl.glVertex3f(center.x + coord.x, center.y + coord.y, center.z + coord.z);
	}
	
	public static void drawLine(GL gl, PVector color, PVector top, PVector bottom, LineMap lineMap){

		
		float alt = pApplet.map(bottom.z, lineMap.maxPosition.z, lineMap.minPosition.z, 0f, 0.5f);
		
		gl.glLineWidth(1f);
		
		//Line
		gl.glColor3f(color.x, color.y, color.z);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(bottom.x,
					  bottom.y + bottom.y * (alt * alt),
					  bottom.z);
		
		gl.glVertex3f(top.x,
					top.y + top.y * (alt * alt),
					top.z);
		gl.glEnd();
		
		//Bottom Dot
		gl.glColor3f(1f,1f,1f);

		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(top.x,
				      top.y + top.y * (alt * alt),
					  top.z);

		
		gl.glVertex3f(top.x,
				(top.y + LineMap.DOT_HEIGHT) + (top.y + LineMap.DOT_HEIGHT) * (alt * alt),
				top.z);
		
		gl.glEnd();
		
		//Bottom Dot
		gl.glColor3f(1f,1f,1f);

		gl.glBegin(GL.GL_LINE_STRIP);
		
		gl.glVertex3f(bottom.x,
				bottom.y + bottom.y * (alt * alt),
				bottom.z);
		gl.glVertex3f(bottom.x,
				(bottom.y - LineMap.DOT_HEIGHT) + (bottom.y - LineMap.DOT_HEIGHT) * (alt * alt),
				bottom.z);
		
		gl.glEnd();
	}
	
	public static void drawLineNoDots(GL gl, PVector color, PVector top, PVector bottom, LineMap lineMap){

//		float factor = pApplet.sq(top.z);
//		System.out.println(factor);
		//Line
		
		float alt = pApplet.map(bottom.z, lineMap.maxPosition.z, lineMap.minPosition.z, 0f, 0.5f);
		
		gl.glColor3f(color.x, color.y, color.z);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(bottom.x,
					  bottom.y + bottom.y * (alt * alt),
					  bottom.z);
		
		gl.glVertex3f(top.x,
					top.y + top.y * (alt * alt),
					top.z);
		
		gl.glEnd();
	}
	
	public static void drawPoint(GL gl, PVector coord){
		gl.glColor3f(1f,1f,1f);

		gl.glPointSize(5.0f);
		gl.glBegin(GL.GL_POINTS);
		
		gl.glVertex3f(coord.x,
				coord.y,
				coord.z);
		
		gl.glEnd();
	}
	
	public PVector rotateView(float angle, float x, float y, float z, PVector pos, Coord center)
	{
		Coord vNewView = new Coord();

		PVector vView = Coord.sub(center, pos);

		float cosTheta = (float)PApplet.cos(angle);
		float sinTheta = (float)PApplet.sin(angle);

		// x position of the new rotated point

		vNewView.x   = (cosTheta + (1 - cosTheta) * x * x)		* vView.x;
		vNewView.x  += ((1 - cosTheta) * x * y - z * sinTheta)	* vView.y;
		vNewView.x  += ((1 - cosTheta) * x * z + y * sinTheta)	* vView.z;

		// y position of the new rotated point


		vNewView.y  = ((1 - cosTheta) * x * y + z * sinTheta)	* vView.x;
		vNewView.y += (cosTheta + (1 - cosTheta) * y * y)		* vView.y;
		vNewView.y += ((1 - cosTheta) * y * z - x * sinTheta)	* vView.z;

		// z position of the new rotated point


		vNewView.z   = ((1 - cosTheta) * x * z - y * sinTheta)	* vView.x;
		vNewView.z  += ((1 - cosTheta) * y * z + x * sinTheta)	* vView.y;
		vNewView.z  += (cosTheta + (1 - cosTheta) * z * z)		* vView.z;

		pos = Coord.add(center, vNewView);
		
		return pos;
	}
	
	public static PVector rotateX(PVector center, PVector rotatePt, float angle) {
		
//		System.out.println(angle);
		
		PVector dir = Coord.sub(rotatePt, center);

		float dist = PApplet.dist(center.z, center.y, rotatePt.z, rotatePt.y);
		
		angle += PApplet.atan2(dir.y, dir.z);

		dir.z = PApplet.cos(angle) * dist + center.z;
		dir.y = PApplet.sin(angle) * dist + center.y;
		dir.x = rotatePt.x;
		
		return dir;
	} 
	
	public static PVector rotateY(PVector center, PVector rotatePt, float angle) {
		
//		System.out.println(angle);
		
		PVector dir = Coord.sub(rotatePt, center);

		float dist = PApplet.dist(center.x, center.z, rotatePt.x, rotatePt.z);
		
		angle += PApplet.atan2(dir.z, dir.x);

		dir.x = PApplet.cos(angle) * dist + center.x;
		dir.z = PApplet.sin(angle) * dist + center.z;
		dir.y = rotatePt.y;
		
		return dir;
	} 
	
	public static PVector rotateZ(PVector center, PVector rotatePt, float angle) {
		
//		System.out.println(angle);
		
		PVector dir = Coord.sub(rotatePt, center);

		float dist = PApplet.dist(center.x, center.y, rotatePt.x, rotatePt.y);
		
		angle += PApplet.atan2(dir.y, dir.x);

		dir.x = PApplet.cos(angle) * dist + center.x;
		dir.y = PApplet.sin(angle) * dist + center.y;
		dir.z = rotatePt.z;
		
		return dir;
	} 
	
	public static void log(String str){
		if(LumarcaLib.LOGGING){
			System.out.println(str);
		}
	}
	
	public static PVector clonePVector(PVector vect){
		return new PVector(vect.x, vect.y, vect.z);
	}

}
