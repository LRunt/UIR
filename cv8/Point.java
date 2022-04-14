/**
 * 
 */
package cv8;

/**
 * @author Lukas Runt
 * @version 1.0 (29-03-2022)
 */
public class Point {
	/** The x coordinate of point*/
	public int x;
	/** The y-coordinate of point*/
	public int y;
	public int center;

	/**
	 *Constructor of point 
	 */
	public Point(int x, int y, int center) {
		this.x = x;
		this.y = y;
		this.center = center;
	}
	
	/**
	 * Text representation of point
	 */
	@Override
	public String toString() {
		return String.format("[%d, %d]", x, y);
	}

}
