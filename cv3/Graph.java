package cv3;

import java.util.ArrayList;

public class Graph {
	
	public ArrayList<ArrayList<Integer>> edges;
	public ArrayList<String> condition;
	
	public Graph() {
		edges = new ArrayList<>();
		condition = new ArrayList<>();
	}
	
	public void addVertex(String condition) {
		edges.add(new ArrayList<Integer>());
		this.condition.add(condition);
	}
	
	public void addEdge(int start, int end) {
		edges.get(start).add(end);
	}
}
