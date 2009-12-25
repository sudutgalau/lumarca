package lumarca.program.addons;

import javax.media.opengl.GL;

import lumarca.LumarcaPRO;
import lumarca.program.LineProgram;
import lumarca.util.Coord;
import lumarca.util.Util;
import processing.core.PApplet;


public class PerlinCloudProgram extends LineProgram {

       private float cloudPos = 0.0f;
       
       public PerlinCloudProgram(LumarcaPRO lumarca) {
               super(lumarca);
       }

       @Override
       public void update() {
               cloudPos += 0.05f;
       }

       @Override
       public void display(GL gl) {
               
               for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
                       
                       Coord coord = lumarca.lineMap.lines[lineNum].bottom;

                       //Util.drawPoint(gl, coord);
                       for (int i = 0; i < 200; i++) {
                               float noiseVal = pApplet.noise(coord.x/100, (coord.y - i)/100, cloudPos + coord.z/100);
                               if (noiseVal > 0.5) {
                                      Util.drawLineNoDots(gl, new Coord(1f,0.5f,1f),
                                                       new Coord(coord.x, coord.y - (i*2), coord.z),
                                                       new Coord(coord.x, coord.y - (i*2) + 1, coord.z));
                               }
                       }

               }
       }

       @Override
       public void keyPressed() {
               // TODO Auto-generated method stub

       }

       @Override
       public void mousePressed() {
               // TODO Auto-generated method stub

       }

}