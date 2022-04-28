/**
 * Class {@code Sentence} is messenger of one sentence
 * @author Lukas Runt
 * @version 1.0 (28-04-2022)
 */
public class Sentence {
    /**A category of sentence */
    public String category;
    /** A sentence content */
    public String text;

    /**
     * Constructor of class sentence
     * @param category category of sentence
     * @param text the sentence
     */
    public Sentence(String category, String text){
        this.category = category;
        this.text = text;
    }
}
