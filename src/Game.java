import java.util.Observer;
import java.util.Timer;
import java.text.DecimalFormat;


/**
 * Created by wescratty on 10/31/15.
 */
public class Game implements Runnable {
    // Match currentBout;
    // experienceCap:int
//    gui:GUI
    Timer timer = new Timer();
    //    Observer watcher;
    // boxerBuilder:Director
    Boxer[] boxers = new Boxer[2];
    ChanceBot rand = new ChanceBot();
    Paint_aBoxer pb = Paint_aBoxer.getInstance();
//    ObservaBoxing obs1;
//    ObservaBoxing obs2;

    boolean round_in_Play = true;



    public Game(){}

    public Game(Boxer b1,Boxer b2){


        boxers[0]=b1;
        boxers[1]=b2;
        boxers[0].setLoc(200,400);
        boxers[1].setLoc(600,400);
        pb.setBoxers(boxers[0], boxers[1]);
//        this.obs1=obs1;
//        this.obs2=obs2;

    }

    public void  run(){

        while(round_in_Play){

            // get thread to see if b1 or b2 boxer
            if (System.identityHashCode(Thread.currentThread())==boxers[1].getid()){
                boxers[1].selectMove();
            }else if (System.identityHashCode(Thread.currentThread())==boxers[0].getid()){
                boxers[0].selectMove();
            }else {
                pb.revalidate();
                pb.repaint();
                boxers[0].move();
                boxers[1].move();
                try {
                    Thread.sleep(50);

                }catch (Exception e){}


            }

        }

        AudioPlayer bell =  AudioPlayer.getInstance();
        bell.bellSound();

    }


}
