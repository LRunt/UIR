import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The program for classification of sentences from comics
 * @author Lukas Runt
 * @version 1.0 (21-04-2022)
 */
public class Main {

    /**
     * Method loads data from file
     * @param fileName name of file from which is data reading
     * @return list of lines
     */
    public static List<String> loadData(String fileName){
        List<String> listOfLines = null;
        try {
            listOfLines = Files.readAllLines(Paths.get(fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return listOfLines;
    }

    /**
     * Method creates sentences
     * @param data list of lines
     * @return list of sentences
     */
    public static List<Sentence> createSentences(List<String> data){
        List<Sentence> trainData = new ArrayList<>();
        for(String sentence : data){
            String[] splitData = sentence.split(" ", 2);
            trainData.add(new Sentence(splitData[0], splitData[1]));
        }
        return trainData;
    }

    /**
     * Method creates symptoms
     * @param method
     * @param sentences
     */
    public static void setMethod(String method, List<Sentence> sentences){
        for(Sentence sentence : sentences){
            if(method.equals("BOW") || method.equals("Bag of words")){
                sentence.symptoms = sentence.BOW;
            }else if(method.equals("TF") || method.equals("Term Frequency")){
                sentence.symptoms = sentence.TF;
            }else if(method.equals("TF-IDF") || method.equals("Term Frequency - Inverse Document Frequency")){
                Utils.countTFIDF(sentences);
                break;
            }else {
                System.out.println("Wrong parameter function!");
            }
        }
    }

    public static void allMethods(List<Sentence> trainData, List<Sentence> testData, List<String> listOfCategories){
        BagOfWords bagOfWords = new BagOfWords(trainData, listOfCategories);
        TermFrequency termFrequency = new TermFrequency(trainData, listOfCategories);
        TF_IDF inverseDocumentFrequency = new TF_IDF(trainData, listOfCategories);
        Bayes bayesClassification = new Bayes(bagOfWords.getSymptoms(), testData);
        System.out.printf("Bayes - Bag of words: %.02f%%\n", Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
        bayesClassification = new Bayes(termFrequency.getSymptoms(), testData);
        System.out.printf("Bayes - Term Frequency: %.02f%%\n", Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
        bayesClassification = new Bayes(inverseDocumentFrequency.getSymptoms(), testData);
        System.out.printf("Bayes - TF-IDF: %.02f%%\n", Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
        KNearestNeighbors neighbors = new KNearestNeighbors(trainData, testData, 5);
        System.out.printf("knn - BOW: %.02f%%\n", Utils.compareResults(testData, neighbors.getClassifiedSentences()));
    }

    /**
     * Entry point of the program
     * @param args input arguments
     */
    public static void main(String[] args){
        Utils utils = new Utils();
        List<String> listOfCategories = loadData(args[0]);
        List<String> listOfLines = loadData(args[2]);
        List<Sentence> trainData = createSentences(listOfLines);
        //Utils.countTFIDF(trainData);
        List<String> listOfTestSentences = loadData(args[1]);
        List<Sentence> testData = createSentences(listOfTestSentences);
        //Utils.countTFIDF(testData);

        setMethod(args[3], trainData);
        setMethod(args[3], testData);

        allMethods(trainData, testData, listOfCategories);
    }
}
