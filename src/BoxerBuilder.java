/**
 * Created by sinless on 11/4/15.
 */
public class BoxerBuilder {
    private Boxer boxer;
    private RNG rng;

    public BoxerBuilder() {
        rng = RNG.getInstance();
    }

    private Boxer setStats(int unusedPoints){
        boxer = new Boxer();
        //call dialogue box
        // set user specified stats
        return boxer;
    }

    private Boxer setAIStats(int unusedPoints){
        boxer = new Boxer();
        //set random stats

        return boxer;
    }
}
