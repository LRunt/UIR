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
    private HashMap<String, Double> numberOfWordsPerCategory;

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
    private HashMap<String, Double> countWordsPerCategories(HashMap<String, HashMap> bagOfWords){
        HashMap<String, Double> wordPerCategoryCount = new HashMap<>();
        for(String category : bagOfWords.keySet()){
            double numberOfWords = 0;
            HashMap<String, Double> helpHash = bagOfWords.get(category);
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
    private HashMap<String, HashMap> countTermFrequency(HashMap<String, Double> categoryFrequency, HashMap<String, HashMap> bagOfWords){
        HashMap<String, HashMap> termFrequency = createDeepCopy(bagOfWords);
        for(String key : categoryFrequency.keySet()){
            HashMap<String, Double> helpHash = bagOfWords.get(key);
            for(String key2 : helpHash.keySet()){
                termFrequency.get(key).put(key2, count(categoryFrequency.get(key), helpHash.get(key2)));
            }
        }
        return termFrequency;
    }

    /**
     * Method creates deep copy of hashMap
     * @param originalHashMap draft of the copied hashMap
     * @return deep copy of original hashMap
     */
    private HashMap<String, HashMap> createDeepCopy(HashMap<String, HashMap> originalHashMap){
        HashMap<String, HashMap> deepCopy = new HashMap<>();
        for(String categoryKey : originalHashMap.keySet()){
            HashMap<String, Double> helpHash = originalHashMap.get(categoryKey);
            deepCopy.put(categoryKey, new HashMap<String, Double>());
            for(String wordKey : helpHash.keySet()){
                deepCopy.get(categoryKey).put(wordKey, helpHash.get(wordKey));
            }
        }
        return deepCopy;
    }


    /**
     * Method count term frequency (number of occurrences of the word/total number of words in category)
     * @param totalNumberOfWordsInCategory total number of words in category
     * @param numberOfWord number of occurrence of one word
     * @return term frequency
     */
    private double count(double totalNumberOfWordsInCategory, double numberOfWord){
        return numberOfWord/totalNumberOfWordsInCategory;
    }

    /**
     * Getter of termFrequency
     * @return termFrequency
     */
    public HashMap<String, HashMap> getTermFrequency() {
        return termFrequency;
    }
}
