import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermFrequency {

    private HashMap<String,HashMap> termFrequency;
    private HashMap<String, Integer> numberOfWordsPerCategory;

    public TermFrequency(List<Sentence> trainData){
        this.termFrequency = new HashMap<>();
        this.numberOfWordsPerCategory = new HashMap<>();
        fillTables(trainData);
        this.numberOfWordsPerCategory = countWordsPerCategories(termFrequency);
        countTermFrequency(numberOfWordsPerCategory, termFrequency);
    }

    /**
     * Method fill table with train data
     * @param trainData train sentences
     */
    private void fillTables(List<Sentence> trainData){
        for(Sentence sentence : trainData){
            if(!termFrequency.containsKey(sentence.category)) {
                termFrequency.put(sentence.category, new HashMap<>());
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
                if(termFrequency.get(category).containsKey(word)){
                    int count = (int)termFrequency.get(category).get(word);
                    termFrequency.get(category).put(word, count + 1);
                }else {
                    termFrequency.get(category).put(word, 1);
                }
            }
        }
    }

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

    private void countTermFrequency(HashMap<String, Integer> categoryFrequency, HashMap<String, HashMap> termFrequency){
        for(String key : categoryFrequency.keySet()){
            HashMap<String, Integer> helpHash = termFrequency.get(key);
            for(String key2 : helpHash.keySet()){
                termFrequency.get(key).put(key2, count(categoryFrequency.get(key), helpHash.get(key2)));
            }
        }
    }

    private double count(int totalNumberOfWordsInCategory, int numberOfWord){
        return (double)numberOfWord/(double)totalNumberOfWordsInCategory;
    }
}
