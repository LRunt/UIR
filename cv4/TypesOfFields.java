/**
 * 
 */
package cv4;

/**
 * @author Lukas Runt
 * @version 1.0 (13-03-2022)
 */
public enum TypesOfFields {
	FREE,
	QUEEN,
	DISABLED;
	
	@Override
	public String toString() {
		switch (this) {
		case FREE:
			return ".";
		case QUEEN:
			return "#";
		case DISABLED:
			return "x";
		}
		return "";
	}
}
