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

    Boxer _boxer1;
    Boxer _boxer2;
    int b_1x = 200;
    int b_1y = 400;
    int b_2x = 600;
    int b_2y = 400;
    HurtBox hb = HurtBox.getInstance();
    ArrayList<Point> bSplat = new ArrayList<Point>();
    ArrayList<String> bSize = new ArrayList<String>();

    ArrayList<Point> rSplat = new ArrayList<Point>();
    ArrayList<String> rSize = new ArrayList<String>();


    public Paint_aBoxer(){
    }

    public void setBoxers(Boxer a, Boxer b) {
        _boxer1 = a;
        _boxer2 = b;
    }


    protected void paintComponent (Graphics g){
        super.paintComponent(g);

        List<Point> gloves;
        int top = 100;
        int width = 800;
        int poleDiag = 80;
        int postBall = 20;
        int fullCirc = 360;
        int topwidth = 860;
        int spatterSize;
        boolean _b1block = _boxer1.getDidBlock();
        boolean _b2block = _boxer2.getDidBlock();

        spatterSize=Integer.parseInt(hb.calculateDamage(_boxer1.getAttack(), _boxer1.getBlock()));

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

        gloves=circleLine.getCircleLineIntersectionPoint(_b_1, _b_2, _b_1, 50,_b1block);

        _b_1Left = gloves.get(0);
        _b_1Right = gloves.get(1);

        gloves=circleLine.getCircleLineIntersectionPoint(_b_2, _b_1, _b_2, 50,_b2block);

        _b_2Left = gloves.get(0);
        _b_2Right = gloves.get(1);

        if(_boxer1.getDidPunch()){
            _b_1Right =  _b_2;

            if(!_b2block) {
                rSplat.add(_b_2);
                rSize.add(Integer.toString(spatterSize));
            }
        }

        if(_boxer2.getDidPunch()){
            _b_2Right =  _b_1;

            if(!_b1block) {
                bSplat.add(_b_1);
                bSize.add(Integer.toString(spatterSize));
            }
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


        g.drawArc(postBall, postBall, postBall, postBall, postBall, fullCirc);
        g.drawArc(postBall, topwidth, postBall, postBall, postBall, fullCirc);
        g.drawArc(topwidth, topwidth, postBall, postBall, postBall, fullCirc);
        g.drawArc(topwidth, postBall, postBall, postBall, postBall, fullCirc);


        g.drawArc(30, 5, 840, 50, fullCirc/2, fullCirc/2);    // top rope
        g.drawArc(5, 30, 50, 840, 270, fullCirc/2);    // left rope

        g.drawArc(845, 30, 50, 840, 90, fullCirc/2);   // right rope
        g.drawArc(30, 845, 840, 50, 0, fullCirc/2);    // bottom rope

        g.setColor(Color.RED);
        int j = 0;

        for (Point i : rSplat) {    //(String i : data)


            g.drawArc((int) i.X(), (int) i.Y(), Integer.parseInt(rSize.get(j)), Integer.parseInt(rSize.get(j)), 0, fullCirc);
//            g.drawRect((int)i.X(),(int)i.Y(), Integer.parseInt(rSize.get(j)), Integer.parseInt(rSize.get(j)));
            j++;

        }
        g.setColor(Color.BLUE);
        j = 0;

        for(Point i : bSplat){    //(String i : data)

//            g.drawRect((int) i.X(), (int) i.Y(), Integer.parseInt(bSize.get(j)), Integer.parseInt(bSize.get(j)));
            g.drawArc((int) i.X(), (int) i.Y(), Integer.parseInt(bSize.get(j)), Integer.parseInt(bSize.get(j)), 0, fullCirc);
            j++;


        }

        // Boxer 1
        g.setColor(Color.BLUE);
        g.fillArc(b_1x, b_1y, 50, 50, 0, fullCirc);
        g.fillArc((int)_b_1Left.X(),(int)_b_1Left.Y(), 30, 30, 0, fullCirc);
        g.fillArc((int) _b_1Right.X(), (int) _b_1Right.Y(), 30, 30, 0, fullCirc);

        // Boxer 1
        g.setColor(Color.RED);
        g.fillArc(b_2x, b_2y, 50, 50, 0, fullCirc);
        g.fillArc((int)_b_2Left.X(),(int)_b_2Left.Y(), 30, 30, 0, fullCirc);
        g.fillArc((int)_b_2Right.X(),(int)_b_2Right.Y(), 30, 30, 0, fullCirc);



}

//    public void updateBoxers() {
//        System.out.println("repaint--------------------");
//        repaint();
//
//    }

}
