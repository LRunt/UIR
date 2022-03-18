/**
 * 
 */
package cv4;

import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Lukas Runt
 * @version 1.0 (08-03-2022)
 */
public class Main {
	
	public static Scanner sc;
	
	/**
	 * Method reads input file and creates Graph
	 * @param fileName name of input file
	 */
	public static Graph readInput(String fileName) {
		Graph model = new Graph();
		try {
			sc = new Scanner(Paths.get(fileName));
			String in;
			while(sc.hasNext()) {
				in = sc.nextLine();
				if(in.equals("start:")) {
					model.start = sc.next();
				}else if(in.equals("cil:")) {
					model.goal = sc.next();
				}if(in.equals("seznam sousednosti:")) {
					createVertices(in, model);
				}
			}
		}catch(Exception ex) {
			System.out.println("Error while reading a file: " + fileName);
		}
		return model;
	}
	
	/**
	 * Method creates verticles from input file
	 * @param in input
	 * @param graph graph which is creating
	 */
	public static void createVertices(String in, Graph graph) {
		int p = 0;
		while(sc.hasNext()) {
			in = sc.nextLine();
			if(in.equals("vzdusna vzdalenost od cile:")) {
				distanceAsTheCrowFlies(in, graph);
			}else {
				String[] splited = in.split(";");
				graph.addVertex(splited[0]);
				for(int i = 1; i < splited.length; i++) {
					String[] nameWeight = splited[i].split("=");
					int distance = Integer.parseInt(nameWeight[1]);
					graph.addEdge(p, nameWeight[0], distance);
				}
			}
			p++;
		}
	}
	
	/**
	 * Method fill the air distance to the vertices
	 * @param in input
	 * @param graph graph which is creating
	 */
	public static void distanceAsTheCrowFlies(String in, Graph graph) {
		while(sc.hasNext()) {
			in = sc.nextLine();
			String[] splited = in.split("=");
			for(int i = 0; i < graph.edges.size(); i++) {
				if(graph.edges.get(i).name.equals(splited[0])) {
					graph.edges.get(i).distance = Integer.parseInt(splited[1]);
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = readInput("cv4_vstup.txt");
		System.out.println("Start: " + graph.start);
		System.out.println("Goal: " + graph.goal);
		System.out.println("-------------Greedy----------------");
		graph.greedy();
		System.out.println("---------------A*------------------");
		graph.aPointer();
		
		/*System.out.println();
		System.out.println("BONUSOVA ULOHA");
		//Queens queen = new Queens(4, 4, 0, 0, 0);
		Generator generator = new Generator(4, 4);
		generator.generateVariations();
		generator.printVariations();
g 		//Queens test = new Queens(4, 4, 13, 1, 1);
		//test.chessboard[0][1] = TypesOfFields.QUEEN;
		//test.chessboard[8] = TypesOfFields.QUEEN;
		//test.activateRules();
		//generator.generateVariationsWithRules();
		//generator.printVariations();
		//test.printChessboard();*/
		
	}

}
