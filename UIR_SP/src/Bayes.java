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
    /** Sentences to be classified */
    private List<Sentence> sentences;
    /** Classified sentences by bayes algorithm*/
    private List<Sentence> classifiedSentences;
    /** Table of probabilities of categories*/
    private HashMap<String, Double> probabilityOfCategories;
    /** Minimal value in symptoms*/
    private double minValue;

    /**
     * Constructor of class {@Bayes}
     * @param symptoms symptoms of train data
     * @param sentences sentences for classification
     */
    public Bayes(HashMap<String, HashMap> symptoms, List<Sentence> sentences){
        this.symptoms = symptoms;
        this.sentences = sentences;
        this.probabilityOfCategories = computeProbabilityOfCategories(symptoms);
        this.classifiedSentences = classifySentences(sentences, probabilityOfCategories);
    }

    /**
     * Method classify sentences
     * @param sentences sentences which will be classified
     * @param categoryProbability probabilities of each category
     * @return list of classified sentences
     */
    private List<Sentence> classifySentences(List<Sentence> sentences, HashMap<String, Double> categoryProbability){
        List<Sentence> classifiedSentences = new ArrayList<>();
        for(Sentence sentence : sentences){
            HashMap<String, Double> probabilities = computeTableOfProbabilities(sentence.text, categoryProbability);
            String assignedCategory = assignCategory(probabilities);
            classifiedSentences.add(new Sentence(assignedCategory, sentence.text));
        }
        return classifiedSentences;
    }

    /**
     * Method picks the category with the biggest probability
     * @param probabilities probabilities of each categories
     * @return the category with the biggest probability
     */
    private String assignCategory(HashMap<String, Double> probabilities){
        double maxProbability = 0;
        String nameOfCategory = "";
        for(String key : probabilities.keySet()){
            if(maxProbability < probabilities.get(key)){
                maxProbability = probabilities.get(key);
                nameOfCategory = key;
            }
        }
        return nameOfCategory;
    }

    /**
     * Method computes all category probabilities
     * @param sentence
     * @param categoryProbability
     * @return probabilities of all categories
     */
    private HashMap<String, Double> computeTableOfProbabilities(String sentence, HashMap<String, Double> categoryProbability){
        HashMap<String, Double> probabilityTable = new HashMap<>();
        for(String categoryKey : categoryProbability.keySet()){
            probabilityTable.put(categoryKey, computeProbability(sentence, categoryKey, categoryProbability, this.symptoms, this.minValue));
        }
        return probabilityTable;
    }

    /**
     * Method counts the probability, that sentence asserts to the category
     * @param sentence sentence who will assigned
     * @param category category which is counted
     * @param categoryProbability probability of each category
     * @param symptoms symptoms of the train data
     * @param minimalValue minimal value of symptom
     * @return probability of one category
     */
    private double computeProbability(String sentence, String category, HashMap<String, Double> categoryProbability,HashMap<String, HashMap> symptoms, double minimalValue){
        String[] words = sentence.split("\\W");
        double[] probalities = new double[words.length];
        for(int i = 0; i < words.length; i++){
            if(symptoms.get(category).containsKey(words[i])){
                probalities[i] = (double)symptoms.get(category).get(words[i]) + minimalValue;
            }else{
                probalities[i] = minimalValue;
            }
        }
        double probality = categoryProbability.get(category);
        for(int i = 0; i < probalities.length; i++){
            probality *= probalities[i];
        }
        return probality;
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
        double minimalValue = Double.MAX_VALUE;
        for(String categoryKey : symptoms.keySet()){
            HashMap<String, Double> helpHash = symptoms.get(categoryKey);
            for(String wordKey : helpHash.keySet()){
                double symptomValue = (double)symptoms.get(categoryKey).get(wordKey);
                sum += symptomValue;
                if(minimalValue > symptomValue){
                    minimalValue = symptomValue;
                }
            }
        }
        this.minValue = minimalValue;
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
            sumOfCategory += categorySymptoms.get(wordKey);
        }
        return sumOfCategory;
    }

    /**
     * Getter of classified sentences
     * @return classified sentences
     */
    public List<Sentence> getClassifiedSentences() {
        return classifiedSentences;
    }
}