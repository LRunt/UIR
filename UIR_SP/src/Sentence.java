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
    /** Term frequency of one sentence*/
    public HashMap<String, Double> TF;
    /** Term Frequency - Inverse Document Frequency of sentence*/
    public HashMap<String, Double> TFIDF = new HashMap<>();
    /** Symptoms of sentence*/
    public HashMap<String, Double> symptoms;

    /**
     * Constructor of class sentence
     * @param category category of sentence
     * @param text the sentence
     */
    public Sentence(String category, String text){
        this.category = category;
        this.text = text;
        this.BOW = createBagOfWords();
        this.TF = createTermFrequency();
    }

    /**
     * Method create bag of words
     * @return bag of word of sentence
     */
    public HashMap<String, Double> createBagOfWords() {
        HashMap<String, Double> bagOfWords = new HashMap<>();
        String[] words = text.split("[^0-9A-Za-z']");
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

    /**
     * Metohd creates term frequency of words in
     * @return term frequency
     */
    public HashMap<String, Double> createTermFrequency(){
        HashMap<String, Double> termFrequency = new HashMap<>();
        double wordsInSentence = BOW.values().stream().reduce(0.0, Double::sum);
        for(String word : BOW.keySet()){
            termFrequency.put(word, BOW.get(word)/wordsInSentence);
        }
        return termFrequency;
    }
}
