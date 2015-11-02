# Introduction #

This guide will walk you through how to get started with the Lumarca library and run the basic content it comes with.


# Details #

**Step 1 - Download and setup the Lumarca library**:

Using SVN download the library.  If you're using Eclipse, your classpath should be all set up for you.  If you are not, you will need to add all the libraries in the "lib" dir to your classpath and set the "lib" directory for your native library folder for openGL.jar and jogl.jar.


**Step 2 - Compile the code and run**:

Compile the code and run the lumarca.Lumarca class as a Java Application.  The program will launch in fullscreen mode, but with the actual app only taking up 1024x768.  It will start running the Construction Program.  The different content programs will be explained in Step 4.


**Step 3 - Using Simulation Mode**:

By default, the Lumarca library is in Projection Mode.  This means it will display the image that is meant to be projected on the Lumarca.  Press space bar toggle into Simulation Mode for any Content Program.  This will shift your view into a simulation of what the content will like on the Lumarca structure.  Press space bar again to toggle back to projection mode.

While in simulation mode, click the mouse in any mode to skip ahead in the camera rotation.

**Step 4 - Running different Content Programs**:

There are several Content Programs in the Lumarca library.  To activate these programs, press the number keys.  Here's a list of what programs are mapped to which keys:

1: This is the Construction Program.  It will show all the lines, lit up green.  There will be dots displayed at the bottom of each string, to indicate where to place the string.  For a more detailed explaination of how to use this program, go to www.madparker.com/lumarca/construction

2: This is the Calibration Program.  It will display 1 line, lit up.  Press "n" to go to the next string.  Press "b" to go back a string.  This program will be used to calibrate each string in the lumarca.  For a more detailed explaination of how to use this program, go to www.madparker.com/lumarca/construction

3:  This is a sin wave program.

4:  This is a bouncing ball program.

5:  This is a series of different images, each lasting for 10 seconds.

6:  This is another sign wave program, but with 2 sin waves.

7:  This is an object program.  It shows how to display 3D objects on the Lumarca.  Press any of the letter keys to see that letter rendered.  Press capital 'S' to see the Smiley Face.

8:  This is a boat that rocks back and forth.

9:  This is a game, that uses camera input.  You must have a camera attached to your computer to use this program.  It is similar to the game Break Out, but in 3d.

0:  This displays camera input, extruded over depth, onto the lumarca.  Press any buttom to reset the background for background subtraction.

**Step 5 - Time to explore**:

Now that you've seen some of what the lumarca can do, try looking at some of these programs and making your own content.  Look in the lumarca.program package to see the source code for these examples.

Enjoy and Good Luck!