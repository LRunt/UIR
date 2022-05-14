import java.util.HashMap;
import java.util.List;

/**
 * Class {@code SymptomsForBayes} represents data for classification with bayes,
 * @author Lukas Runt
 * @version 1.0 (28-04-2022)
 */
public class SymptomsForBayes{

    /** Table of words per category */
    private HashMap<String, HashMap> symptoms;

    /**
     * Constructor of class {@code SymptomsForBayes}
     * @param trainData training sentences from inputFile
     * @param listOfCategories list of all possible categories
     */
    public SymptomsForBayes(List<Sentence> trainData, List<String> listOfCategories){
        //this.symptoms = Utils.createHashMapOfCategories(listOfCategories);
        this.symptoms = createSymptoms(Utils.createHashMapOfCategories(listOfCategories), trainData);
    }

    /**
     * Consroctor of class {@code SymptomsForBayes}
     * @param trainData training sentences from inputFile
     */
    public SymptomsForBayes(List<Sentence> trainData){
        this.symptoms = createSymptoms(createEmptyMap(trainData), trainData);
    }

    /**
     *
     * @param trainSentence
     * @return
     */
    private HashMap<String, HashMap> createEmptyMap(List<Sentence> trainSentence){
        HashMap<String, HashMap> emptyMap = new HashMap<>();
        for(Sentence sentence : trainSentence){
            if(!emptyMap.containsKey(sentence.category)){
                emptyMap.put(sentence.category, new HashMap<>());
            }
        }
        return emptyMap;
    }

    /**
     * Method fill table with train data
     * @param trainData train sentences
     */
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        for(Sentence sentence : trainData){
            HashMap<String, Double> wordMap = sentence.symptoms;
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
     * Getter of bagOfWords
     * @return HashMap of words per category
     */
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
