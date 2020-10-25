import com.mysql.cj.log.Log;

//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
import javax.swing.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Registrasi extends JFrame {

    private JPanel userRegisPanel;
    private JTextField namaTextField;
    private JTextField alamatTextField;
    private JTextField emailTextField;
    private JButton registrasiButton;
    private JButton cancelButton;
    private JPasswordField passwordField;
    private JLabel validasiMailLabel;
    private JLabel validasiPassLabel;
    private JLabel passLabel2;
    private String isAdmin;

//    Connection con = null;
//    Statement stmt = null;
//    ResultSet rs = null;

    public Registrasi(){
        add(userRegisPanel);
        setTitle("Registrasi Pengguna Baru");
        setSize(500, 500);


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login ul = new Login();
                ul.setVisible(true);
            }
        });
        registrasiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement ps;
                String query = "INSERT INTO `parking`.`user` (`nama`, `alamat`, `email`, `password`, `is_admin`) VALUES (?, ?, ?, ?, ?)";

                char[] pswd = passwordField.getPassword();
                String input = new String(pswd);

                String passw = md5Spring(input);

                if (namaTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong");
                }
                else if (alamatTextField.equals("")) {
                    JOptionPane.showMessageDialog(null, "Alamat tidak boleh kosong");
                }
                else if (!isPasswordValid(input)) {
                    emailTextField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "Password tidak valid");
                }
                else if (isMailAvailable(emailTextField.getText())) {
                    emailTextField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "E-mail sudah terdaftar");
                }
                else if (!isMailValid(emailTextField.getText())) {
                    emailTextField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "E-mail tidak valid");

                } else {
                    try {

                        ps = DbConnection.getConnection().prepareStatement(query);
                        ps.setString(1, namaTextField.getText());
                        ps.setString(2, alamatTextField.getText());
                        ps.setString(3, emailTextField.getText());
                        ps.setString(4, passw);
                        ps.setString(5, getIsAdmin());
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Register Berhasil!");
                        dispose();
                        Login login = new Login();
                        login.setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });
        emailTextField.addKeyListener(new KeyAdapter() {
            @Override
            //validasi e-mail format
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

                Pattern pat = Pattern.compile(emailRegex);
                if (!pat.matcher(emailTextField.getText()).matches()){
                    validasiMailLabel.setText("Format e-mail salah!");
                } else {
                    validasiMailLabel.setText("");
                }
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                char[] pswd = passwordField.getPassword();
                String input = new String(pswd);

                String upperCaseChars = "(.*[A-Z].*)";
                String lowerCaseChars = "(.*[a-z].*)";
                String numbers = "(.*[0-9].*)";

                if (input.length() < 6 || !input.matches(upperCaseChars) || !input.matches(lowerCaseChars) || !input.matches(numbers)){
                    validasiPassLabel.setText("Format Password salah!");
                    passLabel2.setText("Password min.6 karakter, 1 huruf besar, 1 huruf kecil, dan 1 number");
                } else {
                    validasiPassLabel.setText("");
                    passLabel2.setText("");
                }
            }
        });
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public boolean isMailAvailable(String email){
        PreparedStatement ps;
        ResultSet rs;
        boolean mailUsed = false;

        String query = "SELECT * FROM `parking`.`user` WHERE `email`=?";
        try{
            ps = DbConnection.getConnection().prepareStatement(query);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()){
                mailUsed = true;
            }

        } catch (SQLException sqle){
            Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return mailUsed;
    }

    public boolean isMailValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    public static boolean isPasswordValid(String password)
    {
        boolean isValid = true;
        if (password.length() < 6)
        {
            //Password must be more than 6 characters in length
            isValid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            //Password must have atleast one uppercase character
            isValid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            //Password must have atleast one lowercase character"
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            //Password must have atleast one number
            isValid = false;
        }
//        String specialChars = "(.*[@,#,$,%].*$)";
//        if (!password.matches(specialChars ))
//        {
//            System.out.println("Password must have atleast one special character among @#$%");
//            isValid = false;
//        }
        return isValid;
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
}
