package lumarca.program.addons;



import java.net.SocketException;


import javax.media.opengl.GL;


import lumarca.LumarcaPRO;

import lumarca.lineMap.Line;

import lumarca.lineMap.LineMap;

import lumarca.program.LineProgram;

import lumarca.util.Coord;

import lumarca.util.Util;


import java.util.Vector;

import com.illposed.osc.*;


public class MudTub extends LineProgram{

    

 

public OSCPortIn receiver;

public float[] tubData = new float[192];

    public Thread thread;

    public MudTub(LumarcaPRO lumarca) {

    super(lumarca);

    

    for(int i = 0; i < 192; i++){

    tubData[i] = 0;

    }

    

        thread = new Thread() {

        

            public void run() {
            	
            System.out.println("CALLED");

                try {

                	receiver = new OSCPortIn(9001);

               OSCListener listener = new OSCListener() {

                public void acceptMessage(java.util.Date time, OSCMessage message) {

                Object[] args = message.getArguments();

                System.out.print("Message received!");

                for(int i = 0; i < 192; i++){

                tubData[i] = ((Float)args[i]).floatValue();

                }

                }

                };


            receiver.addListener("/fft", listener);

            receiver.startListening();

           } catch (Exception e) { 

           e.printStackTrace(); 

           }

        }

        };

        

        thread.run();


    } 


    public void addSection() {

 

    }


    public void move() {

   

    }

    

public void update(){


}

public void display(GL gl){

    

    for(int lineNum = 0; lineNum < lumarca.lineMap.lines.length; lineNum++){

    Line line = lumarca.lineMap.lines[lineNum];

    

    float percent = lineNum/256f;


    float x = 15 - lumarca.floor((line.bottom.x/lumarca.lineMap.maxPosition.x) * 15f);

    float y = 11- lumarca.floor((line.top.z/lumarca.lineMap.maxPosition.z) * 11f);

    

    int position = lumarca.floor(y * 16 + x) ;

    

//    System.out.println("x: " + x + " y: " + y + " : " + lumarca.lineMap.minPosition.y + " : " + lumarca.lineMap.maxPosition.y);

    

    float val = tubData[position];

    

    Coord modCord = line.top.clone();

    modCord.y = 500 * val;

    

//    System.out.println(modCord.y + " = " + val + " : " + percent * 16 + " : " + percent);

    

//    Util.drawLine(gl, new Coord(0.5f,0.3f,0.2f), 
    Util.drawLine(gl, new Coord(1.0f,0.0f,0.0f), 

    line.top, modCord);

    

    // fftData[i] * ymax

    }

}

public void keyPressed(){

}

public void mousePressed(){

}

}