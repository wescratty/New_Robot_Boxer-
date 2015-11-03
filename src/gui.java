

import javax.swing.JFrame;

public class gui {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new MainPanel());
        mainFrame.setSize(900, 1000);
        mainFrame.setLocation(200, 200);
        mainFrame.setVisible(true);

    }
}
