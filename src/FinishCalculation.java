/**
 * Created by Brian Trethewey on 11/5/15.
 */
public class FinishCalculation extends DamageCalculation {
    @Override
    public String execute(Attack attack, Block block, int damage) {
        return Integer.toString(damage);
    }
}
