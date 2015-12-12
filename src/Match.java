/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class Match implements Runnable {

    private static Match ourInstance = new Match();

    public static Match getInstance() {
        return ourInstance;
    }

    protected Match(){

    }

    private final int TKOTHESHOLD = 150;
    private final int COUNTDELAY = 10;
    private final int COUNTTOLERANCE = 2;
    private final int DOWNTHRESHOLD = 100;
    //TODO changed round durration from 300 for testing
    private final int ROUNDDURATION = 300;
    private final int HEALRATE = 50;
    private final double MAXUPPERCENT = 0.9;
    private final double MINUPPERCENT = 0.7;
    private final long ROUNDRESETTME = 1000;//todo 10000?
    private int currentRound = 0;
    private int totalRounds;
    private Boxer[] boxers;
    private DownTimer downTimer;
    private  GameTimer roundTimer;
    private int[] score;
    private ChanceBot chance = ChanceBot.getInstance();
    private Game game;
    private AudioPlayer audio;
    private HurtBox hurt;
    private MainPanel mp;
    private Boxer winner=null;
    private int attackerId;
    private Attack currentAttack;
    private Block currentBlock;
    private boolean newAttack = false;
    private boolean instantiated = false;

    public void match(int totalRounds, Boxer boxer1, Boxer boxer2, Game game) {

        this.totalRounds = totalRounds;
        this.boxers = new Boxer[2];
        this.boxers[0] =boxer1;
        this.boxers[1] = boxer2;
        this.roundTimer = GameTimer.getInstance();
        this.downTimer = DownTimer.getInstance();
        this.game = game;
        this.hurt = HurtBox.getInstance();
        this.mp = MainPanel.getInstance();
        this.audio = new AudioPlayer();
        this.instantiated = true;
//        this.game.setRoundInPlay(true);

    }

    /**
     * this method runs a single fight instance
     * @return the winner of the bout
     */
    public int  Bout(){
        while (!game.getMadeOnce()) {

            try {
                wait();

            } catch (Exception e) {
            }
        }
//        makeWait(game.getMadeOnce());
        updateRoundStartInfo();

        int boxerWinner = 2;
        for (Boxer boxer: boxers){
            boxer.reset();

        }



        int defender;
        Attack attack;
        Block block;
        int attacker;

        while (roundTimer.elapsedTime()< ROUNDDURATION && boxerWinner==2) {
            while (!newAttack) {

                try {
                    wait();

//                } catch (InterruptedException e) {
//                    return boxerWinner;
                } catch (Exception e){

                }
            }

            if(newAttack) {

                newAttack = false;
//                System.out.println("newAttack: " );
                attack = this.currentAttack;
                block = this.currentBlock;
                attacker = this.attackerId;

                if (attacker == 0) {
                    defender = 1;
                } else {
                    defender = 0;
                }

                if (attack != null) {
                    boxerWinner = checkDamage(attack, block, attacker, defender);
                }




            }

        }

        updateRoundEndInfo();

        return boxerWinner;
    }

    /**
     * handles the damage calculation for the fight this includes knockout and damage handeling
     * @param attack the attack being performed
     * @param block the block(if any) that is being performed
     * @param attackerIDX identifies the boxer who is performing the attack
     * @param defenderIDX identifies the boxer who is being attacked
     * @return returns the index of the boxer who won the bout with that attack, returns 2 if the attack did not result in a bout win
     */
    private int checkDamage(Attack attack, Block block,int attackerIDX ,int defenderIDX){
        int boxerWinner = 2;
        String damageString = hurt.calculateDamage(attack, block);
        int damage = Integer.parseInt(damageString);
        int amplify = 1;

//        System.out.println("damage: " + damage *amplify);
        setSplash("damage: " + damage*amplify );

        boxers[defenderIDX].takeDamage(damage *amplify);
//        boxers[defenderIDX].notifyObserver();

        if (checkTKO(damage)){
//            System.out.println("TKO by: "+ attackerIDX);
            boxerWinner = attackerIDX;

        }else if(checkDown(boxers[defenderIDX].getFatigue())){
            boxers[defenderIDX].setThisBoxerDown(true);
            boxers[attackerIDX].setOpponentDown(true);
            setSplash("Boxer "+defenderIDX+" Down!");
            int countResult = count(boxers[defenderIDX].getFatigue());
//            System.out.println("countResult: " +countResult);

            if( countResult > 0){
                boxers[defenderIDX].setFatigue(countResult);
                boxers[defenderIDX].setThisBoxerDown(false);
                boxers[attackerIDX].setOpponentDown(false);

            }else {
                boxerWinner = attackerIDX;
//                System.out.println("Attacker " +attackerIDX+" wins!");
            }
        }
        boxers[0].upDateLabels();
        return  boxerWinner;
    }


    /**
     * checks if a technical knock out occured(no chance of getting up)
     * @param damage how much damage was taken in this attack
     * @return true if the defender was TKOed
     */
    private boolean checkTKO(int damage){
        return damage > TKOTHESHOLD;
    }

    /**
     * checks if the defender was knocked down due to fatigue after teh attack
     * @param fatigue cumulative amount of damage taken by the boxer in the bout
     * @return true if the boxer was knocked down
     */
    private boolean checkDown(int fatigue){return fatigue > DOWNTHRESHOLD;}

    /**
     * handles the count for a knocked down boxer to decalre a winner
     * @param fatigue cumulative amount of damage taken by the boxer in the bout
     * @return new fatigue value of the defending boxer, if 0 the defender did not get up
     */
    private int count(int fatigue){

        boolean isUp = false;
        int counter = 1;
        int fatigueValue = fatigue;

        downTimer.Stopwatch();
        while(downTimer.elapsedTime()<COUNTDELAY*10){
            if (roundTimer.clockTime() <=0){
                return 0;
            }
            if (downTimer.elapsedTime() > (counter*COUNTDELAY)-COUNTTOLERANCE){
                fatigueValue =  fatigue -counter * HEALRATE;
                int upTolerance = (int)Math.round(DOWNTHRESHOLD*Math.max(chance.getChance()+MINUPPERCENT,MAXUPPERCENT));
                if (fatigueValue>upTolerance){
                    isUp  = true;

                }
                if (isUp&&chance.getCoinFlip()) {
                    return fatigueValue;
                }
                counter++;
            }

        }
        return 0;
    }

    /**
     * this controls the overall match run is an override for the thread ignalling this is the main loop of the thread
     */
    @Override
    public void run() {

        System.out.println(" NEWGAME  ");
        score = new int[3];
        score[0] = 0;
        score[1] = 0;
        score[2] = 0;
        while (Math.max(score[0], score[1])<= totalRounds/2) {
            int winner = Bout();
            System.out.println(" boxerWinner from run  "+ winner);
            score[winner] += 1;
            try {
                Thread.sleep(ROUNDRESETTME);
            }catch (Exception e){}
        }
        if (score[0]>totalRounds/2){
            this.winner = boxers[0];
        }else{
            this.winner = boxers[1];
        }

        game.setGameOn(false);
        System.out.println(" GAMEOVER  ");
        game.setUpNewGame();

    }

    /**
     * returns the winner of the overall match
     * @return the winner of the overall match
     */
    public String getWinner(){
        if (winner==null){
            return null;
        }else {
            return winner.getBoxerID();
        }
    }

    public int getCurrentRound(){ return currentRound;}

    /**
     * this sets up the various parameters for the beginning of a bout
     */
    public void updateRoundStartInfo(){
        currentRound++;
        game.setRoundInPlay(true);
        roundTimer.Stopwatch(ROUNDDURATION);

        setSplash("Round " + getCurrentRound());
        mp.setRound(Integer.toString(getCurrentRound()));
        audio.startBell();

    }

    private void setSplash(String st) {
        mp.setSplash(st);

    }

    /**
     * this handles cleanup of a bout
     */
    private void updateRoundEndInfo(){
        mp.setSplash("Round " + getCurrentRound() + " is over");
        game.setRoundInPlay(false);
        audio.endBell();

    }

    /**
     * handled by notifys from the observers to gather the data needed for an attack
     * @param attackerId the Id of the boxer performing the attack
     * @param a the attack being performed
     * @param b the block in response to the attack
     */
    public void  setCurrentAttack(int attackerId,Attack a,Block b){
        this.attackerId = attackerId;
        this.currentAttack = a;
        this.currentBlock = b;
        this.newAttack = true;
    }

    /**
     * creates a new copy of our match this allows for different new isntaces of our match to be created even though it is a singleton.
     * i do wish this could be cleaner but i have no idea how to get this functionality otherwise
     * @return our new instance
     */
    public Match reset(){
        ourInstance = new Match();
        return ourInstance;
    }

    public boolean getInstantiated(){
        return this.instantiated;
    }

}
