/**
 * Created by Brian Trethewey on 11/5/15.
 */
//todo this class
public class BlockCalculation extends DamageCalculation {
    final double HEAVYATTACKSTRENGHTCOEFFICIENT = 3;
    final double HEAVYATTACKSSPEEDCOEFFICENT = 0.35;
    final double FASTATTACKSTRENGTHCOEFFIENCT = 2;
    final double FASTATTACKSPEEDCOEEFICIENT = 0.20;

    @Override
    public String execute(Attack attack, Block block, int damage) {
        //if there is no block
        if (block ==null){
            return successor.execute(attack,block,damage);
        }
        //block calculation
        int newDamage = 0;
        int blockAmount = 0;
        String attackType = attack.getAttackType();
        String blockStats = block.getStats();
        String[] blockArray = blockStats.split("\\|");
        int strength = Integer.parseInt(blockArray[0]);

        int speed = Integer.parseInt(blockArray[1]);
        int accuracy = Integer.parseInt(blockArray[3]);

        if (attackType.compareTo("Heavy")==0){
            blockAmount = (int)Math.round(strength * HEAVYATTACKSTRENGHTCOEFFICIENT);
            blockAmount*=speed*HEAVYATTACKSSPEEDCOEFFICENT;
        }else if(attackType.compareTo("Fast")==0) {
            blockAmount =(int)Math.round (speed * FASTATTACKSPEEDCOEEFICIENT);
            blockAmount*=strength*FASTATTACKSTRENGTHCOEFFIENCT;
        }
        newDamage = damage - blockAmount;
        return successor.execute(attack,block,newDamage);
    }
}
