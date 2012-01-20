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
float waveRot = 0.0f;

void setup() {

  ProcessingObject.setPApplet(this);

  size(1024, 768 * 2, OPENGL);

  lumarca = new LumarcaLibrary(this, 256, false);
}

void draw() {

  waveRot += 0.001f;

  Line[] lines = lumarca.getLines();

  for (int lineNum = 0; lineNum < lines.length; lineNum++) {

    PVector coord = lines[lineNum].bottom;

    lumarca.drawLine(
    new PVector(0, 0, 1), 
    new PVector(coord.x, coord.y
      - PApplet.sin(lumarca.lib.lineMap.projectorX
      - coord.x / 100 + waveRot * 50)
      * 150 - 200, coord.z), 
    new PVector(coord.x, coord.y
      - PApplet.sin(lumarca.lib.lineMap.projectorX
      - coord.x / 100 + waveRot * 50)
      * 150 - 150, coord.z));
  }
}