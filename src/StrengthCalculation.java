/**
 * Created by Brian Trethewey on 11/5/15.
 */
public class StrengthCalculation extends DamageCalculation {

    final double HEAVYATTACKCOEFFICIENT = 1.5;
    final double FASTATTACKCOEFFIENCT = 0.05;
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
