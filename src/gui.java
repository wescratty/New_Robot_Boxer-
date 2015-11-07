

import javax.swing.JFrame;

public class gui {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel main = MainPanel.getInstance();
        mainFrame.add(main);
        main.makeMain();
        mainFrame.setSize(900, 1000);
        mainFrame.setLocation(200, 200);
        mainFrame.setVisible(true);

    }
}
