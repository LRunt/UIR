package cv3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Lukas Runt
 * @version 1.0 (01-03-2022)
 */
public class HanoiTower {
	/** number of disks*/
	private int number;
	private static int NUMBER_OF_TOWERS = 3;
	private Stack<Integer>[] towers;
	private Queue<Stack<Integer>[]> conditions;
	public ArrayList<String> condition;
	public Graph tree;
	
	public HanoiTower(int number) {
		this.number = number;
		towers = new Stack[NUMBER_OF_TOWERS];
		towers[0] =  new Stack<Integer>();
		towers[1] =  new Stack<Integer>();
		towers[2] =  new Stack<Integer>();
		
		for(int i = this.number; i > 0; i--) {
			towers[0].add(i);
		}
		
		conditions = new LinkedList<>();
		condition = new ArrayList<>();
		conditions.add(towers);
		condition.add(getString(towers));
		
		tree = new Graph();
		tree.addVertex(getString(towers));
	}
	
	public void createTree() {
		int i = 0;
		while(!conditions.isEmpty()) {
			towers = conditions.peek();
			createMoves(i);
			conditions.poll();
			i++;
		}
		System.out.println("Hotovo!");
	}
	
	private void createMoves(int vertex) {
		int value;
		Stack<Integer>[] clone;
		
		for(int i = 0; i < NUMBER_OF_TOWERS; i++) {
			if(towers[i].size() != 0) {
				value = towers[i].peek();
				for(int j = 0; j < NUMBER_OF_TOWERS; j++) {
					if(i != j) {
						if(towers[j].size() != 0) {
							if(towers[j].peek() > value) {
								clone = createClone(towers);
								value = clone[i].pop();
								clone[j].add(value);
								if(!exist(getString(clone))) {
									conditions.add(clone);
									condition.add(getString(clone));
									tree.addVertex(getString(clone));
									tree.addEdge(vertex, condition.size() - 1);
								}
							}
						}else {
							clone = createClone(towers);
							value = clone[i].pop();
							clone[j].add(value);
							if(!exist(getString(clone))) {
								conditions.add(clone);
								condition.add(getString(clone));
								tree.addVertex(getString(clone));
								tree.addEdge(vertex, condition.size() - 1);
							}
						}
					}
				}
			}		
		}
	}
	
	private boolean exist(String compared) {
		for(int i = 0; i < condition.size(); i++) {
			if(condition.get(i).equals(compared.toString())) {
				return true;
			}
		}
		return false;
	}
	
	private Stack<Integer>[] createClone(Stack<Integer>[] original){
		Stack<Integer>[] clone = new Stack[NUMBER_OF_TOWERS];
		for(int i = 0; i < NUMBER_OF_TOWERS; i++) {
			clone[i] = new Stack<>();
			for(int j = 0; j < original[i].size(); j++) {
				clone[i].add(original[i].get(j));
			}
		}
		return clone;
	}
	
	public String getString(Stack<Integer>[] towers) {
		String vystup = "Left: ";
		for(int i = 0; i < towers[0].size(); i++) {
			vystup += towers[0].get(i) + ", ";
		}
		vystup += "Middle: ";
		for(int i = 0; i < towers[1].size(); i++) {
			vystup += towers[1].get(i) + ", ";
		}
		vystup += "Right: ";
		for(int i = 0; i < towers[2].size(); i++) {
			vystup += towers[2].get(i) + ", ";
		}
		return vystup;	
	}
	
	public String toString() {
		String vystup = "Left: ";
		for(int i = 0; i < towers[0].size(); i++) {
			vystup += towers[0].get(i) + ", ";
		}
		vystup += "Middle: ";
		for(int i = 0; i < towers[1].size(); i++) {
			vystup += towers[1].get(i) + ", ";
		}
		vystup += "Right: ";
		for(int i = 0; i < towers[2].size(); i++) {
			vystup += towers[2].get(i) + ", ";
		}
		return vystup;	
	}
}
