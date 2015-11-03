import java.util.Observer;
import java.util.Timer;
import java.text.DecimalFormat;


/**
 * Created by wescratty on 10/31/15.
 */
public class Game implements Runnable{
    // Match currentBout;
    // experienceCap:int
//    gui:GUI
    Timer timer = new Timer();
    //    Observer watcher;
    // boxerBuilder:Director
    Boxer[] boxers = new Boxer[2];
    ChanceBot rand = new ChanceBot();
    ObservaBoxing obs1;
    ObservaBoxing obs2;



    public Game(){}

    public Game(Boxer b1,Boxer b2){


        boxers[0]=b1;
        boxers[1]=b2;
//        this.obs1=obs1;
//        this.obs2=obs2;

    }

    public void  run(){

        for(int i = 1; i <= 20; i++){

            // get thread to see if b1 or b2 boxer
            if (System.identityHashCode(Thread.currentThread())==boxers[1].getid()){
                boxers[1].selectMove();
            }else{
                boxers[0].selectMove();
            }

        }

        AudioPlayer bell =  AudioPlayer.getInstance();
        bell.bellSound();

    }


}
