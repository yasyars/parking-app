package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiwayatTransaksiForm extends JFrame{
    private JButton backButton;
    private JLabel IDTransaksiLabel;
    private JPanel panelriwayattransaksi;
    private JButton harianButton;
    private JButton mingguanButton;
    private JButton bulananButton;

    public RiwayatTransaksiForm(){
        add(panelriwayattransaksi);
        setTitle("Aplikasi Parking Subcription");
        setSize(600,450);
        setLocationRelativeTo(null);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuAdminForm mu = new MenuAdminForm();
                mu.setLocationRelativeTo(null);
                mu.setVisible(true);

            }
        });
    }

}
