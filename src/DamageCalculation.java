/**
 * Created by Brian Trethewey on 11/4/15.
 */
//*Abstract for the Chain of Responsibility Classes
public abstract class DamageCalculation {


    private DamageCalculation successor = null;


    //setter
    public void setSuccessor(DamageCalculation successor) {
        this.successor = successor;
    }


    //abstract methods will be overwritten to preform task specified by that step
    public abstract String execute(Attack attack, Block block);


}
