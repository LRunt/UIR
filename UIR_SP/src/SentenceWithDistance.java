/**
 *Class {@code SentenceWithDistance} represents messenger pattern for sorting a distances and sentences
 */
public class SentenceWithDistance implements Comparable<SentenceWithDistance>{

    /** Sentence*/
    public Sentence sentence;
    /** distace of sentence to another sentence*/
    public double distance;

    /**
     * Constructor of SentenceWithDistance
     * @param sentence sentence
     * @param distance distance to another sentence
     */
    public SentenceWithDistance(Sentence sentence, double distance){
        this.sentence = sentence;
        this.distance = distance;
    }

    /**
     * Comparation method
     * @param o another instance
     * @return what instance is closer
     */
    @Override
    public int compareTo(SentenceWithDistance o) {
        if(this.distance < o.distance){
            return -1;
        }else if(this.distance > o.distance){
            return 1;
        }else{
            return 0;
        }
    }
}
