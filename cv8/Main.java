package cv8;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * The program assert point to its cluster with train data
 * @author Lukas Runt
 * @version 1.0 (14-04-2022)
 */
public class Main {
	/** Centers of cluster of train data*/
	public static Point[] centers;
	/**Points of train data*/
	public static Point[] points;
	/** Poit which will be assigned*/
	public static Point point = new Point(25, 20, 3);
	
	/**
	 * Method saves train data
	 * @param name of the file 
	 */
	public static void importTrainData(String fileName) {
		try {
			Scanner sc = new Scanner(Paths.get(fileName));
			String in;
			in = sc.nextLine();
			String[] splitted = in.split(" ");
			int numberOfCenters = Integer.parseInt(splitted[0]);
			centers = new Point[numberOfCenters];
			for(int i = 0; i < numberOfCenters; i++) {
				in = sc.nextLine();
				splitted = in.split(";");
				centers[i] = new Point(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[0]));
			}
			in = sc.nextLine();
			splitted = in.split(" ");
			int numberOfPoints = Integer.parseInt(splitted[0]);
			points = new Point[numberOfPoints];
			for(int i = 0; i < numberOfPoints; i++) {
				in = sc.nextLine();
				splitted = in.split(";");
				points[i] = new Point(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[0]));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method assert point to nearest cluster (point in cluster)
	 * @param point point which will be assigned
	 * @return nearest point to input point
	 */
	public static Point nearestNeighbor(Point point) {
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < points.length; i++) {
			double distance = Main.euclideanDistance(points[i], point);
			if(min > distance) {
				index = i;
				min = distance;
			}
		}
		//point.center = points[index].center;
		System.out.println("The point was assigned to cluster: " + points[index].center);
		return points[index];
	}
	
	/**
	 * Method assert point to nearest cluster (k points in cluster)
	 * @param point point which will be assigned to cluster
	 * @param k number of nearest points, odd number
	 * @param numberOfClusters number of clusters in train data
	 * @return array of k nearest points 
	 */
	public static Point[] kNearestNeighbor(Point point, int k, int numberOfClusters) {
		double[] min = new double[k];
		Point[] nernest = new Point[k];
		List<PointDistance> distances = new ArrayList<>();
		int clusters[] = new int[numberOfClusters];
		for(int i = 0; i < min.length; i++) {
			min[i] = Double.MAX_VALUE;
		}
		for(int i = 0; i < points.length; i++) {
			double distance = Main.euclideanDistance(points[i], point);
			distances.add(new PointDistance(points[i], distance));
		}
		Collections.sort(distances);
		for(int i = 0; i < k; i++) {
			nernest[i] = distances.get(i).point;
			clusters[distances.get(i).point.center]++;
		}
		int cluster = 0;
		int number = 0;
		for(int i = 0; i < clusters.length; i++) {
			if(clusters[i] > number) {
				cluster = i;
				number = clusters[i];
			}
		}
		System.out.println("The point was assigned to cluster: " + cluster);
		return nernest;
	}
	
	/**
	 * Method assert point to cluster by distance to center of cluster
	 * @param point point which will be assigned
	 * @return center which is nearest
	 */
	public static Point centerMethod(Point point) {
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < centers.length; i++) {
			double distance = Main.euclideanDistance(centers[i], point);
			if(min > distance) {
				index = i;
				min = distance;
			}
		}
		System.out.println("The point was assigned to cluster: " + index);
		return centers[index];
	}
	
	/**
	 * Method count average distance for every cluster and assigns a point
	 * @param point point which will be assigned
	 * @return number of cluster to which was point assigned
	 */
	public static int averageDistance(Point point) {
		double[] distances = new double[centers.length];
		int[] number = new int[centers.length];
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < points.length; i++) {
			distances[points[i].center] += Main.euclideanDistance(points[i], point);
			number[points[i].center]++;
		}
		for(int i = 0; i < distances.length; i++) {
			double distance = distances[i]/number[i];
			System.out.println("Average distacne form cluster: " + i + " is: " + distance);
			if(distance < min) {
				min = distance;
				index = i;
			}
		}
		System.out.println("The point was assigned to cluster: " + index);
		return index;
	}
	
	/**
	 * Method counts euclidean distance between two points
	 * @param a point one
	 * @param b point two
	 * @return euclidiean distance
	 */
	public static double euclideanDistance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	public static void main(String[] args) {
		//Vytvoreni okna
				JFrame okno = new JFrame();
				okno.setTitle("UIR");
				okno.setSize(640, 480);
				DrawingPanel panel = new DrawingPanel();
				
				okno.add(panel);//pridani komponenty
				okno.pack(); //udela resize okna dle komponent
				
				okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
				okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
				okno.setVisible(true);
				
	}

}
