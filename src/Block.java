/**
 * Created by Brian Trethewey on 11/3/15.
 */
public interface Block {
    void refresh(int strength, int speed, int accuracy);

    void update(int fatigue);

    String getStats();

    int getTiming();

    void setTiming(int timing);
}
