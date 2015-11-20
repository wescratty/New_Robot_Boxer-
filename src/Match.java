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
    private final int DOWNTHRESHOLD = 150;
    //TODO changed round durration from 300 for testing
    private final int ROUNDDURATION = 30;
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
    private Boxer winner;
    private int attackerId;
    private Attack currentAttack;
    private Block currentBlock;
    private boolean newAttack = false;

    public void match(int totalRounds, Boxer boxer1, Boxer boxer2) {
        this.totalRounds = totalRounds;
        this.boxers = new Boxer[2];
        this.boxers[0] =boxer1;
        this.boxers[1] = boxer2;
        this.roundTimer = GameTimer.getInstance();
        this.downTimer = DownTimer.getInstance();
        this.game = Game.getInstance();
        this.hurt = HurtBox.getInstance();
        this.mp = MainPanel.getInstance();
        this.audio = new AudioPlayer();

    }

    public int  Bout(){

        int boxerWinner = 2;
        for (Boxer boxer: boxers){
            boxer.reset();
        }

        updateRoundStartInfo();

        int defender;
        Attack attack;
        Block block;
        int attacker;

        while (roundTimer.elapsedTime()< ROUNDDURATION && boxerWinner==2) {

            if(newAttack) {  //todo: added this so boxers don't get awarded multiple times for same attack, delete after read

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

                this.newAttack = false;

            }
        }

        updateRoundEndInfo();

        return boxerWinner;
    }

    private int checkDamage(Attack attack, Block block,int attackerIDX ,int defenderIDX){
        int boxerWinner = 2;
        String damageString = hurt.calculateDamage(attack, block);
        int damage = Integer.parseInt(damageString);

        boxers[defenderIDX].takeDamage(damage*10);// todo: added *10 for testing
        boxers[defenderIDX].notifyObserver();

        if (checkTKO(damage)){
            boxerWinner = attackerIDX;

        }else if(checkDown(boxers[defenderIDX].getFatigue())){
            boxers[defenderIDX].setThisBoxerDown(true);
            boxers[attackerIDX].setApponentDown(true);
            setSplash("Boxer "+defenderIDX+" Down!");
            int countResult = count(boxers[defenderIDX].getFatigue());
            System.out.println("countResult: " +countResult);

            if( countResult > 0){
                boxers[defenderIDX].setFatigue(countResult);
                boxers[defenderIDX].setThisBoxerDown(false);
                boxers[attackerIDX].setApponentDown(false);

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
            if(roundTimer.elapsedTime()< ROUNDDURATION){
//               todo  needs to stop clock but allow other stuff
            }
        }
        return 0;
    }

    @Override
    public void run() {
        score = new int[3];
        score[0] = 0;
        score[1] = 0;
        score[2] = 0;
        while (Math.max(score[0], score[1])<= totalRounds/2) {
            //todo i think logic to have them fight has to be here
            int winner = Bout();
            System.out.println(" boxerWinner  "+ winner);
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

    }

    public String getWinner(){
        return winner.getBoxerID();
    }
    public int getCurrentRound(){ return currentRound;}

    public void updateRoundStartInfo(){
        currentRound++;
        game.setRoundInPlay(true);
        roundTimer.Stopwatch(ROUNDDURATION);
        audio.startBell();
        setSplash("Round " + getCurrentRound());
        mp.setRound(Integer.toString(getCurrentRound()));

    }

    private void setSplash(String st){
        mp.setSplash(st);

    }

    private void updateRoundEndInfo(){
        mp.setSplash("Round " + getCurrentRound()+" is over");
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
}
