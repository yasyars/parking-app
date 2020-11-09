package ViewController;

import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdminForm extends JFrame{
    private JPanel panelmenuadmin;
    private JButton areaButton;
    private JButton garageButton;
    private JButton riwayatTransaksiButton;
    private JButton keluarButton;

    public MenuAdminForm(){
        add(panelmenuadmin);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);
        setLocationRelativeTo(null);

        areaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AreaAdminForm aa = new AreaAdminForm();
                aa.setVisible(true);
            }
        });

        garageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GarageForm g = new GarageForm();
                g.setVisible(true);
            }
        });

        riwayatTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RiwayatTransaksiForm r = new RiwayatTransaksiForm();
                r.setVisible(true);
            }
        });

        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginAdmin login = new LoginAdmin();
                login.setVisible(true);
            }
        });
    }
}
