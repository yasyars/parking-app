package ViewController;

import DAO.DAOCustomer;
import DAO.DAOUser;
import Model.Customer;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class SubscriptionForm extends JFrame {
    private JPanel subPanel;
    private JButton easyButton;
    private JButton plusButton;
    private JButton backButton;
    Customer user;

    public SubscriptionForm(Customer user){

        DAOCustomer daoUser = new DAOCustomer();
        add(subPanel);
        setTitle("Subcription");
        setSize(500,300);
        setLocationRelativeTo(null);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    dispose();
                    Registrasi reg = new Registrasi();
                    reg.setLocationRelativeTo(null);
                    reg.setVisible(true);
                    reg.setIsAdmin(0);
                    reg.setUserData(user);
            }
        });

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setSubscription("easy");
                try{
                    daoUser.insert(user);
                    JOptionPane.showMessageDialog(null, "Pilihan Subscription: Easy \nRegistrasi Berhasil!");
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                dispose();
                Login login = new Login();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            }
        });

        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setSubscription("plus");
                try {
                    daoUser.insert(user);
                    JOptionPane.showMessageDialog(null, "Pilihan Subscription: Plus\nRegistrasi Berhasil!");
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                dispose();
                Login login = new Login();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            }
        });
    }
}
