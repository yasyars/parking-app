package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import DAO.DAOArea;
import Model.*;
import java.util.List;


public class AreaAdminForm extends JFrame {
    private JPanel panelAreaParkir;
    private JTextField txtNamaArea;
    private JTextField txtIdArea;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JScrollPane scrollAreaParkir;
    private JTable tabelAreaParkir;
    private JButton backButton;
    private List<Area> listOfArea;

    public void kosongkan_form(){
        txtIdArea.setEditable(true);
        txtIdArea.setText(null);
        txtNamaArea.setEditable(true);
        txtNamaArea.setText(null);
    }

    public void tampilkan_data(){
        DAOArea dArea = new DAOArea();
        TableModelArea model = new TableModelArea(dArea.getAll());

        tabelAreaParkir.setModel(model);

    }
    public AreaAdminForm(){
        DAOArea dArea = new DAOArea();

        tampilkan_data();
        kosongkan_form();


        add(panelAreaParkir);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);
        setLocationRelativeTo(null);


        tabelAreaParkir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = tabelAreaParkir.rowAtPoint(e.getPoint());
                String id_area = tabelAreaParkir.getValueAt(baris, 0).toString();
                txtIdArea.setText(id_area);
                String nama_area = tabelAreaParkir.getValueAt(baris, 1).toString();
                txtNamaArea.setText(nama_area);

            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Area area = new Area();
                    area.setName(txtNamaArea.getText());

                    dArea.insert(area);
                    JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");

                    tampilkan_data();

                }catch (Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Area area = new Area();
                    area.setName(txtNamaArea.getText());
                    area.setId(Integer.parseInt(txtIdArea.getText()));

                    dArea.update(area);
                    JOptionPane.showMessageDialog(null, "Edit Data Berhasil");

                    tampilkan_data();
                }catch (Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    dArea.delete(Integer.parseInt(txtIdArea.getText()));
                    JOptionPane.showMessageDialog(null, "Delete Data Berhasil");
                    tampilkan_data();
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuAdminForm mu = new MenuAdminForm();
                mu.setVisible(true);
            }
        });
    }
}
