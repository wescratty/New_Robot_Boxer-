import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wescratty on 11/7/15.
 */
public class CircleLine {
    private static CircleLine ourInstance = new CircleLine();

    public static CircleLine getInstance() {
        return ourInstance;
    }

    private CircleLine() {
    }


    /**
     * Calculates line and slope between boxer centers, makes radius size of boxer reach, returns location of
     * boxer gloves
     * @param pointA center of boxer A
     * @param pointB center of boxer B
     * @param center  center of boxer A, redundant but had ideas for extendability which are unimplemented
     * @param radius of boxer reach
     * @param didBlock whether or not boxer blocked
     * @return two points that are gloves of boxer
     */
    protected List<Point> getCircleLineIntersectionPoint(Point pointA, Point pointB, Point center, double radius, boolean didBlock) {

        double baX;
        double baY;
        baX = pointB.Y() - pointA.Y();
        baY = pointB.X() - pointA.X();
        baX =baX*(-1);

        double caX = center.X() - pointA.X();
        double caY = center.Y() - pointA.Y();

        double a = baX * baX + baY * baY; // = distance ^2
        double bBy2 = baX * caX + baY * caY;// = distance ^2
        double c = caX * caX + caY * caY - radius * radius;// = distance ^2 of


        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;


        Point p1 = new Point(pointA.X() - baX * abScalingFactor1, pointA.Y()- baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Point p2 = new Point(pointA.X() - baX * abScalingFactor2, pointA.Y()
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }
}

