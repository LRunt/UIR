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
	/** marking array */
	private int[] mark;
	
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
	 * Breadth-first search
	 * @param start start vertex
	 */
	public void bfs(int start) {
		int target = -1;
		mark = new int[edges.size()];
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
			System.out.println("Target not found!");
		}else {
			writeSolution(target);
		}
	}
	
	/**
	 * Start Method of Depth-first search
	 * @param start start vertex
	 */
	public void dfs(int start) {
		int target;
		mark = new int[edges.size()];
		if (mark[start] == 0) {
			target = dfsPartial(start);
			if (target != -1) {
				writeSolution(target);
			}else {
				System.out.println("Target not found!");
			}
		}
	}
	
	/**
	 * Recursion method of Depth-first search
	 * @param s start vertex
	 * @return target vertex, -1 if target vertex not found
	 */
	public int dfsPartial(int s) {
		mark[s] = 1;
		int target = -1;
		System.out.print(s + ", ");
		ArrayList<Integer> neighbours = edges.get(s);
		for(int i = 0; i < neighbours.size(); i++) {
			int n = neighbours.get(i);
			if (condition.get(n).equals(goal)) {
				System.out.print(n + ", ");
				System.out.println("Target found!");
				return n;
			} 
			if(mark[n] == 0) {
				target = dfsPartial(n);
				if (target != -1) {
					return target;
				}	
			}
		}
		mark[s] = 2;
		return target;	
	}
	
	/**
	 * Method writes solution to console
	 * @param target target state
	 */
	private void writeSolution(int target) {
		Stack<String> text = new Stack<>();
		text.push("State " + target +  " - " + condition.get(target));
		while(prevous.get(target) != -1) {
			target = prevous.get(target);
			text.push("State " + target +  " - " + condition.get(target));
		}
		while(!text.empty()) {
			System.out.println(text.pop());
		}
	}
}
