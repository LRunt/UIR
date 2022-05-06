import java.util.HashMap;

/**
 * Class {@code Sentence} is messenger of one sentence
 * @author Lukas Runt
 * @version 1.0 (28-04-2022)
 */
public class Sentence {
    /**A category of sentence */
    public String category;
    /** A sentence content */
    public String text;
    /** Bag of words of one sentence*/
    public HashMap<String, Double> BOW;

    /**
     * Constructor of class sentence
     * @param category category of sentence
     * @param text the sentence
     */
    public Sentence(String category, String text){
        this.category = category;
        this.text = text;
        this.BOW = createBagOfWords(this.text);
    }

    /**
     * Method create bag of words
     * @param sentence sentence for which a bag of words is created
     * @return bag of word of sentence
     */
    private HashMap<String, Double> createBagOfWords(String sentence) {
        HashMap<String, Double> bagOfWords = new HashMap<>();
        String[] words = sentence.split("[^0-9A-Za-z']");
        for (String word : words) {
            if (!word.equals("")) {
                if (bagOfWords.containsKey(word)) {
                    double count = bagOfWords.get(word);
                    bagOfWords.put(word, count + 1);
                } else {
                    bagOfWords.put(word, 1.0);
                }
            }
        }
        return bagOfWords;
    }
}
