/**
 * Created by Brian Trethewey on 11/7/15.
 */
public class StatDialogue {
    //constatns for this program
    final int STEPPING = 5;
    final int STRCOST = 1;
    final int SPEEDCOST = 1;
    final int ACCURACYCOST =1;
    final int RANGECOST = 1;

    private int pointsUsed;
    private Dialog dialog = Dialog.getInstance();


    public String getStats(int unusedPoints, String currentStats){
        //set default values
        pointsUsed= 0;
        int currentPoints = unusedPoints;
        int strength = 0;
        int speed = 0;
        int accuracy = 0;
        int range = 0;

        // break intput string into arrays
        String[] statsArray = currentStats.split("|");
        //parse string results into correct variables
        try {
            strength = Integer.parseInt(statsArray[0]);
            speed = Integer.parseInt(statsArray[1]);
            accuracy = Integer.parseInt(statsArray[2]);
            range = Integer.parseInt(statsArray[4]);
        }catch (IndexOutOfBoundsException e){
            throw e;
        }
        //get strength from User
        Integer[] strengthPossibilites = range(strength, (unusedPoints /(5*STRCOST)), STEPPING);
        Integer newStrength = (Integer)dialog.getStat("Strength","Strength Value:(Cost "+STRCOST+" Points )",strengthPossibilites);
        int deltaStrength = newStrength -strength;
        pointsUsed += deltaStrength * STRCOST;
        unusedPoints -= pointsUsed;
        strength = newStrength;

        //get Speed from User
        Integer[] SpeedPossibilites = range(speed, (unusedPoints /(5*SPEEDCOST)), STEPPING);
        Integer newSpeed = (Integer)dialog.getStat("Speed","Speed Value: (Cost "+SPEEDCOST+" Points )",SpeedPossibilites);
        int deltaSpeed = newSpeed -speed;
        pointsUsed += deltaSpeed * SPEEDCOST;
        unusedPoints -= pointsUsed;
        speed = newSpeed;

        //get Accuracy from User
        Integer[] AccuracyPossibilites = range(accuracy, (unusedPoints /(5*ACCURACYCOST)), STEPPING);
        Integer newAccuracy = (Integer)dialog.getStat("Accuracy","Accuracy Value: (Cost "+ACCURACYCOST+" Points )",AccuracyPossibilites);
        int deltaAccuracy = newAccuracy -accuracy;
        pointsUsed += deltaAccuracy * ACCURACYCOST;
        unusedPoints -= pointsUsed;
        accuracy = newAccuracy;

        //get Range from User
        Integer[] rangePossibilities = range(accuracy, (unusedPoints /(5*RANGECOST)), STEPPING);
        Integer newRange = (Integer)dialog.getStat("Range","Range Value: (Cost "+RANGECOST+" Points )",rangePossibilities);
        int detaRange = newRange -range;
        pointsUsed += detaRange * RANGECOST;
        unusedPoints -= pointsUsed;
        range = newRange;

        //create String Pipe delimiter usedPoints, Strength, speed, accuracy range order
        String resultString = ""+pointsUsed+"|"+strength+"|"+speed+"|"+accuracy+"|"+range;
        return resultString;
    }

    public Integer[] range(int start, int stop, int step)
    {
        Integer[] result = new Integer[((stop-start)/step)+1];

        for(int i=0;i<stop-start;i+=step)
            result[i] = start+i;

        return result;
    }
}
