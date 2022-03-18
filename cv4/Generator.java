/**
 * 
 */
package cv4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Lukas Runt
 * @version 1.0 (13-3-2022)
 */
public class Generator {

	/** list of variations*/
	public ArrayList<Queens> variations;
	/** width*/
	public int x;
	/** height*/
	public int y;
	
	/**
	 *Constructor of generator 
	 */
	public Generator(int x, int y) {
		this.x = x;
		this.y = y;
		variations = new ArrayList<>();
	}
	
	public void generateVariations() {
		variations = new ArrayList<>();
		Queens start = new Queens(x, y, 0, 0, 0);
		variations.add(start);
		for(int i = 0; i < start.chessboard.length; i++) {
			for(int j = 0; j < start.chessboard[i].length; j++) {
				Queens oneQueen = new Queens(x, y, 0, 0, 1);
				oneQueen.chessboard[i][j] = TypesOfFields.QUEEN;
				oneQueen.lastX = j;
				oneQueen.lastY = i;
				variations.add(oneQueen);
			}
		}
		for(int i = 1; i < variations.size(); i++) {
			Queens queen = variations.get(i).clone();
			for(int j = queen.lastY; j < queen.chessboard.length; j++) {
				for(int k = queen.lastX + 1; k < queen.chessboard[j].length; k++) {
					Queens newQueen = variations.get(i).clone();
					if(newQueen.lastX == queen.chessboard[j].length - 1) {
						newQueen.lastX = 0;
						newQueen.lastY = j + 1;
					}else {
						newQueen.lastX = k + 1;
						newQueen.lastY = j;
					}
					newQueen.number++;
					newQueen.chessboard[newQueen.lastY][newQueen.lastX] = TypesOfFields.QUEEN;
					variations.add(newQueen);
				}
			}		
		}
	}
	
	/*public void generateVariationsWithRules() {
		variations = new ArrayList<>();
		Queens start = new Queens(x, y, 0, 0, 0);
		variations.add(start);
		for(int i = 0; i < start.chessboard.length; i++) {
			Queens oneQueen = new Queens(x, y, 0, 0, 1);
			oneQueen.chessboard[i] = TypesOfFields.QUEEN;
			oneQueen.last = i;
			oneQueen.activateRules();
			variations.add(oneQueen);
		}
		for(int i = 1; i < variations.size(); i++) {
			Queens queen = variations.get(i).clone();
			for(int j = queen.last + 1; j < queen.chessboard.length; j++) {
				Queens newQueen = variations.get(i).clone();
				newQueen.last = j;
				newQueen.number++;
				if(newQueen.chessboard[j] == TypesOfFields.FREE) {
					newQueen.chessboard[j] = TypesOfFields.QUEEN;
					newQueen.activateRules();
					variations.add(newQueen);
				}
			}
		}
	}*/
	
	public void printVariations() {
		try {
			 PrintWriter pw = new PrintWriter(
                     new BufferedWriter(
                     new FileWriter(new File("Variations.txt"))));
			 pw.write("Number of variations: " + variations.size() + "\n");
			 for (Queens queens : variations) {
				 pw.write(queens.number + "\n");
				 pw.write(queens.toString());
			 }
			 pw.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
