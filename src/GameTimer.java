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

    Timer ourTimer = new Timer();
}
