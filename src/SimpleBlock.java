/**
 * Created by Brian Trethewey on 11/3/15.
 */
public class SimpleBlock implements Block {
    //attributes
    private int strength;
    private int speed;
    private int accuracy;
    private int fatigue;
    private int timing;

    //methods

    @Override
    public void refresh(int strength, int speed, int accuracy){

    }

    @Override
    public void update(int fatigue){

    }

    @Override
    public String getStats(){
        return null;
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
