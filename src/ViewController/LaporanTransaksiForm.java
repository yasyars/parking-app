package ViewController;

import DAO.DAOArea;
import DAO.DAOTransaksi;
import Model.Area;
import Model.TableModelTransaksiAdmin;
import Model.Transaksi;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class LaporanTransaksiForm extends JFrame{
    private JTable TabelTransaksiAdmin;
    private JLabel jenisLabel;
    private JPanel panelriwayattransaksi;
    private JButton backButton;
    private JComboBox cbJenis;
    private DatePicker datePicker;
    private JLabel titleLaporan;
    private JLabel labelJumlahTransaksi;
    private JLabel labelTotal;
    private DAOTransaksi daoTransaksi = new DAOTransaksi();

    public LaporanTransaksiForm() {
        DAOTransaksi daoTransaksi = new DAOTransaksi();
        initDate();
        showTableAll();


        add(panelriwayattransaksi);
        setTitle("Aplikasi Parking Subcription");
        setSize(900, 700);
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
                updateTable();
            }
        });

        datePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                updateTable();
            }
        });

    }

    public void updateTable(){
        if (cbJenis.getSelectedItem().equals("Semua")){
            showTableAll();
        }else if (cbJenis.getSelectedItem().equals("Harian")){
            showHarian();
        }else if (cbJenis.getSelectedItem().equals("Mingguan")){
            //
        }else if (cbJenis.getSelectedItem().equals("Bulanan")){
            //
        }else if (cbJenis.getSelectedItem().equals("Tahunan")){
            //
        }
    }


    public void showTableAll() {
        this.titleLaporan.setText("");

        DAOTransaksi daoTransaksi = new DAOTransaksi();
        List<Transaksi> listOfTransaksi = daoTransaksi.getAll();
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(listOfTransaksi);
        TabelTransaksiAdmin.setModel(model);

        this.showTotal(listOfTransaksi);
    }

    public void showTotal(List<Transaksi> listOfTransaksi){
        labelJumlahTransaksi.setText(String.valueOf(listOfTransaksi.size()));

        double total = 0;
        for (Transaksi transaksi : listOfTransaksi){
            total+= transaksi.getTotalTransaction();
        }

        labelTotal.setText(String.valueOf(total));
    }

    public String indoHari(String day){
        day = day.toLowerCase();
        if (day.equals("monday")){
            return "Senin";
        }else if (day.equals("tuesday")){
            return "Selasa";
        }else if (day.equals("wednesday")){
            return "Rabu";
        }else if (day.equals("thursday")){
            return "Kamis";
        }else if (day.equals("friday")){
            return "Jumat";
        }else if (day.equals("saturday")){
            return "Sabtu";
        }else if (day.equals("sunday")){
            return "Minggu";
        }else{
            return null;
        }
    }
    public void updateTitle(String jenis){
        if (jenis.equals("Semua")){
            titleLaporan.setText("");
        }else if (jenis.equals("Harian")){
            //
        }else if (jenis.equals("Mingguan")){

        }else if (jenis.equals("Bulanan")){

        }else if (jenis.equals("Tahunan")){

        }
    }

    public void showHarian(){
        LocalDate date = datePicker.getDate();
        List<Transaksi> listOfTransaction = daoTransaksi.getAllinDate(date);
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(listOfTransaction);
        TabelTransaksiAdmin.setModel(model);

        showTotal(listOfTransaction);

    }

    public void initDate(){
        datePicker.setDate(LocalDate.now());
    }


}
