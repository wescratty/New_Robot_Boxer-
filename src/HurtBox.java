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
    private DamageCalculation fastCalculation;
    private ChanceBot chance = ChanceBot.getInstance();


    public HurtBox(){
        //create heavy calculation chain
        heavyCalculation = new StrengthCalculation();
        DamageCalculation  nextNode = new SpeedCalculation();
        heavyCalculation.setSuccessor(nextNode);
        DamageCalculation currentNode = nextNode;
        nextNode = new BlockCalculation();
        currentNode.setSuccessor(nextNode);
        currentNode = nextNode;
        nextNode = new AccuracyCalculation();
        currentNode.setSuccessor(nextNode);
        currentNode = nextNode;
        nextNode =new FinishCalculation();
        currentNode.setSuccessor(nextNode);

        //create fast calculation chain
        fastCalculation = new SpeedCalculation();
        nextNode = new StrengthCalculation();
        fastCalculation.setSuccessor(nextNode);
        currentNode = nextNode;
        nextNode = new BlockCalculation();
        currentNode.setSuccessor(nextNode);
        currentNode = nextNode;
        nextNode = new AccuracyCalculation();
        currentNode.setSuccessor(nextNode);
        currentNode = nextNode;
        nextNode =new FinishCalculation();
        currentNode.setSuccessor(nextNode);
    }


    /**
     * Function to wrap the calculation of damage from any part of the altercation
     * @param attack The attack object that represents the punch thrown
     * @param block The block Object if the punch has been defended or null if not, this is handled in the chain
     * @return a string containing damage caused and potentially any other data.
     */

    public String calculateDamage(Attack attack,Block block, int differentiate){
        String type = attack.getAttackType();
        String result;
        if (type.compareTo("Heavy")==0){
            result =  heavyCalculation.execute(attack,block,0);
        }else{
            result = fastCalculation.execute(attack,block,0);
        }
        return result;

    }

}
