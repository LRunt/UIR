/**
 * 
 */
package cv4;

/**
 * @author Lukas Runt
 * @version 1.0 (13-03-2022)
 */
public class Queens {
	
	/** width*/
	public int x;
	/** height*/
	public int y;
	/** chessboard*/
	public TypesOfFields[][] chessboard;
	/** number of queens*/
	public int number;
	/** last queen*/
	public int lastX;
	public int lastY;
	

	/**
	 * Constructor of Queens
	 */
	public Queens(int x, int y, int lastX, int lastY, int number) {
		this.x = x;
		this.y = y;
		this.number = number;
		this.lastX = lastX;
		this.lastY = lastY;
		chessboard = new TypesOfFields[x][y];
		fillArray();
	}
	
	public void fillArray() {
		for(int i = 0; i < chessboard.length; i++) {
			for(int j = 0; j < chessboard[i].length; j++) {
				chessboard[i][j] = TypesOfFields.FREE;
			}
		}
	}
	
	public Queens clone() {
		Queens queens = new Queens(x, y, lastX, lastY, number);
		for(int i = 0; i < chessboard.length; i++) {
			for(int j = 0; j < chessboard[i].length; j++) {
				queens.chessboard[i][j] = this.chessboard[i][j];
			}
			
		}
		return queens;
	}
	
	/**
	 * Method testing if queen can be placed to field
	 */
	public void activateRules() {
		for(int i = 0; i < chessboard.length; i++) {
			for(int l = 0; l < chessboard[i].length; l++) {
				TypesOfFields typ = chessboard[i][l];
				if(typ == TypesOfFields.QUEEN) {
					//S
					int j = l - x;
					while(j >= 0) {
						typ = chessboard[j][l];
						if(typ == TypesOfFields.FREE) {
							chessboard[j][l] = TypesOfFields.DISABLED;
						}
						j -= x;
					}
					//J
					j = l + x;
					while(j < chessboard.length) {
						typ = chessboard[j][l];
						if(typ == TypesOfFields.FREE) {
							chessboard[j][l] = TypesOfFields.DISABLED;
						}
						j += x;
					}
					/*//Z a V
					j = l--;
					while(j >= 0) {
						typ = chessboard[i][j];
						if(typ == TypesOfFields.FREE) {
							chessboard[i][j] = TypesOfFields.DISABLED;
						}
						j--;
					}
					j = l++;
					while(j < x) {
						typ = chessboard[i][j];
						if(typ == TypesOfFields.FREE) {
							chessboard[i][j] = TypesOfFields.DISABLED;
						}
						j++;
					}
					//SZ
					j = l--;
					int k = i--;
					while(j >= 0 && k >= 0) {
						typ = chessboard[k][j];
						if(typ == TypesOfFields.FREE) {
							chessboard[k][j] = TypesOfFields.DISABLED;
						}
						j -= (x + 1);
					}
					//JV
					j = l++;
					k = i++;
					while(j < x && k < y) {
						typ = chessboard[j][k];
						if(typ == TypesOfFields.FREE) {
							chessboard[j][k] = TypesOfFields.DISABLED;
						}
						j += (x + 1);
					}
					//JZ
					j = l--;
					k = i++;
					while(j >= 0 && k < y) {
						typ = chessboard[j][k];
						if(typ == TypesOfFields.FREE) {
							chessboard[j][k] = TypesOfFields.DISABLED;
						}
					}
					//SV
					j = l++;
					k = i--;
					while(j < x && k >= 0) {
						typ = chessboard[j][k];
						if(typ == TypesOfFields.FREE) {
							chessboard[j][k] = TypesOfFields.DISABLED;
						}
					}*/
				}	
			}
		}
	}

	/**
	 * Method print view of chessboard
	 */
	public void printChessboard() {
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				System.out.print(chessboard[i][j]);
			}
			System.out.println();
		}
	}
	
	public String toString() {
		String textRepresation = "";
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				textRepresation += chessboard[i][j];
			}
			textRepresation += "\n";
		}
		return textRepresation;
	}
}
