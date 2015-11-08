/**
 * Created by Brian Trethewey on 11/3/15.
 */
public class SimpleBlock implements Block {
    //constants
    final double ACCURACYCORRECTION = 0.5;

    //attributes
    private int strength;
    private int speed;
    private int accuracy;
    private int fatigue;
    private int timing;

    //methods

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

    @Override
    public int getTiming() {
        return timing;
    }


    @Override
    public void setTiming(int timing) {
        this.timing = timing;
    }


}
