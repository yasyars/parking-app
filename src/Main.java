import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Main {
    public static void main (String[] args)  {

//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try{
//
//            con = DbConnection.getConnection();
//            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            rs = stmt.executeQuery("SELECT * FROM user");
//
//            rs.last();
//
//
//            System.out.println(rs.getRow());
//
//        } catch (SQLException err){
//            System.out.println(err.getMessage());
//        }
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
