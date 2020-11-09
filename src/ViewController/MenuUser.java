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

public class MenuUser extends JFrame implements ActionListener{
    private JButton homeButton;
    private JButton dataDiriButton;
    private JButton dataKendaraanButton;
    private JButton parkirButton;
    private JButton riwayatTransaksiButton;
    private JPanel mainUserPanel;
    private JLabel iconLabel;
    private JLabel mailField;
    private Integer idUser;
    private User user;

    public MenuUser(User user){
       add(mainUserPanel);
       setTitle("Aplikasi Parking Subcription");
       setSize(500,500);
       iconLabel.setHorizontalTextPosition(JLabel.CENTER);
       iconLabel.setVerticalTextPosition(JLabel.TOP);
       dataDiriButton.addActionListener(this);
       dataKendaraanButton.addActionListener(this);

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
                AreaParkirForm ap = new AreaParkirForm(user);
                ap.setVisible(true);
            }
        });
        riwayatTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RiwayatTransaksiForm rt = new RiwayatTransaksiForm();
                rt.setVisible(true);
            }
        });
        dataDiriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dataDiriButton){
            UserForm user = new UserForm(mailField.getText());
            user.setLocationRelativeTo(null);
            dispose();
            user.setVisible(true);
        } else if (e.getSource() == dataKendaraanButton) {
            if (getIdUser() != null) {
                KendaraanForm kf = new KendaraanForm(this.user);
                kf.setIdUser(getIdUser());
                kf.setLocationRelativeTo(null);
                dispose();
                kf.setVisible(true);
            }
        }

    }
    public void setMailLabel(String mail) {
        this.mailField.setText(mail);
    }

    public Integer getIdUser() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM `parking`.`user` WHERE `email`=?";
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, mailField.getText());

            rs = ps.executeQuery();

            while (rs.next()){
                idUser = rs.getInt(1);
            }
            return idUser;
        } catch (SQLException sqle){
            Logger.getLogger(MenuUser.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { ps.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

}
