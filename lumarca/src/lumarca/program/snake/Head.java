package lumarca.program.snake;

/**
 * This file is part of the Snake program for the Lumarca project.
 * By Elie Zananiri, 2009, http://www.silentlycrashing.net/
 */

import lumarca.util.*;

public class Head extends Section {
    
    private Coord target;
    
    public Head(Snake s) {
        super(s, 500, 300);
        target = new Coord();
        findNewTarget();
    } 

    public void move() {
        prev.x = center.x;
        prev.y = center.y;

        // calculate the position of the mouse 
        // in the grid using integer division
        /*int mX = pApplet.mouseX/SIZE;
        mX = mX*SIZE;
        int mY = (pApplet.mouseY - 750)/SIZE;
        mY = mY*SIZE;*/

        if (center.x < target.x) {
            center.x += SIZE;
        } else if (center.x > target.x) {
            center.x -= SIZE; 
        }

        if (center.y < target.y) {
            center.y += SIZE;
        } else if (center.y > target.y) {
            center.y -= SIZE;
        }
        
        if (center.z < target.z) {
            center.z += SIZE;
        } else if (center.z > target.z) {
            center.z -= SIZE;
        }
        
        if (center.x == target.x && center.y == target.y && center.z == target.z) {
            findNewTarget();
            snake.addSection();
        }
    }
    
    public void findNewTarget() {
        int tX = (int)(pApplet.random(1) * 1024f) / SIZE;
        tX = tX*SIZE;
        int tY = (int)(pApplet.random(1) * 600f) / SIZE;
        tY = tY*SIZE;
        int tZ = (int)(pApplet.random(1) * 600f) / SIZE;
        tZ = tZ*SIZE;
        
        target.x = tX;
        target.y = tY;
        target.z = tZ;
    }
}
