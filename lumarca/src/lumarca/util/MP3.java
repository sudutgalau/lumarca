package lumarca.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javazoom.jl.player.Player;


public class MP3 {
    private String filename;
    private Player player; 
    public Thread thread;

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        this.filename = filename;
    }

    public void close() { if (player != null) player.close(); }

    // play the MP3 file to the sound card
    public Player play() {
        try {

    	    InputStream instream = getClass().getResourceAsStream(filename);
    	    InputStreamReader infile = new InputStreamReader(instream);

            BufferedInputStream bis = new BufferedInputStream(instream);
            player = new Player(bis);
        	
//            FileInputStream fis     = new FileInputStream(filename);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
            e.printStackTrace();
        }

        // run in new thread to play in background
        thread = new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        };
        
        thread.start();
        
        return player;
    }
}