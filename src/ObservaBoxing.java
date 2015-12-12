/**
 * Created by wescratty on 10/31/15.
 */

// Represents each Observer that is monitoring changes in the subject

public class ObservaBoxing implements Observer {

    // Static used as a counter

    private static int observerIDTracker = 0;


    // Used to track the observers

    private int observerID;

    // Will hold reference to object

    private Subject boxer;
    private Match match = Match.getInstance();
    private MainPanel mp = MainPanel.getInstance();

    public ObservaBoxing(Subject boxer){

        this.boxer = boxer;

        // Assign an observer ID and increment the static counter

        this.observerID = ++observerIDTracker;

        // Add the observer to the Subjects ArrayList

        boxer.register(this);

    }

    // Called to update all observers

    public void update() {
        this.boxer.upDateLabels();
    }


    /**
     * Set match of attack and set gui current attack
     * @param a Attack
     * @param attackerId the boxer whom is attacking
     */
    public void  notifyPunch(Attack a, int attackerId){
        Block b =this.boxer.setSentMessage(a);
        match = Match.getInstance();
        match.setCurrentAttack(attackerId, a, b);
        mp.setSplash(a.getAttackName());
    }

    public int  getObserverId(){
        return this.observerID;
    }

    public void  observerCheckDidBLock(){
         this.boxer.checkDidBlock();
    }
}