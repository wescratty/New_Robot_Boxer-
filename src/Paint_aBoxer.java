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


    private boolean b1_first = true;
    private boolean b2_first = true;
    List<Point> b_1gloves;
    List<Point> b_2gloves;
    public Paint_aBoxer(){
    }

    public void setBoxers(Boxer a, Boxer b) {
        _boxer1 = a;
        _boxer2 = b;
    }


    protected void paintComponent (Graphics g){
        super.paintComponent(g);


        final double REACHSCALE = 0.0007030371;

        double gw = getWidth();
        double factor = gw/889;
        int spatterSize;
        boolean _b1block = _boxer1.getDidBlock();
        boolean _b2block = _boxer2.getDidBlock();

        spatterSize=Integer.parseInt(hb.calculateDamage(_boxer1.getAttack(), _boxer1.getBlock()));


        Point _b_1;
        Point _b_1Left;
        Point _b_1Right;
        Point _b_2Left;
        Point _b_2Right;

        Point _b_2;
        CircleLine circleLine = CircleLine.getInstance();

        int newR1 = (int)(gw*_boxer1.getBoxerReach()*REACHSCALE);
        int newR2 = (int)(gw*_boxer2.getBoxerReach()*REACHSCALE);

        b_1x = (int)(_boxer1.getX()*factor);
        b_1y = (int)(_boxer1.getY()*factor);

        b_2x = (int)(_boxer2.getX()*factor);
        b_2y = (int)(_boxer2.getY()*factor);

        _b_1 = new Point(b_1x,b_1y);
        _b_2 = new Point(b_2x,b_2y);

        if(b1_first) {
            b_1gloves=circleLine.getCircleLineIntersectionPoint(_b_1, _b_2, _b_1, newR1,_b1block);
        }


        _b_1Left = b_1gloves.get(0);
        _b_1Right = b_1gloves.get(1);


        if(b2_first) {
            b_2gloves=circleLine.getCircleLineIntersectionPoint(_b_2, _b_1, _b_2, newR2,_b2block);
        }



        _b_2Left = b_2gloves.get(0);
        _b_2Right = b_2gloves.get(1);

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



        //947
        double fac1 = .8998875;
        int w = (int)(gw*fac1);
        int ballwidth = (int)(gw*.02249719);
        int ballHight=(int)(gw*.02111932);
        int top = 100;
        int poleDiag = (int)(gw*0.08998875);
        int fullCirc = 360;
        int glvSze = (int)(gw*0.03374578);
        int hedSze = (int)(gw*.05624297);
        Point nw= new Point(top,top);

        //ring
        g.drawLine((int)nw.X(), (int)nw.Y(), (int)nw.X(), w);
        g.drawLine((int)nw.X(), (int)nw.Y(), w, (int)nw.Y());
        g.drawLine(w, (int)nw.Y(), w, w);
        g.drawLine((int)nw.X(), w, w, w);

        //poles
        g.drawLine((int) nw.X(), (int) nw.Y(), (int) nw.X() - poleDiag, (int) nw.Y() - poleDiag);
        g.drawLine(w, (int) nw.Y(), w + poleDiag, (int) nw.Y() - poleDiag);
        g.drawLine((int) nw.X(), w, (int) nw.X() - poleDiag, w + poleDiag);
        g.drawLine(w, w, w + poleDiag, w + poleDiag);


        g.setColor(Color.BLUE);
        //balls
        g.drawArc((int) nw.X() - poleDiag, (int) nw.Y() - poleDiag, ballHight, ballwidth, ballwidth, fullCirc);
        g.drawArc(w - ballwidth + poleDiag, (int) nw.Y() - poleDiag, ballHight, ballwidth, ballwidth, fullCirc);
        g.drawArc((int) nw.X() - poleDiag, w - ballwidth + poleDiag, ballHight, ballwidth, ballwidth, fullCirc);
        g.drawArc(w - ballwidth + poleDiag, w - ballwidth + poleDiag, ballHight, ballwidth, ballwidth, fullCirc);




        //ropes

        g.drawLine((int) nw.X() - poleDiag, (int) nw.Y()- poleDiag,(int) nw.X() - poleDiag, w+ poleDiag);
        g.drawLine((int) nw.X()- poleDiag, (int) nw.Y()- poleDiag,  w+ poleDiag,(int) nw.Y()- poleDiag);
        g.drawLine( w+ poleDiag,(int) nw.Y()- poleDiag, w+poleDiag, w+poleDiag);
        g.drawLine((int) nw.X() - poleDiag, w+ poleDiag, w+poleDiag, w+poleDiag);


        g.setColor(Color.RED);
        poleDiag = (int)(gw*0.08998875/2);
        //balls
        g.drawArc((int) nw.X() - poleDiag, (int) nw.Y() - poleDiag, ballHight, ballwidth, ballwidth, fullCirc);
        g.drawArc(w - ballwidth + poleDiag, (int) nw.Y() - poleDiag, ballHight, ballwidth, ballwidth, fullCirc);
        g.drawArc((int) nw.X() - poleDiag, w - ballwidth + poleDiag, ballHight, ballwidth, ballwidth, fullCirc);
        g.drawArc(w - ballwidth + poleDiag, w - ballwidth + poleDiag, ballHight, ballwidth, ballwidth, fullCirc);


        //ropes
        g.drawLine((int) nw.X() - poleDiag, (int) nw.Y()- poleDiag,(int) nw.X() - poleDiag, w+ poleDiag);
        g.drawLine((int) nw.X()- poleDiag, (int) nw.Y()- poleDiag,  w+ poleDiag,(int) nw.Y()- poleDiag);
        g.drawLine( w+ poleDiag,(int) nw.Y()- poleDiag, w+poleDiag, w+poleDiag);
        g.drawLine((int) nw.X() - poleDiag, w+ poleDiag, w+poleDiag, w+poleDiag);









        g.setColor(Color.RED);
        int j = 0;

        for (Point i : rSplat) {    //(String i : data)


            g.drawArc((int) i.X(), (int) i.Y(), Integer.parseInt(rSize.get(j)), Integer.parseInt(rSize.get(j)), 0, fullCirc);
            j++;

        }
        g.setColor(Color.BLUE);
        j = 0;

        for(Point i : bSplat){    //(String i : data)

            g.drawArc((int) i.X(), (int) i.Y(), Integer.parseInt(bSize.get(j)), Integer.parseInt(bSize.get(j)), 0, fullCirc);
            j++;


        }

        // Boxer 1
        g.setColor(Color.BLUE);
        if(_boxer1.getThisBoxerDown()){


                b1_first = false;

                g.fillArc(b_1x, b_1y, hedSze * 2, hedSze / 2, 0, fullCirc);
                g.fillArc((int) _b_1Left.X(), (int) _b_1Left.Y(), glvSze * 2, glvSze / 2, 0, fullCirc);
                g.fillArc((int) _b_1Right.X(), (int) _b_1Right.Y(), glvSze * 2, glvSze / 2, 0, fullCirc);


        }else{
            b1_first = true;

            g.fillArc(b_1x, b_1y, hedSze, hedSze, 0, fullCirc);
            g.fillArc((int)_b_1Left.X(),(int)_b_1Left.Y(), glvSze, glvSze, 0, fullCirc);
            g.fillArc((int) _b_1Right.X(), (int) _b_1Right.Y(), glvSze, glvSze, 0, fullCirc);
        }



        // Boxer 2
        g.setColor(Color.RED);
        if(_boxer2.getThisBoxerDown()){

                b2_first = false;

                g.fillArc(b_2x, b_2y, hedSze * 2, hedSze / 2, 0, fullCirc);
                g.fillArc((int) _b_2Left.X(), (int) _b_2Left.Y(), glvSze * 2, glvSze / 2, 0, fullCirc);
                g.fillArc((int) _b_2Right.X(), (int) _b_2Right.Y(), glvSze * 2, glvSze / 2, 0, fullCirc);


        }else{

            b2_first = true;
            g.fillArc(b_2x, b_2y, hedSze, hedSze, 0, fullCirc);
            g.fillArc((int)_b_2Left.X(),(int)_b_2Left.Y(), glvSze, glvSze, 0, fullCirc);
            g.fillArc((int) _b_2Right.X(), (int) _b_2Right.Y(), glvSze, glvSze, 0, fullCirc);
        }

}


}
