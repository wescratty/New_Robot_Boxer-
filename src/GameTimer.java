import java.util.Timer;

/**
 * Created by wescratty on 11/1/15.
 */
public class GameTimer {
    private static GameTimer ourInstance = new GameTimer();

    public static GameTimer getInstance() {
        return ourInstance;
    }

    private GameTimer() {
    }

    private long start = 0;
    int _roundTime = 0;




    /**
     * Initializes a new stopwatch.
     */
    public void Stopwatch(int roundTime) {

        start = System.currentTimeMillis();
        _roundTime = roundTime;
    }


    /**
     * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
     *
     * @return elapsed CPU time (in seconds) since the stopwatch was created
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return _roundTime - ((now - start) /1000);
    }
}
