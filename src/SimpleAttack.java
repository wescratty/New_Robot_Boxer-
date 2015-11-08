/**
 * Created by Brian Trethewey on 11/2/15.
 */

public class SimpleAttack extends Attack {
    //constants
    final double ACCURACYCORRECTION = 0.5;

    //Stat Variables
    private int strength;
    private int speed;
    private int accuracy;
    private int fatigue;


    @Override
    public void update(int fatigue) {
    double deltaFatigue = fatigue - this.fatigue;
    double percentReduction = deltaFatigue/fatigue;
    strength = (int)(strength* percentReduction);
    speed = (int)(speed* percentReduction);
    accuracy = (int)(accuracy * (percentReduction*ACCURACYCORRECTION));
        this.fatigue = fatigue;
    }

    @Override
    public void refresh(int str, int speed, int accuracy) {
    strength = str;
    this.speed = speed;
    this.accuracy = accuracy;
    fatigue = 0;
    }

    public String getStats()
    {
        String statString = ""+strength+"|"+speed+"|"+accuracy;
        return statString;
    }
}
