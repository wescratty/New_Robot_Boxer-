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

    private final int TKOTHESHOLD = 350;
    private final int COUNTDELAY = 10;
    private final int COUNTTOLERANCE = 2;
    private final int DOWNTHRESHOLD = 200;
    //TODO changed round durration from 300 for testing
    private final int ROUNDDURATION = 20;
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
//            makeWait(newAttack);
            while (!newAttack) {

                try {
                    wait();

                } catch (InterruptedException e) {
                    return boxerWinner;
                } catch (Exception e){

                }
            }

            if(newAttack) {  //todo: added this so boxers don't get awarded multiple times for same attack, delete after read

                newAttack = false;
                System.out.println("newAttack: " );
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



                boxers[attackerId].notifyObserver();
            }

        }

        updateRoundEndInfo();

        return boxerWinner;
    }

    private int checkDamage(Attack attack, Block block,int attackerIDX ,int defenderIDX){
        int boxerWinner = 2;
        String damageString = hurt.calculateDamage(attack, block);
        int damage = Integer.parseInt(damageString);
        int amplify = 1;

        System.out.println("damage: " + damage *amplify);
        setSplash("damage: " + damage*amplify );

        boxers[defenderIDX].takeDamage(damage *amplify);// todo: added *10 for testing
//        boxers[defenderIDX].notifyObserver();

        if (checkTKO(damage)){
            System.out.println("TKO by: "+ attackerIDX);
            boxerWinner = attackerIDX;

        }else if(checkDown(boxers[defenderIDX].getFatigue())){
            boxers[defenderIDX].setThisBoxerDown(true);
            boxers[attackerIDX].setOpponentDown(true);
            setSplash("Boxer "+defenderIDX+" Down!");
            int countResult = count(boxers[defenderIDX].getFatigue());
            System.out.println("countResult: " +countResult);

            if( countResult > 0){
                boxers[defenderIDX].setFatigue(countResult);
                boxers[defenderIDX].setThisBoxerDown(false);
                boxers[attackerIDX].setOpponentDown(false);

            }else {
                boxerWinner = attackerIDX;
                System.out.println("Attacker " +attackerIDX+" wins!");
            }
        }
        return  boxerWinner;
    }




    private boolean checkTKO(int damage){
        return damage > TKOTHESHOLD;
    }

    private boolean checkDown(int fatigue){return fatigue > DOWNTHRESHOLD;}

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

    public String getWinner(){
        if (winner==null){
            return null;
        }else {
            return winner.getBoxerID();
        }
    }
    public int getCurrentRound(){ return currentRound;}

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

    private void updateRoundEndInfo(){
        mp.setSplash("Round " + getCurrentRound() + " is over");
        game.setRoundInPlay(false);
        audio.endBell();

        //todo make rounds won label for each boxer in mp and update
    }

    public void  setCurrentAttack(int attackerId,Attack a,Block b){
        this.attackerId = attackerId;
        this.currentAttack = a;
        this.currentBlock = b;
        this.newAttack = true;
    }

    public Match reset(){
        ourInstance = new Match();
        return ourInstance;
    }

    public boolean getInstantiated(){
        return this.instantiated;
    }

    private void makeWait(boolean waitFor){
        System.out.println("waiting for...");
        while (!waitFor) {

            try {
                wait();

            } catch (Exception e) {
            }
        }

    }
}
