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
	/** number of towers*/
	private static int NUMBER_OF_TOWERS = 3;
	/** towers */
	private Stack<Integer>[] towers;
	/** queue fo conditions whih is not processed*/
	private Queue<Stack<Integer>[]> conditions;
	/** List of posiible conditions*/
	public ArrayList<String> condition;
	/** Desicion tree*/
	public Graph tree;
	/** Goal condition */
	private Stack<Integer>[] goal;
	
	/**
	 * Constructor
	 * @param number number of disks
	 */
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
	
	/**
	 * Method creates decision tree 
	 */
	public void createTree() {
		int i = 0;
		while(!conditions.isEmpty()) {
			towers = conditions.peek();
			createMoves(i);
			conditions.poll();
			i++;
		}
		createGoal();
	}
	
	/**
	 * Method add possible no duplicated moves
	 * @param vertex tested condition
	 */
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
	
	/**
	 * Methos tests, if condition already exist in graph
	 * @param compared new condition
	 * @return true - condition already exist, false - condition not exist
	 */
	private boolean exist(String compared) {
		for(int i = 0; i < condition.size(); i++) {
			if(condition.get(i).equals(compared.toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Methos creates clone of stack
	 * @param original stack to which we create a clone
	 * @return clone of original stack
	 */
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
	
	/**
	 * Method create goal condition
	 */
	private void createGoal() {
		if(tree != null) {
			goal = new Stack[NUMBER_OF_TOWERS];
			goal[0] =  new Stack<Integer>();
			goal[1] =  new Stack<Integer>();
			goal[2] =  new Stack<Integer>();
		
			for(int i = this.number; i > 0; i--) {
				goal[2].add(i);
			}
			tree.goal = getString(goal);
		}
	}
	
	/**
	 * Methos creates text reprezentation of condition (something like hashCode)
	 * @param towers astual condition of towers
	 * @return text representation
	 */
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
	
	/**
	 * Text reprezentation of HanoiTowers
	 */
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
