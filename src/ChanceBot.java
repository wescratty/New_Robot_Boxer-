import java.util.Random;

/**
 * Created by wescratty on 10/31/15.
 */
public class ChanceBot {
    private static ChanceBot ourInstance = new ChanceBot();

    public static ChanceBot getInstance() {
        return ourInstance;
    }



    private Random random= new Random();
    protected Point point;

    public ChanceBot(){


    }



    public int getRandInstance(){
        return random.nextInt();

    }
    public Point pickNewLocation(){
        point = new Point(random.nextInt(600)+200,random.nextInt(600)+200);
        return point;

    }
    public int getRandomAttackDelay(int delay){
        return random.nextInt(delay)+delay/2;

    }
    public int getRandomChoice(int choices){
        return random.nextInt(choices);

    }
    public int getChance(){
        return random.nextInt();

    }
    public int getCoinFlip(){

        return random.nextInt(2);



    }

}
