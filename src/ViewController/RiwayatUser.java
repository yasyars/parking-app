package ViewController;

import DAO.DAOKendaraan;

import DAO.DAOTransaksi;
import Model.*;

import Model.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

public class RiwayatUser extends JFrame{
    private JPanel riwayatUserPanel;
    private JPanel paneltransaksiuser;
    private JPanel PanelAtas;
    private JPanel JudulPanel;
    private JPanel PanelBawah;
    private JTable tabletransaksiuser;
    private JButton backButton;
    private int idUser;
    private Customer user;
    DAOTransaksi daoTransaksiUser = new DAOTransaksi();


    public void showData() {
        try {
            TabelModelTransaksiUser model = new TabelModelTransaksiUser(daoTransaksiUser.getByUser(user));
            tabletransaksiuser.setModel(model);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public RiwayatUser(Customer user){
        this.user = user;
        //tampilkan_data();
        showData();

        add(riwayatUserPanel);
        setTitle("Riwayat Transaksi Pengguna");
        setSize(1200,500);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUser mu = new MenuUser(user);
                mu.setVisible(true);
                mu.setLocationRelativeTo(null);
            }
        });
    }

}
