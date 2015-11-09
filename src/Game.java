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

//    private Game() {
//    }


    // Match currentBout;
    // experienceCap:int
//    gui:GUI
    GameTimer timer = GameTimer.getInstance();
    //    Observer watcher;
    // boxerBuilder:Director
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



    public Game(){}

    public Game(Boxer b1,Boxer b2){


        boxers[0]=b1;
        boxers[1]=b2;
        boxers[0].setLoc(200,400);
        boxers[1].setLoc(600,400);
        pb.setBoxers(boxers[0], boxers[1]);

    }

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
                    mp.setLables(Double.toString(len-timer.elapsedTime()));
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
