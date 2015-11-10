/**
 * Created by wescratty on 10/31/15.
 */

public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }


    public double X(){
        return this.x;

    }
    public double Y(){
        return this.y;

    }

    public void setX(int x){
        this.x=x;

    }
    public void setY(int y){
        this.y=y;

    }

    public  void setPoint(int x, int y){
        this.x=x;
        this.y=y;

    }

    /**
     *
     * @return
     */
    public String toString(){
        return "("+ this.x+","+this.y+")";
    }

}

