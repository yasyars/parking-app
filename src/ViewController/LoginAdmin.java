package ViewController;

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

import DAO.DAOUser;
import Model.User;

public class LoginAdmin extends JFrame{
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
        DAOUser admin = new DAOUser();
        admin.setIsAdmin(1);
        add(panelloginadmin);
        setTitle("Login sebagai Admin");
        setSize(500,500);
        setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String mail = mailField.getText();
                    char[] passw = passField.getPassword();

                    User user = admin.login(mail, passw);

                    if (user.getId() != 0) {
                        MenuAdminForm ma = new MenuAdminForm();
                        dispose();
                        ma.setVisible(true);
                        ma.setLocationRelativeTo(null);
                        JOptionPane.showMessageDialog(null, " Welcome Administrator");
                    }

                }catch(Exception error){
                    JOptionPane.showMessageDialog(null, "Incorrect Username/Password", "Login Failed", 2);
                }
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Init init = new Init();
                init.setVisible(true);
                init.setLocationRelativeTo(null);
            }
        });


    }

}
