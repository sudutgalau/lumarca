package lumarca.obj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import lumarca.util.Coord;
import lumarca.util.ProcessingObject;


public class Obj extends ProcessingObject {

	public static double maxDepth = -1000;

	public List<List<Double>> newDepthGrid;
	
	public Coord[][] objCoords = new Coord[61][61];
	
	public Obj(String fileName) {

		List<List<Double>> newDepthGrid = new ArrayList<List<Double>>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String str = in.readLine();

			int length = Integer.parseInt(str);

			for (int i = 0; i < length; i++) {

				str = in.readLine();
				String[] depths = str.split(",");

				List<Double> depthRow = new ArrayList<Double>();

				for (String depth : depths) {

					if (!depth.equals("")) {

						double thisDepth = new Double(depth);
						depthRow.add(new Double(thisDepth));

						if (thisDepth > maxDepth) {
							maxDepth = thisDepth;
						}
					}
				}

				newDepthGrid.add(depthRow);

				System.out.println("Loaded Row: " + i);

			}

			System.out.println("DONE!!!");

			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}



	public static List<List<Double>> loadFile(String fileName) {

		List<List<Double>> newDepthGrid = new ArrayList<List<Double>>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String str = in.readLine();

			int length = Integer.parseInt(str);

			for (int i = 0; i < length; i++) {

				str = in.readLine();
				String[] depths = str.split(",");

				List<Double> depthRow = new ArrayList<Double>();

				for (String depth : depths) {

					if (!depth.equals("")) {

						double thisDepth = new Double(depth);
						depthRow.add(new Double(thisDepth));

						if (thisDepth > maxDepth) {
							maxDepth = thisDepth;
						}
					}
				}

				newDepthGrid.add(depthRow);

				System.out.println("Loaded Row: " + i);

			}

			System.out.println("DONE!!!");

			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return newDepthGrid;

	}
}