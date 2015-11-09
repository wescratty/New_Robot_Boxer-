/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class AIGame implements Runnable {

    final int STARTINGPOINTS = 100;

    final int WINEXP = 10;

    final int LOSEEXP = 5;

    private int currentpoints = STARTINGPOINTS;


    private int rounds = 3;

    private BoxerDirector builder;

    private Match currentBout;

    private Boxer playerBoxer;

    private Boxer aiBoxer;

    private boolean isRunning = true;


    public AIGame(){
        builder = new BoxerDirector();

    }
    @Override
    public void run() {
       playerBoxer =  builder.build(STARTINGPOINTS, "Player");

        while (isRunning){
            aiBoxer =  builder.buildAI(currentpoints);
            currentBout = new Match(rounds, playerBoxer, aiBoxer);
            currentBout.run();
            String winner =  currentBout.getWinner();
            if(playerBoxer.getBoxerID() == winner){
                playerBoxer.setExp(playerBoxer.getExp()+WINEXP);
                currentpoints += LOSEEXP;
            }else{
                playerBoxer.setExp(playerBoxer.getExp()+LOSEEXP);
                currentpoints += WINEXP;
            }
            playerBoxer.grow();
        }
    }
}