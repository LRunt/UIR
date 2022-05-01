import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class {@code Bayes} represents a Naive Bayes classifier
 * @author Lukas Runt
 * @version 1.0 (01-05-2022)
 */
public class Bayes {

    /** Table of symptoms*/
    private HashMap<String, HashMap> symptoms;
    /** sentences to be classified */
    private List<Sentence> sentences;
    /** classified sentences by bayes algorithm*/
    private List<Sentence> clasifiedSentences;
    private HashMap<String, Double> probabilityOfCategories;

    /**
     * Constructor of class {@Bayes}
     * @param symptoms symptoms of train data
     * @param sentences sentences for classification
     */
    public Bayes(HashMap<String, HashMap> symptoms, List<Sentence> sentences){
        this.symptoms = symptoms;
        this.sentences = sentences;
        clasifiedSentences = new ArrayList<>();
    }

    /**
     * Method creates table of probability per each category
     * @param symptoms symptoms of categories
     * @return table of category probability
     */
    private HashMap<String, Double> computeProbabilityOfCategories(HashMap<String, HashMap> symptoms){
        HashMap<String, Double> sumsOfProbabilityPerCategory = new HashMap<>();
        double sumOfAllSymptoms = sumOfAllSymptoms(symptoms);
        for(String categoryKey : symptoms.keySet()){
            HashMap<String, Double> categoryHash = symptoms.get(categoryKey);
            sumsOfProbabilityPerCategory.put(categoryKey, sumOfSymptomPerCategory(categoryHash) / sumOfAllSymptoms);
        }
        return sumsOfProbabilityPerCategory;
    }

    /**
     * Method counts total sum of all symptoms
     * @param symptoms Hashmap of symptoms
     * @return sum of all values of symptoms
     */
    private double sumOfAllSymptoms(HashMap<String, HashMap> symptoms){
        double sum = 0;
        for(String categoryKey : symptoms.keySet()){
            HashMap<String, Double> helpHash = symptoms.get(categoryKey);
            for(String wordKey : helpHash.keySet()){
                sum += (double)symptoms.get(categoryKey).get(wordKey);
            }
        }
        return sum;
    }

    /**
     * Method counts sum of symptoms per category
     * @param categorySymptoms symptoms of category
     * @return sum of category symptoms
     */
    private double sumOfSymptomPerCategory(HashMap<String, Double> categorySymptoms){
        double sumOfCategory = 0;
        for(String wordKey : categorySymptoms.keySet()){
            sumOfCategory += (double)categorySymptoms.get(wordKey);
        }
        return sumOfCategory;
    }


}
