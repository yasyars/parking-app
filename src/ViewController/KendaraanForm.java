package ViewController;

import DAO.DAOKendaraan;
import Model.Customer;
import Model.Kendaraan;
import Model.TableModelKendaraan;

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


    public void kosongkan_form(){
        platNoField.setEditable(true);
        platNoField.setText(null);
        jenisKendaraanCombo.setEditable(true);
        jenisKendaraanCombo.setSelectedItem(null);
    }

    public void tampilkan_data(){
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        TableModelKendaraan model = new TableModelKendaraan(daoKendaraan.getAll());
        tableKendaraan.setModel(model);
    }


    public KendaraanForm(Customer user){
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        tampilkan_data();
        kosongkan_form();

        add(panelKendaraan);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);
        setLocationRelativeTo(null);

//        System.out.println("Debug ")

        tableKendaraan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = tableKendaraan.rowAtPoint(e.getPoint());
                String plat_no = tableKendaraan.getValueAt(baris, 0).toString();
                platNoField.setText(plat_no);
                String jenis_kendaraan = tableKendaraan.getValueAt(baris, 1).toString();
                jenisKendaraanCombo.setSelectedItem(jenis_kendaraan);

            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kendaraan kendaraan = new Kendaraan();
                try{
                    kendaraan.setNoPlat(platNoField.getText());
                    kendaraan.setTipe(jenisKendaraanCombo.getSelectedItem().toString());
                    daoKendaraan.insert(kendaraan);
                    JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
                    kosongkan_form();

                }catch (Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                tampilkan_data();
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUser mu = new MenuUser(user);
                //         mu.setVisible(true);
            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}


