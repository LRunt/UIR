import java.util.HashMap;
import java.util.List;

/**
 * Class {@code TF-IDF} represents algorithm of Term frequency - inverse document frequency
 * @author Lukas Runt
 * @version 1.0 (30-04-2022)
 */
public class TF_IDF implements IParametriable{

    /** Number of occurrences of words between categories */
    private HashMap<String, Integer> occurencyBetweenCategories;
    /** Table of Term Frequency - Inverse Document Frequency*/
    private HashMap<String, HashMap> symptoms;

    /**
     * Constructor of class {@code TF_IDF}
     * @param termFrequency
     * @param bagOfWords
     */
    public TF_IDF(TermFrequency termFrequency, BagOfWords bagOfWords){
        this.occurencyBetweenCategories = countNumberOfOccurency(bagOfWords.getSymptoms());
        this.symptoms = countTF_IDF(termFrequency.getSymptoms(), occurencyBetweenCategories);
    }

    public TF_IDF(List<Sentence> trainData, List<String> listOfCategories){
        BagOfWords bag = new BagOfWords(trainData, listOfCategories);
        TermFrequency termFrequency = new TermFrequency(bag, trainData);
        this.occurencyBetweenCategories = countNumberOfOccurency(bag.getSymptoms());
        this.symptoms = createSymptoms(termFrequency.getSymptoms(), trainData);
    }

    /**
     * Method count number of occurrences of words between categories
     * @param bagOfWords Table of number of words per category
     * @return number of occurrences of words between categories
     */
    private HashMap<String, Integer> countNumberOfOccurency(HashMap<String, HashMap> bagOfWords){
        HashMap<String, Integer> countOfOccurency = new HashMap<>();
        for(String categoryKey : bagOfWords.keySet()){
            HashMap<String, Integer> helpHash = bagOfWords.get(categoryKey);
            for(String wordKey : helpHash.keySet()){
                if(countOfOccurency.containsKey(wordKey)){
                    int count = countOfOccurency.get(wordKey);
                    countOfOccurency.put(wordKey, count + 1);
                }else{
                    countOfOccurency.put(wordKey, 1);
                }
            }
        }
        return countOfOccurency;
    }

    /**
     * Method count Term Frequency - Invert Document Frequency
     * @param emptyMap table of term frequency
     * @param trainData train data from which will the symptoms created
     * @return Ter frequency - invert document frequency
     */
    @Override
    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData) {
        HashMap<String, HashMap> termFrequencyInverseDocumentFrequency = new HashMap<>();
        for(String categoryKey : emptyMap.keySet()){
            termFrequencyInverseDocumentFrequency.put(categoryKey, new HashMap<String, Double>());
            HashMap<String, Double> helpHash = emptyMap.get(categoryKey);
            for(String wordKey : helpHash.keySet()){
                termFrequencyInverseDocumentFrequency.get(categoryKey).put(wordKey, countIDF(occurencyBetweenCategories.get(wordKey), occurencyBetweenCategories.size()) * (Double)emptyMap.get(categoryKey).get(wordKey));
            }
        }
        return termFrequencyInverseDocumentFrequency;
    }

    /**
     * Method count Term Frequency - Invert Document Frequency
     * @param termFrequency table of term frequency
     * @param occurencyBetweenCategories table of occurency in categories
     * @return Ter frequency - invert document frequency
     */
    private HashMap<String, HashMap> countTF_IDF(HashMap<String, HashMap> termFrequency, HashMap<String, Integer> occurencyBetweenCategories){
        HashMap<String, HashMap> termFrequencyInverseDocumentFrequency = new HashMap<>();
        for(String categoryKey : termFrequency.keySet()){
            termFrequencyInverseDocumentFrequency.put(categoryKey, new HashMap<String, Double>());
            HashMap<String, Double> helpHash = termFrequency.get(categoryKey);
            for(String wordKey : helpHash.keySet()){
                termFrequencyInverseDocumentFrequency.get(categoryKey).put(wordKey, countIDF(occurencyBetweenCategories.get(wordKey), occurencyBetweenCategories.size()) * (Double)termFrequency.get(categoryKey).get(wordKey));
            }
        }
        return termFrequencyInverseDocumentFrequency;
    }

    /**
     * Method counts inverse document frequency
     * @param occurencyBetweenCategories in how many documents did the word appear
     * @param numberOfCategories number of all categories
     * @return inverse document frequency
     */
    private Double countIDF(int occurencyBetweenCategories, int numberOfCategories){
        return Math.log(numberOfCategories/occurencyBetweenCategories);
    }

    /**
     * Getter of term frequency - inverse document frequency
     * @return term frequency - inverse document frequency
     */
    @Override
    public HashMap<String, HashMap> getSymptoms() {
        return symptoms;
    }
}
