package ViewController;

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
                LaporanTransaksiForm r = new LaporanTransaksiForm();
                r.setVisible(true);
            }
        });

        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, " Apakah Anda yakin keluar", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(response ==JOptionPane.YES_OPTION){
                    dispose();
                    LoginAdmin login = new LoginAdmin();
                    login.setVisible(true);
                    login.setLocationRelativeTo(null);
                }else if (response == JOptionPane.CLOSED_OPTION){
                }
            }
        });
    }
}
