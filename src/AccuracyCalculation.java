/**
 * Created by Brian Trethewey on 11/14/15.
 */
public class AccuracyCalculation extends DamageCalculation {
    private final double GLANCINGCHANCE = 0.10;
    private final double GLANCECOEEFICIENT = 0.5;
    private final double CRITCHANCE = 0.10;
    private final double CRITCOEEFICIENT = 1.1;
    /**
     *Calculates the chance of the attack hitting, also performs the crit anc glancing blows chance
     * @param attack the Attack that is being performed
     * @param block the Block, if any, that is being applied to the attack\
     * @param damage the currently calculated damage of the attack
     * @return A String Detailing the results of the damage calculations, String so more than just a value can be returned if desired
     */
    @Override
    public String execute(Attack attack, Block block, int damage) {
        ChanceBot chance = ChanceBot.getInstance();
        double randomValue = chance.getChance();
        String attackStats = attack.getStats();
        String accuracyString = attackStats.split("\\|")[2];
        int accuracyPercent = Integer.parseInt(accuracyString);
        int newDamage = 0;

        double accuracy = (double) accuracyPercent / 100.0;

        if (randomValue > accuracy + CRITCHANCE) {
            newDamage = (int) Math.round(damage * CRITCOEEFICIENT);
        } else if (randomValue > accuracy) {
            newDamage = damage;
        } else if (randomValue > accuracy - GLANCINGCHANCE) {
            newDamage = (int) Math.round(damage * GLANCECOEEFICIENT);
        }else{
//            System.out.print("Miss");
        }



        return successor.execute(attack,block,newDamage);
    }
}
