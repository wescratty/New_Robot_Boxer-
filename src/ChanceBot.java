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
        // todo I had an error that said delay was negative called from boxer.

    }
    public int getRandomChoice(int choices){
        return random.nextInt(choices);

    }
    public double getChance(){
        return random.nextDouble();

    }
    public boolean getCoinFlip(){

        return getChance()>0.5;
    }
    public int[] RandomizeArray(int[] array){

        for (int i=0; i<array.length; i++) {
            int randomPosition = random.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }
}
