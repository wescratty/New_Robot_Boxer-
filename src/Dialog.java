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


    public String getBoxerStats(){
        String infoString;
        Object[] possibilities = {"10", "20", "30","40", "50", "60", "70","80", "90", "100"};
        infoString = (String)JOptionPane.showInputDialog(null,"Select Speed:","Boxer Stats",JOptionPane.PLAIN_MESSAGE,
                null,possibilities,"10");


        infoString =infoString+", "+ JOptionPane.showInputDialog(null,"Select power:","Boxer Stats",JOptionPane.PLAIN_MESSAGE,
                null,possibilities,"10");


        System.out.println("Boxer Stats: " + infoString);

        return infoString;
    }
}
