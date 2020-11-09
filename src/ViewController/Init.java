package ViewController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Init extends JFrame{
    private JPanel initPanel;
    private JLabel labelUser;
    private JLabel labelAdmin;


    public Init(){
        add(initPanel);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);
        setLocationRelativeTo(null);
        labelAdmin.setVerticalTextPosition(JLabel.BOTTOM);
        labelAdmin.setHorizontalTextPosition(JLabel.CENTER);

        labelUser.setVerticalTextPosition(JLabel.BOTTOM);
        labelUser.setHorizontalTextPosition(JLabel.CENTER);

        labelUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                Login login = new Login();
                //login.pack();
                login.setLocationRelativeTo(null);
                login.setVisible(true);
            }
        });

        labelAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                LoginAdmin la = new LoginAdmin();
                //login.pack();
                la.setLocationRelativeTo(null);
                la.setVisible(true);
            }
        });

    }

}
