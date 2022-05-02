import java.util.HashMap;
import java.util.List;

/**
 * Class {@code TermFrequency} represents term frequency in every category
 * @author Lukas Runt
 * @version 1.0 (30-04-2022)
 */
public class TermFrequency implements IParametriable{

    /** HashMap with term frequency*/
    private HashMap<String,HashMap> symptoms;

    /**
     * Constructor of class {@code TermFrequency}
     * @param countedBagOfWords bagOfWords, where in counted how many times the words appear in each category
     * @param trainData train data from which will the symptoms created
     */
    public TermFrequency(BagOfWords countedBagOfWords, List<Sentence> trainData){
       this.symptoms = createSymptoms(countedBagOfWords.getSymptoms(), trainData);
    }

    /**
     * Constructor of class {@code TermFrequency}
     * @param trainData train data from which will the symptoms created
     * @param  listOfCategories list of possible categories
     */
    public TermFrequency(List<Sentence> trainData, List<String> listOfCategories){
        BagOfWords bag = new BagOfWords(trainData, listOfCategories);
        this.symptoms = createSymptoms(bag.getSymptoms(), trainData);
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
     * @param emptyMap map of bagOfWords
     * @param trainData training data
     * @return table of term frequency
     */
    @Override
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        HashMap<String, Double> numberOfWordsPerCategory = countWordsPerCategories(emptyMap);
        for(String key : numberOfWordsPerCategory.keySet()){
            HashMap<String, Double> helpHash = emptyMap.get(key);
            for(String key2 : helpHash.keySet()){
                emptyMap.get(key).put(key2, count(numberOfWordsPerCategory.get(key), helpHash.get(key2)));
            }
        }
        return emptyMap;
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
    @Override
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
