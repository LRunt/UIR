/**
 * 
 */
package cv7;

/**
 * @author Lukas Runt
 * @version 1.0 (29-03-2022)
 */
public class Point {
	
	public int x;
	public int y;

	/**
	 * 
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return String.format("[%d, %d]", x, y);
	}

}
