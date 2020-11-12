package ViewController;

import DAO.DAOKendaraan;
import DAO.DAOTransaksiAdmin;
import Model.TableModelKendaraan;
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
        DAOTransaksiAdmin daoTransaksiAdmin= new DAOTransaksiAdmin();
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(daoTransaksiAdmin.getAll());
        TabelTransaksiAdmin.setModel(model);
    }

    public RiwayatTransaksiForm(){
        DAOTransaksiAdmin daoTransaksiAdmin= new DAOTransaksiAdmin();
        tampilkan_data();
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
