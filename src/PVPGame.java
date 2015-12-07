/**
 * Created by wescratty on 10/31/15.
 */
public class PVPGame implements Game {
    final int STARTINGPOINTS = 100;

    final int WINEXP = 10;

    final int LOSEEXP = 5;

    private int currentpoints = STARTINGPOINTS;

    private int rounds = 3;
    private static PVPGame ourInstance = new PVPGame();

    public static PVPGame getInstance() {
        return ourInstance;
    }


    BoxerDirector builder = new BoxerDirector();

    Boxer _boxer1 = builder.build(currentpoints, "Player 1");
    Boxer _boxer2 = builder.build(currentpoints, "Player 2");
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
    Thread matchThread ;
    Thread boxer1Thread ;
    Thread boxer2Thread;
    Thread paintThread;
    Object lock;



    protected PVPGame(){}


    public void start(){
        lock = new Object();
        boxers[0]=_boxer1;
        boxers[1]=_boxer2;
        updateNewBoxer();

        makeThreads();
        setIdentifier();
        startThreads();


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
                } else { //paint thread

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

            while (!round_in_Play&&gameOn) {
                try {
//                    System.out.println("waiting for round");
                    wait();


                } catch (Exception e) {
                }
            }
        }
        try {
            Thread.currentThread().join();
        } catch (Exception e) {
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

        boxers[0].setOtherBoxer(boxers[1]);
        boxers[1].setOtherBoxer(boxers[0]);


    }

    public void setUpNewGame(){
        System.out.println("setUpNewGame");

        if (!madeOnce) {
            System.out.println("first if");
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
                    boxers[1].grow();

                    match = match.reset();

                    madeOnce= false;
                    gameOn = true;
                    lock = new Object();

                    updateNewBoxer();
                    makeThreads();
                    setIdentifier();
                    startThreads();

                }

            }


        }

    }
    public void setIdentifier(){
        boxers[0].setid(b1Identifier, 0);
        boxers[1].setid(b2Identifier, 1);
    }


    public void makeThreads(){
        cleanup();
        matchThread = new Thread(match);
        boxer1Thread = new Thread(this);
        boxer2Thread = new Thread(this);
        paintThread = new Thread(this);




        b1Identifier = System.identityHashCode(boxer1Thread);
        b2Identifier = System.identityHashCode(boxer2Thread);



    }
    private void startThreads(){
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
        System.out.println("all threads started");


    }


    public void cleanup(){
        int waitTime = 1000;
        try{
            if(matchThread != null)
                matchThread.join(waitTime);
            if(boxer1Thread != null)
                boxer1Thread.join(waitTime);
            if(boxer2Thread != null)
                boxer2Thread.join(waitTime);
            if(paintThread != null)
                paintThread. join(waitTime);
        }catch (InterruptedException e){
            return;
        }
    }


}
