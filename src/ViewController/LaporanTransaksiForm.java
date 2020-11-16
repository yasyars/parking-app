package ViewController;

import DAO.DAOArea;
import DAO.DAOTransaksi;
import Helper.Translate;
import Model.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import Helper.Translate.*;

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
            updateTitle("Semua");
        }else if (cbJenis.getSelectedItem().equals("Harian")){
            showHarian();
            updateTitle("Harian");
        }else if (cbJenis.getSelectedItem().equals("Mingguan")){
            showMingguan();
            updateTitle("Mingguan");
        }else if (cbJenis.getSelectedItem().equals("Bulanan")){
            //

        }else if (cbJenis.getSelectedItem().equals("Tahunan")){
            showTahunan();
            updateTitle("Tahunan");

        }
    }

    public void showTotal(List<Transaksi> listOfTransaksi){
        labelJumlahTransaksi.setText(String.valueOf(listOfTransaksi.size()));

        double total = 0;
        for (Transaksi transaksi : listOfTransaksi){
            total+= transaksi.getTotalTransaction();
        }

        labelTotal.setText(String.valueOf(total));
    }


    public void showTableAll() {
        this.titleLaporan.setText("");

        DAOTransaksi daoTransaksi = new DAOTransaksi();
        List<Transaksi> listOfTransaksi = daoTransaksi.getAll();
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(listOfTransaksi);
        TabelTransaksiAdmin.setModel(model);

        this.showTotal(listOfTransaksi);
    }


    public void showHarian(){
        LocalDate date = datePicker.getDate();
        List<Transaksi> listOfTransaction = daoTransaksi.getAllinDate(date);
        TableModelTransaksiAdmin model = new TableModelTransaksiAdmin(listOfTransaction);
        TabelTransaksiAdmin.setModel(model);

        showTotal(listOfTransaction);
    }

    public void showMingguan(){
        LocalDate date = datePicker.getDate();
        List<Transaksi> listOfTransaction = daoTransaksi.getAllinWeek(date);

        TableModelTransaksiMingguan model = new TableModelTransaksiMingguan(listOfTransaction);
        TabelTransaksiAdmin.setModel(model);

        showTotal(listOfTransaction);
    }

    public void showTahunan(){
        LocalDate date = datePicker.getDate();
        List<Transaksi> listOfTransaction = daoTransaksi.getAllinYear(date);

        TableModelTransaksiTahunan model = new TableModelTransaksiTahunan(listOfTransaction);
        TabelTransaksiAdmin.setModel(model);
        showTotal(listOfTransaction);
    }

    public void initDate(){
        datePicker.setDate(LocalDate.now());
    }

    public void updateTitle(String jenis){
        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);

        // apply weekOfMonth()
        TemporalField weekOfMonth= weekFields.weekOfMonth();

        if (jenis.equals("Semua")){
            titleLaporan.setText("");
        }else if (jenis.equals("Harian")){
            titleLaporan.setText("Hari " +
                    Translate.getIndoHari(datePicker.getDate().format(DateTimeFormatter.ofPattern("EEEE"))) + ", "+
                    datePicker.getDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")));
        }else if (jenis.equals("Mingguan")){
            titleLaporan.setText("Minggu ke-" +datePicker.getDate().get(weekOfMonth) + " Bulan "+
                    datePicker.getDate().getMonth() +" " +  datePicker.getDate().getYear());
        }else if (jenis.equals("Bulanan")){

        }else if (jenis.equals("Tahunan")){
            titleLaporan.setText("Tahun " + datePicker.getDate().getYear());
        }
    }



}
