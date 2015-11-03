/**
 * Created by wescratty on 10/31/15.
 */

// TODO On Uml this is an interface but I have not found a way to make a singleton interface or should we just have all
// of the methods in here?

public class RNG  {
    private static RNG ourInstance = new RNG();

    public static RNG getInstance() {
        return ourInstance;
    }

    private RNG() {
    }







}
