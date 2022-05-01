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
     * Entry point of the program
     * @param args input arguments
     */
    public static void main(String[] args){
        List<String> listOfLines = loadData("train.txt");
        List<Sentence> trainData = createSentences(listOfLines);
        List<String> listOfTestSentences = loadData("train.txt");
        List<Sentence> testData = createSentences(listOfTestSentences);
        BagOfWords bagOfWords = new BagOfWords(trainData);
        TermFrequency termFrequency = new TermFrequency(bagOfWords);
        TF_IDF inverseDocumentFrequency = new TF_IDF(termFrequency, bagOfWords);
        Bayes bayesClassification = new Bayes(bagOfWords.getCategoriesMap(), testData);
        System.out.println();
    }
}
