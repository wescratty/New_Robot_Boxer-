/**
 * Created by wescratty on 10/31/15.
 */

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }


    public int X(){
        return this.x;

    }
    public int Y(){
        return this.y;

    }

    public void setX(int x){
        this.x=x;

    }
    public void setY(int y){
        this.y=y;

    }



    public String toString(){
        return "("+ this.x+","+this.y+")";
    }

}

