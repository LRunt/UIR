import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The program for classification of sentences from comics
 * @author Lukas Runt
 * @version 2.0 (14-05-2022)
 */
public class Main {

    /** Label in GUI of category of input sentence*/
    public static JLabel category;

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

    /**
     * Method performs all the symptom and classification methods and evaluates its success
     * @param trainData training dataset
     * @param testData test dataset
     * @param listOfCategories list of possible categories
     */
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

    /**
     * Method choice which parameter method will be used
     * @param trainData train dataset
     * @param testData test dataset
     * @param listOfCategories list of possible categories
     * @param parameterAlgo name of parameter algorithm
     * @param klasificationAlgo name of classification algorithm
     * @param modelName model which will be used
     */
    public static void methodChoice(List<Sentence> trainData, List<Sentence> testData,List<String> listOfCategories, String parameterAlgo, String klasificationAlgo, String modelName){
        setMethod(parameterAlgo, trainData);
        setMethod(parameterAlgo, testData);
        if(klasificationAlgo.equals("KNN")){
            KNearestNeighbors neighbors = new KNearestNeighbors(trainData, testData, 5);
            System.out.printf("%s - %s: %.02f%%\n",klasificationAlgo, parameterAlgo, Utils.compareResults(testData, neighbors.getClassifiedSentences()));
            Utils.saveModel(modelName, trainData, klasificationAlgo, parameterAlgo);
        }else if(klasificationAlgo.equals("Bayes")){
            SymptomsForBayes symptoms = new SymptomsForBayes(trainData, listOfCategories);
            Bayes bayesClassification = new Bayes(symptoms.getSymptoms(), testData);
            System.out.printf("%s - %s: %.02f%%\n",klasificationAlgo, parameterAlgo, Utils.compareResults(testData, bayesClassification.getClassifiedSentences()));
            Utils.saveModel(modelName, trainData, klasificationAlgo, parameterAlgo);
        }else{
            System.out.print("Wrong classification algorithm.\n Try KNN or Bayes.");
        }
    }

    /**
     * Method load model and creates list of sentences
     * @param listOfLines list of lines from input file
     * @return list of sentences
     */
    public static List<Sentence> loadModel(List<String> listOfLines){
        List<Sentence> sentences = new ArrayList<>();
        String category = "";
        for(int i = 1; i < listOfLines.size(); i++){
            if(listOfLines.get(i).contains("#")){
                String[] categoryNumber = listOfLines.get(i).split(";");
                category = categoryNumber[0].replace("#", "");
                HashMap<String, Double> symptomsOfSentence = new HashMap<>();
                for(int j = 0; j < Integer.parseInt(categoryNumber[1]); j++){
                    i++;
                    String[] split = listOfLines.get(i).split(";");
                    symptomsOfSentence.put(split[0].replace("|",""), Double.parseDouble(split[1]));
                }
                sentences.add(new Sentence(category, symptomsOfSentence));
            }
        }
        return sentences;
    }

    /**
     * Method classifies the sentence
     * @param sentence sentence which will be classified
     * @param model model of symptoms
     * @param algorithm classifying what will be used
     * @param parameter parameter method what will be used
     */
    private static void classifySentence(String sentence, List<Sentence> model, String algorithm, String parameter) {
        List<Sentence> testSentence = new ArrayList<>();
        Sentence sentenceToClassify = new Sentence("None", sentence);
        testSentence.add(sentenceToClassify);
        setMethod(parameter, testSentence);
        if(algorithm.equals("KNN")){
            KNearestNeighbors knn = new KNearestNeighbors(5);
            Sentence classifiedSentence = knn.classifySentence(sentenceToClassify, model);
            category.setText(classifiedSentence.category);
        }else if(algorithm.equals("Bayes")){
            SymptomsForBayes bayesSymptoms = new SymptomsForBayes(model);
            Bayes bayes = new Bayes(bayesSymptoms.getSymptoms(), testSentence);
            Sentence classifiedSentence = bayes.getClassifiedSentences().get(0);
            category.setText(classifiedSentence.category);
        }else{
            System.out.println("Wrong algorithm of model!");
        }
    }

    /**
     * Entry point of the program
     * @param args input arguments
     */
    public static void main(String[] args){
        //Opening the GUI - arguments: model
        if(args.length == 1){
            List<String> modelData = loadData(args[0]);
            List<Sentence> model = loadModel(modelData);
            String algorithm = modelData.get(0).split(";")[0];
            String parameter = modelData.get(0).split(";")[1];
            //Creating GUI
            JFrame okno = new JFrame();
            okno.setTitle("UIR - classification of sentences");

            okno.setMinimumSize(new Dimension(550, 150));

            JTextField textField =new JTextField();
            textField.setPreferredSize(new Dimension(300,20));
            JButton classifyButton = new JButton("Classify");
            classifyButton.addActionListener(e -> classifySentence(textField.getText(), model, algorithm, parameter));

            okno.setSize(600,150);

            JPanel classifyPanel = new JPanel();
            classifyPanel.add(textField);
            classifyPanel.add(classifyButton);

            JPanel labelPanel = new JPanel();
            JLabel description = new JLabel("The sentence is: ");
            category = new JLabel("");

            labelPanel.add(description);
            labelPanel.add(category);

            okno.add(classifyPanel, BorderLayout.CENTER);
            okno.add(labelPanel, BorderLayout.SOUTH);

            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
            okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
            okno.setVisible(true);
            //List of all methods with success rates - Arguments: listOfCategories, trainData, testData
        }else if(args.length == 3){
            List<String> listOfCategories = loadData(args[0]);
            List<String> listOfLines = loadData(args[2]);
            List<Sentence> trainData = createSentences(listOfLines);
            List<String> listOfTestSentences = loadData(args[1]);
            List<Sentence> testData = createSentences(listOfTestSentences);
            allMethods(trainData, testData, listOfCategories);
            //Creating a model - Arguments: listOfCategories, trainData, testData, parameterAlgorithm, classificationAlgorithm, nameOfModel
        } else if(args.length == 6){
            List<String> listOfCategories = loadData(args[0]);
            List<String> listOfLines = loadData(args[2]);
            List<Sentence> trainData = createSentences(listOfLines);
            List<String> listOfTestSentences = loadData(args[1]);
            List<Sentence> testData = createSentences(listOfTestSentences);
            methodChoice(trainData, testData, listOfCategories, args[3], args[4], args[5]);
            //Wrong number of arguments
        }else{
            System.out.println("Wrong parameters!");
        }

    }
}
