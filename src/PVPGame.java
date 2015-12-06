/**
 * Created by wescratty on 10/31/15.
 */
public class PVPGame implements Game {
    private static Game ourInstance = new PVPGame();

    public static Game getInstance() {
        return ourInstance;
    }


    BoxerDirector builder = new BoxerDirector();

    Boxer _boxer1 = builder.build(100, "Player 1");
    Boxer _boxer2 = builder.build(100, "Player 2");
    Boxer[] boxers = new Boxer[2];

    Paint_aBoxer pb = Paint_aBoxer.getInstance();
    MainPanel mp = MainPanel.getInstance();
    Match match = Match.getInstance();

    boolean round_in_Play = false;
    boolean gameOn = true;
    boolean madeOnce = false;



    protected PVPGame(){}


    @Override
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


        Game game = this;
        Thread paintThread = new Thread(game);
        Thread matchThread = new Thread(match);
        Thread boxer1Thread = new Thread(game);
        Thread boxer2Thread = new Thread(game);

        match.match(3,boxers[0],boxers[1], this);

        int b1Identifier = System.identityHashCode(boxer1Thread);
        int b2Identifier = System.identityHashCode(boxer2Thread);


        boxers[0].setid(b1Identifier, 0);
        boxers[1].setid(b2Identifier, 1);

        boxer1Thread.start();
        boxer2Thread.start();
        paintThread.start();
        matchThread.start();

    }


    @Override
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

                } catch (Exception e) {
                }
            }

        }



    }
    @Override
    public void setRoundInPlay(boolean update){
        round_in_Play = update;
    }
    @Override
    public void setGameOn(boolean update){
        gameOn = update;
    }

    public boolean getMadeOnce(){
        return madeOnce;
    }

     public void setUpNewGame(){}




}
