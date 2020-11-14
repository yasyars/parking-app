package ViewController;

import DAO.DAOTransaksi;
import Model.TableModelTransaksiAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiwayatTransaksiForm extends JFrame{
    private JTable TabelTransaksiAdmin;
    private JLabel jenisLabel;
    private JLabel dateLabel;
    private JPanel panelriwayattransaksi;
    private JButton backButton;
    private JComboBox cbJenis;
    private JComboBox cbDate;

    public void tampilkan_data() {
        DAOTransaksi daoTransaksi = new DAOTransaksi();
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(daoTransaksi.getAll());
        TabelTransaksiAdmin.setModel(model);
    }

    public RiwayatTransaksiForm(){
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
    }


}
