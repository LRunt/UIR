import java.util.HashMap;
import java.util.List;

/**
 * Class {@code BagOfWords} represents bag with categories,
 * where each category has counted how many words were contained in the training data per category
 * @author Lukas Runt
 * @version 1.0 (28-04-2022)
 */
public class BagOfWords {

    /** Table of words per category */
    private HashMap<String, HashMap> categoriesMap;

    /**
     * Constructor of class {@code BagOfWords}
     * @param trainData training sentences from inputFile
     */
    public BagOfWords(List<Sentence> trainData){
        this.categoriesMap = new HashMap<>();
        fillTables(trainData);
    }

    /**
     * Method fill table with train data
     * @param trainData train sentences
     */
    private void fillTables(List<Sentence> trainData){
        for(Sentence sentence : trainData){
          if(!categoriesMap.containsKey(sentence.category)) {
              categoriesMap.put(sentence.category, new HashMap<String, Double>());
          }
              putWords(sentence.category, sentence.text);
        }
    }

    /**
     * Mathod count increase number of words of sentence in dictionary
     * @param category category of sentence
     * @param text sentence content
     */
    private void putWords(String category, String text){
        String[] words = text.split("\\W");
        for(String word : words){
            if(!word.equals("")){
                if(categoriesMap.get(category).containsKey(word)){
                    double count = (double)categoriesMap.get(category).get(word);
                    categoriesMap.get(category).put(word, count + 1.0);
                }else {
                    categoriesMap.get(category).put(word, 1.0);
                }
            }
        }
    }

    /**
     * Getter of bagOfWords
     * @return HashMap of words per category
     */
    public HashMap<String, HashMap> getCategoriesMap() {
        return categoriesMap;
    }
}
