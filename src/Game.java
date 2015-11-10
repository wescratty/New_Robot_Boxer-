import java.util.Observer;
import java.util.Timer;
import java.text.DecimalFormat;


/**
 * Created by wescratty on 10/31/15.
 */
public class Game implements Runnable {
    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }



    // Match currentBout;
    // experienceCap:int
//    gui:GUI
    GameTimer timer = GameTimer.getInstance();
    //    Observer watcher;
    // boxerBuilder:Director
    BoxerDirector builder = new BoxerDirector();

    Boxer _boxer1 = builder.build(100, "Player 1");
    Boxer _boxer2 = builder.build(100, "Player 2");
    Boxer[] boxers = new Boxer[2];

    ChanceBot rand = ChanceBot.getInstance();
    Paint_aBoxer pb = Paint_aBoxer.getInstance();
//    ObservaBoxing obs1;
//    ObservaBoxing obs2;

    boolean round_in_Play = true;
    AudioPlayer bell = new AudioPlayer();


    public String getTimer() {
        return (Double.toString(timer.elapsedTime()));
    }


    protected Game(){}


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

//        _boxer1.setOtherBoxerLoc(_boxer2);
//        _boxer2.setOtherBoxerLoc(_boxer1);


//        Runnable game = new Game(boxers[0],_boxer2);
//        Runnable match = new Match(3,boxers[0],boxers[1]);


        Game game = Game.getInstance();
        Thread paintThread = new Thread(game);
        Thread matchThread = new Thread(new Match(3,boxers[0],boxers[1]));
        Thread boxer1Thread = new Thread(game);
        Thread boxer2Thread = new Thread(game);

        int b1Identifier = System.identityHashCode(boxer1Thread);
        int b2Identifier = System.identityHashCode(boxer2Thread);


        boxers[0].setid(b1Identifier, 1);
        boxers[1].setid(b2Identifier, 2);

        boxer1Thread.start();
        boxer2Thread.start();
        paintThread.start();
        matchThread.start();

        // TODO join threads
//        try {
//            boxer1Thread.join();
//            boxer2Thread.join();
//        }catch(Exception e){}


        //TODO +create() pushImageState(String state)<<no idea on formatting yet>>

    }

//    public Game(){
//
//
//
//
//    }

    public void  run(){
        timer.Stopwatch();
        int len = 60;


        while(round_in_Play){

            // get thread to see if b1 or b2 boxer
            if (System.identityHashCode(Thread.currentThread())==boxers[1].getid()){
                boxers[1].selectMove();
            }else if (System.identityHashCode(Thread.currentThread())==boxers[0].getid()){
                boxers[0].selectMove();
            }else {




                if(timer.elapsedTime()>len){
                    round_in_Play = false;

                    bell.bellSound();
                    bell.bellSound();

                }else if (timer.elapsedTime()<1){

                    bell.bellSound();


                }else {
                    MainPanel mp = MainPanel.getInstance();
                    mp.setLables(Double.toString(len-timer.elapsedTime()), _boxer1,_boxer2);
                    pb.revalidate();
                    pb.repaint();
                    boxers[0].setOtherBoxerLoc(boxers[1]);
                    boxers[1].setOtherBoxerLoc(boxers[0]);
                    boxers[0].move();
                    boxers[1].move();
                    try {
                        Thread.sleep(10);

                    } catch (Exception e) {
                    }
                }


            }

        }



    }




}
