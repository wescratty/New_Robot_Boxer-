/**
 * Created by sinless on 11/4/15.
 */

public class BoxerBuilder {
    private Boxer boxer;
    private RNG rng;
    private int pointsUsed;

    public BoxerBuilder() {
        rng = RNG.getInstance();
    }

    /**
     * Function for the creation of a boxer this includes the initial pass for stat allocation
     * @param unusedPoints number of points the player has available to assign to stats
     * @return A boxer object with the stats supplied by the player
     */
    public Boxer createBoxer(int unusedPoints){
        boxer = new Boxer();
        pointsUsed =  setStats(unusedPoints);
        return boxer;
    }
    /**
     * Function for the autonomous creation of a boxer this includes the initial pass for stat allocation
     * @param unusedPoints number of points the computer has available to assign to stats
     * @return A boxer object with the stats created via heuristic
     */
    public Boxer createAIBoxer(int unusedPoints){
        boxer = new Boxer();
        setAIStats(unusedPoints);
        return boxer;
    }

    /**
     * Method to handle the allocation of stats to the boxer
     * @param unusedPoints number of points the player has available to assign to stats
     * @return number of points spent during this step
     */
    private int setStats(int unusedPoints){
        int pointsSpent = 0;
        //todo add dialogue box call here
        //call dialogue box
        //set boxer stats
        return pointsSpent;
    }

    /**
     * Method that controls the allocation of stats to a non-player boxer
     * @param unusedPoints number of points the computer has available to assign to stats
     * @return number of points spent during this step
     */
    private int setAIStats(int unusedPoints){
        int pointsSpent = 0;
        //create random stats
        //todo create random stats code
        //set boxer stats
        return pointsSpent;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }
}
