/**
 * Created by Brian Trethewey on 11/5/15.
 */


public class FinishCalculation extends DamageCalculation {
    /**
     *performs any final modifications and starts the return for the calculation
     * @param attack the Attack that is being performed
     * @param block the Block, if any, that is being applied to the attack\
     * @param damage the currently calculated damage of the attack
     * @return A String Detailing the results of the damage calculations, String so more than just a value can be returned if desired
     */
    @Override
    public String execute(Attack attack, Block block, int damage) {
        return Integer.toString(damage);
    }
}
