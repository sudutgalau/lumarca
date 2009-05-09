/*
 * Alias .obj loader for processing
 * programmed by Tatsuya SAITO / UCLA Design | Media Arts 
 * Created on 2005/04/17
 *
 * 
 *
 */

package objloader;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

import lumarca.util.Coord;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * 
 * @author tatsuyas
 * 
 * TODO:
 * 
 */

public class OBJModel {

	// global variables
	public Vector<Coord> vertexes = new Vector<Coord>(); // vertexes

	Vector<Coord> texturev = new Vector<Coord>(); // texture coordinates

	public Vector<Coord> normv = new Vector<Coord>();

	Hashtable<String, Material> materials = new Hashtable<String, Material>();

	Hashtable<String, Group> groups = new Hashtable<String, Group>();

	public Vector<ModelSegment> modelSegments = new Vector<ModelSegment>();

	String objName = "default";

	String defaultMaterialName = "default";

	Group defaultGroup = new Group("default");

	ModelSegment defaultModelSegment = new ModelSegment();

	// processing variables
	PApplet parent;

	PImage texture; // texture image applied from the code.

	// runtime rendering variables
	int mode = PApplet.POLYGON; // render mode (ex. POLYGON, POINTS ..)

	boolean flagTexture = true;

	Debug debug;

	public OBJModel(PApplet parent) {
		this.parent = parent;
		parent.registerDispose(this);

		debug = new Debug(parent);
		debug.enabled = true;
	}

	public void pre() {
		// do something cool
	}

	public void draw() {
		drawModel();
	}

	public void size(int w, int h) {

	}

	public void post() {
	}

	public void mouse(MouseEvent event) {
	}

	public void key(KeyEvent e) {
	}

	public void dispose() {
	}

	public void showModelInfo() {

		debug.println("Obj Name: " + objName);
		debug.println("");
		debug.println("\tV  Size: " + vertexes.size());
		debug.println("\tVt Size: " + texturev.size());
		debug.println("\tVn Size: " + normv.size());
		debug.println("");
		debug.println("\tG  Size: " + groups.size());
		debug.println("\tS Size: " + modelSegments.size());

	}

	public void disableTexture() {
		flagTexture = false;
	}

	public void enableTexture() {
		flagTexture = true;
	}

	public void texture(PImage tex) {
		/*
		 * 
		 * try { PImage image = (PImage) tex.clone(); } catch (Exception e) { }
		 * if (image.width > image.height){ }
		 */
		texture = tex;

	}

	public void drawModel() {
		try {
			Coord v = null, vt = null, vn = null;
			int vtidx = 0, vnidx = 0, vidx = 0;
			Material mtl = null;
			// render all triangles

			for (int s = 0; s < modelSegments.size(); s++) {

				boolean bTexture = true;

				ModelSegment tmpModelSegment = (ModelSegment) modelSegments
						.elementAt(s);

				mtl = (Material) materials.get(tmpModelSegment.mtlName);
				if (mtl == null) // if the material is not assigned for some
									// reason, it uses the default material
									// setting
				{
					mtl = (Material) materials.get(defaultMaterialName);
					debug.println("Material '" + tmpModelSegment.mtlName
							+ "' not defined");
				}

				parent.fill(255.0f * mtl.Ka[0], 255.0f * mtl.Ka[1],
						255.0f * mtl.Ka[2], 255.0f * mtl.d);

				for (int f = 0; f < tmpModelSegment.elements.size(); f++) {

					ModelElement tmpf = (ModelElement) (tmpModelSegment.elements
							.elementAt(f));
					parent.textureMode(PApplet.NORMALIZED);
					parent.beginShape(mode); // specify render mode

					if (flagTexture == false)
						bTexture = false;

					if (tmpf.tindexes.size() == 0)
						bTexture = false;

					if (mtl.map_Kd == null)
						bTexture = false;

					if (bTexture)
						if (texture != null)
							parent.texture(texture); // setting applied
														// texture
						else
							parent.texture(mtl.map_Kd); // setting texture from
														// mtl info

					if (tmpf.indexes.size() > 0) {
						for (int fp = 0; fp < tmpf.indexes.size(); fp++) {
							vidx = ((Integer) (tmpf.indexes.elementAt(fp)))
									.intValue();

							v = vertexes.elementAt(vidx - 1);
							if (v != null) {
								try {
									if (tmpf.nindexes.size() > 0) {
										vnidx = ((Integer) (tmpf.nindexes
												.elementAt(fp))).intValue();
										vn = normv.elementAt(vnidx - 1);
										parent.normal(-vn.x, -vn.y, -vn.z);
									}

									if (bTexture) {
										vtidx = ((Integer) (tmpf.tindexes
												.elementAt(fp))).intValue();

										vt = texturev.elementAt(vtidx - 1);
										parent.vertex(-v.x, -v.y, v.z,
												vt.x, 1.0f - vt.y);
									} else
										parent.vertex(-v.x, -v.y, v.z);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								parent.vertex(-v.x, v.y, v.z);
							}

						}
					}
					parent.endShape();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawMode(int mode) {
		this.mode = mode;
	}

	public BufferedReader getBufferedReader(String filename) {

		BufferedReader retval = null;

		try {

			URL url = null;
			InputStream is = null;

			/*
			 * parent.openStream(arg0); if (filename.startsWith("http://")) {
			 * try { url = new URL(filename); retval = new BufferedReader(new
			 * InputStreamReader(parent.openStream(filename))); return retval; }
			 * catch (MalformedURLException e) { e.printStackTrace(); return
			 * null; } catch (IOException ioe) { ioe.printStackTrace(); return
			 * null; } }
			 */

			is = parent.openStream(filename);
			if (is != null) {
				try {
					retval = new BufferedReader(new InputStreamReader(is));
					return retval;
				} catch (Exception ioe) {
					ioe.printStackTrace();
					return null;
				}
			}

			/*
			 * is = getClass().getResourceAsStream("/data/" + filename); if (is !=
			 * null) { try { retval = new BufferedReader(new
			 * InputStreamReader(is)); return retval; } catch (Exception ioe) {
			 * ioe.printStackTrace(); return null; } }
			 * 
			 * url = getClass().getResource("/" + filename); if (url != null) {
			 * System.out.println(url.toString()); try { url = new
			 * URL(filename); retval = new BufferedReader(new
			 * InputStreamReader(parent.openStream())); return retval; } catch
			 * (MalformedURLException e) { e.printStackTrace(); return null; }
			 * catch (IOException ioe) { ioe.printStackTrace(); return null; } }
			 * 
			 * url = getClass().getResource("/data/" + filename); if (url !=
			 * null) { System.out.println(url.toString()); try { url = new
			 * URL(filename); retval = new BufferedReader(new
			 * InputStreamReader(url .openStream())); return retval; } catch
			 * (MalformedURLException e) { e.printStackTrace(); return null; }
			 * catch (IOException ioe) { ioe.printStackTrace(); return null; } }
			 * 
			 * try { // look inside the sketch folder (if set) String location =
			 * parent.sketchPath + File.separator + "data"; File file = new
			 * File(location, filename); if (file.exists()) { retval = new
			 * BufferedReader(new FileReader(file)); return retval; } } catch
			 * (IOException e) { e.printStackTrace(); return null; } // ignored
			 * 
			 * try { File file = new File("data", filename); if (file.exists()) {
			 * retval = new BufferedReader(new FileReader(file)); return retval; } }
			 * catch (IOException ioe) { ioe.printStackTrace(); }
			 * 
			 * try { File file = new File(filename); if (file.exists()) { retval =
			 * new BufferedReader(new FileReader(file)); return retval; } }
			 * catch (IOException ioe) { ioe.printStackTrace(); return null; }
			 */

		} catch (SecurityException se) {
		} // online, whups

		parent.die("Could not find .OBJ file " + filename, null);

		return retval;
	}

	public void load(String filename) {
		parseOBJ(getBufferedReader(filename));
		this.showModelInfo();
	}

	public void parseOBJ(BufferedReader bread) {
		try {

			// adding default variables to the global data table
			// creating the default group

			groups.put("default", defaultGroup);
			Group currentGroup = defaultGroup;

			// creating the default material

			Material defaultMaterial = new Material();
			defaultMaterial.mtlName = defaultMaterialName;
			materials.put(defaultMaterialName, defaultMaterial);
			String currentMaterial = defaultMaterialName;

			// creating the default model segment

			modelSegments.add(defaultModelSegment);
			defaultModelSegment.mtlName = currentMaterial;
			currentGroup.segments.add(defaultModelSegment);
			ModelSegment currentModelSegment = defaultModelSegment;

			String line;
			while ((line = bread.readLine()) != null) {
				// debug.println(line);
				// parse the line
				String[] elements = line.split("\\s+");

				// if not a blank line, process the line.
				if (elements.length > 0) {

					// analyze the format
					if (elements[0].equals("v")) { // point vector
						Coord tmpv = new Coord();
						tmpv.x = Float.valueOf(elements[1]).floatValue();
						tmpv.y = Float.valueOf(elements[2]).floatValue();
						tmpv.z = Float.valueOf(elements[3]).floatValue();
						vertexes.add(tmpv);
					} else if (elements[0].equals("vn")) { // normal vector
						Coord tmpv = new Coord();
						tmpv.x = Float.valueOf(elements[1]).floatValue();
						tmpv.y = Float.valueOf(elements[2]).floatValue();
						tmpv.z = Float.valueOf(elements[3]).floatValue();
						normv.add(tmpv);
					} else if (elements[0].equals("vt")) {
						Coord tmpv = new Coord();
						tmpv.x = Float.valueOf(elements[1]).floatValue();
						tmpv.y = Float.valueOf(elements[2]).floatValue();
						texturev.add(tmpv);
					} else if (elements[0].equals("o")) {
						if (elements[1] != null)
							objName = elements[1];
					} else if (elements[0].equals("mtllib")) {
						if (elements[1] != null)
							this.parseMTL(this.getBufferedReader(elements[1]));
					}

					// elements that needs to consider the current context

					else if (elements[0].equals("g")) { // grouping
						// setting
						ModelSegment newModelSegment = new ModelSegment();
						modelSegments.add(newModelSegment);
						currentModelSegment = newModelSegment;
						currentModelSegment.mtlName = currentMaterial;
						
						String groupName = "";
						for (int e = 1; e < elements.length; e++)
							groupName += (" "+elements[e]);
						
							if (groups.get(groupName) == null) {
								debug.println("group '" + groupName +"'");
								Group newGroup = new Group(groupName);
								groups.put(groupName, newGroup);
							} else {
								debug.println("double declaration of a same group name.");
							}
						
					} else if (elements[0].equals("usemtl")) { // material
						// setting

						String mtlName = "";
						for (int e = 1; e < elements.length; e++)
							mtlName += (elements[e]);

						ModelSegment newModelSegment = new ModelSegment();
						modelSegments.add(newModelSegment);
						currentModelSegment = newModelSegment;
						currentModelSegment.mtlName = mtlName;
						currentMaterial = mtlName;
						
					} else if (elements[0].equals("f")) { // Element
						ModelElement tmpf = new ModelElement();
						if (elements.length < 3) {
							debug
									.println("Warning: potential model data error");
						}
						for (int i = 1; i < elements.length; i++) {
							String seg = elements[i];
							if (seg.indexOf("/") > 0) {
								String[] forder = seg.split("/");

								if (forder.length > 2) {
									if (forder[2].length() > 0)
										tmpf.nindexes.add(Integer
												.valueOf(forder[2]));
									if (forder[1].length() > 0)
										tmpf.tindexes.add(Integer
												.valueOf(forder[1]));
									if (forder[0].length() > 0)
										tmpf.indexes.add(Integer
												.valueOf(forder[0]));
								} else if (forder.length > 1) {
									if (forder[1].length() > 0)
										tmpf.tindexes.add(Integer
												.valueOf(forder[1]));
									if (forder[0].length() > 0)
										tmpf.indexes.add(Integer
												.valueOf(forder[0]));
								} else if (forder.length > 0) {
									if (forder[0].length() > 0)
										tmpf.indexes.add(Integer
												.valueOf(forder[0]));
								}
							} else {
								if (seg.length() > 0)
									tmpf.indexes.add(Integer.valueOf(seg));
							}
						}
						currentModelSegment.elements.add(tmpf);
					} else if (elements[0].equals("ll")) { // line
						ModelElement tmpf = new ModelElement();
						tmpf.iType = ModelElement.POLYGON;
						if (elements.length < 2) {
							debug
									.println("Warning: potential model data error");
						}
						for (int i = 1; i < elements.length; i++) {
							tmpf.indexes.add(Integer.valueOf(elements[i]));
						}
						currentModelSegment.elements.add(tmpf);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseMTL(BufferedReader bread) {
		try {
			String line;
			Material currentMtl = null;
			while ((line = bread.readLine()) != null) {
				// parse the line
				String elements[] = line.split("\\s+");
				if (elements.length > 0) {
					// analyze the format

					if (elements[0].equals("newmtl")) {
						debug.println("material '" + elements[1] + "'");
						String mtlName = elements[1];
						Material tmpMtl = new Material();
						currentMtl = tmpMtl;
						materials.put(mtlName, tmpMtl);
					} else if (elements[0].equals("map_Ka")
							&& elements.length > 1) {
						debug
								.println("\ttexture ambient '" + elements[1]
										+ "'");
						String texname = elements[1];
						// currentMtl.map_Ka = parent.loadImage(texname);
					} else if (elements[0].equals("map_Kd")
							&& elements.length > 1) {
						debug
								.println("\ttexture diffuse '" + elements[1]
										+ "'");
						String texname = elements[1];
						currentMtl.map_Kd = parent.loadImage(texname);
					} else if (elements[0].equals("Ka") && elements.length > 1) {
						currentMtl.Ka[0] = Float.valueOf(elements[1])
								.floatValue();
						currentMtl.Ka[1] = Float.valueOf(elements[2])
								.floatValue();
						currentMtl.Ka[2] = Float.valueOf(elements[3])
								.floatValue();
					} else if (elements[0].equals("d") && elements.length > 1) {
						currentMtl.d = Float.valueOf(elements[1]).floatValue();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Functions for addressing each group/Element/vertex */

	public int getGroupsize() {
		return this.groups.size();
	}

	public Group getGroup(String groupName) {
		return (Group) this.groups.get(groupName);
	}

	public int getVertexsize() {
		return this.vertexes.size();
	}

	public Coord getVertex(int i) {
		return vertexes.elementAt(i);
	}

	public void setVertex(int i, Coord vertex) {
		Coord tmpv = vertexes.elementAt(i);
		tmpv.x = vertex.x;
		tmpv.y = vertex.y;
		tmpv.z = vertex.z;
	}

	public void debugMode() {
		debug.enabled = true;
	}

	// added on May 29 2007 (suggestion by polymonkey)
	public void setTexture(PImage textureName) {
		texture = textureName;
	}
}
