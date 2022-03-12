/**
 * 
 */
package cv4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * PArt of linkedList
 * @author Lukas Runt
 */
class Link {
	
	/**
	 *Constructor of Link 
	 * @param name name of link
	 * @param distance distance of link
	 */
	public Link(String name, int distance) {
		this.name = name;
		this.distance = distance;
	}
	
	 /**
	  * Constructor of Link
	  * @param name name of link
	  */
	public Link(String name) {
		this.name = name;
	}
	
	/** Name of vertex */
	public String name;
	/** Value of distance*/
	public int distance;
	/** Link to neighbour */
	public Link next;
}

class Node implements Comparable<Node>{
	int value;
	int distance;
	String name;
	int parent;
	ArrayList<Integer> children;
	int status;
	int id;
	
	public Node(int value, String name, int parent, int id, int distance) {
		this.value = value;
		this.name = name;
		this.parent = parent;
		this.id = id;
		this.distance = distance;
		children = new ArrayList<>();
		status = 0;
	}

	@Override
	public int compareTo(Node o) {
		if(this.status == o.status){
			return (this.value + this.distance) - (o.value + o.distance);
		} else if(this.status < o.status){
		    return -1;
		} else{
		    return 1;
		}
	}
	
	public String getStringAPointer() {
		return String.format("%s(%d+%d)", name, value, distance);
	}
	
	public String toString() {
		return String.format("%s(%d)", name, value);
	}
} 

/**
 * @author Lukas Runt
 * @version 1.0 (08-03-2022)
 */
public class Graph {
	
	/** List of edges */
	public ArrayList<Link> edges;
	/** Starting vertex*/
	public String start;
	/** Goal vertex*/
	public String goal;

	/**
	 * Constructor of graph
	 */
	public Graph() {
		edges = new ArrayList<>();
	}
	
	/**
	 * Method add vertex
	 * @param condition unique condition
	 */
	public void addVertex(String name) {
		edges.add(new Link(name));
	}
	
	/**
	 * Method add edge to graph
	 * @param start start vertex
	 * @param end end vertex
	 * @param weight weight of edge
	 */
	public void addEdge(int start, String end, int weight) {
		Link helper = edges.get(start);
		while(helper.next != null) {
			helper = helper.next;
		}
		helper.next = new Link(end, weight);
	}
	
	public void greedy() {
		ArrayList<Node> tree = new ArrayList<>();
		//adding root
		int count = 0;
		tree.add(new Node(0, start, -1, count, 0));
		Link link = null;
		//picking root
		Node actual = tree.get(0);
		System.out.print("Picking nodes: " + actual);
		while(!actual.name.equals(goal)) {
			for(int i = 0; i < edges.size(); i++) {
				link = edges.get(i);
				if(actual.name.equals(link.name)) {
					while(link.next != null) {
						link = link.next;
						count++;
						tree.add(new Node(actual.value + link.distance, link.name, actual.id, count, 0));
						actual.children.add(count);
					}
				}
			}
			actual.status = 1;
			Collections.sort(tree);
			actual = tree.get(0);
			System.out.print(", " + actual);
		}
		System.out.println();
		this.writeSolution(tree);
	}
	
	public void aPointer() {
		ArrayList<Node> tree = new ArrayList<>();
		//adding root
		int count = 0;
		tree.add(new Node(0, start, -1, count, 52));
		Link link = null;
		Link vertex = null;
		//picking root
		Node actual = tree.get(0);
		System.out.print("Picking nodes: " + actual.getStringAPointer());
		while(!actual.name.equals(goal)) {
			for(int i = 0; i < edges.size(); i++) {
				link = edges.get(i);
				if(actual.name.equals(link.name)) {
					while(link.next != null) {
						link = link.next;
						for(int j = 0; j < edges.size(); j++) {
							vertex = edges.get(j);
							if(vertex.name.equals(link.name)) {
								break;
							}
						}
						count++;
						tree.add(new Node(actual.value + link.distance, link.name, actual.id, count, vertex.distance));
						actual.children.add(count);
					}
				}
			}
			actual.status = 1;
			Collections.sort(tree);
			actual = tree.get(0);
			System.out.print(", " + actual.getStringAPointer());
		}
		System.out.println();
		this.writeSolution(tree);
	}
	
	public void writeSolution(ArrayList<Node> tree) {
		Node actual = tree.get(0);
		int parent, i;
		Stack<Node> stack= new Stack<>(); 
		do{
			stack.add(actual);
			parent = actual.parent;
			i = 0;
			while(tree.get(i).id != parent) {
				i++;
				actual = tree.get(i);
			}
		}while(actual.parent != -1);
		stack.add(actual);
		
		System.out.print(stack.pop());
		while(!stack.isEmpty()) {
			System.out.print("->");
			System.out.print(stack.pop());
		}
		System.out.println();
	}

}
