package lumarca.program;

import javax.media.opengl.GL;

import lumarca.Lumarca;
import lumarca.lineMap.Line;
import lumarca.util.Coord;


public class IndividualLineProgram extends LineProgram {

	private int lineNum = 0;

	private int lineNum2 = lumarca.LINE - 1;
	
	public IndividualLineProgram(Lumarca lumarca) {
		super(lumarca);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void display(GL gl) {

//		lumarca.lineMap.drawFloorBox(gl);

		lumarca.lineMap.draw3dPointOnZ(gl, lineNum);
//
		lumarca.lineMap.draw3dPointOnY(gl, lineNum);

		lumarca.lineMap.drawVertLines(gl, lineNum);

		lumarca.lineMap.draw3dPointOnZ(gl, lineNum2);
//
		lumarca.lineMap.draw3dPointOnY(gl, lineNum2);

		lumarca.lineMap.drawVertLines(gl, lineNum2);
//			
//			Line line = lumarca.lineMap.lines[lineNum];
//			
//			
//			pApplet.line(line.bottom.x, line.bottom.yIntersect, 0, 0);
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed() {
		if(lumarca.key == 'b')
			lineNum++;
		if(lumarca.key == 'n')
			lineNum--;
		
		if(lineNum >= lumarca.LINE)
			lineNum = 0;
		else if(lineNum < 0)
			lineNum = lumarca.LINE - 1;
		
		if(lumarca.key == '.')
			lineNum2--;
		if(lumarca.key == ',')
			lineNum2++;
		
		if(lineNum2 >= lumarca.LINE)
			lineNum2 = 0;
		else if(lineNum2 < 0)
			lineNum2 = lumarca.LINE - 1;

		if(lumarca.key == 'a'){
			lumarca.lineMap.lines[lineNum] = lumarca.lineMap.makeNewLine(lineNum, +10);
		}
		if(lumarca.key == 'z'){
			lumarca.lineMap.lines[lineNum] = lumarca.lineMap.makeNewLine(lineNum, -10);
		}
		

		System.out.println("LINENUM : " + lineNum);
		
		System.out.println(lumarca.lineMap.lines[lineNum].bottom);
		

		System.out.println("LINENUM2: " + lineNum2);
		System.out.println(lumarca.lineMap.lines[lineNum2].bottom);
	}

}
