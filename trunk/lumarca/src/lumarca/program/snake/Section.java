package lumarca.program.snake;

/**
 * This file is part of the Snake program for the Lumarca project.
 * By Elie Zananiri, 2009 - http://www.silentlycrashing.net/
 */

import javax.media.opengl.*;
import lumarca.lineMap.*;
import lumarca.util.*;
import lumarca.obj.*;

public class Section extends Rectangle {
    
    public final static int SIZE = 100;
    
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
        // place new Section at its sibling's previous position
        super(sibling.getCenter().clone(), SIZE, SIZE, SIZE);
        prev = center.clone();
        setColor(new Coord(1f, 0, 0));
        
        snake = sibling.snake;
        this.sibling = sibling;

    } 

    public void move() {
        // remember the previous position
        prev.x = center.x;
        prev.y = center.y;
        prev.z = center.z;

        // follow sibling, but only if it moved
        if (sibling.moved()) {
            center.x = sibling.prev.x;
            center.y = sibling.prev.y;
            center.z = sibling.prev.z;
        }
    }

    public void draw(GL gl, LineMap lineMap) {
        lineMap.drawShape(gl, color, this);
    }

    public boolean moved() {
        return (prev.x != center.x || prev.y != center.y || prev.z != center.z); 
    }
}
