package lumarca.program;

/**
 * This file is part of the Snake program for the Lumarca project.
 * By Elie Zananiri, 2009 - http://www.silentlycrashing.net/
 */

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.program.snake.Snake;

public class OtherSnakeProgram extends LineProgram {

    Snake snake;
    public static boolean drawMesh = true;
	
	public OtherSnakeProgram(Lumarca lumarca) {
		super(lumarca);
		lumarca.frameRate(5);
		snake = new Snake();
	}

	@Override
	public void update() {
	    snake.move();
	}

	@Override
	public void display(GL gl) {
	    snake.draw(gl, lumarca.lineMap);
	}

	@Override
	public void keyPressed() {
	    drawMesh = !drawMesh;
	}

	@Override
	public void mousePressed() {
	    snake.addSection(); 
	}
	
	@Override
    public void exit() {
        pApplet.frameRate(60); 
    }

}
