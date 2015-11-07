import javax.swing.*;
import java.awt.*;

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

    public Paint_aBoxer(){

    }

    public void setBoxers(Boxer a, Boxer b) {
        _boxer1 = a;
        _boxer2 = b;

    }

    protected void paintComponent (Graphics g){

        super.paintComponent(g);

        int top = 100;
        int width = 800;
        int poleDiag = 80;
        Point nw= new Point(100,100);

        b_1x = _boxer1.getX();
        b_1y = _boxer1.getY();

        b_2x = _boxer2.getX();
        b_2y = _boxer2.getY();


        //create ring
        g.drawLine(nw.X(), nw.Y(), top - poleDiag, top - poleDiag);
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

        // Boxer 1
        g.setColor(Color.BLUE);
        g.fillArc(b_1x, b_1y, 50, 50, 0, 360);
//        g.fillArc(200+30, 400+50, 30, 30, 0, 360);
//        g.fillArc(200+30, 400-30, 30, 30, 0, 360);

        // Boxer 1
        g.setColor(Color.RED);
        g.fillArc(b_2x, b_2y, 50, 50, 0, 360);
//        g.fillArc(600, 400+50, 30, 30, 0, 360);
//        g.fillArc(600, 400-30, 30, 30, 0, 360);

}

//    public void updateBoxers() {
//        System.out.println("repaint--------------------");
//        repaint();
//
//    }

}
