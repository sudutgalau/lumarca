package lumarca;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import lumarca.lineMap.LineMap;
import lumarca.program.BounceProgram;
import lumarca.program.CalabProgram;
import lumarca.program.CamProgram;
import lumarca.program.ColorBounceProgram;
import lumarca.program.ColorVortexProgram;
import lumarca.program.GameProgram;
import lumarca.program.IndividualLineProgram;
import lumarca.program.LineProgram;
import lumarca.program.ObjProgram;
import lumarca.program.OtherSnakeProgram;
import lumarca.program.SnakeProgram;
import lumarca.program.VortexProgram;
import lumarca.program.WaveProgram;
import lumarca.program.WaveProgram2;
import lumarca.program.addons.FFTVis;
import lumarca.program.addons.MudTub;
import lumarca.program.addons.PerlinCloudProgram;
import lumarca.program.hear.HearProgram;
import lumarca.program.show.Gizmodo;
import lumarca.program.show.ShowProgram;
import lumarca.util.ProcessingObject;
import processing.core.PApplet;
import processing.opengl.PGraphicsOpenGL;
import processing.video.Capture;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

//DRAW TIPS IN WHITE, LINES IN COLOR

public class Lumarca extends PApplet {


	public final static int LINE = 256; // number of lines in contraption
	
	public LineMap lineMap;
	
	public static float WIN_WIDTH = 1024f;
	public static float WIN_HEIGHT = 768 * 2;

	public static float DEFAULT_CAMERA_X = WIN_WIDTH / 2.0f;
	public static float DEFAULT_CAMERA_Y = 0; //WIN_HEIGHT / 5.0f;
	public static float DEFAULT_CAMERA_Z = (WIN_HEIGHT / 2.0f)
		/ tan(PI * 60.0f / 360.0f);

	private boolean moveCamera = false;
	
	private LineProgram currentProgram;
	
	private float cameraRot = 0.0f;
	
	AudioPlayer player;
	public static Minim minim;
	
	public boolean useCamera = false;
	public static Capture camera;
	private final static int captureWidth = 320;
	private final static int captureHeight = 240;
	
	private GLU glu = new GLU();
	
	private static LineProgram iwp;
	
	private enum PROGRAM {
		CALAB, WAVE, WAVE2, SHOW, GAME, ONE_WIRE, 
		OBJ_PROGRAM, TIMER, BOUNCE, CAM, MUDTUB, VORTEX, SOUND;
		LineProgram eval(Lumarca lumarca) {
			switch (this) {
			case CALAB:
				return new CalabProgram(lumarca);
			case WAVE:
				return new WaveProgram(lumarca);
			case WAVE2:
				return new WaveProgram2(lumarca);
			case SHOW:
				return new ShowProgram(lumarca);
			case GAME:
				return new GameProgram(lumarca);
			case ONE_WIRE:
				return iwp;
			case OBJ_PROGRAM:
				return new ObjProgram(lumarca);
			case TIMER:
				return new HearProgram(lumarca);
			case BOUNCE:
				return new BounceProgram(lumarca, camera);
			case CAM:
				return new CamProgram(lumarca, camera);
			case MUDTUB:
				return new MudTub(lumarca);
			case VORTEX:
				return new ColorVortexProgram(lumarca);
			case SOUND:
				return new FFTVis(lumarca);
			default:
				return new CalabProgram(lumarca);
			}
		}
	}

	private static final String myClassName = getQualifiedClassName();

	static public void main(String _args[]) {  
//		PApplet.main(new String[] {  "--present", myClassName });
		PApplet.main(new String[] {  myClassName });
	}

	public static String getQualifiedClassName() {
		return new Exception().getStackTrace()[1].getClassName();
	}
	
	public void init() {
		
		frame.dispose(); 
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
        GraphicsDevice displayDevice=ge.getDefaultScreenDevice(); 
        frame.setUndecorated(true); 
        displayDevice.setFullScreenWindow(frame); 
        Dimension fullscreen = frame.getSize(); 
		
		super.init();
	}
	
	public void setup() {
		
		Capture.list();
		
		iwp = new IndividualLineProgram(this);
		
		minim = new Minim(this);
		
		ProcessingObject.setPApplet(this); 
		
		size((int)WIN_WIDTH, (int)WIN_HEIGHT, OPENGL);
		
		frame.setLocation(0, (int)-WIN_HEIGHT/2);

		if(useCamera){
			
			for(String name: Capture.list()){
				System.out.println(name);
			}

			camera = new Capture(this, captureWidth, captureHeight, "IIDC FireWire Video");
		}
		
		background(0);
		
		lineMap = new LineMap(LINE, DEFAULT_CAMERA_X, DEFAULT_CAMERA_Y, DEFAULT_CAMERA_Z, "testMap" + LINE +".txt");

		currentProgram = new SnakeProgram(this);
		currentProgram = new WaveProgram(this);
		currentProgram = new CalabProgram(this);
		
		currentProgram = new Gizmodo(this);
	}


	public void moveCamera() {
		if (moveCamera){
			
			camera(DEFAULT_CAMERA_X * sin(PI/2f + cameraRot),
//					DEFAULT_CAMERA_Y * cos(PI/2f + cameraRot), 
					DEFAULT_CAMERA_Y + -100 * cameraRot,
					DEFAULT_CAMERA_Z + cameraRot, 
					DEFAULT_CAMERA_X, 
					DEFAULT_CAMERA_Y, 
					0, 
					0, 1, 0);
			
//			camera(sin(cameraRot) * 1000, // eye
//					0f, 
//					cos(cameraRot) * 1000,
//
//					width/2.0f, height/2.0f, 250f, // Scene
//
//					0, 1, 0); // Orientation
			
//			camera(sin(cameraRot) * 1000, // eye
//					0f, 
//					cos(cameraRot) * 1000,
//
//					width/2.0f, height/2.0f, 250f, // Scene
//
//					0, 1, 0); // Orientation

			
			cameraRot += 0.005f;
		} else{
			cameraRot = 0.0f;
			camera(DEFAULT_CAMERA_X, DEFAULT_CAMERA_Y, DEFAULT_CAMERA_Z,
					DEFAULT_CAMERA_X, 
					DEFAULT_CAMERA_Y, 
					0, 
					0, 1, 0);
		}
		

	    glu.gluPerspective( 5.0, (float)width / (float)height, 1.0, 1000.0 );
	}

	public void draw() {
		background(0);
		
		noCursor();
		
		PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;

		GL gl = pgl.beginGL();

		currentProgram.update();
		currentProgram.display(gl);


		moveCamera();
		
		pgl.endGL();
	}

	public void mousePressed() {
		cameraRot++;

		currentProgram.mousePressed();
	}

	public void keyPressed() { // adjust globe[] accordingly

		if (key == ' ')
			moveCamera = !moveCamera;
		
		currentProgram.keyPressed();
		
		LineProgram cp = currentProgram;
		
		switch (key) {
		case '1':
			currentProgram = PROGRAM.CALAB.eval(this);
			break;
		case '2':
			currentProgram = PROGRAM.ONE_WIRE.eval(this);
			break;
		case '3':
			currentProgram = PROGRAM.WAVE.eval(this);
			break;
		case '4':
			currentProgram = PROGRAM.GAME.eval(this);
			break;
		case '5':
			currentProgram = PROGRAM.SHOW.eval(this); 
			break;
		case '6':
			currentProgram = PROGRAM.WAVE2.eval(this);
			break;
		case '7':
			currentProgram = PROGRAM.OBJ_PROGRAM.eval(this);
			break;
		case '8':
			currentProgram = PROGRAM.TIMER.eval(this);
			break;
		case '9':
			currentProgram = PROGRAM.BOUNCE.eval(this);
			break;
		case '0':
			currentProgram = PROGRAM.CAM.eval(this);
			break;
		case '-':
			currentProgram = PROGRAM.MUDTUB.eval(this);
			break;
		case '=':
			currentProgram = PROGRAM.VORTEX.eval(this);
			break;
		case '`':
			currentProgram = PROGRAM.SOUND.eval(this);
			break;
		default:
			break;
		}
		
		if(cp != currentProgram){
			cp.exit();
		}
	}

}
