/**
 * Created by Brian Trethewey on 11/14/15.
 */
public class AccuracyCalculation extends DamageCalculation {
    private final double GLANCINGCHANCE = 0.10;
    private final double GLANCECOEEFICIENT = 0.5;
    private final double CRITCHANCE = 0.10;
    private final double CRITCOEEFICIENT = 1.1;

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
