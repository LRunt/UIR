/**
 * 
 */
package cv6;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Lukas Runt
 * @version 1.0
 */
public class Generation {
	
	/** One generation*/
	public Backpack[] generation;
	/** number of natural selection*/
	public int naturalSelection;
	/** Random number generator*/
	Random r = new Random();

	/**
	 * Default constructor of class {@code Generation} 
	 */
	public Generation(int numberOfMembers, int naturalSelection) {
		this.naturalSelection = naturalSelection;
		generateGeneration(numberOfMembers);
		}
	
	/**
	 * Constructor of class {@code Generation}
	 * @param generation
	 */
	public Generation(Backpack[] generation, int naturalSelection) {
		this.naturalSelection = naturalSelection;
		this.generation = generation;
	}
	
	/**
	 * Method print the best solution 
	 */
	public void printBest() {
		Arrays.sort(generation);
		System.out.println(generation[0]);
	}
	
	/**
	 * Method generate completely new generation
	 * @param NumberOfMembers
	 */
	private void generateGeneration(int numberOfMembers) {
		generation = new Backpack[numberOfMembers];
		for(int i = 0; i < numberOfMembers; i++) {
			byte[] vector = new byte[Main.itemList.length];
			for(int j = 0; j < vector.length; j++) {
				vector[j] =  (byte)Math.round(Math.random());
			}
			generation[i] = new Backpack(vector);
		}
	}
	
	/**
	 *Method generating new generation
	 */
	public Generation generateNewGeneration() {
		byte[][]bestSamples = naturalSelection(naturalSelection);
		byte[][]crossover = createCrossover((int)(0.3 * generation.length), bestSamples);
		byte[][]mutation = mutate((int)(0.4 * generation.length), bestSamples);
		return createNewGeneration(crossover, mutation);
	}
	
	/**
	 * Method creates new generation
	 * @param crossover list of chromozomes which was created by crossover
	 * @param mutation list of chromozomes creted by mutation
	 * @return new generation
	 */
	private Generation createNewGeneration(byte[][] crossover, byte[][] mutation) {
		Backpack[] backpacks = new Backpack[crossover.length + mutation.length];
		for(int i = 0; i < crossover.length; i++) {
			backpacks[i] = new Backpack(crossover[i]);
		}
		for(int i = 0; i < mutation.length; i++) {
			backpacks[i + crossover.length] = new Backpack(mutation[i]);
		}
		Generation newGeneration = new Generation(backpacks, naturalSelection);
		return newGeneration;
	}
	
	/**
	 * Method crossover two vector
	 * @param number number of crossovers
	 */
	private byte[][] createCrossover(int number, byte[][] chromozomes) {
		int splitPoint;
		byte[] ch1, ch2;
		byte[][] newGeneration = new byte[number * 2][];
		for(int i = 0; i < number; i++) {
			ch1 = chromozomes[r.nextInt(chromozomes.length)];
			ch2 = chromozomes[r.nextInt(chromozomes.length)];
			splitPoint = r.nextInt(Main.itemList.length);
			newGeneration[i] = crossover(ch1, ch2, splitPoint);
			newGeneration[number + i] = crossover(ch2, ch1, splitPoint);
		}
		return newGeneration;
	}
	
	/**
	 * Method crossover two chromozomes and creating new chromozome
	 * @param ch1
	 * @param ch2
	 * @param splitPoint
	 * @return
	 */
	private byte[] crossover(byte[] ch1, byte[] ch2, int splitPoint) {
		byte[] newChromozome = new byte[ch1.length];
		for(int j = 0; j < splitPoint; j++) {
			newChromozome[j] = ch1[j];
		}
		for(int j = splitPoint; j < newChromozome.length; j++) {
			newChromozome[j] = ch2[j];
		}
		return newChromozome;
	}
	
	/**
	 * Method mutate one vector
	 * @param number number of mutated vectors
	 */
	private byte[][] mutate(int number, byte[][] chromozomes) {
		byte[][] newGeneration = new byte[number][];
		byte[] ch1;
		int mutationIndex;
		for(int i = 0; i < number; i++) {
			ch1 = chromozomes[r.nextInt(chromozomes.length)];
			mutationIndex = r.nextInt(ch1.length);
			if(ch1[mutationIndex] == 0) {
				ch1[mutationIndex] = 1;
			}else {
				ch1[mutationIndex] = 0;
			}
			newGeneration[i] = ch1;
		}
		return newGeneration; 
	}
	
	/**
	 * Method picks the best samples
	 * @param number number of best samples
	 * @return best samples
	 */
	private byte[][] naturalSelection(int number) {
		Arrays.sort(generation);
		byte[][] bestSamples = new byte[number][];
		for(int i = 0; i < number; i++) {
			bestSamples[i] = generation[i].itemVector;
		}
		return bestSamples;
	}
	

}
