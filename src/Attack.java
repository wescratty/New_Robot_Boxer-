/**
 * Created by Brian Trethewey on 11/2/15.
 */
public abstract class Attack {

    private String attackName;
    private String attackType;
    private int timing;

    public String getAttackName() {
        return attackName;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    public abstract void update(int fatigue);

    public abstract void refresh(int str, int speed,int accuracy);
}
