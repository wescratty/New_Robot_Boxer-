/**
 * Created by Brian Trethewey on 11/5/15.
 */
public class SpeedCalculation extends DamageCalculation {
    final double HEAVYATTACKCOEFFICIENT = .10;
    final int FASTATTACKCOEFFIENCT = 1;
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
