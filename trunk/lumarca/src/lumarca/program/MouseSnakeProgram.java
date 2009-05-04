package lumarca.program;

/**
 * This file is part of the Snake program for the Lumarca project.
 * By Elie Zananiri, 2009, http://www.silentlycrashing.net/
 */

import javax.media.opengl.*;
import lumarca.*;
import lumarca.program.snake.*;

public class MouseSnakeProgram extends LineProgram {

    Snake snake;
    public static boolean drawMesh = true;
	
	public MouseSnakeProgram(Lumarca lumarca) {
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
		
		/*for (int lineNum = 0; lineNum < lumarca.LINE; lineNum++) {
			
			Coord coord = lumarca.lineMap.lines[lineNum].bottom;
			
			Util.drawLine(
						gl,
						new Coord(coord.x/1024f, 1f, 1024f - coord.x/1024f),
						new Coord(coord.x, 
								coord.y - PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 250,
								coord.z),
						new Coord(coord.x, 
													
								coord.y - 
								PApplet.sin(lumarca.lineMap.projectorX- coord.x / 100 + waveRot * 50) * 150 - 200,
								
								coord.z));

		}*/
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub
	    drawMesh = !drawMesh;
	}

	@Override
	public void mousePressed() {
	    snake.addSection(); 

	}

}
