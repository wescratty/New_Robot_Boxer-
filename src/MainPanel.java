
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;



public class MainPanel extends JPanel {
    private static MainPanel ourInstance = new MainPanel();

    public static MainPanel getInstance() {
        return ourInstance;
    }

    private MainPanel() {

    }

      JLabel expLbP1, strenghtLblP1, agilityLblP1, accuracyLblP1, reachLblP1, fatigueLbP1, expLbP2, strenghtLblP2, agilityLblP2, accuracyLblP2, reachLblP2,fatigueLbP2,  time, timer, splash,
              p1Exp, p1Strenght,p1Agile,P1Accuracy,p1Reach,p1Fatigue ,p2Exp,p2Strenght,p2Agile,P2Accuracy,p2Reach,p2Fatigue,roundLbl,roundNumLbl,roundsWonp1, roundsWonp2,wonp1,wonp2;

    ArrayList<JLabel> labArray = new ArrayList<JLabel>();
    ArrayList<JLabel> titleLabArray = new ArrayList<JLabel>();
    GameTimer dt = GameTimer.getInstance();
    String padding = "              ";
    Game game;


    protected void makeMain() {


        JPanel b1LabelPanel = new JPanel();
        JPanel b2LabelPanel = new JPanel();
        JPanel gameLabelPanel = new JPanel();
        JPanel subPanel = new JPanel();

        time = new JLabel("Time left in bought: ");
        time.setBorder(new EmptyBorder(10, 10, 10, 10));
        roundLbl = new JLabel("Round: ");
        roundLbl.setBorder(new EmptyBorder(10, 10, 10, 10));
        roundNumLbl = new JLabel(padding+"0");
        roundNumLbl.setBorder(new EmptyBorder(10, 10, 10, 10));
        timer = new JLabel("5:00");
        timer.setBorder(new EmptyBorder(10, 10, 10, 10));
        splash = new JLabel("client message");
        splash.setBorder(new EmptyBorder(10, 10, 10, 10));

        titleLabArray.add(p1Exp = new JLabel("  Experience: "));
        titleLabArray.add(p1Strenght = new JLabel("  Strength: "));
        titleLabArray.add(p1Agile = new JLabel("  Agility: "));
        titleLabArray.add(P1Accuracy = new JLabel("  Accuracy: "));
        titleLabArray.add(p1Reach = new JLabel("  Reach: "));
        titleLabArray.add(p1Fatigue = new JLabel("  Fatigue: "));

        titleLabArray.add(p2Exp = new JLabel(" Experience: "));
        titleLabArray.add(p2Strenght = new JLabel("  Strength: "));
        titleLabArray.add(p2Agile = new JLabel("  Agility: "));
        titleLabArray.add(P2Accuracy = new JLabel(" P2 Accuracy: "));
        titleLabArray.add(p2Reach = new JLabel("  Reach: "));
        titleLabArray.add(p2Fatigue = new JLabel("  Fatigue: "));

        labArray.add(expLbP1 = new JLabel("  0   "));
        labArray.add(strenghtLblP1 = new JLabel("  0   "));
        labArray.add(agilityLblP1 = new JLabel("  0  "));
        labArray.add(accuracyLblP1 = new JLabel("  0   "));
        labArray.add(reachLblP1 = new JLabel("   0   "));
        labArray.add(fatigueLbP1 = new JLabel("   0   "));

        labArray.add(expLbP2 = new JLabel("  0   "));
        labArray.add(strenghtLblP2 = new JLabel("  0   "));
        labArray.add(agilityLblP2 = new JLabel("  0  "));
        labArray.add(accuracyLblP2 = new JLabel("   0   "));
        labArray.add(reachLblP2 = new JLabel("   0   "));
        labArray.add(fatigueLbP2 = new JLabel("   0   "));


        for (int i = 0; i < titleLabArray.size(); i++) {
            titleLabArray.get(i).setBorder(new EmptyBorder(10, 10, 10, 10));
            if(i<titleLabArray.size()/2) {
                b1LabelPanel.add(titleLabArray.get(i));
                b1LabelPanel.add(labArray.get(i));
            }else{
                b2LabelPanel.add(titleLabArray.get(i));
                b2LabelPanel.add(labArray.get(i));
            }
        }

        b1LabelPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
        b1LabelPanel.setLayout(new BoxLayout(b1LabelPanel, BoxLayout.PAGE_AXIS));

        b2LabelPanel.setLayout(new BoxLayout(b2LabelPanel, BoxLayout.PAGE_AXIS));
        b2LabelPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.blue));


        gameLabelPanel.setLayout(new BoxLayout(gameLabelPanel, BoxLayout.PAGE_AXIS));
        gameLabelPanel.add(time);
        gameLabelPanel.add(timer);
        gameLabelPanel.add(roundLbl);
        gameLabelPanel.add(roundNumLbl);
        gameLabelPanel.add(splash);
        gameLabelPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        subPanel.setLayout(new BorderLayout());
        subPanel.add(b1LabelPanel, BorderLayout.NORTH);
        subPanel.add(b2LabelPanel, BorderLayout.SOUTH);
        subPanel.add(gameLabelPanel, BorderLayout.CENTER);
        subPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLoweredBevelBorder());
        setBackground(Color.LIGHT_GRAY);

        Paint_aBoxer paintBoxers = Paint_aBoxer.getInstance();
        add(paintBoxers);

        add(subPanel, BorderLayout.EAST);
       int choice =-1;

        while(choice==-1) {
            choice =getChoice();
            if (choice == 0) {
                game = PVPGame.getInstance();
            } else if (choice == 1) {
                game = AIGame.getInstance();

            }
        }


        game.start();


    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setLables(String _boxer1, int b1Id, String _boxer2, int b2Id){

        String inputs_b1;
        String inputs_b2;

        if(b1Id<b2Id){
             inputs_b1= _boxer1;
             inputs_b2= _boxer2;
        }else{
             inputs_b1= _boxer2;
             inputs_b2= _boxer1;
        }

        String[] inputArrayB1 = inputs_b1.split("\\|");

        String[] inputArrayB2 = inputs_b2.split("\\|");


        try {
            for(int i=0; i<labArray.size(); i++){
                if(i<(labArray.size()/2)) {
                    labArray.get(i).setText(padding+inputArrayB1[i]);
                }else{
                    labArray.get(i).setText(padding+inputArrayB2[i - labArray.size()/2]);
                }
            }


        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void setTime(){
        timer .setText(padding + Double.toString(dt.clockTime()));
    }
    public void setSplash(String sp){
        splash.setText(sp);
    }
    public void setRound(String rd){
        roundNumLbl.setText(padding+rd);
    }
    public void close(){
        game.cleanup();
    }

    public int getChoice(){
        ImageIcon icon = new ImageIcon("src/im.png");
        String[] choices = { "Player VS Player", "Player VS Computer" };
        String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                "Choose game type", JOptionPane.QUESTION_MESSAGE, icon,
                choices,
                choices[0]);
        System.out.println(input);
        if (input==("Player VS Computer")){
            return 1;
        }else if (input==("Player VS Player")){
            return 0;
        }else{
            return -1;
        }

    }

}
