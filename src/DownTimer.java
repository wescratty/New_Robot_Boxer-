/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class DownTimer {
    private static DownTimer ourInstance = new DownTimer();

    public static DownTimer getInstance() {
        return ourInstance;
    }

    private DownTimer() {
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

