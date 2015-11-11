
import java.util.ArrayList;

/**
 * Created by wescratty on 10/31/15.
 */
public class Boxer implements Subject {
    //final for setting the boxers intital stats for all abilities
    final int INTITALSTATS = 10;
    final int INITAILREACH = 80;
    private int id;
    private int bNum;
    private int fatigue;
    private int strengthScore;
    private int agilityScore;
    private int accuracy;
    private int reach;
    //these must be positive nonzero
    private int punchTime = 1000;
    private int punchedTime = 50;
    private int x, y = 0;
    private int stepSize = 10;



    private ArrayList<Attack> attackList;
    private ArrayList<Block> blockList;

    private Point _otherBoxer = new Point(0, 0);

    private Point desiredLocation = new Point(x, y);
    private Point thisBoxerLocation = new Point(x, y);
    public Boxer otherBoxer;

    private String boxerID;

    private int exp;

    private ArrayList<Observer> observers;

    public boolean sentMessage = false;
    public boolean didBLock = false;
    public boolean attack = true;
    private boolean didPunch = false;

    private Attack incomingAttack;
    private ChanceBot chance = ChanceBot.getInstance();
    private AudioPlayer player = new AudioPlayer();
    private HurtBox hurtBox = HurtBox.getInstance();
    private MainPanel mp = MainPanel.getInstance();



    public Boxer() {

        observers = new ArrayList<Observer>();
        strengthScore = INTITALSTATS;
        agilityScore = INTITALSTATS;
        accuracy = INTITALSTATS;
        reach = INITAILREACH;

    }


    public int selectMove() {

        checkForPunch();
        double dist = distance(thisBoxerLocation, _otherBoxer);
        int choiceCount = 4;
        int choice = chance.getRandomChoice(choiceCount);

        if (_otherBoxer.X() == thisBoxerLocation.X() && _otherBoxer.Y() == thisBoxerLocation.Y()) {
            choice = 2;
        }
        if (dist < reach && choice == 0) {

            punch();
        } else if (choice == 1) {
            //todo rest();
        } else if (choice == 2) {
            attack = false;
            changeLocation();

        } else if (choice == 3) {
            //TODO change out this if for real attack logic

            attack = true;
            desiredLocation = _otherBoxer;

        }

        //TODO thinking this should be here once we get fatigue working
//        if (desiredLocation !=thisBoxerLocation){move();}

        checkForPunch();
        checkIfAttack();

        return 0;
    }

    private void changeLocation() {
        desiredLocation = chance.pickNewLocation();
    }

    public void move() {
        thisBoxerLocation.setPoint(x, y);

        double dist = distance(thisBoxerLocation, _otherBoxer);

        if (dist < reach && attack) {

            attack = false;
            desiredLocation = thisBoxerLocation;

        }


        if (desiredLocation.X() > x) {
            x = x + stepSize;
        } else if (desiredLocation.X() < x) {
            x = x - stepSize;
        }
        if (desiredLocation.Y() > y) {
            y = y + stepSize;
        } else if (desiredLocation.Y() < y) {
            y = y - stepSize;

        }

        sleepTime(50);
//        sleepTime(fatigue);??TODO

    }

//    notifyObserverOfMove()

    public void checkIfAttack() {
        if (attack) {
            desiredLocation = _otherBoxer;
        }

    }

    public void setOtherBoxer(Boxer otherBoxer) {


        this.otherBoxer = otherBoxer;
        upDateLabels();

    }

    public void upDateOtherBoxerLoc(){
        _otherBoxer.setX(otherBoxer.getX());
        _otherBoxer.setY(otherBoxer.getY());
    }

    public Block setSentMessage(Attack a) {
        sentMessage = true;
        incomingAttack = a;
        return  getBlock();
        //todo do something with this attack from other boxer

    }
    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
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

        //todo not sure if this is what you were thinking------------------
        Attack a = getAttack();
        a.setAttackName(a.getAttackName());
        mp.setSplash(a.getAttackName());


        for (Observer observer : observers) {
            if (observer.getObserverId() != this.bNum) {

                observer.notifyPunch(a,this.bNum);
            }

        }
    }

    public void notifyObserverOfMove() {

        // Cycle through all observers and notifies them

        for (Observer observer : observers) {
            if (observer.getObserverId() != this.bNum) {

//                observer.todo();
            }

        }
    }

    public void upDateLabels(){

        mp.setLables(getStats(), getid(), this.otherBoxer.getStats(),this.otherBoxer.getid());

    }


    public void notifyObserver() {

        // Cycle through all observers and notifies them

        for (Observer observer : observers) {
            if (observer.getObserverId() != this.bNum) {

                observer.update();
            }

        }
    }


    public void observerCheckDidBLock() {

        // Cycle through all observers and notifies them
        for (Observer observer : observers) {
            if (observer.getObserverId() != bNum) {

                observer.observerCheckDidBLock();
            }

        }
    }

    public int getFatigue() {
        return fatigue;
    }

    public double distance(Point b_1, Point b_2) {
        return Math.sqrt(Math.pow(b_2.X() - b_1.X(), 2) + Math.pow(b_2.Y() - b_1.Y(), 2));

    }

    public void punch() {

        notifyObserverOfPunch();  //punch in motion
        //TODO make sleeptime reflect punch strangth
        sleepTime(chance.getRandomAttackDelay(punchTime+fatigue));  // wait

        if (distance(thisBoxerLocation, _otherBoxer) < reach) {
            didPunch = true;
            observerCheckDidBLock();  // see if blocked
        }

//        upDateLabels();
    }

    public boolean getDidPunch() {
        boolean retBool = didPunch;
        didPunch = false;
        return retBool;
    }

    public boolean getDidBlock() {
        boolean retBool = didBLock;
//        didBLock = false;
        return retBool;
    }


    //TODO this seems redundant to have both functions getDidBlock and checkDidBlock


    public void checkDidBlock() {


        takeDamage(Integer.parseInt(hurtBox.calculateDamage(incomingAttack,getBlock())));

        if(didBLock){
            player.blockSound();
            didBLock = false;
        }else{

            attack = false;
            player.punchSound();
            sleepTime(chance.getRandomAttackDelay(punchedTime+fatigue));
            //TODO make sleeptime reflect punch strength


        }
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

    public void takeDamage(int damage){
        if (damage>= 0){
            fatigue += damage;
            updateAttacks(fatigue);
            updateBlock(fatigue);
        }
    }


   private void updateAttacks( int fatigue){
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
//    resetAttacks();
    resetBlocks();
    }
    private void resetAttacks(){
        for(Attack attack : attackList){
            attack.refresh(strengthScore, agilityScore,accuracy);
        }
    }
    private void resetBlocks(){
        for(Block block : blockList){
            block.refresh(strengthScore,agilityScore,accuracy);
        }
    }
      public void grow(){
        StatDialogue dialogue = new StatDialogue();
          int pointsUsed = 0;
          String NewStats = dialogue.getStats(exp,this.getStats(),boxerID);
              if (NewStats !=null) {

                  String[] inputArray = NewStats.split("\\|");
                  int strength = 0;
                  int speed = 0;
                  int accuracy = 0;
                  int reach = 0;
                  try {
                      pointsUsed = Integer.parseInt(inputArray[0]);
                      strength = Integer.parseInt(inputArray[1]);
                      speed = Integer.parseInt(inputArray[2]);
                      accuracy = Integer.parseInt(inputArray[3]);
                      reach = Integer.parseInt(inputArray[4]);
                  } catch (Exception e) {
                      System.out.println(e.toString());
                  }

                  this.setAgilityScore(speed);
                  this.setStrengthScore(strength);
                  this.setAccuracy(accuracy);
                  this.setReach(reach);
                  this.setFatigue(0);
                  this.exp -= pointsUsed;
              }else{
                  dialogue.errorBox("Error Collecting New Parameters");
              }
      }//<String in to dialogue, string out of dialogue pipe delim, attribute order,unused points leader>


    public String getBoxerID() {
        return boxerID;
    }

    public void setBoxerID(String boxerID) {
        this.boxerID = boxerID;
    }

}