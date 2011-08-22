package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;


public class CalabProgram extends LineProgram {

	public CalabProgram(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void display(GL gl) {

		
		for (int lineNum = 0; lineNum < lumarca.lineMap.lines.length; lineNum++) {
			
			//line from Camera to Y points
//			lumarca.lineMap.drawIntersectLine(gl, lineNum);

			//Z Points
			lumarca.lineMap.draw3dPointOnZ(gl, lineNum);

			//Y Points
			lumarca.lineMap.draw3dPointOnY(gl, lineNum);
//			
//
			lumarca.lineMap.drawVertLines(gl, lineNum);
			
		}

		lumarca.lineMap.drawFloorBox(gl);

	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

}
