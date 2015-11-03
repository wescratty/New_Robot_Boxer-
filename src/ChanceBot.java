import java.util.Random;

/**
 * Created by wescratty on 10/31/15.
 */
public class ChanceBot {
    private Random random= new Random();

    public ChanceBot(){


    }



    public int getRandInstance(){
        return random.nextInt();

    }
    public int getRandomAttackDelay(){
        return random.nextInt(1000);

    }
    public int getRandomChoice(){
        return random.nextInt(3);

    }
    public int getChance(){
        return random.nextInt();

    }
    public int getCoinFlip(){

        return random.nextInt(2);



    }

}
