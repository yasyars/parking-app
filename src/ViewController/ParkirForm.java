package ViewController;

import DAO.*;
import Model.*;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
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
    private JButton pilihWaktuButton;
    private Customer user;
    private Parkir parkir;
    private JPanel panel;

    public ParkirForm(Customer user){
        add(parkirPanel);
        setSize(700,500);
        setLocationRelativeTo(null);
        this.user = user;
        parkir = new Parkir();
        parkir.setUser(user);

        loadData();

        disableStopButton();
        DAOParkir daoParkir = new DAOParkir();

        setLabelBukaTutup();

        if (daoParkir.getByUser(user) != null){
            disableAllforStart();
        }

        pilihWaktuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLabelBukaTutup();
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
                Parkir park = daoParkir.getByUser(user) ;
                daoParkir.unpark(park);

                System.out.println("Debug Parkir Form 1: "+ park.getStartTime());
                DAOTransaksi daoTransaksi = new DAOTransaksi();
                Transaksi tr = new Transaksi();

                tr.setArea(park.getArea());
                tr.setEndTime(dateFormtoDB());
                tr.setGarage(park.getGarage());
                tr.setKendaraan(park.getKendaraan());
                tr.setStartTime(park.getStartTime());
                tr.setUser(user);

                System.out.println("debug :"+ tr.setCalculateDuration());

                dispose();
                ParkirForm p = new ParkirForm(user);
                p.setVisible(true);
                p.setLocationRelativeTo(null);

            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parkir.setStartTime(dateFormtoDB());
                parkir.setArea((Area)cmbArea.getSelectedItem());
                parkir.setKendaraan((Kendaraan)cmbKendaraan.getSelectedItem());
                parkir.setGarage((Garage)cmbGarage.getSelectedItem());

                if (parkir.getGarage().getOperationalTime().isOpen(getDateTimePickerText())){
                    parkir.setUser(user);
                    daoParkir.park(parkir);

                    disableAllforStart();
                    loadTable();
                    labelBukaTutup.setText("");

                }else{
                    JOptionPane.showMessageDialog(null,"Garage tutup di waktu yang dipilih");
                    updateLabel();
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
        if (cmbGarage.getItemCount()!=0) {
            OperationalTime opTime = ((Garage) cmbGarage.getSelectedItem()).getOperationalTime();
            labelJamOperasional.setText(
                    "Jam Operasional: " + (opTime.getDay() + ", " + opTime.getOpenHourTime() + " - " + opTime.getCloseHourTime()));


            if (!opTime.isOpen(this.getDateTimePickerText())){
                disableStartButton();
                setLabelBukaTutup();
            }
        }

    }

    public void initDateTime(){

        JTextField dateField = dateTimePicker.getDatePicker().getComponentDateTextField();
        JTextField timeField = dateTimePicker.getTimePicker().getComponentTimeTextField();
        LocalDateTime dt = LocalDateTime.now();
        String dateNow =  DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(dt);
        dateField.setText(dateNow);
        String timeNow = dt.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
        timeField.setText(timeNow);



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
            List<Kendaraan> kendaraans =  daoKendaraan.getUnparkedKendaraanByUser(this.user);
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

    private void setLabelBukaTutup(){
        if (cmbKendaraan.getItemCount()==0 || cmbArea.getItemCount()==0 || cmbGarage.getItemCount()==0) {
            disableStartButton();
        }else{
            if (((Garage)cmbGarage.getSelectedItem()).getOperationalTime().isOpen( getDateTimePickerText())) {
                labelBukaTutup.setText("Garage Buka");
                labelBukaTutup.setForeground(Color.BLUE);
                enableStartButton();
            }else{
                labelBukaTutup.setText("Garage Tutup");
                labelBukaTutup.setForeground(Color.RED);
                disableStartButton();
            }
        }

    }

    private String dateFormtoDB(){

        JTextField dateField = this.dateTimePicker.getDatePicker().getComponentDateTextField();
        JTextField timeField = this.dateTimePicker.getTimePicker().getComponentTimeTextField();

        String str = dateField.getText() +" "+ timeField.getText().toUpperCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mma");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));

        System.out.println("Debug: \n" + "Day: " +
                dateTime.format(DateTimeFormatter.ofPattern("EEEE"))+
                "\nTime: " + dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return formattedDateTime;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
