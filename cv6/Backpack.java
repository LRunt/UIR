/**
 * 
 */
package cv6;

/**
 * @author Lukas Runt
 * @version 1.0 (22-03-2022)
 */
public class Backpack implements Comparable<Backpack>{
	
	/** vector of item 1 - item is in backpack, 0 - item is not there*/
	public byte[] itemVector;
	/** Value of back pack*/
	public int totalPrice;
	/** Weight of item in backpack*/
	public int totalWeight; 
	

	/**
	 * Constructor of class {@code Bacpack} 
	 */
	public Backpack(byte[] itemVector) {
		this.itemVector = itemVector;
		this.totalWeight = countWeight();
		this.totalPrice = countPrice();
	}
	
	/**
	 * Method counts weight of items in backpack 
	 * @return weight of items
	 */
	private int countWeight() {
		int weight = 0;
		for(int i = 0; i < itemVector.length; i++) {
			weight += itemVector[i] * Main.itemList[i].weight;
		}
		return weight;
	}
	
	/**
	 * Method counts price of item in backpack
	 * @return 0 - if weight is bigger than capacity of backpack, else returns price of items 
	 */
	private int countPrice() {
		int price = 0;
		if(totalWeight <= Main.CAPACITY) {
			for(int i = 0; i < itemVector.length; i++) {
				price += itemVector[i] * Main.itemList[i].price; 
			}
		}
		return price;
	}

	/**
	 * Comparable method
	 */
	@Override
	public int compareTo(Backpack o) {
		return o.totalPrice - this.totalPrice;
	}
	
	@Override
	public String toString() {
		String output = "[";
		for(int i = 0; i < itemVector.length - 1; i++) {
			output += String.format("%d, ", itemVector[i]);
		}
		output += String.format("%d] - ", itemVector[itemVector.length - 1]);
		output += String.format("Price %d, Weight %d", totalPrice, totalWeight);
		return output;
	}

}
