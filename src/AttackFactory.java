/**
 * Created by Brian Trethewey on 11/4/15.
 */
public class AttackFactory {
    //attack type
    private Attack currentAttack = null;

    /**
     *
     * @param inputs A String with pipe delimiters, order of arguments is Attack Name, Attack Type(currently 2 types Heavy and Fast)
     * @return created Attack object
     */
    public Attack create(String inputs){
        //parse String
        String[] inputArray = inputs.split("\\|");
        //get Attack Type
        //perform creation based on attack type
        if (inputArray[1].compareTo("Heavy")==0){
            currentAttack = new SimpleAttack();
            currentAttack.setAttackName(inputArray[0]);
            currentAttack.setAttackType("Heavy");
            currentAttack.setTiming(5);
        }
        else if(inputArray[1].compareTo("Fast") == 0) {
            currentAttack = new SimpleAttack();
            currentAttack.setAttackName(inputArray[0]);
            currentAttack.setAttackType("Fast");
            currentAttack.setTiming(3);
        }
        return currentAttack;
    }
}
