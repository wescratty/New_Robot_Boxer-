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

    public BoxerDirector(){
        boxerbuilder = new BoxerBuilder();
        attackBuilder = new AttackBuilder();
        blockBuilder = new BlockBuilder();
    }

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
