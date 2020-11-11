package ViewController;

import DAO.DAOKendaraan;
import DAO.DAOUser;
import Model.Customer;
import Model.Kendaraan;
import Model.TableModelKendaraan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class KendaraanForm extends JFrame {

    private JPanel PanelBawah;
    private JPanel PanelAtas;
    private JTextField platNoField;
    private JButton insertButton;
    private JButton deleteButton;
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

    public KendaraanForm(Customer user) {
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        showData();

        add(panelKendaraan);
        setTitle("Aplikasi Parking Subcription");
        setSize(500, 500);
        setLocationRelativeTo(null);
        jenisKendaraanCombo.setSelectedItem(null);

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

                String plat_no = platNoField.getText().replaceAll("\\s", "").toUpperCase();
                try {
                    kendaraan.setOwner(user);
                    kendaraan.setTipe(String.valueOf(jenisKendaraanCombo.getSelectedItem()));
                    kendaraan.setNoPlat(plat_no);
                    daoKendaraan.insert(kendaraan);
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                    platNoField.setText("");
                    jenisKendaraanCombo.setSelectedItem(null);
                    //kosongkan_form();
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                showData();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUser mu = new MenuUser(user);
                mu.setVisible(true);
                mu.setLocationRelativeTo(null);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kendaraan kendaraan = new Kendaraan();

                String plat_no = platNoField.getText().replaceAll("\\s", "").toUpperCase();

                //System.out.println(plat_no);
                try{
                    if (!DAOKendaraan.isInTransaction(plat_no)){
                        daoKendaraan.delete(plat_no);
                        JOptionPane.showMessageDialog(null, "Kendaraan berhasil dihapus");
                        platNoField.setText("");
                        jenisKendaraanCombo.setSelectedItem(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Kendaraan tidak dapat dihapus");
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                showData();
            }
        });
    }
    public void showData() {
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        try {
            TableModelKendaraan model = new TableModelKendaraan(daoKendaraan.getAll());
            tableKendaraan.setModel(model);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }



}


