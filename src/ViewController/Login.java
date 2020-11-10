package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import DAO.DAOCustomer;
import DAO.DAOUser;
import Model.Customer;
import Model.User;

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
        DAOCustomer daoUser = new DAOCustomer();
        daoUser.setIsAdmin(0);
        add(userLoginPanel);
        setTitle("Login sebagai pengguna");
        setSize(500,500);

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String mail = mailField.getText();
                    char[] passw = passField.getPassword();

                    Customer user = daoUser.login(mail,passw);

                    Customer customer = new Customer();
                    customer.setId(user.getId());
                    customer.setName(user.getName());
                    customer.setAddress(user.getAddress());
                    customer.setEmail(user.getEmail());
                    customer.setPassword(user.getPassword());
                    customer.setAdmin(user.isAdmin());
                    customer.setSubscription(user.getSubscription());

                    if (customer.getId()!= 0){
                        MenuUser mu = new MenuUser(customer);
                        dispose();
                        mu.setVisible(true);
                        mu.setMailLabel(mail);
                        mu.setLocationRelativeTo(null);
                    }

                    System.out.println(customer.getId());
                }catch (Exception error){
                    JOptionPane.showMessageDialog(null,"Incorrect Email/Password", "Login Failed",2);
                }
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
