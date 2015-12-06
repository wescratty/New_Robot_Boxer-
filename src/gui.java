

import javax.swing.*;

public class gui {
    static JFrame mainFrame;
    static MainPanel main;
    public static void main(String[] args) {

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = MainPanel.getInstance();
        mainFrame.add(main);
        main.makeMain();
        mainFrame.setSize(1050, 1000);
        mainFrame.setLocation(200, 200);
        mainFrame.setVisible(true);


        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you would like to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    main.close();
                    System.exit(0);
                }
            }
        });

    }
}
