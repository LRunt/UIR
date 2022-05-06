public class SentenceWithDistance implements Comparable<SentenceWithDistance>{

    public Sentence sentence;
    public double distance;

    public SentenceWithDistance(Sentence sentence, double distance){
        this.sentence = sentence;
        this.distance = distance;
    }


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
