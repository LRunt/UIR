/**
 * 
 */
package cv8;

/**
 * @author Lukas Runt
 * @versoin 1.0 (14-04-2022)
 */
public class PointDistance implements Comparable<PointDistance>{
	
	public Point point;
	public double distance;

	/**
	 * Constructor of PointDistance
	 */
	public PointDistance(Point point, double distance) {
		this.point = point;
		this.distance = distance;
	}

	@Override
	public int compareTo(PointDistance o) {
		return (int) (this.distance - o.distance);
	}

}
