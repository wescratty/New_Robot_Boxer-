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


    public String getStats(int unusedPoints, String currentStats,String boxerID){
        //set default values
        pointsUsed= 0;
        int currentPoints = unusedPoints;
        int strength = 0;
        int speed = 0;
        int accuracy = 0;
        int range = 0;

        // flag for cancel
        boolean isValid = true;

        // break input string into arrays
        String[] statsArray = currentStats.split("\\|");
        //parse string results into correct variables
        System.out.println(statsArray.toString());
        try {
            strength = Integer.parseInt(statsArray[1]);
            speed = Integer.parseInt(statsArray[2]);
            accuracy = Integer.parseInt(statsArray[3]);
            range = Integer.parseInt(statsArray[4]);
        }catch (IndexOutOfBoundsException e){
            throw e;
        }
        //get strength from User
        int maxStr =strength +  (unusedPoints /(STEPPING*STRCOST)*STEPPING);
        Integer[] strengthPossibilites = range(strength, maxStr, STEPPING);
        Integer newStrength = (Integer)dialog.getStat(boxerID,"Strength Value:(Cost "+STRCOST+" Points )",strengthPossibilites);
        if (newStrength == null) {
        isValid = false;
        }else {
            int deltaStrength = newStrength - strength;
            pointsUsed += deltaStrength * STRCOST;
            unusedPoints -= pointsUsed;
            strength = newStrength;
        }

        //get Speed from User
        if (isValid) {
            int maxSpeed = speed + (unusedPoints / (STEPPING * SPEEDCOST) * STEPPING);
            Integer[] SpeedPossibilites = range(speed, maxSpeed, STEPPING);
            Integer newSpeed = (Integer) dialog.getStat(boxerID, "Speed Value: (Cost " + SPEEDCOST + " Points )", SpeedPossibilites);
            if (newSpeed == null) {
                isValid = false;
            } else {
                int deltaSpeed = newSpeed - speed;
                pointsUsed += deltaSpeed * SPEEDCOST;
                unusedPoints -= pointsUsed;
                speed = newSpeed;
            }
        }
        //get Accuracy from User
        if (isValid) {
            int maxAccuracy = accuracy + (unusedPoints / (STEPPING * ACCURACYCOST) * STEPPING);
            Integer[] AccuracyPossibilites = range(accuracy, maxAccuracy, STEPPING);
            Integer newAccuracy = (Integer) dialog.getStat(boxerID, "Accuracy Value: (Cost " + ACCURACYCOST + " Points )", AccuracyPossibilites);
            if (newAccuracy == null) {
                isValid = false;
            }else {
                int deltaAccuracy = newAccuracy - accuracy;
                pointsUsed += deltaAccuracy * ACCURACYCOST;
                unusedPoints -= pointsUsed;
                accuracy = newAccuracy;
            }
        }
        //get Range from User
        if (isValid) {
            int maxRange = range + (unusedPoints / (STEPPING * RANGECOST) * STEPPING);
            Integer[] rangePossibilities = range(range, maxRange, STEPPING);
            Integer newRange = (Integer) dialog.getStat(boxerID, "Range Value: (Cost " + RANGECOST + " Points )", rangePossibilities);
            if (newRange == null) {
                isValid = false;
            } else {
                int detaRange = newRange - range;
                pointsUsed += detaRange * RANGECOST;
                unusedPoints -= pointsUsed;
                range = newRange;
                //TODO fix min range
                if (range<90) {
                    range = 90;
                }
            }
        }
        String resultString = null;
        if (isValid) {
            //create String Pipe delimiter usedPoints, Strength, speed, accuracy range order
            resultString = "" + pointsUsed + "|" + strength + "|" + speed + "|" + accuracy + "|" + range;
        }
        return resultString;
    }

    public Integer[] range(int start, int stop, int step){
    Integer[] result;

        if (start >= stop){
        result = new Integer[1];
        result[0]=start;
    }else {
            result = new Integer[((stop - start) / step) + 1];
            int topValue = start + (((stop - start) / step)) * step;
            Integer value = start;
            for (int i = 0; value < topValue; i++) {
                value = start + i * step;
                result[i] = value;
            }
        }
        return result;
    }

    public void errorBox(String message){
        dialog.errorBox(message);
    }
}
