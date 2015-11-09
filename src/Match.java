/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class Match {
    private final int TKOTHESHOLD = 150;
    private final int COUNTDELAY = 100;
    private final int COUNTTOLERANCE = 10;
    private final int DOWNTHRESHOLD = 1500;
    private final int ROUNDDURATION = 100;
    private final int HEALRATE = 50;
    private final double MAXUPPERCENT = 0.9;
    private final double MINUPPERCENT = 0.7;
    private int totalRounds;
    private Boxer boxer1;
    private Boxer boxer2;
    private DownTimer timer;
    private int[] score;
    private ChanceBot chance = ChanceBot.getInstance();

    public Match(int totalRounds, Boxer boxer1, Boxer boxer2) {
        this.totalRounds = totalRounds;
        this.boxer1 = boxer1;
        this.boxer2 = boxer2;
    }
        //todo this needs to handle the fight loop will probably need wes to help
    public String  Bout(){
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
}
