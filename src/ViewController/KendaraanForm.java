package ViewController;

import DAO.DAOArea;
import DAO.DAOKendaraan;
import Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KendaraanForm extends JFrame {

    private JPanel PanelBawah;
    private JPanel PanelAtas;
    private JTextField platNoField;
    private JButton insertButton;
    private JButton selectButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JPanel panelKendaraan;
    private JPanel JudulPanel;
    private JComboBox jenisKendaraanCombo;
    private JButton backButton;
    private JTable tableKendaraan;
    private int idUser;
    private Customer user;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int id_user) {
        this.idUser = id_user;
    }

    public void tampilkan_data() {
        DAOKendaraan daokendaraan = new DAOKendaraan();
        TableModelKendaraan model = new TableModelKendaraan(daokendaraan.getAll());
        tableKendaraan.setModel(model);
    }


    public KendaraanForm(Customer user){
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        tampilkan_data();

        add(panelKendaraan);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);
        setLocationRelativeTo(null);



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUser mu = new MenuUser(user);
                //         mu.setVisible(true);
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Kendaraan kendaraan = new Kendaraan();
                    kendaraan.setNoPlat(platNoField.getText());
                    kendaraan.setTipe(jenisKendaraanCombo.getSelectedItem().toString());

                    daoKendaraan.insert(kendaraan);
                    JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");

                    tampilkan_data();

                }catch (Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }


            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}


