package cv4;

import java.util.ArrayList;

class Noder {
	/** Value of node*/
	int value;
	/** Name of node (name of vertex*/
	String name;
	/** Parent node*/
	Noder parent;
	/** List of chilren*/
	ArrayList<Node> children;
}

public class Tree {
	/** root node*/
	public Noder root;
	
	/**
	 * Constructor of Tree
	 */
	public Tree(Noder root) {
		this.root = root;
	}

}
