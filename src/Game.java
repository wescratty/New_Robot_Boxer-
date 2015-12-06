/**
 * Created by sinless on 12/4/15.
 */
public interface Game extends Runnable {
    void start();

    void  run();

    void setRoundInPlay(boolean update);

    void setGameOn(boolean update);
    void setUpNewGame();

    boolean getMadeOnce();

}
