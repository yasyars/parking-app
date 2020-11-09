import Helper.DbConnection;
import ViewController.*;
import javax.swing.*;

public class Main {


    public static void main (String[] args)  {
        DbConnection.getConnection();

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        Init initForm = new Init();
        initForm.setVisible(true);
    }
}
