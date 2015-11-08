import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wescratty on 11/6/15.
 */
public class Paint_aBoxer extends JPanel {
    private static Paint_aBoxer ourInstance = new Paint_aBoxer();

    public static Paint_aBoxer getInstance() {
        return ourInstance;
    }


//    boolean makeRing = true;
    Boxer _boxer1;
    Boxer _boxer2;
    int b_1x = 200;
    int b_1y = 400;
    int b_2x = 600;
    int b_2y = 400;
    ArrayList<Point> bSplat = new ArrayList<>();
    ArrayList<String> bSize = new ArrayList<>();

    ArrayList<Point> rSplat = new ArrayList<>();
    ArrayList<String> rSize = new ArrayList<>();



    public Paint_aBoxer(){

    }

    public void setBoxers(Boxer a, Boxer b) {
        _boxer1 = a;
        _boxer2 = b;

    }


    protected void paintComponent (Graphics g){

        super.paintComponent(g);


        List<Point> gloves = new  ArrayList<Point>();
        int top = 100;
        int width = 800;
        int poleDiag = 80;
        int spatterSize = 5;

        ChanceBot chance = ChanceBot.getInstance();
        spatterSize=spatterSize+chance.getRandomChoice(8);

        Point nw= new Point(100,100);
        Point _b_1;
        Point _b_1Left;
        Point _b_1Right;
        Point _b_2Left;
        Point _b_2Right;


        Point _b_2;
        CircleLine circleLine = CircleLine.getInstance();

        b_1x = _boxer1.getX();
        b_1y = _boxer1.getY();

        b_2x = _boxer2.getX();
        b_2y = _boxer2.getY();

        _b_1 = new Point(b_1x,b_1y);
        _b_2 = new Point(b_2x,b_2y);

        gloves=circleLine.getCircleLineIntersectionPoint(_b_1, _b_2, _b_1, 50);

        _b_1Left = gloves.get(0);
        _b_1Right = gloves.get(1);

        gloves=circleLine.getCircleLineIntersectionPoint(_b_2, _b_1, _b_2, 50);

        _b_2Left = gloves.get(0);
        _b_2Right = gloves.get(1);

        if(_boxer1.getDidPunch()){
            _b_1Right =  _b_2;

            if(!_boxer2.getDidBlock()) {
                rSplat.add(_b_2);
                rSize.add(Integer.toString(spatterSize));
            }
//            else{
//                rSize.add(Integer.toString(0));
//            }

        }
        if(_boxer2.getDidPunch()){
            _b_2Right =  _b_1;


            if(!_boxer1.getDidBlock()) {
                bSplat.add(_b_1);
                bSize.add(Integer.toString(spatterSize));
            }
//            else{
//                bSplat.add(_b_1);
//                bSize.add(Integer.toString(spatterSize));
//            }
        }




        //create ring
        g.drawLine((int)nw.X(),(int) nw.Y(), top - poleDiag, top - poleDiag);
        g.drawLine(width, top, width + poleDiag, top - poleDiag);
        g.drawLine(top, width, top - poleDiag, width + poleDiag);
        g.drawLine(width, width, width + poleDiag, width + poleDiag);


        g.drawLine(top, top, top, width);
        g.drawLine(top, top, width, top);
        g.drawLine(width, top, width, width);
        g.drawLine(top, width, width, width);


        g.drawArc(20, 20, 20, 20, 20, 360);
        g.drawArc(20, 860, 20, 20, 20, 360);
        g.drawArc(860, 860, 20, 20, 20, 360);
        g.drawArc(860, 20, 20, 20, 20, 360);


        g.drawArc(30, 5, 840, 50, 180, 180);  //top rope
        g.drawArc(5, 30, 50, 840, 270, 180);  // left rope

        g.drawArc(845, 30, 50, 840, 90, 180);  //right rope
        g.drawArc(30, 845, 840, 50, 0, 180);

        g.setColor(Color.RED);
        int j = 0;

        for(Point i : rSplat){    //(String i : data)


            g.fillArc((int)i.X(),(int)i.Y(), Integer.parseInt(rSize.get(j)), Integer.parseInt(rSize.get(j)), 0, 360);
            j++;

        }
        g.setColor(Color.BLUE);
        j = 0;

        for(Point i : bSplat){    //(String i : data)

            g.fillArc((int)i.X(),(int)i.Y(), Integer.parseInt(bSize.get(j)), Integer.parseInt(bSize.get(j)), 0, 360);
            j++;

        }

        // Boxer 1
        g.setColor(Color.BLUE);
        g.fillArc(b_1x, b_1y, 50, 50, 0, 360);
        g.fillArc((int)_b_1Left.X(),(int)_b_1Left.Y(), 30, 30, 0, 360);
        g.fillArc((int) _b_1Right.X(), (int) _b_1Right.Y(), 30, 30, 0, 360);

        // Boxer 1
        g.setColor(Color.RED);
        g.fillArc(b_2x, b_2y, 50, 50, 0, 360);
        g.fillArc((int)_b_2Left.X(),(int)_b_2Left.Y(), 30, 30, 0, 360);
        g.fillArc((int)_b_2Right.X(),(int)_b_2Right.Y(), 30, 30, 0, 360);



}

//    public void updateBoxers() {
//        System.out.println("repaint--------------------");
//        repaint();
//
//    }

}
