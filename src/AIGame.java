/**
 * Created by Brian Trethewey on 11/8/15.
 */
public class AIGame implements Game, Runnable {

    final int STARTINGPOINTS = 100;

    final int WINEXP = 10;

    final int LOSEEXP = 5;

    private int currentpoints = STARTINGPOINTS;

    private Thread paintThread;
    private Thread matchThread;
    private Thread boxer1Thread;
    private Thread boxer2Thread;
    private static final Object lock = new Object();

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
    boolean madeOnce = false;

    ObservaBoxing obs1;
    ObservaBoxing obs2;
    int b1Identifier;
    int b2Identifier;



    protected AIGame(){}


    public void start(){
        boxers[0]=_boxer1;
        boxers[1]=_boxer2;


        updateNewBoxer();
        // todo can we move this lower wes? so we can restart the match threads
        Game game = this;
        paintThread = new Thread(game);
        matchThread = new Thread(match);
        boxer1Thread = new Thread(game);
        boxer2Thread = new Thread(game);



         b1Identifier = System.identityHashCode(boxer1Thread);
         b2Identifier = System.identityHashCode(boxer2Thread);


        setIdentifier();

        boxer1Thread.start();
        boxer2Thread.start();
        paintThread.start();
        while (!madeOnce) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        matchThread.start();

    }


    public void  run(){

        setUpNewGame();
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
//                    System.out.println("waiting for round");
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

    public boolean getMadeOnce(){
        return madeOnce;
    }


//    private void unRegBoxers(){
//        boxers[0].unregister(obs2);
//        boxers[1].unregister(obs1);
//
//    }

    private void updateNewBoxer(){
        boxers[0].setLoc(200,400);
        boxers[1].setLoc(600,400);
        pb.setBoxers(boxers[0], boxers[1]);

         obs1 = new ObservaBoxing(boxers[0]);
         obs2 = new ObservaBoxing(boxers[1]);

        boxers[0].register(obs2);
        boxers[1].register(obs1);

        _boxer1.setOtherBoxer(boxers[1]);
        _boxer2.setOtherBoxer(boxers[0]);

        boxers[0].move();
        boxers[1].move();

    }

    public void setUpNewGame(){

            if (!madeOnce) {
                synchronized (lock) {
                    if (!madeOnce) {
                        System.out.println("new game");
                        match.match(3, boxers[0], boxers[1], this);
                        madeOnce = true;
                    }

                }



            } else if (match.getWinner() != null) {
                synchronized (lock) {
                    if (match.getWinner() != null) {
                        System.out.println("new match");
                        String winner = match.getWinner();
                        if (winner.compareTo(boxers[0].getBoxerID()) == 0) {
                            boxers[0].setExp(boxers[0].getExp() + WINEXP);
                            currentpoints += LOSEEXP;
                        } else {
                            boxers[0].setExp(boxers[0].getExp() + LOSEEXP);
                            currentpoints += WINEXP;
                        }
                        boxers[0].grow();
                        boxers[1] = builder.buildAI(currentpoints);

                        updateNewBoxer();
                        setIdentifier();

                        match = match.reset();
                        match.match(3, boxers[0], boxers[1], this);
                        Thread matchThread = new Thread(match);
                        matchThread.start();

                    }

                }


            }

    }
    public void setIdentifier(){
        boxers[0].setid(b1Identifier, 0);
        boxers[1].setid(b2Identifier, 1);
    }

    public void cleanup(){
        int waitTime = 1000;
        try{
        matchThread.join(waitTime);
        boxer1Thread.join(waitTime);
        boxer2Thread.join(waitTime);
        paintThread. join(waitTime);
        }catch (InterruptedException e){
            return;
        }
    }

}