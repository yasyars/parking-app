import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main (String[] args)  {
        //throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{

            con = DbConnection.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM user");

            rs.last();


            System.out.println(rs.getRow());

        } catch (SQLException err){
            System.out.println(err.getMessage());
        }

//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        InitForm initForm = new InitForm();
        initForm.setVisible(true);
//        LoginForm loginForm = new LoginForm();
//        loginForm.setVisible(true);
    }
}
