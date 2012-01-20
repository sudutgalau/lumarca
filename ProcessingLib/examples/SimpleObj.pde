import processing.opengl.*;
import objloader.*;
import lumarca.lineMap.*;
import lumarca.util.*;
import lumarca.obj.*;
import lumarca.lib.*;
import processinglib.*;
import lumarca.action.*;
import template.library.*;

LumarcaLibrary lumarca;
Shape shape;

void setup() {

  ProcessingObject.setPApplet(this);

  size(1024, 768 * 2, OPENGL);

  lumarca = new LumarcaLibrary(this, 256, false);

  shape = new ObjFile(this, lumarca.lib.lineMap.midPosition.clone(), 
  new Coord(1, 0, 0), "sphere.obj", 
  1.5);
}

void draw() {
//   lumarca.drawWireFramce(shape);
   lumarca.drawShape(new PVector(1, 1, 0), shape);
}
