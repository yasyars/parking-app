import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.*;

public class Login extends JFrame{
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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InitForm init = new InitForm();
                init.setVisible(true);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement ps;
                ResultSet rs;
                String query = "SELECT * FROM `parking`.`user` WHERE `email`=? AND `password`=?";

                try{
                    String mail = mailField.getText();

                    char[] pswd = passField.getPassword();
                    String str = new String(pswd);
                    String passw = md5Spring(str);

                    ps = DbConnection.getConnection().prepareStatement(query);
                    ps.setString(1, mail);
                    ps.setString(2, passw);

                    rs = ps.executeQuery();
                    if(rs.next()){
                        ParkingTest page = new ParkingTest();
                        dispose();
                        page.setVisible(true);
                        page.labelNama.setText("Welcome <"+mail+">");
                    } else{
                        JOptionPane.showMessageDialog(null,"Incorrect Username/Password","Login Failed",2);
                    }
                } catch (Exception exc){//(SQLException ex){
                    //Logger.getLogger(Login.class.getName()).log(Level.SEVERE,null, ex);

                }
            }
        });
        createNewAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                Registrasi reg = new Registrasi();
                reg.setVisible(true);
                reg.setIsAdmin("0");
            }
        });
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
            //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageIcon iconUsername = createImageIcon("images/uname.png", "admin image");
        ImageIcon iconPass = createImageIcon("images/pass.png", "user image");

        unameLabel = new JLabel(iconUsername);

        passLabel = new JLabel(iconPass);

    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
