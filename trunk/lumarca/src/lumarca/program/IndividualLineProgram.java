package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;


public class IndividualLineProgram extends LineProgram {

	private int lineNum = 0;
	
	public IndividualLineProgram(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void display(GL gl) {
			
			lumarca.lineMap.draw3dPointOnZ(gl, lineNum);

			lumarca.lineMap.draw3dPointOnY(gl, lineNum);

			lumarca.lineMap.drawVertLines(gl, lineNum);
			
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed() {
		if(lumarca.key == 'n')
			lineNum++;
		if(lumarca.key == 'b')
			lineNum--;
		
		if(lineNum >= lumarca.LINE)
			lineNum = 0;
		else if(lineNum < 0)
			lineNum = lumarca.LINE - 1;
		

		System.out.println(lineNum);
	}

}
