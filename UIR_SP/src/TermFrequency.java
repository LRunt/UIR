import java.util.HashMap;

/**
 * Class {@code TermFrequency} represents term frequency in every category
 * @author Lukas Runt
 * @version 1.0 (30-04-2022)
 */
public class TermFrequency {

    /** HashMap with term frequency*/
    private HashMap<String,HashMap> termFrequency;
    /** HashMap which stores how many words are in each category*/
    private HashMap<String, Integer> numberOfWordsPerCategory;

    /**
     * Constructor of class {@code TermFrequency}
     * @param countedBagOfWords bagOfWords, where in counted how many times the words appear in each category
     */
    public TermFrequency(BagOfWords countedBagOfWords){
        this.numberOfWordsPerCategory = countWordsPerCategories(countedBagOfWords.getCategoriesMap());
        this.termFrequency = countTermFrequency(numberOfWordsPerCategory, countedBagOfWords.getCategoriesMap());
    }

    /**
     * Method counts words per each category
     * @param bagOfWords bag of words, from which everything is calculated
     * @return table with numbers of words per category
     */
    private HashMap<String, Integer> countWordsPerCategories(HashMap<String, HashMap> bagOfWords){
        HashMap<String, Integer> wordPerCategoryCount = new HashMap<>();
        for(String category : bagOfWords.keySet()){
            int numberOfWords = 0;
            HashMap<String, Integer> helpHash = bagOfWords.get(category);
            for(String word : helpHash.keySet()){
                numberOfWords += helpHash.get(word);
            }
            wordPerCategoryCount.put(category, numberOfWords);
        }
        return wordPerCategoryCount;
    }

    /**
     * Method counts the term frequency
     * @param categoryFrequency table of nuber of words per category
     * @param bagOfWords bag of words, from which everything is calculated
     * @return table of term frequency
     */
    private HashMap<String, HashMap> countTermFrequency(HashMap<String, Integer> categoryFrequency, HashMap<String, HashMap> bagOfWords){
        HashMap<String, HashMap> termFrequency = (HashMap<String, HashMap>) bagOfWords.clone();
        for(String key : categoryFrequency.keySet()){
            HashMap<String, Integer> helpHash = bagOfWords.get(key);
            for(String key2 : helpHash.keySet()){
                termFrequency.get(key).put(key2, count(categoryFrequency.get(key), helpHash.get(key2)));
            }
        }
        return termFrequency;
    }

    /**
     * Method count term frequency (number of occurrences of the word/total number of words in category)
     * @param totalNumberOfWordsInCategory total number of words in category
     * @param numberOfWord number of occurrence of one word
     * @return term frequency
     */
    private double count(int totalNumberOfWordsInCategory, int numberOfWord){
        return (double)numberOfWord/(double)totalNumberOfWordsInCategory;
    }

    /**
     * Getter of termFrequency
     * @return termFrequency
     */
    public HashMap<String, HashMap> getTermFrequency() {
        return termFrequency;
    }
}
