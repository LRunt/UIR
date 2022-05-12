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
            }else {
                System.out.println("Wrong parameter function!");
            }
        }
    }

    public static void allMethods(List<Sentence> trainData, List<Sentence> testData, List<String> listOfCategories){
        setMethod("BOW", trainData);
        setMethod("BOW", testData);
        SymptomsForBayes symptoms = new SymptomsForBayes(trainData, listOfCategories);
        Bayes bayesClassification = new Bayes(symptoms.getSymptoms(), testData);
        System.out.printf("Bayes - Bag of words: %.02f%%\n", Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
        KNearestNeighbors neighbors = new KNearestNeighbors(trainData, testData, 5);
        System.out.printf("knn - Bag of words: %.02f%%\n", Utils.compareResults(testData, neighbors.getClassifiedSentences()));
        setMethod("TF", trainData);
        setMethod("TF", testData);
        symptoms = new SymptomsForBayes(trainData, listOfCategories);
        bayesClassification = new Bayes(symptoms.getSymptoms(), testData);
        System.out.printf("Bayes - Term Frequency: %.02f%%\n", Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
        neighbors = new KNearestNeighbors(trainData, testData, 5);
        System.out.printf("knn - Term Frequency: %.02f%%\n", Utils.compareResults(testData, neighbors.getClassifiedSentences()));
        setMethod("TF-IDF", trainData);
        setMethod("TF-IDF", testData);
        symptoms = new SymptomsForBayes(trainData, listOfCategories);
        bayesClassification = new Bayes(symptoms.getSymptoms(), testData);
        System.out.printf("Bayes - TF-IDF: %.02f%%\n", Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
        neighbors = new KNearestNeighbors(trainData, testData, 5);
        System.out.printf("knn - TF-IDF: %.02f%%\n", Utils.compareResults(testData, neighbors.getClassifiedSentences()));
    }

    public static void methodChoice(List<Sentence> trainData, List<Sentence> testData,List<String> listOfCategories, String parameterAlgo, String klasificationAlgo, String modelName){
        setMethod(parameterAlgo, trainData);
        setMethod(parameterAlgo, testData);
        if(klasificationAlgo.equals("KNN")){
            KNearestNeighbors neighbors = new KNearestNeighbors(trainData, testData, 5);
            System.out.printf("%s - %s: %.02f%%\n",klasificationAlgo, parameterAlgo, Utils.compareResults(testData, neighbors.getClassifiedSentences()));
            Utils.saveModel(modelName, trainData, klasificationAlgo);
        }else if(klasificationAlgo.equals("Bayes")){
            SymptomsForBayes symptoms = new SymptomsForBayes(trainData, listOfCategories);
            Bayes bayesClassification = new Bayes(symptoms.getSymptoms(), testData);
            System.out.printf("%s - %s: %.02f%%\n",klasificationAlgo, parameterAlgo, Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
            Utils.saveModel(modelName, trainData, klasificationAlgo);
        }else{
            System.out.print("Wrong classification algorithm.\n Try KNN or Bayes.");
        }
    }

    public static void loadModel(){

    }

    /**
     * Entry point of the program
     * @param args input arguments
     */
    public static void main(String[] args){
        if(args.length == 1){

        }else if(args.length == 3){
            List<String> listOfCategories = loadData(args[0]);
            List<String> listOfLines = loadData(args[2]);
            List<Sentence> trainData = createSentences(listOfLines);
            List<String> listOfTestSentences = loadData(args[1]);
            List<Sentence> testData = createSentences(listOfTestSentences);
            allMethods(trainData, testData, listOfCategories);
        } else if(args.length == 6){
            List<String> listOfCategories = loadData(args[0]);
            List<String> listOfLines = loadData(args[2]);
            List<Sentence> trainData = createSentences(listOfLines);
            List<String> listOfTestSentences = loadData(args[1]);
            List<Sentence> testData = createSentences(listOfTestSentences);
            methodChoice(trainData, testData, listOfCategories, args[3], args[4], args[5]);
        }else{
            System.out.println("Wrong parameters!");
        }

    }
}
