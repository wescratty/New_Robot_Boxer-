/**
 * Created by wescratty on 10/31/15.
 */
//public interface ObservaBoxing {


// Represents each Observer that is monitoring changes in the subject

public class ObservaBoxing implements Observer {



    // Static used as a counter

    private static int observerIDTracker = 0;


    // Used to track the observers

    private int observerID;

    // Will hold reference to the StockGrabber object

    private Subject boxer;

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

        this.boxer.setSentMessage();

    }


    //    HurtBox calculation;
    public boolean  notifyRange(){
        return true;

    }
    public boolean  notifyPunch(){
        this.boxer.setSentMessage();
        return true;
    }

    public int notifyDamage(){
        return 0;

    }
    public int  calculateDamage(){
        return 0;


    }
    public int  getObserverId(){
        return this.observerID;


    }

    public void  observerCheckDidBLock(){
         this.boxer.checkDidBlock();

    }




}