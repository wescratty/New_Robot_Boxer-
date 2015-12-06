/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class AIGame implements Game, Runnable {

    final int STARTINGPOINTS = 100;

    final int WINEXP = 10;

    final int LOSEEXP = 5;

    private int currentpoints = STARTINGPOINTS;


    private int rounds = 3;
    private static AIGame ourInstance = new AIGame();

    public static AIGame getInstance() {
        return ourInstance;
    }


    BoxerDirector builder = new BoxerDirector();

    Boxer _boxer1 = builder.build(currentpoints, "Player 1");
    Boxer _boxer2 = builder.buildAI(currentpoints);
    Boxer[] boxers = new Boxer[2];

    Paint_aBoxer pb = Paint_aBoxer.getInstance();
    MainPanel mp = MainPanel.getInstance();
    Match match = Match.getInstance();

    boolean round_in_Play = false;
    boolean gameOn = true;



    protected AIGame(){}


    public void start(){
        boxers[0]=_boxer1;
        boxers[1]=_boxer2;
        boxers[0].setLoc(200,400);
        boxers[1].setLoc(600,400);
        pb.setBoxers(boxers[0], boxers[1]);

        ObservaBoxing obs1 = new ObservaBoxing(boxers[0]);
        ObservaBoxing obs2 = new ObservaBoxing(boxers[1]);

        boxers[0].register(obs2);
        boxers[1].register(obs1);

        _boxer1.setOtherBoxer(boxers[1]);
        _boxer2.setOtherBoxer(boxers[0]);

        // todo can we move this lower wes? so we can restart the match threads
        Game game = this;
        Thread paintThread = new Thread(game);
        Thread matchThread = new Thread(match);
        Thread boxer1Thread = new Thread(game);
        Thread boxer2Thread = new Thread(game);


        int b1Identifier = System.identityHashCode(boxer1Thread);
        int b2Identifier = System.identityHashCode(boxer2Thread);


        boxers[0].setid(b1Identifier, 0);
        boxers[1].setid(b2Identifier, 1);

        boxer1Thread.start();
        boxer2Thread.start();
        paintThread.start();
        matchThread.start();

    }


    public void  run(){


        while(gameOn) {

            while (round_in_Play) {

                // get thread to see if b1 or b2 boxer
                if (System.identityHashCode(Thread.currentThread()) == boxers[1].getid()) {
                    boxers[1].selectMove();
                } else if (System.identityHashCode(Thread.currentThread()) == boxers[0].getid()) {
                    boxers[0].selectMove();
                } else {

                    mp.setTime();
                    pb.revalidate();
                    pb.repaint();

                    boxers[0].upDateOtherBoxerLoc();
                    boxers[1].upDateOtherBoxerLoc();

                    boxers[0].move();
                    boxers[1].move();
                    try {
                        Thread.sleep(10);

                    } catch (Exception e) {
                    }
                }
            }
            while (!round_in_Play) {

                try {
                    wait();
                    //todo Does this work how do i make this work??
                    match.match(3,boxers[0],boxers[1],this);
                    String winner = match.getWinner();
                    if (winner.compareTo(boxers[0].getBoxerID())==0){
                        boxers[0].setExp(boxers[0].getExp()+WINEXP);
                        currentpoints+=LOSEEXP;
                    }else{
                         boxers[0].setExp(boxers[0].getExp()+LOSEEXP);
                         currentpoints+=WINEXP;
                    }
                    boxers[0].grow();
                    boxers[1] = builder.buildAI(currentpoints);
                    match  = match.reset();
                    match.match(3,boxers[0],boxers[1],this);
                    wait();

                } catch (Exception e) {
                }
            }

        }



    }
    public void setRoundInPlay(boolean update){
        round_in_Play = update;
    }
    public void setGameOn(boolean update){
        gameOn = update;
    }




}