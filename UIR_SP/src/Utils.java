import java.util.HashMap;
import java.util.List;

/**
 * Class {@code Utils} represents utils for classifications
 * @author Lukas Runt
 * @version 1.0 (02-05-2022)
 */
public class Utils {

    /**
     * Method creates list of hashMap of symptoms
     * @param categories list of categories
     * @return empty hashMap of symptoms
     */
    public static HashMap<String, HashMap> createHashMapOfCategories(List<String> categories){
        HashMap<String, HashMap> emptySymptoms = new HashMap<>();
        for(String category : categories){
            emptySymptoms.put(category, new HashMap<>());
        }
        return emptySymptoms;
    }

    /**
     * Method compares result
     * @param goodResults right classified sentences
     * @param classifiedSentences sentences classified by classifiers
     * @return correct classifications in %
     */
    public double compareResults(List<Sentence> goodResults,List<Sentence> classifiedSentences){
        if(goodResults.size() != classifiedSentences.size()){
            System.err.println("Arrays are not same. They have different size!");
            return 0;
        }
        int numberOfSentences = goodResults.size();
        int correctlyClassified = 0;
        for(int i = 0; i < numberOfSentences; i++){
            if(goodResults.get(i).category.equals(classifiedSentences.get(i).category)){
                correctlyClassified++;
            }
        }
        return ((double)correctlyClassified/(double)numberOfSentences)*100;
    }

    /**
     * Method counts Term Frequency - Inverse Document Frequency of all sentences
     * @param sentences list of sentences where we want count the TF-IDF
     */
    public static void countTFIDF(List<Sentence> sentences){
        int numberOfSentences = sentences.size();
        HashMap<String, Integer> numberOfOccurrences = new HashMap<>();
        for(Sentence sentence : sentences){
            for(String word : sentence.BOW.keySet()){
                if(numberOfOccurrences.containsKey(word)){
                    int count = numberOfOccurrences.get(word);
                    numberOfOccurrences.put(word, count + 1);
                }else{
                    numberOfOccurrences.put(word, 1);
                }
            }
        }
        for(Sentence sentence : sentences){
            for(String word : sentence.TF.keySet()){
                double tfidf = Math.log((double)numberOfOccurrences.get(word)/(double)numberOfSentences);
                sentence.TFIDF.put(word, tfidf * sentence.TF.get(word));
            }
            sentence.symptoms = sentence.TFIDF;
        }
    }

    public static void createModel(){

    }
}
