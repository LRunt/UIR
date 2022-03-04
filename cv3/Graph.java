package cv3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Class {@code Graph} represents discrete graph - desicion tree
 * @author Lukas Runt
 * @version 1.0 (03-03-2022)
 */
public class Graph {
	
	/** List of edges */
	public ArrayList<ArrayList<Integer>> edges;
	/** List of conditions */
	public ArrayList<String> condition;
	/** List of prevous vertices*/
	public ArrayList<Integer> prevous;
	/** Goal condition*/
	public String goal;
	
	/**
	 * constructor
	 */
	public Graph() {
		edges = new ArrayList<>();
		condition = new ArrayList<>();
		prevous = new ArrayList<>();
	}
	
	/**
	 * Nethod add vertex
	 * @param condition unique condition
	 */
	public void addVertex(String condition) {
		edges.add(new ArrayList<Integer>());
		this.condition.add(condition);
		this.prevous.add(-1);
	}
	
	/**
	 * Method add edge
	 * @param start start vertex
	 * @param end end vertex
	 */
	public void addEdge(int start, int end) {
		edges.get(start).add(end);
		prevous.set(end, start);
	}
	
	/**
	 * Method 
	 */
	public void bfs() {
		int target = -1;
		int[] mark = new int[edges.size()];
		mark[0] = 1;
		Queue<Integer> q = new LinkedList<>();
		q.add(0);
		while(!q.isEmpty()) {
			int v = q.poll();
			System.out.print(v + ", ");
			if(condition.get(v).equals(goal)) {
				target = v;
				System.out.println("Target found!");
				break;
			}
			for(int i = 0; i < edges.get(v).size(); i++) {
				int n = edges.get(v).get(i);
				if (mark[n] == 0) {
					mark[n] = 1;
					q.add(n);
				}
			}
			mark[v] = 2;
		}
		if(target == -1) {
			System.out.println("Cil nebyl nalezen!");
		}else {
			writeSolution(target);
		}
	}
	
	/**
	 * Method writes solution to console
	 * @param target target state
	 */
	private void writeSolution(int target) {
		Stack<String> text = new Stack<>();
		text.push(condition.get(target));
		while(prevous.get(target) != -1) {
			target = prevous.get(target);
			text.push(condition.get(target));
		}
		while(!text.empty()) {
			System.out.print(text.pop() + "| ");
		}
	}
}
