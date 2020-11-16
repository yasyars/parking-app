package ViewController;

import DAO.DAOTransaksi;
import Model.TableModelTransaksiAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaporanTransaksiForm extends JFrame{
    private JTable TabelTransaksiAdmin;
    private JLabel jenisLabel;
    private JLabel dateLabel;
    private JPanel panelriwayattransaksi;
    private JButton backButton;
    private JComboBox cbJenis;
    private JComboBox cbDate;
    private JLabel labelTotal;

    public void tampilkan_data() {
        DAOTransaksi daoTransaksi = new DAOTransaksi();
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(daoTransaksi.getAll());
        TabelTransaksiAdmin.setModel(model);
    }

    public LaporanTransaksiForm(){
        DAOTransaksi daoTransaksi = new DAOTransaksi();
        tampilkan_data();
        add(panelriwayattransaksi);
        setTitle("Aplikasi Parking Subcription");
        setSize(600,450);
        setLocationRelativeTo(null);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuAdminForm mu = new MenuAdminForm();
                mu.setVisible(true);
                mu.setLocationRelativeTo(null);
            }
        });
        cbJenis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbJenis.getSelectedItem().equals("Mingguan") ||  cbJenis.getSelectedItem().equals("Bulanan")){
                    cbDate.removeAllItems();
                    cbDate.addItem("Juli");
                    cbDate.addItem("Agustus");
                    cbDate.addItem("September");
                    cbDate.addItem("Oktober");
                    cbDate.addItem("November");
                    cbDate.addItem("Desember");
                } else {
                    cbDate.removeAllItems();
                    cbDate.addItem("Senin");
                    cbDate.addItem("Selasa");
                    cbDate.addItem("Rabu");
                    cbDate.addItem("Kamis");
                    cbDate.addItem("Jumat");
                    cbDate.addItem("Sabtu");
                    cbDate.addItem("Minggu");
                }
            }
        });
    }


}
