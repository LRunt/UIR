/**
 * 
 */
package cv3;

/**
 * @author Lukas Runt
 * @version 1.1 (01-03-2022)
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HanoiTower pokus = new HanoiTower(3);
		System.out.println(pokus.toString());
		pokus.createTree();
		pokus.tree.bfs();
	}

}
