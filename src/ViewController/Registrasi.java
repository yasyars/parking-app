package ViewController;//import com.mysql.cj.log.Log;

//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
import javax.swing.*;
import java.awt.event.*;

import DAO.DAOUser;
import Helper.*;
import Model.Customer;
import Model.User;

public class Registrasi extends JFrame{

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
    private User user = new User();

    public Registrasi(){
        DAOUser daoUser = new DAOUser();
        add(userRegisPanel);
        setTitle("Registrasi Pengguna Baru");
        setSize(500, 500);
        showSavedDAta();

        emailTextField.addKeyListener(new KeyAdapter() {
            @Override
            //validasi e-mail format
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                boolean emailValid = User.isEmailValid(emailTextField.getText());
                if (!emailValid){
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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Init ul = new Init();
                ul.setVisible(true);
                ul.setLocationRelativeTo(null);
            }
        });

        registrasiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = String.valueOf(passwordField.getPassword());

                if (!User.isPasswordValid(pass)){
                    JOptionPane.showMessageDialog(null, "Format password tidak valid!");
                    passwordField.setText("");
                }else if (isAnyFieldNull()) {
                    JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!");
                } else if (!User.isEmailValid(emailTextField.getText())) {
                    JOptionPane.showMessageDialog(null, "Email tidak valid!");
                    emailTextField.setText("");
                } else if (DAOUser.isEmailUsed(emailTextField.getText())){
                    JOptionPane.showMessageDialog(null, "Email sudah terdaftar!");
                    emailTextField.setText("");
                    passwordField.setText("");
                }else{
                        String passEncrypt = User.md5Spring(pass);
                        User user = new User();
                        try {
//                            if (Validasi.isEmpty(emailTextField.getText())){
//                                throw new Exception("Alamat email tidak boleh kosong!");
//                            } // sdh masuk di isAnyFieldnull
//                            if (!User.isEmailValid(emailTextField.getText())){
//                                throw new Exception("Email tidak valid");
//                            }
//
//                            if (DAOUser.isEmailUsed(emailTextField.getText())){
//                                throw new Exception ("Email sudah terdaftar");
//                            }
                            user.setName(namaTextField.getText());
                            user.setAddress(alamatTextField.getText());
                            user.setEmail(emailTextField.getText());
                            user.setPassword(passEncrypt);
                            user.setAdmin(getIsAdmin());

                            if (user.isAdmin() == 0){
                                Customer customer = new Customer();
                                customer.setName(user.getName());
                                customer.setAddress(user.getAddress());
                                customer.setEmail(user.getEmail());
                                customer.setPassword(passEncrypt);
                                customer.setAdmin(user.isAdmin());
                                dispose();
                                SubscriptionForm sub = new SubscriptionForm(customer);
                                sub.setVisible(true);
                                sub.setLocationRelativeTo(null);
                            }else{
                                daoUser.insert(user);
                                JOptionPane.showMessageDialog(null, "Registrasi Berhasil!");
                                dispose();
                                Init init = new Init();
                                init.setVisible(true);
                                init.setLocationRelativeTo(null);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Gagal input data");
                            JOptionPane.showMessageDialog(null, ex);
                        }
                }

            }
        });
    }

    public boolean isAnyFieldNull(){
        if(Validasi.isEmpty(namaTextField.getText()) ||
                Validasi.isEmpty(alamatTextField.getText())||
                Validasi.isEmpty(emailTextField.getText()) ||
                Validasi.isEmpty(String.valueOf(passwordField.getPassword()))){
            return true;
        }else {
            return false;
        }
    }

    public int getIsAdmin() {
        return isAdminCombo.getSelectedIndex();
    }

    public void setIsAdmin(int isAdmin) {
        isAdminCombo.setSelectedIndex(isAdmin);
    }
    public void setUserData(User user){
        this.user = user;
        showSavedDAta();
    }

    public void showSavedDAta() {
        try {
            alamatTextField.setText(this.user.getAddress());
            emailTextField.setText(this.user.getEmail());
            namaTextField.setText(this.user.getName());
        } catch (Exception e) {

        }
    }



}
