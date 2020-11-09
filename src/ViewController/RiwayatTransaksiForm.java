package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiwayatTransaksiForm extends JFrame{
    private JTable table1;
    private JLabel jenisLabel;
    private JLabel dateLabel;
    private JPanel panelriwayattransaksi;
    private JButton backButton;
    private JComboBox cbJenis;
    private JComboBox cbDate;

    public RiwayatTransaksiForm(){
        add(panelriwayattransaksi);
        setTitle("Aplikasi Parking Subcription");
        setSize(600,450);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuAdminForm mu = new MenuAdminForm();
                mu.setVisible(true);
                mu.setLocationRelativeTo(null);
            }
        });
    }


}
