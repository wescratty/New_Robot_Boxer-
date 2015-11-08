
import java.util.ArrayList;

/**
 * Created by wescratty on 10/31/15.
 */
public class Boxer implements Subject {
    //final for setting the boxers intital stats for all abilities
    final int INTITALSTATS = 10;

    private int id;
    private int bNum;
    private int fatigue;
    private int strengthScore;
    private int agilityScore;
    private int accuracy;
    private int reach;
    private int punchTime = 1000;
    private int punchedTime = 0;
    private int x, y = 0;

    private RNG rng;

    private AudioPlayer player;
    private ArrayList<Attack> attackList;
    private ArrayList<Block> blockList;

    private Point _otherBoxer = new Point(0, 0);

    private Point desiredLocation = new Point(x, y);
    private Point thisBoxerLocation = new Point(x, y);


    private int exp;

    private ArrayList<Observer> observers;

    public boolean sentMessage = false;
    public boolean didBLock = false;
    public boolean attack = true;
    private boolean didPunch = false;
    private ChanceBot chance = new ChanceBot();


    public Boxer() {

        observers = new ArrayList<Observer>();
        strengthScore = INTITALSTATS;
        agilityScore = INTITALSTATS;
        accuracy = INTITALSTATS;
        reach = INTITALSTATS;
    }


    public int selectMove() {

        checkForPunch();
        double dist = distance(thisBoxerLocation, _otherBoxer);
        int choiceCount = 4;
        int choice = chance.getRandomChoice(choiceCount);

        if (_otherBoxer.X() == thisBoxerLocation.X() && _otherBoxer.Y() == thisBoxerLocation.Y()) {
            choice = 2;
        }
        if (dist < 80 && choice == 0) {
//            System.out.println("Boxer with id: " + this.id + " decided to punch"+ "Punch:  ");
            punch();
        } else if (choice == 1) {
            //System.out.println("Boxer with id: "+this.id+" decided to stand there");
        } else if (choice == 2) {
            attack = false;
            changeLocation();
//            System.out.println("Boxer with id: " + this.id + " decided to move");

        } else if (choice == 3) {
            //TODO change out this if for real attack logic

            attack = true;
            desiredLocation = _otherBoxer;

        }

        checkForPunch();
        checkIfAttack();

        return 0;
    }

    private void changeLocation() {
        desiredLocation = chance.pickNewLocation();
//        System.out.println(desiredLocation.X() + ", " + desiredLocation.Y());
    }

    public void move() {
        thisBoxerLocation.setPoint(x, y);

        double dist = distance(thisBoxerLocation, _otherBoxer);
        if (dist < 100 && attack) {
            attack = false;
            desiredLocation = thisBoxerLocation;
//            System.out.println("this one : ");

        }

//        System.out.println("distance : "+dist);

        if (desiredLocation.X() > x) {
            x = x + 10;
        } else if (desiredLocation.X() < x) {
            x = x - 10;
        }
        if (desiredLocation.Y() > y) {
            y = y + 10;
        } else if (desiredLocation.Y() < y) {
            y = y - 10;

        }
        sleepTime(50);

    }


    public void checkIfAttack() {
        if (attack) {
            desiredLocation = _otherBoxer;
        }

    }

    public void setOtherBoxerLoc(Boxer otherBoxer) {

        _otherBoxer.setX(otherBoxer.getX());
        _otherBoxer.setY(otherBoxer.getY());

    }

    public void setSentMessage() {
        sentMessage = true;
//        System.out.println("Boxer with id: " + this.id + " got message about punch: ");

    }


    public void register(Observer newObserver) {

        // Adds a new observer to the ArrayList

        observers.add(newObserver);

    }

    public void unregister(Observer deleteObserver) {

        // Get the index of the observer to delete

        int observerIndex = observers.indexOf(deleteObserver);

        // Print out message (Have to increment index to match)

        System.out.println("Observer " + (observerIndex + 1) + " deleted");

        // Removes observer from the ArrayList

        observers.remove(observerIndex);

    }

    public void notifyObserverOfPunch() {

        // Cycle through all observers and notifies them

        for (Observer observer : observers) {
            if (observer.getObserverId() != this.bNum) {

                observer.notifyPunch();//ibmPrice, aaplPrice, googPrice
//                System.out.println("Notifying Observer " + (observer.getObserverId()));
            }

        }
    }


    public void notifyObserver() {

        // Cycle through all observers and notifies them

        for (Observer observer : observers) {
            if (observer.getObserverId() != this.bNum) {

                observer.update();//ibmPrice, aaplPrice, googPrice
//                System.out.println("Notifying Observer " + (observer.getObserverId()));
            }

        }
    }


    public void observerCheckDidBLock() {

        // Cycle through all observers and notifies them
        for (Observer observer : observers) {
            if (observer.getObserverId() != bNum) {

                observer.observerCheckDidBLock();//ibmPrice, aaplPrice, googPrice
//                System.out.println("Notifying Observer " + (observer.getObserverId()));
            }

        }
    }


    public double distance(Point b_1, Point b_2) {
        return Math.sqrt(Math.pow(b_2.X() - b_1.X(), 2) + Math.pow(b_2.Y() - b_1.Y(), 2));

    }

    public void punch() {

        notifyObserverOfPunch();  //punch in motion
        //TODO make sleeptime reflect punch strangth
        sleepTime(chance.getRandomAttackDelay());  // wait

        if (distance(thisBoxerLocation, _otherBoxer) < 80) {
            didPunch = true;
            observerCheckDidBLock();  // see if blocked
        }

    }

    public boolean getDidPunch() {
        boolean retBool = didPunch;
        didPunch = false;
        return retBool;
    }

    public boolean getDidBlock() {
        boolean retBool = didBLock;
        didBLock = false;
        return retBool;
    }

    //TODO this seems redundant to have both functions getDidBlock and checkDidBlock


    public void checkDidBlock() {
        AudioPlayer player = AudioPlayer.getInstance();

        if (didBLock) {
//            System.out.println(id+" blocked punch");
            player.blockSound();
        } else {
//            System.out.println(id+" got Punched");
            attack = false;
            player.punchSound();
            sleepTime(chance.getRandomAttackDelay());
            //TODO make sleeptime reflect punch strangth
            //TODO make punch graphics

        }
//        didBLock = false;
    }


    public void checkForPunch() {
        if (sentMessage) {
            sentMessage = false;
            didBLock = true;
        }
    }

    public String getStats() {
        String stats = "" + exp + "|" + strengthScore + "|" + agilityScore + "|" + accuracy + "|" + reach + "|" + fatigue;
        return stats;
    }

    public void sleepTime(int sleepTime) {
        try {

            Thread.sleep(sleepTime);  // wait

        } catch (InterruptedException e) {
        }//TODO actually deal with exception

    }

    public Point getBoxerPoint() {
        return thisBoxerLocation;

    }

    public void setLoc(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return this.x;

    }

    public int getY() {
        return this.y;

    }

    public void setid(int id, int bNum) {
        this.id = id;
        this.bNum = bNum;
    }

    public int getid() {
        return this.id;
    }


    public void setStrengthScore(int strengthScore) {
        this.strengthScore = strengthScore;
    }

    public void setAgilityScore(int agilityScore) {
        this.agilityScore = agilityScore;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setAttackList(ArrayList<Attack> attackList) {
        this.attackList = attackList;
    }

    public void setBlockList(ArrayList<Block> blockList) {
        this.blockList = blockList;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }


    public Attack getAttack() {
        int randAttackIdx = chance.getRandomChoice(attackList.size());
        return attackList.get(randAttackIdx);

    }


   private void updateAttacks(){
   for(Attack attack :attackList){
       updateAttack(fatigue,attack);
   }

   }

    private void updateAttack(int fatigue, Attack attack){
        attack.update(fatigue);
    }
//    public void getAction(){
//
//    }
   public Block getBlock(){
       int randBlockIdx = chance.getRandomChoice(blockList.size());
       return blockList.get(randBlockIdx);
    }
    public void updateBlock(int fatigue){
    for(Block block : blockList){
        block.update(fatigue);
    }
    }

    public void reset(){
    fatigue = 0;
    resetAttacks();
    resetBlocks();
    }
    private void resetAttacks(){
        for(Attack attack : attackList){
            attack.refresh(strengthScore,agilityScore,accuracy);
        }
    }
    private void resetBlocks(){
        for(Block block : blockList){
            block.refresh(strengthScore,agilityScore,accuracy);
        }
    }
      public void grow(){

      }//<String in to dialogue, string out of dialogue pipe delim, attribute order,unused points leader>

}