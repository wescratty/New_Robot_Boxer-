
import java.util.ArrayList;

/**
 * Created by wescratty on 10/31/15.
 */
public class Boxer implements Subject {

    private int id;
    private int bNum;
    private int fatigue;
    private int strengthScore;
    private int agilityScore;
    private int accuracy;
    private int reach;
    private int punchTime = 200;
    private int punchedTime = 100;
    int punchNum =0;
    private RNG rng;
    private Point center;
    private AudioPlayer player;
    //    private ArrayList<Attack> attackList;
    //private block:Block
    int x,y = 0;
    public Point location = new Point(x, y);
    private int exp;

    private ArrayList<Observer> observers;

    public  boolean sentMessage = false;
    public  boolean didBLock = false;
    private  ChanceBot chance = new ChanceBot();


// constructor
    public Boxer() {

        observers = new ArrayList<Observer>();

    }

    public void setid(int id, int bNum){
        this.id = id;
        this.bNum = bNum;
    }

    public int getid(){
        return this.id;
    }


    public int selectMove(){

        checkForPunch();

        int choice =chance.getRandomChoice();
        if(choice==0){
            System.out.println("Boxer with id: " + this.id + " decided to punch"+ "Punch:  ");
            punch();
        }else if(choice==1) {
            System.out.println("Boxer with id: "+this.id+" decided to stand there");
        }else if(choice==2) {
            System.out.println("Boxer with id: " + this.id + " decided to move");
            sleepTime(chance.getRandomAttackDelay());

        }
        checkForPunch();


        return 0;
    }

    public void setSentMessage(){
        sentMessage = true;
        System.out.println("Boxer with id: " + this.id + " got message about punch: ");

    }


    public void register(Observer newObserver) {

        // Adds a new observer to the ArrayList

        observers.add(newObserver);

    }

    public void unregister(Observer deleteObserver) {

        // Get the index of the observer to delete

        int observerIndex = observers.indexOf(deleteObserver);

        // Print out message (Have to increment index to match)

        System.out.println("Observer " + (observerIndex+1) + " deleted");

        // Removes observer from the ArrayList

        observers.remove(observerIndex);

    }

    public void notifyObserverOfPunch() {

        // Cycle through all observers and notifies them

        for(Observer observer : observers){
            if(observer.getObserverId()!=this.bNum) {

                observer.notifyPunch();//ibmPrice, aaplPrice, googPrice
                System.out.println("Notifying Observer " + (observer.getObserverId()));
            }

        }
    }


    public void notifyObserver() {

        // Cycle through all observers and notifies them

        for(Observer observer : observers){
            if(observer.getObserverId()!=this.bNum) {

                observer.update();//ibmPrice, aaplPrice, googPrice
                System.out.println("Notifying Observer " + (observer.getObserverId()));
            }

        }
    }


    public void observerCheckDidBLock() {

        // Cycle through all observers and notifies them
        for(Observer observer : observers){
            if(observer.getObserverId()!=this.bNum) {

                observer.observerCheckDidBLock();//ibmPrice, aaplPrice, googPrice
                System.out.println("Notifying Observer " + (observer.getObserverId()));
            }

        }
    }



    public void punch(){

            notifyObserverOfPunch();  //punch in motion
            sleepTime(punchTime);  // wait
            observerCheckDidBLock();  // see if blocked

    }

    public void checkDidBlock(){
        AudioPlayer player = AudioPlayer.getInstance();

        if(this.didBLock){
            System.out.println(this.id+" blocked punch");
            player.blockSound();
        }else{
            System.out.println(this.id+" got Punched in face");
            player.punchSound();
            sleepTime(punchedTime);

        }
        this.didBLock = false;
    }


    public void checkForPunch(){
        if(this.sentMessage){
            this.sentMessage= false;
            this.didBLock = true;
        }
    }

    public void sleepTime(int sleepTime){
        try {

            Thread.sleep(sleepTime);  // wait

        }catch(InterruptedException e)
        {}//TODO actually deal with exception

    }




}

//
////    public location move(): {
////
////    }
////    public Attack getAttack(){
////
////    }
//
//    private void updateAttacks(){
//
//    }
//
//    //    private Attack updateAttack(int fatigue, Attack attack){
////
////    }
//    public void getAction(){
//
//    }
//    //    public Block getBlock(){
////
////    }
////    public Block updateBlock(int fatigue, Block block){
////
////    }
//    public void reset(){
//
//    }
//    private void resetAttacks(){
//
//    }
//    private void resetBlocks(){
//
//    }
////    public grow()<String in to dialogue, string out of dialogue pipe delim, attribute order,unused points leader>