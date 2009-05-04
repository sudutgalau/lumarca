package lumarca.program.snake;

/**
 * This file is part of the Snake program for the Lumarca project.
 * By Elie Zananiri, 2009, http://www.silentlycrashing.net/
 */

import lumarca.lineMap.*;
import lumarca.util.*;
import lumarca.obj.*;

import javax.media.opengl.GL;

public class Section extends Rectangle {
    public final static int SIZE = 100;
    public static float CURRCOLOR = 1f;

    // previous coords
    protected Coord prev;

    // head sibling (before in line)
    protected Section sibling;
    
    // parent snake
    protected Snake snake;

    // Head constructor
    public Section(Snake s, int x, int y) {
        super(new Coord(x, y, 200), SIZE, SIZE, SIZE);
        prev = center.clone();
        setColor(new Coord(1f, 1f, 1f));

        snake = s;
        // Head has no Section before it
        sibling = null;
    }

    // default constructor
    public Section(Section sibling) {
        // place new Section at its sibling's
        // previous position
        super(sibling.getCenter().clone(), SIZE, SIZE, SIZE);
        prev = center.clone();
        setColor(new Coord(pApplet.random(1), pApplet.random(1), pApplet.random(1)));
        //CURRCOLOR -= .05f;
        
        snake = sibling.snake;
        this.sibling = sibling;

    } 

    public void move() {
        // remember previous coordinates
        prev = center.clone();

        if (sibling.moved()) {
            // only move if sibling moved
            center.x = sibling.prev.x;
            center.y = sibling.prev.y;

            //shape.x = x;
            //shape.y = y;
        }
    }

    public void draw(GL gl, LineMap lineMap) {
        lineMap.drawShape(gl, color, this);
        //pApplet.rect(x, y, SIZE, SIZE); 
    }

    public boolean moved() {
        return (prev.x != center.x || prev.y != center.y); 
    }
}
