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
        pointsUsed =  initiateBoxer(unusedPoints);
        return boxer;
    }
    /**
     * Function for the autonomous creation of a boxer this includes the initial pass for stat allocation
     * @param unusedPoints number of points the computer has available to assign to stats
     * @return A boxer object with the stats created via heuristic
     */
    public Boxer createAIBoxer(int unusedPoints){
        boxer = new Boxer();
        initiateAI(unusedPoints);
        return boxer;
    }

    /**
     * Method to handle the allocation of stats to the boxer
     * @param unusedPoints number of points the player has available to assign to stats
     * @return number of points spent during this step
     */
    private int initiateBoxer(int unusedPoints){
        int pointsSpent = 0;
        //todo add dialogue box call here
        //call dialogue box
        String stats = "";
        //call dialogue box
        //todo get dialogebox working
        //stats = dialogueBox.gatherStats(unusedPoints);
        //set boxer stats
        setStats(stats);
        //set boxer stats
        return pointsSpent;
    }

    /**
     * Method that controls the allocation of stats to a non-player boxer
     * @param unusedPoints number of points the computer has available to assign to stats
     * @return number of points spent during this step
     */
    private int initiateAI(int unusedPoints){
        int pointsSpent = 0;

        String stats = "";
        //create random stats
        //todo create random stats code
        //set boxer stats
        setStats(stats);
        return pointsSpent;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    private void setStats(String inputs){

        String[] inputArray = inputs.split("|");
        int strength = 0;
        int speed =0;
        int accuracy = 0;
        int reach = 0;
        try {
            strength = Integer.parseInt(inputArray[0]);
            speed = Integer.parseInt(inputArray[1]);
            accuracy = Integer.parseInt(inputArray[2]);
            reach = Integer.parseInt(inputArray[3]);
        }catch (Exception e){
           System.out.println(e.toString());
        }

        boxer.setAgilityScore(speed);
        boxer.setStrengthScore(strength);
        boxer.setAccuracy(accuracy);
        boxer.setReach(reach);
        boxer.setFatigue(0);
    }
}
