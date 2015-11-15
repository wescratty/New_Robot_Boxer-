/**
 * Created by Brian Trethewey on 11/5/15.
 */
//todo This Class
public class SpeedCalculation extends DamageCalculation {
    final int HEAVYATTACKCOEFFICIENT = 3;
    final int FASTATTACKCOEFFIENCT = 20;
    @Override
    public String execute(Attack attack, Block block, int damage) {
        int newDamage = 0;
        String attackStats = attack.getStats();
        String speedString = attackStats.split("\\|")[1];
        int speed = Integer.parseInt(speedString);
        if (damage == 0){
            newDamage = speed * HEAVYATTACKCOEFFICIENT;
        }else{
            newDamage = damage *(1+speed/FASTATTACKCOEFFIENCT);
        }
        return successor.execute(attack,block, newDamage);
    }
}
