package ViewController;

import javax.swing.*;

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
    }

}
