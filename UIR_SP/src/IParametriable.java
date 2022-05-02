import java.util.HashMap;
import java.util.List;

public interface IParametriable {

    public HashMap<String, HashMap> createSymptoms(HashMap<String, HashMap> emptyMap, List<Sentence> trainData);

    public HashMap<String, HashMap> getSymptoms();
}
