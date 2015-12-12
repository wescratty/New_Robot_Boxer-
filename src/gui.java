

import javax.swing.*;

public class gui {
    static JFrame mainFrame;
    static MainPanel main;

    /**
     * Main gui set up; set window size etc. also joption pane for shutdown
     * @param args
     */
    public static void main(String[] args) {

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = MainPanel.getInstance();
        mainFrame.add(main);
        main.makeMain();
        mainFrame.setSize(1050, 1000);
        mainFrame.setLocation(200, 200);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ImageIcon icon = new ImageIcon("src/im.png");

                if (JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you would like to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,icon) == JOptionPane.YES_OPTION){
                    main.close();
                    System.exit(0);
                }
            }
        });

    }
}
