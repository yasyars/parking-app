package ViewController;

import DAO.*;
import Model.*;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ParkirForm extends JFrame {

    private JPanel parkirPanel;
    private DateTimePicker dateTimePicker;
    private JTable tableParkir;
    private JComboBox cmbKendaraan;
    private JComboBox cmbArea;
    private JComboBox cmbGarage;
    private JButton startButton;
    private JButton stopButton;
    private JButton backButton;

    private JLabel labelJamOperasional;
    private JLabel labelBukaTutup;
    private Customer user;
    private Parkir parkir;
    private int isStarted;
    private int garageOpen;
    private JPanel panel;

    public ParkirForm(Customer user){
        add(parkirPanel);
        setSize(500,500);
        setLocationRelativeTo(null);
        this.user = user;
        parkir = new Parkir();
        parkir.setUser(user);
        this.isStarted = 0;
        loadData();

        disableStopButton();
        DAOParkir daoParkir = new DAOParkir();

        updateLabel();
        if (daoParkir.getByUser(user) != null){
            disableAllforStart();
            parkir = daoParkir.getByUser(user) ;
        }

        dateTimePicker.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent dateTimeChangeEvent) {
                if (isStarted==0) {
                    updateLabel();
                }else{
                    updateLabel();
                    disableStartButton();
                    if (garageOpen == 0){
                        disableStopButton();
                    }else{
                        enableStopButton();
                    }
                }

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

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cmp = parkir.compareStopParkingTime(getDateTimePickerLocalDateTime());
                DAOTransaksi daoTransaksi = new DAOTransaksi();
                List<Transaksi> allTransaksi = daoTransaksi.getByUser(user);

                Transaksi tr = new Transaksi();

                if (cmp>0 ){
                    daoParkir.delete(parkir);

                    tr.setArea(parkir.getArea());
                    tr.setEndTime(dateFormtoDB());
                    tr.setGarage(parkir.getGarage());
                    tr.setKendaraan(parkir.getKendaraan());
                    tr.setStartTime(parkir.getStartTime());
                    if (daoTransaksi.getAllinMonth(tr.getMonthNumberFromStartTime(),tr.getYearFromStartTime(),user).size() == 0){
                        tr.setFirstinMonth(true);
                    }else{
                        tr.setFirstinMonth(false);
                    }
                    tr.setUser(user);
                    tr.setCalculateDuration();
                    tr.setCalculateTotalPrice();

                    System.out.println("Debug parkir form: "+daoTransaksi.getAllinMonth(tr.getMonthNumberFromStartTime(),tr.getYearFromStartTime(),user) );



                    daoTransaksi.insert(tr);
                    JOptionPane.showMessageDialog(null,"Riwayat parkir telah disimpan\nWaktu transaksi keluar: " + tr.getEndTime());

                    dispose();
                    ParkirForm p = new ParkirForm(user);
                    p.setVisible(true);
                    p.setLocationRelativeTo(null);

                }else if (cmp==0){
                    daoParkir.delete(parkir);
                    JOptionPane.showMessageDialog(null,"Anda batal parkir");
                    dispose();
                    ParkirForm p = new ParkirForm(user);
                    p.setVisible(true);
                    p.setLocationRelativeTo(null);

                }else{
                    JOptionPane.showMessageDialog(null,"Waktu dan tanggal yang dipilih harus setelah \nwaktu masuk di hari operasional yang sama");
                }

            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DAOTransaksi daoTransaksi = new DAOTransaksi();
                List<Transaksi> allTransaksi = daoTransaksi.getByUser(user);
                parkir.setStartTime(dateFormtoDB());
                parkir.setArea((Area)cmbArea.getSelectedItem());
                parkir.setKendaraan((Kendaraan)cmbKendaraan.getSelectedItem());
                parkir.setGarage((Garage)cmbGarage.getSelectedItem());

                if (allTransaksi.size()!=0){
                    Transaksi lastTransaction = allTransaksi.get(allTransaksi.size()-1);
                    int cmp = lastTransaction.getEndLocalTime().compareTo(getDateTimePickerLocalDateTime());

                    if (cmp<0 && parkir.getGarage().getOperationalTime().isOpen(dateTimePicker)){
                        parkir.setUser(user);
                        daoParkir.park(parkir);

                        disableAllforStart();
                        loadTable();
                        isStarted = 1;

                    }else if (cmp>=0) {
                        JOptionPane.showMessageDialog(null,"Waktu yang dipilih harus lebih besar dari waktu keluar transaksi terakhir!\nWaktu terakhir: "+lastTransaction.getEndLocalTime());
                    }else{
                        JOptionPane.showMessageDialog(null,"Garage tutup di waktu yang dipilih");
                        updateLabel();
                    }
                }else{

                    if (parkir.getGarage().getOperationalTime().isOpen(dateTimePicker)){
                        parkir.setUser(user);
                        daoParkir.park(parkir);

                        disableAllforStart();
                        loadTable();
                        isStarted = 1;

                    }else{
                        JOptionPane.showMessageDialog(null,"Garage tutup di waktu yang dipilih");
                        updateLabel();
                    }
                }

            }
        });

        cmbArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbArea.getItemCount()!=0) {
                    loadGarage((Area) cmbArea.getSelectedItem());
                }
            }
        });

        cmbGarage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabel();
                if (cmbGarage.getItemCount()==0) {
                    disableStartButton();
                }else{
                    enableStartButton();
                }
            }
        });


    }

    public void loadData(){

        this.initDateTime();
        this.loadKendaraan();
        this.loadArea();

        if (cmbArea.getItemCount()!=0) {
            this.loadGarage((Area) this.cmbArea.getSelectedItem());
        }

        this.loadTable();
    }

    private void loadTable(){
        DAOParkir daoParkir = new DAOParkir();
        List<Parkir> p = new ArrayList<>();
        if (daoParkir.getByUser(this.user) != null){
            p.add(daoParkir.getByUser(this.user));
        }
        TableModelParkir model = new TableModelParkir(p);
        tableParkir.setModel(model);
    }

    public void updateLabel(){
        labelJamOperasional.setText("Jam Operasional: -");
        labelBukaTutup.setText("");

        if (cmbGarage.getItemCount()!=0) {
            OperationalTime opTime = ((Garage) cmbGarage.getSelectedItem()).getOperationalTime();
            labelJamOperasional.setText(
                    "Jam Operasional: " + (opTime.getDay() + ", " + opTime.getOpenHourTime() + " - " + opTime.getCloseHourTime()));
            setLabelBukaTutup();
        }

    }

    public void initDateTime(){

        JTextField dateField = dateTimePicker.getDatePicker().getComponentDateTextField();
        JTextField timeField = dateTimePicker.getTimePicker().getComponentTimeTextField();

        DAOTransaksi daoTransaksi = new DAOTransaksi();
        List<Transaksi> allTransaksi = daoTransaksi.getByUser(user);

        if (allTransaksi.size() != 0){
            Transaksi lastTransaction = allTransaksi.get(allTransaksi.size()-1);

            LocalDateTime last = lastTransaction.getEndLocalTime();
            String dateNow =  DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(last);
            dateField.setText(dateNow);
            String timeNow = last.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
            timeField.setText(timeNow);

        }else{
            LocalDateTime dt = LocalDateTime.now();
            String dateNow =  DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(dt);
            dateField.setText(dateNow);
            String timeNow = dt.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
            timeField.setText(timeNow);
        }




    }

    private void loadArea(){

        try{
            DAOArea daoArea = new DAOArea();
            List<Area> areas =  daoArea.getAll();
            for (Area area: areas){
                cmbArea.addItem(area);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Fail to load area data!","Fail load data", 2);
        }
    }

    private void loadKendaraan(){
        try{
            DAOKendaraan daoKendaraan = new DAOKendaraan();
            List<Kendaraan> kendaraans =  daoKendaraan.getByUser(this.user);
            for (Kendaraan kendaraan: kendaraans){
                cmbKendaraan.addItem(kendaraan);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Fail to load Kendaraan Data!","Fail load data",2);
        }
    }

    private void loadGarage(Area area){
        if (cmbGarage.getItemCount()!=0){
            cmbGarage.removeAllItems();
        }
        try{
            DAOGarage daoGarage = new DAOGarage();
            List<Garage> garages =  daoGarage.getByArea(area);
            for (Garage garage: garages){
                cmbGarage.addItem(garage);
            }

            if (cmbGarage.getItemCount()!=0){
                updateLabel();
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Fail to load Garage Data!","Fail load data",2);
        }
    }

    private void enableStartButton(){
        startButton.setEnabled(true);
    }
    private void disableStartButton(){
        startButton.setEnabled(false);
    }
    private void enableStopButton(){
        stopButton.setEnabled(true);
    }

    private void disableStopButton(){
        stopButton.setEnabled(false);
    }

    private void disableAllforStart(){
        enableStopButton();
        disableStartButton();
        cmbArea.setEnabled(false);
        cmbGarage.setEnabled(false);
        cmbKendaraan.setEnabled(false);
        this.isStarted = 1;
    }

    private void enableAllforStart(){
        disableStopButton();
        enableStartButton();
        cmbKendaraan.setEnabled(true);
        cmbArea.setEnabled(true);
        cmbGarage.setEnabled(true);

    }

    private String getDateTimePickerText(){
        JTextField dateField = this.dateTimePicker.getDatePicker().getComponentDateTextField();
        JTextField timeField = this.dateTimePicker.getTimePicker().getComponentTimeTextField();

        String str = dateField.getText() +" "+ timeField.getText().toUpperCase();
        return str;
    }

    private LocalDateTime getDateTimePickerLocalDateTime(){
        LocalDate dateLocal= dateTimePicker.datePicker.getDate();
        LocalTime timeLocal= dateTimePicker.timePicker.getTime();

        LocalDateTime dateTime = LocalDateTime.of(dateLocal,timeLocal);

        return dateTime;

    }

    private void setLabelBukaTutup(){
        if (cmbKendaraan.getItemCount()==0 || cmbArea.getItemCount()==0 || cmbGarage.getItemCount()==0) {
            disableStartButton();
            labelBukaTutup.setText("");
        }else{
            if (((Garage)cmbGarage.getSelectedItem()).getOperationalTime().isOpen(dateTimePicker)) {
                labelBukaTutup.setText("Garage Buka");
                labelBukaTutup.setForeground(Color.BLUE);
                enableStartButton();
                this.garageOpen = 1;
            }else{
                labelBukaTutup.setText("Garage Tutup");
                labelBukaTutup.setForeground(Color.RED);
                disableStartButton();
                this.garageOpen = 0;
            }
        }
    }

    private String dateFormtoDB(){

//        JTextField dateField = this.dateTimePicker.getDatePicker().getComponentDateTextField();
//        JTextField timeField = this.dateTimePicker.getTimePicker().getComponentTimeTextField();
//
//        String str = dateField.getText() +" "+ timeField.getText().toUpperCase();

        LocalDate dateLocal= dateTimePicker.datePicker.getDate();
        LocalTime timeLocal= dateTimePicker.timePicker.getTime();

        LocalDateTime dateTime = LocalDateTime.of(dateLocal,timeLocal);
//
//        try{
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy h:mma");
//            dateTime = LocalDateTime.parse(str, formatter);
//        }catch(Exception e){
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mma");
//            dateTime = LocalDateTime.parse(str, formatter);
//        }

        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));

        return formattedDateTime;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
