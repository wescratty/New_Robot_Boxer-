/**
 * Created by Brian Trethewey on 11/2/15.
 */

public class SimpleAttack extends Attack {
    //constants
    final int ACCURACYCORRECTION = 2;
    final int DAMAGETHRESHOLD = 300;
    //Stat Variables
    private int strength;
    private int speed;
    private int accuracy;
    private int fatigue;
    private int hitcoutner;

    @Override
    public void update(int fatigue) {
        double deltaFatigue = fatigue - this.fatigue;
        if (deltaFatigue > DAMAGETHRESHOLD) {
            hitcoutner++;
            strength --;
            speed --;
            if (hitcoutner%ACCURACYCORRECTION ==0)
                accuracy --;
            this.fatigue = fatigue;
        }
    }

    @Override
    public void refresh(int str, int speed, int accuracy) {
    strength = str;
    this.speed = speed;
    this.accuracy = accuracy;
    fatigue = 0;
    hitcoutner = 0;
    }

    public String getStats()
    {
        String statString = ""+strength+"|"+speed+"|"+accuracy;
        return statString;
    }
}
