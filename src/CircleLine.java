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



    public static List<Point> getCircleLineIntersectionPoint(Point pointA,
                                                             Point pointB, Point center, double radius) {
        double baX = pointB.Y() - pointA.Y();
        double baY = pointB.X() - pointA.X();
        baX =baX*(-1);
        double caX = center.X() - pointA.X();
        double caY = center.Y() - pointA.Y();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

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

