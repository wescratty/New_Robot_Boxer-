/**
 * Created by Brian Trethewey on 11/4/15.
 */

//todo this class
public class HurtBox {

    //singleton stuff
    private static HurtBox ourInstance = new HurtBox();

    public static HurtBox getInstance() {
        return ourInstance;
    }

    //these variables hold the different Chain of Responsibility heads for the different linked lists for calculating damage
    private DamageCalculation heavyCalculation;
    private DamageCalculation fastAttack;
    private ChanceBot chance = ChanceBot.getInstance();


    /**
     * Function to wrap the calculation of damage from any part of the altercation
     * @param attack The attack object that represents the punch thrown
     * @param block The block Object if the punch has been defended or null if not, this is handled in the chain
     * @return a string containing damage caused and potentially any other data.
     */
    public String calculateDamage(Attack attack, Block block){
        int damage = chance.getRandomChoice(100);
        if(block!=null){
            damage*= .4;
        }

        return Integer.toString(damage);
    }

}
