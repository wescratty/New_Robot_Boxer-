/**
 * Created by Brian Trethewey on 11/4/15.
 */
//*Abstract for the Chain of Responsibility Classes
public abstract class DamageCalculation {


    private DamageCalculation successor = null;


    //setter

    /**
     *
     * @param successor The next Damage Calculation step
     */

    public void setSuccessor(DamageCalculation successor) {
        this.successor = successor;
    }


    //abstract methods will be overwritten to preform task specified by that step

    /**
     *
     * @param attack the Attack that is being performed
     * @param block the Block, if any, that is being applied to the attack
     * @return A String Detailing the results of the damage calculations, String so more than just a value can be returned if desired
     */
    public abstract String execute(Attack attack, Block block);


}
