import java.util.HashMap;

/**
 * Class {@code TF-IDF} represents algorithm of Term frequency - inverse document frequency
 * @author Lukas Runt
 * @version 1.0 (30-04-2022)
 */
public class TF_IDF {

    /** Number of occurrences of words between categories */
    private HashMap<String, Integer> occurencyBetweenCategories;
    /** Table of Term Frequency - Inverse Document Frequency*/
    private HashMap<String, HashMap> termFrequencyInverseDocumentFrequency;

    /**
     * Constructor of class {@code TF_IDF}
     * @param termFrequency
     * @param bagOfWords
     */
    public TF_IDF(TermFrequency termFrequency, BagOfWords bagOfWords){
        this.occurencyBetweenCategories = countNumberOfOccurency(bagOfWords.getCategoriesMap());
        this.termFrequencyInverseDocumentFrequency = countTF_IDF(termFrequency.getTermFrequency(), occurencyBetweenCategories);
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
     * @param termFrequency table of term frequency
     * @param occurencyBetweenCategories table of occurency in categories
     * @return Ter frequency - invert document frequency
     */
    private HashMap<String, HashMap> countTF_IDF(HashMap<String, HashMap> termFrequency, HashMap<String, Integer> occurencyBetweenCategories){
        HashMap<String, HashMap> termFrequencyInverseDocumentFrequency = new HashMap<>();
        for(String categoryKey : termFrequency.keySet()){
            termFrequencyInverseDocumentFrequency.put(categoryKey, new HashMap<String, Double>());
            HashMap<String, Integer> helpHash = termFrequency.get(categoryKey);
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
}
