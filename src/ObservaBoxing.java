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

        // Message notifies user of new observer

        System.out.println("New Observer " + this.observerID);

        // Add the observer to the Subjects ArrayList

        boxer.register(this);

    }

    // Called to update all observers

    public void update() {

        this.boxer.upDateLabels();

    }


    //    HurtBox calculation;
    public boolean  notifyRange(){
        return true;

    }
    public void  notifyPunch(Attack a, int attackerId){
//        System.out.println("attack2:  "+a.getAttackName());
        Block b =this.boxer.setSentMessage(a);
        match = Match.getInstance();
        match.setCurrentAttack(attackerId, a, b);
        mp.setSplash(a.getAttackName());



    }

    public void notifyDamage(){



    }
    //never called... to remove
    public int  calculateDamage(){
        //need to get attack and block for this
        Attack attack = null;
        Block block = null;
        HurtBox hb = HurtBox.getInstance();
        int damage =  Integer.parseInt(hb.calculateDamage(attack, block));
        return damage;

    }
    public int  getObserverId(){
        return this.observerID;


    }

    public void  observerCheckDidBLock(){
         this.boxer.checkDidBlock();

    }




}