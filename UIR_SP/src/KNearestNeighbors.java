import java.util.*;

/**
 * Class {@code KNearestNeighbors} represents a classification with euclid distance by N nearest neighbors
 * @author Luaks Runt
 * @version 1.1(14-05-2022)
 */
public class KNearestNeighbors {

    /** List of classified sentences*/
    private List<Sentence> classifiedSentences;
    /** number of nearest neighbors*/
    private int k;

    /**
     * Constructor of {@code KNearestNeighbors}
     * @param symptoms symptoms of train data
     * @param sentences sentences to classify
     * @param k number of nearest neighbors
     */
    public KNearestNeighbors(List<Sentence> symptoms, List<Sentence> sentences, int k){
        this.k = k;
        this.classifiedSentences = classifySentences(symptoms, sentences);
    }

    /**
     * Constructor of {@code KNearestNeighbors}, for classify of one sentence
     * @param k number of nearest neighbors
     */
    public KNearestNeighbors(int k){
        this.k = k;
    }

    /**
     * Method classify sentences with KNearestNeighbors classify algorithm
     * @param testSentences list of train sentences
     * @param trainSentences list of sentences to classify
     * @return list of classified sentences
     */
    private List<Sentence> classifySentences(List<Sentence> trainSentences, List<Sentence> testSentences){
        List<Sentence> classifiedSentences = new ArrayList<>();
        for(Sentence testSentence : testSentences){
            classifiedSentences.add(classifySentence(testSentence, trainSentences));
        }
        return classifiedSentences;
    }

    /**
     * Method classify one sentence with KNearestNeighbors classify algorithm
     * @param sentence sentence to classify
     * @param trainSentences list of train sentences
     * @return classified sentence
     */
    public Sentence classifySentence(Sentence sentence, List<Sentence> trainSentences){
        List<SentenceWithDistance> distanceOfSentences = new ArrayList<>();
        for(Sentence trainSentence : trainSentences){
            distanceOfSentences.add(new SentenceWithDistance(trainSentence, euclideanDistance(trainSentence.symptoms, sentence.symptoms)));
        }
        Collections.sort(distanceOfSentences);
        HashMap<String, Integer> numberOfOccurrences = new HashMap<>();
        for(int i = 0; i < k; i++){
            String category = distanceOfSentences.get(i).sentence.category;
            if(numberOfOccurrences.containsKey(category)){
                int count = numberOfOccurrences.get(category);
                numberOfOccurrences.put(category, count + 1);
            }else{
                numberOfOccurrences.put(category, 1);
            }
        }
        String bestCategory = "";
        int biggestNumber = 0;
        for(String category : numberOfOccurrences.keySet()){
            if(biggestNumber < numberOfOccurrences.get(category)){
                biggestNumber = numberOfOccurrences.get(category);
                bestCategory = category;
            }
        }
        return new Sentence(bestCategory, sentence.text);
    }

    /**
     * Method counts euclidean distances between two sentences
     * @param trainSentence sentence from train data
     * @param testSentence sentence form test data
     * @return euclidean sentence
     */
    private double euclideanDistance(HashMap<String, Double> trainSentence, HashMap<String, Double> testSentence){
        double distance = 0;
        for(String word : testSentence.keySet()){
            if(trainSentence.containsKey(word)){
                distance += (testSentence.get(word) - trainSentence.get(word)) * (testSentence.get(word) - trainSentence.get(word));
            }else{
                distance += (testSentence.get(word) - 0) * (testSentence.get(word) - 0);
            }

        }
        return Math.sqrt(distance);
    }

    public List<Sentence> getClassifiedSentences() {
        return classifiedSentences;
    }
}
