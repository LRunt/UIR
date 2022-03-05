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
		HanoiTower test = new HanoiTower(3);
		System.out.println("Start: " + test.toString());
		test.createTree();
		System.out.println("Target: " + test.tree.goal);
		System.out.println("---------------------------------");
		System.out.println("Breadth-first search:");
		test.tree.bfs(0);
		System.out.println("---------------------------------");
		System.out.println("Depth-first search:");
		test.tree.dfs(0);
	}

}
