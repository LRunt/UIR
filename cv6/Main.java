/**
 * 
 */
package cv6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas Runt
 * @version 1.0 (21-03-2022)
 */
public class Main {
	
	/** List of item which is avaible*/
	public static Item[] itemList;
	/** Maximum capacity of backpack*/
	public static final int CAPACITY = 40;
	/** List of all generations*/
	public static List<Generation> allGenerations = new ArrayList<>();
	
	/**
	 * Method reads data
	 * @param fileName name of file witch is reads
	 * @return List of rows from input 
	 */
	public static List<String> readData(String fileName) {
		List<String> rowList = null;
        try{
            rowList = Files.readAllLines(Paths.get(fileName));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return rowList;
	}
	
	/**
	 * Method parse data and creates ArrayList of Items
	 * @return Array of items, null if input is null
	 */
	public static Item[] parseData(List<String> rowList){
		Item[] items = new Item[rowList.size() - 1];
		for(int i = 1; i < rowList.size(); i++) {
			String[] splitted = rowList.get(i).split(";");
			items[i - 1] = new Item(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
		}
		return items;
	}
	
	/**
	 * Method print output into the file
	 * @param outputFile name of output file
	 */
	public static void printAllGenerations(String outputFile) {
		  try {
	            PrintWriter pw = new PrintWriter(
	                            new BufferedWriter(
	                            new FileWriter(new File(outputFile))));
	            for(int i = 0; i < allGenerations.size(); i++) {
	            	pw.println("Generace: " + (i + 1));
	            	for(int j = 0; j < allGenerations.get(i).generation.length; j++) {
	            		pw.println(allGenerations.get(i).generation[j]);
	            	}
	            }
	            pw.close();
	        }catch(Exception ex) {
	        	ex.printStackTrace();
	        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> rowList = readData("cv6_vstup.txt");
		itemList = parseData(rowList);
		Generation gen = new Generation(10, 4);
		allGenerations.add(gen);
		for(int i = 0; i < 100; i++) {
			gen = gen.generateNewGeneration();
			allGenerations.add(gen);
		}
		gen.printBest();
		printAllGenerations("Output_cv06.txt");
	}

}
