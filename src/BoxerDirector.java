/**
 * Created by Brian Trethewey on 11/4/15.
 */
//todo needs javadoc comments

public class BoxerDirector {
    /**
     * the buliders for making a boxer object
     */
    private BoxerBuilder boxerbuilder;
    private AttackBuilder attackBuilder;
    private BlockBuilder blockBuilder;
    private Boxer boxer;
    int unusedPoints;

    public BoxerDirector(){
        boxerbuilder = new BoxerBuilder();
        attackBuilder = new AttackBuilder();
        blockBuilder = new BlockBuilder();
    }

    /**
     * creates the boxer object for a player
     * @param startingPoints number of points the player can spend on their stats
     * @param boxerID an identifier for the boxer to differentiate players ect.
     * @return  a boxer object created by the player
     */
    public Boxer build(int startingPoints, String boxerID){
        this.unusedPoints = startingPoints;
        boxer = boxerbuilder.createBoxer(unusedPoints, boxerID);
        unusedPoints = unusedPoints - boxerbuilder.getPointsUsed();
        boxer.setAttackList(attackBuilder.buildAttacks(unusedPoints));
        unusedPoints = unusedPoints - attackBuilder.getPointsUsed();
        boxer.setBlockList(blockBuilder.buildBlocks(unusedPoints));
        boxer.reset();
        return boxer;
    }

    /**
     *create a boxer with randomized stats for the AI
     * @param startingPoints number of points available
     * @return a boxer object
     */
    public Boxer buildAI(int startingPoints){
        this.unusedPoints = startingPoints;
        boxer = boxerbuilder.createAIBoxer(unusedPoints);
        unusedPoints = unusedPoints - boxerbuilder.getPointsUsed();
        boxer.setAttackList(attackBuilder.buildAIAttacks(unusedPoints));
        unusedPoints = unusedPoints - attackBuilder.getPointsUsed();
        boxer.setBlockList(blockBuilder.buildBlocks(unusedPoints));
        boxer.reset();
        return boxer;
    }
}
