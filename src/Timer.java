/**
 * Created by wescratty on 11/1/15.
 */
public class Timer {
    private static Timer ourInstance = new Timer();

    public static Timer getInstance() {
        return ourInstance;
    }

    private Timer() {
    }
}
