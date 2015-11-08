
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
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

    public JLabel  fatigueLblP1, strengthLblP1, agilityLblP1, fatigueLblP2, strengthLblP2, agilityLblP2, time, timer, splash;



    protected void makeMain() {



        JPanel b1LabelPanel = new JPanel();
        JPanel b2LabelPanel = new JPanel();
        JPanel gameLabelPanel = new JPanel();
        JPanel subPanel = new JPanel();

        time = new JLabel("Time left in bought: ");
        timer = new JLabel("5:00");
        splash = new JLabel("client message");
        fatigueLblP1 = new JLabel(" P1 Fatigue: 0   ");
        strengthLblP1 = new JLabel(" P1 Strength: 0  ");
        agilityLblP1 = new JLabel("  P1 Agility: 0   ");
        fatigueLblP2 = new JLabel("  P2 Fatigue: 0   ");
        strengthLblP2 = new JLabel(" P2 Strength: 0  ");
        agilityLblP2 = new JLabel("  P2 Agility: 0   ");


        b1LabelPanel.add(fatigueLblP1);
        b1LabelPanel.add(strengthLblP1);
        b1LabelPanel.add(agilityLblP1);
        b1LabelPanel.setLayout(new BoxLayout(b1LabelPanel, BoxLayout.PAGE_AXIS));
        b1LabelPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        b2LabelPanel.add(fatigueLblP2);
        b2LabelPanel.add(strengthLblP2);
        b2LabelPanel.add(agilityLblP2);
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

    public void setLables(String string){
//        time = new JLabel("Time left in bought: ");
//        timer = new JLabel("5:00");
//        splash = new JLabel("client message");
//        fatigueLblP1 = new JLabel(" P1 Fatigue: 0   ");
//        strengthLblP1 = new JLabel(" P1 Strength: 0  ");
//        agilityLblP1 = new JLabel("  P1 Agility: 0   ");
//        fatigueLblP2 = new JLabel("  P2 Fatigue: 0   ");
//        strengthLblP2 = new JLabel(" P2 Strength: 0  ");
//        agilityLblP2 = new JLabel("  P2 Agility: 0   ");



        timer .setText(string);
        splash.setText("");
//        splash = new JLabel("client message");
//        fatigueLblP1 = new JLabel(" P1 Fatigue: 0   ");
//        strengthLblP1 = new JLabel(" P1 Strength: 0  ");
//        agilityLblP1 = new JLabel("  P1 Agility: 0   ");
//        fatigueLblP2 = new JLabel("  P2 Fatigue: 0   ");
//        strengthLblP2 = new JLabel(" P2 Strength: 0  ");
//        agilityLblP2 = new JLabel("  P2 Agility: 0   ");



    }

}
