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
    private ChanceBot rng;
    private int pointsUsed;
    /**
     * Constructor to create attack factory
     */
    public AttackBuilder() {
        attackFactory = new AttackFactory();
        rng = ChanceBot.getInstance();
        int pointsUsed;
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
        //track points spent on attacks
        int pointsSpent = 0;

        //create starting attacks
        buildBasicAttacks();

        //create user attacks
        //set points spent
        pointsUsed = pointsSpent;
        return attackList;
    }
    /**
     *
     * @param unusedPoints int describing how many points player has left to use on special attacks
     * @return the list of attacks containing the starting attacks and any special attacks the that have been randomly selected
     */
    public ArrayList<Attack>buildAIAttacks(int unusedPoints){
        //track points spent on attacks
        int pointsSpent = 0;
        buildBasicAttacks();
        //create attacks from CPU list
        //set points spent
        pointsUsed = pointsSpent;
        return attackList;
    }

    /**
     * Getter for points used during this step of creation
     * @return integer of points used during this step
     */
    public int getPointsUsed() {
        return pointsUsed;
    }
}

