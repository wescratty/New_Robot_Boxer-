import java.util.ArrayList;

/**
 * Created by Brian Trethewey on 11/4/15.
 */
//todo this class
public class AttackBuilder {
    // this is the list of attacks to be returned
    private ArrayList<Attack> attackList;
    //factory for building individual attacks
    private  AttackFactory attackFactory;
    private RNG rng;
    /**
     * Constructor to create attack factory
     */
    public AttackBuilder() {
        attackFactory = new AttackFactory();
        rng = RNG.getInstance();
    }

    /**
     *
     * @return Array List containing the starting attacks
     */
    private ArrayList<Attack> buildBasicAttacks(){
        //create new attackList
        attackList = new ArrayList<Attack>();
        //create a basic heavy and fast attack
        attackList.add(buildSimpleAttack("Hook|Heavy"));
        attackList.add(buildSimpleAttack("Jab|Fast"));
        //return for completeness
        return attackList;
    }

    /**
     *
     * @param inputs A String with pipe delimiters, order of arguments is Attack Name, Attack Type(currently 2 types Heavy and Fast)
     * @return Attack described by the String
     */
    private Attack buildSimpleAttack(String inputs){
        return attackFactory.create(inputs);
    }


    /**
     *
     * @param unusedPoints int describing how many points player has left to use on special attacks
     * @return the list of attacks containing the starting attacks and any special attacks the player has specified
     */
    public ArrayList<Attack>buildAttacks(int unusedPoints){
        //create starting attacks
        buildBasicAttacks();
        return attackList;
    }
    /**
     *
     * @param unusedPoints int describing how many points player has left to use on special attacks
     * @return the list of attacks containing the starting attacks and any special attacks the that have been randomly selected
     */
    public ArrayList<Attack>buildAIAttacks(int unusedPoints){
        buildBasicAttacks();
        return attackList;
    }
}

