import java.util.HashMap;
import java.util.List;

/**
 * Class {@code BagOfWords} represents bag with categories,
 * where each category has counted how many words were contained in the training data per category
 * @author Lukas Runt
 * @version 1.0 (28-04-2022)
 */
public class BagOfWords{

    /** Table of words per category */
    private HashMap<String, HashMap> symptoms;

    /**
     * Constructor of class {@code BagOfWords}
     * @param trainData training sentences from inputFile
     */
    public BagOfWords(List<Sentence> trainData, List<String> listOfCategories){
        this.symptoms = Utils.createHashMapOfCategories(listOfCategories);
        symptoms = createSymptoms(Utils.createHashMapOfCategories(listOfCategories), trainData);
    }

    /**
     * Method fill table with train data
     * @param trainData train sentences
     */
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        for(Sentence sentence : trainData){
            HashMap<String, Double> wordMap = sentence.BOW;
            for(String word : wordMap.keySet()){
                if (emptyMap.get(sentence.category).containsKey(word)) {
                    double count = (double)emptyMap.get(sentence.category).get(word);
                    emptyMap.get(sentence.category).put(word, count + 1);
                } else {
                    emptyMap.get(sentence.category).put(word, 1.0);
                }
            }
        }
        return emptyMap;
    }

    /**
     * Getter of bagOfWords
     * @return HashMap of words per category
     */
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
