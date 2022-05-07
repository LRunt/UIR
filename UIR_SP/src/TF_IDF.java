import java.util.HashMap;
import java.util.List;

/**
 * Class {@code TF-IDF} represents algorithm of Term frequency - inverse document frequency
 * @author Lukas Runt
 * @version 1.0 (30-04-2022)
 */
public class TF_IDF{

    /** Number of occurrences of words between categories */
    private HashMap<String, Integer> occurencyBetweenCategories;
    /** Table of Term Frequency - Inverse Document Frequency*/
    private HashMap<String, HashMap> symptoms;

    /**
     * Constructor of class {@code TF_IDF}
     */
    public TF_IDF(List<Sentence> trainData, List<String> listOfCategories){
        this.symptoms = Utils.createHashMapOfCategories(listOfCategories);
        symptoms = createSymptoms(Utils.createHashMapOfCategories(listOfCategories), trainData);
    }

    /**
     * Method fill table with train data
     * @param trainData train sentences
     */
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        for(Sentence sentence : trainData){
            HashMap<String, Double> wordMap = sentence.TFIDF;
            for(String word : wordMap.keySet()){
                if (emptyMap.get(sentence.category).containsKey(word)) {
                    double count = (double)emptyMap.get(sentence.category).get(word);
                    emptyMap.get(sentence.category).put(word, count + 1);
                } else {
                    emptyMap.get(sentence.category).put(word, wordMap.get(word));
                }
            }
        }
        return emptyMap;
    }

    /**
     * Getter of term frequency - inverse document frequency
     * @return term frequency - inverse document frequency
     */
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
