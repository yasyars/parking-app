package ViewController;

import Model.User;

import javax.swing.*;

public class SubscriptionForm extends JFrame{
    private JButton easyButton;
    private JButton plusButton;
    private JPanel subPanel;
    private JButton backButton;

    public SubscriptionForm(User user){
        add(subPanel);
        setTitle("Registrasi Pengguna Baru");
        setSize(500, 500);
    }
}
