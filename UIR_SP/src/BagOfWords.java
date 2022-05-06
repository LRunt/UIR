import java.util.HashMap;
import java.util.List;

/**
 * Class {@code BagOfWords} represents bag with categories,
 * where each category has counted how many words were contained in the training data per category
 * @author Lukas Runt
 * @version 1.0 (28-04-2022)
 */
public class BagOfWords implements IParametriable{

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
    @Override
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        for(Sentence sentence : trainData){
            putWords(sentence.category, sentence.text, emptyMap);
        }
        return emptyMap;
    }

    /**
     * Mathod count increase number of words of sentence in dictionary
     * @param category category of sentence
     * @param text sentence content
     */
    private void putWords(String category, String text, HashMap<String, HashMap> emptyMap){
        String[] words = text.split("[^0-9A-Za-z']");
        for(String word : words){
            if(!word.equals("")){
                if(emptyMap.get(category).containsKey(word)){
                    double count = (double) emptyMap.get(category).get(word);
                    emptyMap.get(category).put(word, count + 1.0);
                }else {
                    emptyMap.get(category).put(word, 1.0);
                }
            }
        }
    }

    /**
     * Getter of bagOfWords
     * @return HashMap of words per category
     */
    @Override
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
