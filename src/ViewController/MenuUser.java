package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Helper.DbConnection;
import Model.*;

public class MenuUser extends JFrame{
    private JButton homeButton;
    private JButton dataDiriButton;
    private JButton dataKendaraanButton;
    private JButton parkirButton;
    private JButton riwayatTransaksiButton;
    private JPanel mainUserPanel;
    private JLabel iconLabel;
    private JLabel mailField;
    private Integer idUser;
    private Customer user;

    public MenuUser(Customer user){
        this.user = user;
       add(mainUserPanel);
       setTitle("Aplikasi Parking Subcription");
       setSize(500,500);
       iconLabel.setHorizontalTextPosition(JLabel.CENTER);
       iconLabel.setVerticalTextPosition(JLabel.TOP);

       dataDiriButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               dispose();
               UserForm uf = new UserForm(user);
               uf.setLocationRelativeTo(null);
               uf.setVisible(true);
           }
       });

        dataKendaraanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                KendaraanForm kf = new KendaraanForm(user);
                kf.setVisible(true);
            }
        });

        parkirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.out.println("Debug parkir button: "+ user.getId());
                ParkirForm ap = new ParkirForm(user);
                ap.setVisible(true);
            }
        });

        riwayatTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RiwayatUser rt = new RiwayatUser(user);
                rt.setVisible(true);
                rt.setLocationRelativeTo(null);
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            }
        });
    }

    public void setMailLabel(String mail) {
        this.mailField.setText(user.getEmail());
    }


}
