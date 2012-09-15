/**
 * you can put a one sentence description of your library here.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */

package template.library;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import lumarca.cache.LumarcaFrame;
import lumarca.lib.LumarcaLib;
import lumarca.lineMap.Line;
import lumarca.obj.Shape;
import lumarca.util.LumarcaObject;
import lumarca.util.ProcessingObject;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * This is a template class and can be used to start a new processing library or tool.
 * Make sure you rename this class as well as the name of the example package 'template' 
 * to your own lobrary or tool naming convention.
 * 
 * @example Hello 
 * 
 * (the tag @example followed by the name of an example included in folder 'examples' will
 * automatically include the example in the javadoc.)
 *
 */

public class LumarcaLibrary implements PConstants{
	
	// pApplet is a reference to the parent sketch
	public PApplet pApplet;
	public LumarcaLib lib;

	int myVariable = 0;
	
	public final static String VERSION = "0.1";
	

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 * 
	 * @example Hello
	 * @param pApplet
	 */
	public LumarcaLibrary(PApplet pApplet, String fileName) {
		this.pApplet = pApplet;
		ProcessingObject.pApplet = pApplet;
		LumarcaObject.lumarca = this;
		
		String[] map = pApplet.loadStrings(fileName);
		
		lib = new LumarcaLib(pApplet, map);
		lib.init();
		
		pApplet.frame.setLocation(0, 0);
		
		pApplet.hint(DISABLE_OPENGL_2X_SMOOTH); 
		
		welcome();
		
		pApplet.registerDispose(this);
		pApplet.registerDraw(this);
		pApplet.registerKeyEvent(this);
		pApplet.registerMouseEvent(this);
		pApplet.registerPost(this);
		pApplet.registerPre(this);
		pApplet.registerSize(this);
	}
	
	public LumarcaLibrary(PApplet pApplet, int numLines, boolean newMap) {
		this.pApplet = pApplet;
		lib = new LumarcaLib(pApplet, numLines, newMap);
		ProcessingObject.pApplet = pApplet;
		LumarcaObject.lumarca = this;
		lib.init();
		
		pApplet.frame.setLocation(0, 0);
		
		pApplet.hint(DISABLE_OPENGL_2X_SMOOTH); 
		
		welcome();
		
		pApplet.registerDispose(this);
		pApplet.registerDraw(this);
		pApplet.registerKeyEvent(this);
		pApplet.registerMouseEvent(this);
		pApplet.registerPost(this);
		pApplet.registerPre(this);
		pApplet.registerSize(this);
	}
	
	
	private void welcome() {
		System.out.println("Lumarca " + VERSION + " by madparker");
	}

	public void calibration(){
		lib.calibration();
	}
	
	public Line[] getLines(){
		return lib.lineMap.lines;
	}
	
	public float getMaxX(){
		return lib.lineMap.maxPosition.x;
	}
	
	public float getMaxY(){
		return lib.lineMap.maxPosition.y;
	}
	
	public float getMaxZ(){
		return lib.lineMap.maxPosition.z;
	}
	
	public float getMidX(){
		return lib.lineMap.midPosition.x;
	}
	
	public float getMidY(){
		return lib.lineMap.midPosition.y;
	}
	
	public float getMidZ(){
		return lib.lineMap.midPosition.z;
	}
	
	public float getMinX(){
		return lib.lineMap.minPosition.x;
	}
	
	public float getMinY(){
		return lib.lineMap.minPosition.y;
	}
	
	public float getMinZ(){
		return lib.lineMap.minPosition.z;
	}
	
	public void getCenterPosition(PVector becomeCenter){
		becomeCenter.set(lib.lineMap.midPosition.x, lib.lineMap.midPosition.y, lib.lineMap.midPosition.z);
	}
	
	public void getMinPosition(PVector becomeCenter){
		becomeCenter.set(lib.lineMap.minPosition.x, lib.lineMap.minPosition.y, lib.lineMap.minPosition.z);
	}
	
	public void getMaxPosition(PVector becomeCenter){
		becomeCenter.set(lib.lineMap.maxPosition.x, lib.lineMap.maxPosition.y, lib.lineMap.maxPosition.z);
	}
	
	public List<Line> getShapeLines(Shape shape){
		return lib.lineMap.getShape(shape.color, shape);
	}
	
	public void drawLineNoDots(PVector color, PVector top, PVector bottom){
		lib.lineMap.drawLineNoDots(lib.gl, color, top, bottom);
	}
	
	public void drawLine(PVector color, PVector top, PVector bottom){
		lib.lineMap.drawLine(lib.gl, color, top, bottom);
	}
	
	public void drawShape(PVector color, Shape shape){
		lib.lineMap.drawShape(lib.gl, color, shape);
	}
	
	public void drawShapeNotDots(PVector color, Shape shape){
		lib.lineMap.drawShapeNoDots(lib.gl, color, shape);
	}
	
	public void drawWireFrame(Shape shape){
		shape.drawWireFrame(lib.gl);
	}
	
	public void moveCamera(float rot){
		lib.cameraRot = rot;
	}
	
	// To register any of these methods with the parent, call
	// parent.registerXxxxx(this), replacing Xxxxx with the name of the function
	// (pre, draw, post, etc) that you'd like to use.

	// Method that's called just after beginDraw(), meaning that it can affect
	// drawing.
	public void pre() {
		lib.pre();
	}

	// Method that's called at the end of draw(), but before endDraw().
	public void draw() {
		lib.draw();
	}

	// Method called after draw has completed and the frame is done. No drawing
	// allowed.
	public void post() {
		lib.post();
	}

	// Called when a mouse event occurs in the parent applet. Drawing is allowed
	// because mouse events are queued.
	public void mouseEvent(MouseEvent e) {
		lib.mouseEvent(e);
	}

	// Called when a key event occurs in the parent applet. Drawing is allowed
	// because key events are queued.
	public void keyEvent(KeyEvent e) {
		lib.keyEvent(e);
	}

	// This will be called the first time an applet sets its size, but also any
	// time that it's called while the PApplet is running. No drawing should
	// occur inside of this method, because it may not be the case that the new
	// renderer is yet valid and ready. use this only to flag the new size and
	// prepare for the next frame.
	public void size(int width, int height) {
		lib.size(width, height);
	}

	// Called to halt execution. Can be called by users, for instance
	// movie.stop() will shut down a movie that's being played, or camera.stop()
	// stops capturing video. server.stop() will shut down the server and shut
	// it down completely. May be called multiple times.
	public void stop() {
		lib.stop();
	}

	// Called to free resources before shutting down. This should only be called
	// by PApplet. The dispose() method is what gets called when the host applet
	// is being shut down, so this should stop any threads, disconnect from the
	// net, unload memory, etc.
	public void dispose() {
		lib.dispose();
	}
}

