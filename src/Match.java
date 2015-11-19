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
    private final int DOWNTHRESHOLD = 150;//todo  always going in to count, needs something
    //TODO changed round durration from 300 for testing
    private final int ROUNDDURATION = 15;
    private final int HEALRATE = 50;
    private final double MAXUPPERCENT = 0.9;
    private final double MINUPPERCENT = 0.7;
    private final long ROUNDRESETTME = 10;//todo 10000?
    private int currentRound = 0;
    private int totalRounds;
    private Boxer[] boxers;
    private DownTimer timer;
    private  GameTimer roundTimer;
    private int[] score;
    private ChanceBot chance = ChanceBot.getInstance();
    private Game game = Game.getInstance();
    private AudioPlayer audio = new AudioPlayer();
    private HurtBox hurt = HurtBox.getInstance();
    private MainPanel mp = MainPanel.getInstance();
    private Boxer winner;
    private int attackerId;
    private Attack currentAttack;
    private Block currentBlock;

    public Match(int totalRounds, Boxer boxer1, Boxer boxer2) {
        this.totalRounds = totalRounds;
        this.boxers = new Boxer[2];
        this.boxers[0] =boxer1;
        this.boxers[1] = boxer2;
        this.roundTimer = GameTimer.getInstance();
        this.timer = DownTimer.getInstance();

    }
    //todo this needs to handle the fight loop will probably need wes to help
    // should return which boxer won the round
    public int  Bout(){
        updateRoundStartInfo();
        System.out.println("Start round");

        //TODO fix boxer winner, needs to return 0 or 1
        int boxerWinner = 2;
        for (Boxer boxer: boxers){
            boxer.reset();
        }
        updateRoundStartInfo();
        while (roundTimer.elapsedTime()< ROUNDDURATION && boxerWinner==2) {
            // todo do all the damage stuff no idea how to get to it


            int defender;
            Attack attack = this.currentAttack;
            Block block =this.currentBlock;
            int attacker = this.attackerId;
            if (attacker==0){
                defender = 1;

            }else {
                defender = 0;
            }



////todo this is all in checkDamage *******
//            String damageString = hurt.calculateDamage(attack, block);
//            int damage = Integer.parseInt(damageString);
//
//            if (checkTKO(damage)){
//                boxerWinner = attacker;//todo this gets over ridden below *******
//            }else if(checkDown(boxers[defender].getFatigue())){
//                int countResult = count(boxers[defender].getFatigue());
//                if( countResult > 0){
//                    boxers[defender].setFatigue(countResult);
//                }else {
//                    boxerWinner = attacker;//todo this gets over ridden below *******
//                }
//            }
            if (attack !=null) {
                System.out.print(currentAttack.getStats()+"::"+currentAttack.getAttackName()+":::"+attacker);
                boxerWinner = checkDamage(attack, block, attacker, defender);
                attack = null;
                block= null;
                attacker = 2;
                defender = 2;
            }

        }


        updateRoundEndInfo();

        return boxerWinner;
    }
    private int checkDamage(Attack attack, Block block,int attackerIDX ,int defenderIDX){
        int boxerWinner = 2;
        String damageString = hurt.calculateDamage(attack, block);
        int damage = Integer.parseInt(damageString);


        //todo seems like we need something like this  //exactally like that
        System.out.print(boxers[defenderIDX].getFatigue());
        System.out.print(damage);
        boxers[defenderIDX].takeDamage(damage);
        System.out.print(boxers[defenderIDX].getFatigue());

        if (checkTKO(damage)){
            boxerWinner = attackerIDX;
        }else if(checkDown(boxers[defenderIDX].getFatigue())){
            int countResult = count(boxers[defenderIDX].getFatigue());
            if( countResult > 0){
                boxers[defenderIDX].setFatigue(countResult);
            }else {
                boxerWinner = attackerIDX;
                System.out.println("Attacker " +attackerIDX);
            }
        }
        return  boxerWinner;
    }




    private boolean checkTKO(int damage){
        return damage > TKOTHESHOLD;
    }

    private boolean checkDown(int fatigue){
        return fatigue > DOWNTHRESHOLD;
    }

    private int count(int fatigue){
        boolean isUp = false;
        int counter = 1;
        int fatigueValue = fatigue;
        timer.Stopwatch();
        while(timer.elapsedTime()<COUNTDELAY*10){
            if (roundTimer.clockTime() <=0){
                return 0;
            }
            if (timer.elapsedTime() > (counter*COUNTDELAY)-COUNTTOLERANCE){
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
        score = new int[3];
        score[0] = 0;
        score[1] = 0;
        score[2] = 0;//todo temp fix
        while (Math.max(score[0], score[1])<= totalRounds/2) {
            //todo i think logic to have them fight has to be here
            int winner = Bout();
            System.out.println(" boxerWinner  "+ winner);
            score[winner] += 1;
            try {
                Thread.sleep(ROUNDRESETTME);  //todo this is here to give pause after each round IS this what ROUNDRESETTME is for
            }catch (Exception e){}
        }
        if (score[0]>totalRounds/2){
            this.winner = boxers[0];
        }else{
            this.winner = boxers[1];
        }

        game.setGameOn(false);//TODO dont know if this is what you want here

    }
    public String getWinner(){
        return winner.getBoxerID();
    }
    public int getCurrentRound(){ return currentRound;}

    public void updateRoundStartInfo(){
        currentRound++;
        game.setRoundInPlay(true);//TODO dont know if this is what you want here
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

    }


}
