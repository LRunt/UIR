/**
 * 
 */
package cv7;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luaks Runt
 * @version 1.0 (30-03-2022)
 */
public class Main {
	
	/** x coordinates of points*/
	public static final int[] M1 = {2, 4, 10, 12, 3, 20, 30, 11, 25};
	/** y coordinates of points*/
	public static final int[] M2 = {2, 4, 15, 18, 5, 50, 30, 34, 65};
	
	/**
	 * Methos creates array of points from two arrays of coordinates
	 * @param x array of x-coordinates
	 * @param y array of y-coordinates
	 * @return array of created points
	 */
	public static Point[] createPoints(int[] x, int[] y) {
		Point[] points = new Point[x.length];
		for(int i = 0; i < points.length; i++) {
			points[i] = new Point(x[i], y[i]);
		}
		return points;
	}
	
	/**
	 * Generator of random point
	 * @param min minimal value of possible coordinate
	 * @param max maximal value of possible coordinate
	 * @return generated point with random coordinates
	 */
	public static Point generateRandomPoint(int min, int max) {
		int x = (int) (Math.random() * (max - min + 1) + min);
		int y = (int) (Math.random() * (max - min + 1) + min);
		return new Point(x, y);
	}
	
	/**
	 * Method k-means
	 * @param centre
	 * @param points
	 */
	public static void kMeans(Point[] centre, Point[] points) {
		boolean sameCenters = false;
		List<Point>[] assignedPoints;
		Point[] prevousCenters;
		int n = 1;
		while(!sameCenters) {
			assignedPoints = assignPoints(centre, points);
			printClusters(assignedPoints, centre, n);
			prevousCenters = centre;
			centre = recalculateCenters(assignedPoints);
			sameCenters = samePoints(centre, prevousCenters);
			n++;
		}
	}
	
	/**
	 * Method implements k-methoids algorithm 
	 * @param centre centers of clusters
	 * @param points point which will be clasified
	 */
	public static void kMedoids(Point[] centre, Point[] points) {
		boolean sameCenters = false;
		List<Point>[] assignedPoints;
		Point[] prevousCenters;
		int n = 1;
		while(!sameCenters) {
			assignedPoints = assignPoints(centre, points);
			printClusters(assignedPoints, centre, n);
			prevousCenters = centre;
			centre = recalculateCenters(assignedPoints);
			for(int i = 0; i < centre.length; i++) {
				centre[i] = closestPoint(centre[i], points);
			}
			sameCenters = samePoints(centre, prevousCenters);
			n++;
		}
	}
	
	/**
	 * Method print to center of cluster and point of cluster to console
	 * @param assignedPoints assigned points to clusters
	 * @param centre centers of clusters
	 * @param n number of iteration
	 */
	public static void printClusters(List<Point>[] assignedPoints, Point[] centre, int n) {
		System.out.println("-------------------------------");
		System.out.println("Iteration: " + n);
		System.out.println("-------------------------------");
		for(int i = 0; i < assignedPoints.length; i++) {
			System.out.printf("Cluster %d: Centre - [%d, %d]\n", (i + 1), centre[i].x, centre[i].y);
			System.out.println("-------------------------------");
			for(int j = 0; j < assignedPoints[i].size(); j++) {
				System.out.println("Point: " + assignedPoints[i].get(j).toString());
			}
			System.out.println("-------------------------------");
		}
	}
	
	/**
	 * Method compares two arrays of points
	 * @param a array of points 1
	 * @param b array of points 2
	 * @return true - the points are same, false points are not same
	 */
	public static boolean samePoints(Point[] a, Point[] b) {
		for(int i = 0; i < a.length; i++) {
			if(a[i].x != b[i].x) {
				return false;
			}
			if(a[i].y != b[i].y) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method assign points to centers of gravity
	 * @param centre list of centre of gravity 
	 * @param points point which will be assign to centres
	 * @return assigned points
	 */
	public static List<Point>[] assignPoints(Point[] centre, Point[] points) {
		@SuppressWarnings("unchecked")
		List<Point>[] assertion = new List[centre.length];
		for(int i = 0; i < assertion.length; i++) {
			assertion[i] = new ArrayList<>();
		}
		int closest = 0;
		double distance;
		for(int i = 0; i < points.length; i++) {
			distance = Double.MAX_VALUE;
			for(int j = 0; j < centre.length; j++) {
				if(euclideanDistance(points[i], centre[j]) < distance) {
					closest = j;
					distance = euclideanDistance(points[i], centre[j]);
				}
			}
			assertion[closest].add(points[i]);
		}
		return assertion;
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
	
	/**
	 * Method compute which point id the closest to center of gravity
	 * @param centre center of gravity of cluster
	 * @return the closest point to center of gravity
	 */
	public static Point closestPoint(Point centre, Point[] points) {
		int distance = Integer.MAX_VALUE, index = 0;
		for(int i = 0; i < points.length; i++) {
			int eulerDist = (int)euclideanDistance(centre, points[i]);
			if(eulerDist < distance) {
				distance = eulerDist;
				index = i;
			}
		}
		return points[index];
	}
	
	/**
	 * Method recalculate coordinates of center of cluster
	 * @param assignedPoints 
	 * @return new centers
	 */
	public static Point[] recalculateCenters(List<Point>[] assignedPoints) {
		Point[] points = new Point[assignedPoints.length];
		int sumX, sumY;
		for(int i = 0; i < assignedPoints.length; i++) {
			sumX = 0;
			sumY = 0;
			for(int j = 0; j < assignedPoints[i].size(); j++) {
				sumX += assignedPoints[i].get(j).x;
				sumY += assignedPoints[i].get(j).y;
			}
			points[i] = new Point((int)(sumX/assignedPoints[i].size()), (int)(sumY/assignedPoints[i].size()));
		}
		return points;
	}

	/**
	 * Entry point of program
	 * @param args
	 */
	public static void main(String[] args) {
		List<Point> centreOfGravity = new ArrayList<>();
		centreOfGravity.add(new Point(2, 2));
		centreOfGravity.add(new Point(4, 4));
		Point[] points = createPoints(M2, M1);
		Point[] centre = new Point[centreOfGravity.size()];
	    centre = centreOfGravity.toArray(centre);
	    System.out.println("-------------------------------");
	    System.out.println("\tK-MEANS");
	    System.out.println("-------------------------------");
		kMeans(centre, points);
		System.out.println("-------------------------------");
		System.out.println("\tK-MEDOIDS");
		System.out.println("-------------------------------");
		kMedoids(centre, points);
	}

}
