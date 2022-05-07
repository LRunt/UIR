import java.util.HashMap;
import java.util.List;

/**
 * Class {@code TermFrequency} represents term frequency in every category
 * @author Lukas Runt
 * @version 1.0 (30-04-2022)
 */
public class TermFrequency{

    /** HashMap with term frequency*/
    private HashMap<String,HashMap> symptoms;

    /**
     * Constructor of class {@code TermFrequency}
     * @param trainData train data from which will the symptoms created
     */
    public TermFrequency(List<Sentence> trainData, List<String> listOfCategories){
        this.symptoms = Utils.createHashMapOfCategories(listOfCategories);
        symptoms = createSymptoms(Utils.createHashMapOfCategories(listOfCategories), trainData);
    }


    /**
     * Method fill table with train data
     * @param trainData train sentences
     */
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        for(Sentence sentence : trainData){
            HashMap<String, Double> wordMap = sentence.TF;
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
     * Getter of termFrequency
     * @return termFrequency
     */
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
