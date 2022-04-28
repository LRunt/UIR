import java.nio.file.Files;
import java.nio.file.Paths;
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
     * Method creates train data
     * @param data list of lines
     * @return list of train data
     */
    public static List<String> createTrainData(List<String> data){

        return null;
    }

    public static void main(String[] args){
        List<String> trainData = loadData("data.txt");

    }
}
