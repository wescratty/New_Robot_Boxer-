/**
 * Created by Brian Trethewey on 11/4/15.
 */

//todo this class
public class HurtBox {
    private static HurtBox ourInstance = new HurtBox();

    public static HurtBox getInstance() {
        return ourInstance;
    }

    //these variables hold the different Chain of Responsibility heads for the different linked lists for calculating damage
    private DamageCalculation heavyCalculation;
    private DamageCalculation fastAttack;
    private ChanceBot chance = ChanceBot.getInstance();

    public String calculateDamage(Attack attack, Block block){
        int damage = chance.getRandomChoice(100);
        if(block!=null){
            damage*= .4;
        }

        return Integer.toString(damage);
    }

}
