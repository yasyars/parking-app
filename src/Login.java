import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame implements ActionListener{
    private JTextField mailField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton registrasiButton;
    private JButton backButton;
    private JPanel userLoginPanel;
    private JLabel createNewAccount;
    private JLabel unameLabel;
    private JLabel passLabel;


    public Login(){
        add(userLoginPanel);
        setTitle("Login sebagai pengguna");
        setSize(500,500);
        loginButton.addActionListener(this);
        backButton.addActionListener(this);

        createNewAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                Registrasi reg = new Registrasi();
                reg.setLocationRelativeTo(null);
                reg.setVisible(true);
                reg.setIsAdmin(0);
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
            String query = "SELECT * FROM `parking`.`user` WHERE `email`=? AND `password`=? AND `is_admin`=0";

            try {
                String mail = mailField.getText();
                String passw = md5Spring(String.valueOf(passField.getPassword()));

                ps = DbConnection.getConnection().prepareStatement(query);
                ps.setString(1, mail);
                ps.setString(2, passw);

                rs = ps.executeQuery();
                if (rs.next()) {
                    MenuUser mu = new MenuUser();
                    dispose();
                    mu.setVisible(true);
                    mu.setMailLabel(mail);
                    mu.setLocationRelativeTo(null);
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