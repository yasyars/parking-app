
import javax.swing.*;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import javax.swing.*;

public class Koneksi {
    public static Connection con;
    public static Statement stm;
    public static void main(String args[]) {
        try {
            String url = "jdbc:mysql://localhost:3306/parking";
            String user = "root";
            String pass = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:apache:commons:dbcp:test");
            con = DriverManager.getConnection(url, user, pass);
            stm = con.createStatement();
            System.out.println("koneksi berhasil;");
        } catch (Exception e) {
            throw new RuntimeException(e);
            //System.err.println("koneksi gagal" +e.getMessage());
        }


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
    }
}