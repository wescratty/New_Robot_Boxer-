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




    /**
     * Initializes a new stopwatch.
     */
    public void Stopwatch() {
        start = System.currentTimeMillis();
    }


    /**
     * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
     *
     * @return elapsed CPU time (in seconds) since the stopwatch was created
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) /1000;
    }
}
