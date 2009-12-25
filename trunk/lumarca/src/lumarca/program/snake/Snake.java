package lumarca.program.snake;

/**
 * This file is part of the Snake program for the LumarcaPRO project.
 * By Elie Zananiri, 2009 - http://www.silentlycrashing.net/
 */

import javax.media.opengl.GL;

import lumarca.lineMap.LineMap;
import lumarca.program.OtherSnakeProgram;

public class Snake {
    
    public static final int MAX_SECTIONS = 20;

    private Section[] sections;
    private int numSections;

    public Snake() {
        sections = new Section[MAX_SECTIONS];
        
        // create the Head
        sections[0] = new Head(this);
        numSections = 1;
    } 

    public void addSection() {
        if (numSections < MAX_SECTIONS) {
            sections[numSections] = new Section(sections[numSections-1]);
            numSections++;
        } else {
            numSections = 1;
        }
    }

    public void move() {
        for (int i=0; i < numSections; i++) {
            sections[i].move(); 
        }
    }

    public void draw(GL gl, LineMap lineMap) {
        for (int i=0; i < numSections; i++) {
            if (OtherSnakeProgram.drawMesh) sections[i].drawWireFrame(gl);
            sections[i].draw(gl, lineMap); 
        }
    }
}
