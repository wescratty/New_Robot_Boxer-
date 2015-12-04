import javax.swing.*;
import javax.swing.text.StringContent;
import java.util.ArrayList;

/**
 * Created by wescratty on 11/1/15.
 */
public class Dialog {
    private static Dialog ourInstance = new Dialog();

    public static Dialog getInstance() {
        return ourInstance;
    }

    private Dialog() {
    }


    /**
     * Method for generating a dialogue box with a drop down
     * @param title Title of the window
     * @param field Text  to display in front of box
     * @param possibilities options available in drop down first item in array will be the default selection
     * @return item  selected from drop down or null if none selected
     */
    public Object getStat(String title, String field, Object[] possibilities){
        Object userChoice = null;
        userChoice = (Object)JOptionPane.showInputDialog(null,field,title,JOptionPane.PLAIN_MESSAGE,
                null,possibilities,possibilities[0]);
        return userChoice;
    }
public void errorBox(String message) {
    JOptionPane.showMessageDialog(null, message);
}
}
