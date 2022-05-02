import java.util.List;

/**
 * Class {@code Utils} represents utils for classifications
 * @author Lukas Runt
 * @version 1.0 (02-05-2022)
 */
public class Utils {

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
}
