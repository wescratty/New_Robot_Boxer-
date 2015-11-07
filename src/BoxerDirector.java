/**
 * Created by Brian Trethewey on 11/4/15.
 */
//todo needs javadoc comments

public class BoxerDirector {

    private BoxerBuilder boxerbuilder;
    private AttackBuilder attackBuilder;
    private BlockBuilder blockBuilder;
    private Boxer boxer;
    int unusedPoints;

    public Boxer build(int startingPoints){
        this.unusedPoints = startingPoints;
        boxer = boxerbuilder.createBoxer(unusedPoints);
        unusedPoints = unusedPoints - boxerbuilder.getPointsUsed();
        boxer.setAttackList(attackBuilder.buildAttacks(unusedPoints));
        unusedPoints = unusedPoints - attackBuilder.getPointsUsed();
        boxer.setBlockList(blockBuilder.buildBlocks(unusedPoints));
        return boxer;
    }

    public Boxer buildAI(int startingPoints){
        this.unusedPoints = startingPoints;
        boxer = boxerbuilder.createAIBoxer(unusedPoints);
        unusedPoints = unusedPoints - boxerbuilder.getPointsUsed();
        boxer.setAttackList(attackBuilder.buildAIAttacks(unusedPoints));
        unusedPoints = unusedPoints - attackBuilder.getPointsUsed();
        boxer.setBlockList(blockBuilder.buildBlocks(unusedPoints));
        return boxer;
    }
}