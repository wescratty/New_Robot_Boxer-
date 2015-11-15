/**
 * Created by Brian Trethewey on 11/5/15.
 */
public class StrengthCalculation extends DamageCalculation {

    final int HEAVYATTACKCOEFFICIENT = 3;
    final int FASTATTACKCOEFFIENCT = 20;
    @Override
    public String execute(Attack attack, Block block, int damage) {
        int newDamage = 0;
        String attackStats = attack.getStats();
        String strengthString = attackStats.split("\\|")[0];
        int strength = Integer.parseInt(strengthString);
        if (damage == 0){
            newDamage = strength * HEAVYATTACKCOEFFICIENT;
        }else{
            newDamage = damage *(1+strength/FASTATTACKCOEFFIENCT);
        }
        return successor.execute(attack,block, newDamage);
    }
}
