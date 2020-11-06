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

public class Registrasi extends JFrame implements ActionListener{

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
    private JComboBox isAdminCombo;
    private int isAdmin;

    public Registrasi(){
        add(userRegisPanel);
        //setTitle("Registrasi Pengguna Baru");
        setSize(500, 500);

        registrasiButton.addActionListener(this);
        cancelButton.addActionListener(this);

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

                String input = String.valueOf(passwordField.getPassword());

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

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == cancelButton){
            dispose();
            Login ul = new Login();
            ul.setVisible(true);
            ul.setLocationRelativeTo(null);
        } else {//registrasiButton
            PreparedStatement ps;
            String query = "INSERT INTO `parking`.`user` (`nama`, `alamat`, `email`, `password`, `is_admin`) VALUES (?, ?, ?, ?, ?)";
            String pass = String.valueOf(passwordField.getPassword());
            String passEncrypt = md5Spring(pass);

            if (isAnyFieldNull()){
                JOptionPane.showMessageDialog(null, "Field tidak boleh ada yang kosong");
            }
            else if (!isPasswordValid(pass)) {
                passwordField.setText("");
                JOptionPane.showMessageDialog(null, "Format Password tidak valid");
            }
            else if (isMailUsed(emailTextField.getText())) {
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
                    ps.setString(4, passEncrypt);
                    ps.setString(5, getIsAdmin());


                    if (ps.executeUpdate() != 0) {
                        JOptionPane.showMessageDialog(null, "Registrasi Berhasil!");
                        dispose();
                        Init init = new Init();
                        init.setVisible(true);
                        init.setLocationRelativeTo(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Registrasi Gagal");
                    }

                } catch (Exception ex) {
                    //Logger.getLogger(Registrasi.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }

    public boolean isAnyFieldNull(){
        if (namaTextField.getText().equals("") || alamatTextField.equals("") ||
                emailTextField.equals("") || String.valueOf(passwordField.getPassword()).equals("")){
            return true;
        } else {
            return false;
        }
    }

    public void setIsAdmin(int isAdmin) {
        //this.isAdmin = isAdmin;
        isAdminCombo.setSelectedIndex(isAdmin);
    }

    public String getIsAdmin() {
        return String.valueOf(isAdminCombo.getSelectedIndex());
    }

    public boolean isMailUsed(String email){
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
//        if (email == null)
//            return false;
        //return pat.matcher(email).matches();
        if (pat.matcher(email).matches()) {
            return true;
        } else {
            passwordField.setText("");
            return false;
        }
    }


    public static boolean isPasswordValid(String password)
    {
        boolean isValid = true;
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";

        if (password.length() < 6) //Password must be more than 6 characters in length
        {
            isValid = false;
            JOptionPane.showMessageDialog(null, "Format Password tidak valid");
        }

        if (!password.matches(upperCaseChars )) //Password must have atleast one uppercase character
        {
            isValid = false;
        }

        if (!password.matches(lowerCaseChars )) //Password must have atleast one lowercase character
        {
            isValid = false;
        }
        if (!password.matches(numbers)) //Password must have atleast one number
        {
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
            Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Registrasi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;

    }
}
