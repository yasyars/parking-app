import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAdmin extends JFrame implements ActionListener{
    private JPanel userLoginPanel;
    private JTextField mailField;
    private JPasswordField passField;
    private JLabel unameLabel;
    private JLabel passLabel;
    private JButton loginButton;
    private JButton backButton;
    private JPanel panelloginadmin;
    private JLabel createNewAccount;

    public LoginAdmin(){
        add(panelloginadmin);
        setTitle("Login sebagai Admin");
        setSize(500,500);

        loginButton.addActionListener(this);
        backButton.addActionListener(this);

        createNewAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        createNewAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                Registrasi reg = new Registrasi();
                reg.setLocationRelativeTo(null);
                reg.setVisible(true);
                reg.setIsAdmin(1);
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            Init init = new Init();
            init.setVisible(true);
            init.setLocationRelativeTo(null);
        } else { //loginButton
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM `parking`.`user` WHERE `email`=? AND `password`=? AND `is_admin`=1";

            try {
                String mail = mailField.getText();
                String passw = md5Spring(String.valueOf(passField.getPassword()));

                ps = DbConnection.getConnection().prepareStatement(query);
                ps.setString(1, mail);
                ps.setString(2, passw);

                rs = ps.executeQuery();
                if (rs.next()) {
                    MenuAdminForm ma = new MenuAdminForm();
                    dispose();
                    ma.setVisible(true);
                    ma.setLocationRelativeTo(null);
                    JOptionPane.showMessageDialog(null," Welcome Administrator");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username/Password", "Login Failed", 2);
                }
            } catch (Exception exc) {//(SQLException ex){
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE,null, exc);

            }
        }
    }
    public String md5Spring(String text) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(text.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b: hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;

    }

}
