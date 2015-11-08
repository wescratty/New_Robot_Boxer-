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

//depriciated?
    public String getBoxerStats(){
        String infoString;
        Object[] possibilities = {"10", "20", "30","40", "50", "60", "70","80", "90", "100"};
        infoString = (String)JOptionPane.showInputDialog(null,"Select Speed:","Boxer Stats",JOptionPane.PLAIN_MESSAGE,
                null,possibilities,"10");


        infoString =infoString+","+(String)getStat("Select Strength:", "Boxer Stats", possibilities);


        System.out.println("Boxer Stats: " + infoString);

        return infoString;
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
}
