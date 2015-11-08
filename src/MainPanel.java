
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;



public class MainPanel extends JPanel {
    private static MainPanel ourInstance = new MainPanel();

    public static MainPanel getInstance() {
        return ourInstance;
    }

    private MainPanel() {

    }
    BoxerDirector builder = new BoxerDirector();

    Boxer _boxer1 = builder.build(100);
    Boxer _boxer2 = builder.build(100);
//    public JLabel[] labArr = new JLabel[9];
//
//    for(JLabel label : labArr) {
//        label = new JLabel();
//    }
      JLabel strenghtLblP1, speedLblP1, accuracyLblP1, reachLblP1, strenghtLblP2, speedLblP2, accuracyLblP2, reachLblP2, time, timer, splash,p1Strenght,p1Speed,P1Accuracy,p1Reach,p2Strenght,p2Speed,P2Accuracy,p2Reach;


    ArrayList<JLabel> labArray = new ArrayList<>();
    ArrayList<JLabel> titleLabArray = new ArrayList<>();



    protected void makeMain() {



        JPanel b1LabelPanel = new JPanel();
        JPanel b2LabelPanel = new JPanel();
        JPanel gameLabelPanel = new JPanel();
        JPanel subPanel = new JPanel();

        time = new JLabel("Time left in bought: ");
        timer = new JLabel("5:00");
        splash = new JLabel("client message");

        titleLabArray.add(p1Strenght = new JLabel(" P1 Strength: "));
        titleLabArray.add(p1Speed = new JLabel(" P1 Speed: "));
        titleLabArray.add(P1Accuracy = new JLabel(" P1 Accuracy: "));
        titleLabArray.add(p1Reach = new JLabel(" P1 Reach: "));
        titleLabArray.add(p2Strenght = new JLabel(" P2 Strength: "));
        titleLabArray.add(p2Speed = new JLabel(" P2 Speed: "));
        titleLabArray.add(P2Accuracy = new JLabel(" P2 Accuracy: "));
        titleLabArray.add(p2Reach = new JLabel(" P2 Reach: "));

        labArray.add(strenghtLblP1 = new JLabel("  0   "));
        labArray.add(speedLblP1 = new JLabel("  0  "));
        labArray.add(accuracyLblP1 = new JLabel("  0   "));
        labArray.add(reachLblP1 = new JLabel("   0   "));
        labArray.add(strenghtLblP2 = new JLabel("  0   "));
        labArray.add(speedLblP2 = new JLabel("  0  "));
        labArray.add(accuracyLblP2 = new JLabel("   0   "));
        labArray.add(reachLblP2 = new JLabel("   0   "));



        for(int i=0; i<4; i++){
            b1LabelPanel.add(titleLabArray.get(i));
            b1LabelPanel.add(labArray.get(i));

        }
        for(int i=4; i<8; i++){
            b2LabelPanel.add(titleLabArray.get(i));
            b2LabelPanel.add(labArray.get(i));

        }

        b1LabelPanel.setLayout(new BoxLayout(b1LabelPanel, BoxLayout.PAGE_AXIS));
        b1LabelPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));


        b2LabelPanel.setLayout(new BoxLayout(b2LabelPanel, BoxLayout.PAGE_AXIS));

        b2LabelPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        gameLabelPanel.add(time);
        gameLabelPanel.add(timer);
        gameLabelPanel.add(splash);
        gameLabelPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        subPanel.setLayout(new BorderLayout());
        subPanel.add(b1LabelPanel, BorderLayout.EAST);
        subPanel.add(b2LabelPanel, BorderLayout.WEST);
        subPanel.add(gameLabelPanel, BorderLayout.CENTER);
        subPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLoweredBevelBorder());
        setBackground(Color.LIGHT_GRAY);

        Paint_aBoxer paintBoxers = Paint_aBoxer.getInstance();
        add(paintBoxers);

        add(subPanel, BorderLayout.SOUTH);

        create();

    }


    public void create(){
//        Dialog boxerStats = Dialog.getInstance();
//        String b1Sts = boxerStats.getBoxerStats();


        ObservaBoxing obs1 = new ObservaBoxing(_boxer1);
        ObservaBoxing obs2 = new ObservaBoxing(_boxer2);

        _boxer1.register(obs2);
        _boxer2.register(obs1);

//        _boxer1.setOtherBoxerLoc(_boxer2);
//        _boxer2.setOtherBoxerLoc(_boxer1);


        Runnable game = new Game(_boxer1,_boxer2);


        Thread paintThread = new Thread(game);
        Thread boxer1Thread = new Thread(game);
        int b1Identifier = System.identityHashCode(boxer1Thread);
        Thread boxer2Thread = new Thread(game);
        int b2Identifier = System.identityHashCode(boxer2Thread);


        _boxer1.setid(b1Identifier,1);
        _boxer2.setid(b2Identifier, 2);
        System.out.print(_boxer1.getid());


        boxer1Thread.start();
        boxer2Thread.start();
        paintThread.start();

        // TODO join threads
//        try {
//            boxer1Thread.join();
//            boxer2Thread.join();
//        }catch(Exception e){}


        //TODO +create() pushImageState(String state)<<no idea on formatting yet>>



    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setLables(String time){



        String inputs_b1= _boxer1.getStats();
        String[] inputArrayB1 = inputs_b1.split("\\|");
        String inputs_b2= _boxer2.getStats();
        String[] inputArrayB2 = inputs_b2.split("\\|");

        try {

            for(int i=0; i<4; i++){
                labArray.get(i).setText(inputArrayB1[i+1]);//""+exp+"|"+strengthScore+"|"+agilityScore+"|"+accuracy+"|"+reach+"|"+fatigue;
            }

            for(int i=4; i<8; i++){

                labArray.get(i).setText(inputArrayB2[i-3]);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }




        timer .setText(time);
        splash.setText("");




    }

}
