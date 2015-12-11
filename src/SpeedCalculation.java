/**
 * Created by Brian Trethewey on 11/5/15.
 */
public class SpeedCalculation extends DamageCalculation {
    final double HEAVYATTACKCOEFFICIENT = .10;
    final int FASTATTACKCOEFFIENCT = 1;

    /**
     *Calculates the initial damage of fast attacks and the speed attributes contribution to heavy attacks
     * @param attack the Attack that is being performed
     * @param block the Block, if any, that is being applied to the attack\
     * @param damage the currently calculated damage of the attack
     * @return A String Detailing the results of the damage calculations, String so more than just a value can be returned if desired
     */
    @Override
    public String execute(Attack attack, Block block, int damage) {
        int newDamage = 0;
        String attackStats = attack.getStats();
        String speedString = attackStats.split("\\|")[1];
        int speed = Integer.parseInt(speedString);
        if (damage == 0){
            newDamage = speed * FASTATTACKCOEFFIENCT;
        }else{
            newDamage = (int)Math.round(damage *(1+(speed*HEAVYATTACKCOEFFICIENT)));
        }
        return successor.execute(attack,block, newDamage);
    }
}
