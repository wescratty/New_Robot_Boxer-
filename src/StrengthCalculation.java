/**
 * Created by Brian Trethewey on 11/5/15.
 */
public class StrengthCalculation extends DamageCalculation {

    final double HEAVYATTACKCOEFFICIENT = 1.5;
    final double FASTATTACKCOEFFIENCT = 0.15;
    /**
     *calculates the initial damage for a heavy attack and strengths contribution to a fast attack
     * @param attack the Attack that is being performed
     * @param block the Block, if any, that is being applied to the attack\
     * @param damage the currently calculated damage of the attack
     * @return A String Detailing the results of the damage calculations, String so more than just a value can be returned if desired
     */
    @Override
    public String execute(Attack attack, Block block, int damage) {
          int newDamage = 0;
        String attackStats = attack.getStats();
        String strengthString = attackStats.split("\\|")[0];
        int strength = Integer.parseInt(strengthString);
        if (damage == 0){
            newDamage = (int)Math.round(strength * HEAVYATTACKCOEFFICIENT);
        }else{
            newDamage = (int)Math.round(damage *(1+(strength*FASTATTACKCOEFFIENCT)));
        }
        return successor.execute(attack,block, newDamage);
    }
}
