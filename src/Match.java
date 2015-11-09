/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class Match implements Runnable {
    private final int TKOTHESHOLD = 150;
    private final int COUNTDELAY = 10;
    private final int COUNTTOLERANCE = 2;
    private final int DOWNTHRESHOLD = 1500;
    private final int ROUNDDURATION = 300;
    private final int HEALRATE = 50;
    private final double MAXUPPERCENT = 0.9;
    private final double MINUPPERCENT = 0.7;
    private int totalRounds;
    private Boxer boxer1;
    private Boxer boxer2;
    private DownTimer timer;
    private  GameTimer roundTimer;
    private int[] score;
    private ChanceBot chance = ChanceBot.getInstance();
    private AudioPlayer audio = AudioPlayer.getInstance();

    private Boxer winner;

    public Match(int totalRounds, Boxer boxer1, Boxer boxer2) {
        this.totalRounds = totalRounds;
        this.boxer1 = boxer1;
        this.boxer2 = boxer2;
        this.roundTimer = GameTimer.getInstance();
    }
        //todo this needs to handle the fight loop will probably need wes to help
        // should return which boxer won the round
    public String  Bout(){
        roundTimer.Stopwatch();
        audio.bellSound();
        while (roundTimer.elapsedTime()< ROUNDDURATION){

        }

        return null;
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
        while(timer.elapsedTime()<COUNTDELAY*10){
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

    }
    public String getWinner(){
        return winner.getBoxerID();
    }
}
