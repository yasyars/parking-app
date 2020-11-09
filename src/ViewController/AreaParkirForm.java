package ViewController;

import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AreaParkirForm extends JFrame {
    private JPanel PanelAtas;
    private JPanel PanelBawah;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JPanel PanelAreaParkir;
    private JButton backButton;
    private User user;

    public AreaParkirForm(User user) {
        add(PanelAreaParkir);
        setTitle("Login sebagai pengguna");
        setSize(400,250);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUser mu = new MenuUser(user);
                mu.setVisible(true);
            }
        });
    }
}
