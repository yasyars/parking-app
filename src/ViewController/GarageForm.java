package ViewController;

import DAO.DAOArea;
import DAO.DAOGarage;
import Model.Garage;
import Model.OperationalTime;
import Model.TableModelArea;
import Model.TableModelGarage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import Helper.*;
import Model.*;
import java.util.List;

public class GarageForm extends JFrame{
    private JPanel panelgarage;
    private JTextField txtNamaGarage;
    private JTextField txtIdGarage;
    private JTable tabelGarage;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField txtWaktuBuka;
    private JTextField txtWaktuTutup;
    private JButton backButton;
    private JComboBox cmbhari;
    private JComboBox cmbidarea;
    private JTextField txtTarifMotor;
    private JTextField txtTarifMobil;

    private void kosongkan_form(){
        txtNamaGarage.setEditable(true);
        txtNamaGarage.setText(null);
        txtIdGarage.setEditable(true);
        txtIdGarage.setText(null);
        cmbhari.setEditable(true);
        cmbhari.setSelectedItem(null);
        txtWaktuBuka.setEditable(true);
        txtWaktuBuka.setText(null);
        cmbidarea.setEditable(true);
        cmbidarea.setSelectedItem(null);
    }
    private void tampilkan_data(){
        DAOGarage daoGarage = new DAOGarage();
        TableModelGarage model = new TableModelGarage(daoGarage.getAll());
        tabelGarage.setModel(model);
    }

    private void load_area(){
        try{
            DAOArea daoArea = new DAOArea();
            List<Area> areas =  daoArea.getAll();
            System.out.println("Debug : " + areas);
            for (Area area: areas){
                cmbidarea.addItem(area);
                System.out.println("Debug : " + (Area) cmbidarea.getSelectedItem());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Gagal membuka data area");
        }
    }

    public GarageForm(){
        DAOGarage daoGarage = new DAOGarage();

        load_area();
        tampilkan_data();
        kosongkan_form();

        add(panelgarage);
        setTitle("Aplikasi Parking Subcription");
        setSize(700,500);
        setLocationRelativeTo(null);


        tabelGarage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = tabelGarage.rowAtPoint(e.getPoint());
                String id_garage = tabelGarage.getValueAt(baris,0).toString();
                txtIdGarage.setText(id_garage);
                String nama_garage = tabelGarage.getValueAt(baris,1).toString();
                txtNamaGarage.setText(nama_garage);
                String hari = tabelGarage.getValueAt(baris,2).toString();
                cmbhari.setSelectedItem(hari);
                String waktu_buka = tabelGarage.getValueAt(baris,3).toString();
                txtWaktuBuka.setText(waktu_buka);
                String waktu_tutup = tabelGarage.getValueAt(baris,4).toString();
                txtWaktuTutup.setText(waktu_tutup);
                String id_area = tabelGarage.getValueAt(baris,5).toString();
                DAOArea daoArea = new DAOArea();
                Area area = daoArea.getById(Integer.parseInt(id_area));
                cmbidarea.setSelectedItem(area);

                String tarif_motor = (String) tabelGarage.getValueAt(baris,6);
                txtTarifMotor.setText(tarif_motor);

                String tarif_mobil = (String) tabelGarage.getValueAt(baris, 7);
                txtTarifMobil.setText(tarif_mobil);


            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Garage garage = new Garage();
                    Area selectedArea = (Area) cmbidarea.getSelectedItem();
                    try{
                        garage.setName(txtNamaGarage.getText());
                        OperationalTime time = new OperationalTime();
                        time.setDay(cmbhari.getSelectedItem().toString());
                        time.setOpenHour(txtWaktuBuka.getText());
                        time.setCloseHour(txtWaktuTutup.getText());
                        garage.setOperationalTime(time);
                        garage.setArea(selectedArea.getId());
                        garage.setTarifMotor(Double.parseDouble(txtTarifMotor.getText()));
                        garage.setTarifMobil(Double.parseDouble(txtTarifMobil.getText()));
                        daoGarage.insert(garage);
                        JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
                        kosongkan_form();
                    }catch (Exception setError){
                        JOptionPane.showMessageDialog(null, "Error input : " + setError.getMessage());
                    }


                }catch (Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
                tampilkan_data();

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Garage garage = new Garage();
                    Area selectedArea = (Area) cmbidarea.getSelectedItem();

                    garage.setName(txtNamaGarage.getText());
                    garage.setId(Integer.parseInt(txtIdGarage.getText()));
                    OperationalTime time = new OperationalTime();
                    time.setDay((String) cmbhari.getSelectedItem());
                    time.setOpenHour(txtWaktuBuka.getText());
                    time.setCloseHour(txtWaktuTutup.getText());

                    garage.setOperationalTime(time);
                    garage.setArea(selectedArea.getId());
                    garage.setTarifMotor(Double.parseDouble(txtTarifMotor.getText()));
                    garage.setTarifMobil(Double.parseDouble(txtTarifMobil.getText()));
                    daoGarage.update(garage);
                    JOptionPane.showMessageDialog(null, "Edit Data Berhasil");
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                tampilkan_data();
               }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    daoGarage.delete(Integer.parseInt(txtIdGarage.getText()));
                    JOptionPane.showMessageDialog(null, "Delete Data Berhasil");
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                tampilkan_data();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdminForm mu = new MenuAdminForm();
                dispose();
                mu.setLocationRelativeTo(null);
                mu.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
