package ViewController;

import DAO.DAOArea;
import DAO.DAOGarage;
import DAO.DAOKendaraan;
import Model.*;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class ParkirForm extends JFrame {

    private JPanel parkirPanel;
    private DateTimePicker dateTimePicker;
    private JTable table1;
    private JComboBox cmbKendaraan;
    private JComboBox cmbArea;
    private JComboBox cmbGarage;
    private JButton startButton;
    private JButton stopButton;
    private JButton backButton;
    private JPanel tableParkir;
    private JLabel labelJamOperasional;
    private Customer user;
    private TransaksiAdmin parkir;

    public ParkirForm(Customer user){
        add(parkirPanel);
        setSize(500,500);
        setLocationRelativeTo(null);
        this.user = user;
        parkir = new TransaksiAdmin();
        parkir.setUser(user);

        loadData();
        disableStopButton();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUser mu = new MenuUser(user);
                mu.setVisible(true);
                mu.setLocationRelativeTo(null);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parkir.setStartTime(getDateTime());
                System.out.println("Debug : "+ parkir.getStartTime());

            }
        });

        cmbArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGarage((Area) cmbArea.getSelectedItem());
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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmb
            }
        });

    }

    public void loadData(){

        this.initDateTime();
        this.loadKendaraan();
        this.loadArea();
        this.loadGarage((Area)this.cmbArea.getSelectedItem());


    }

    public void updateLabel(){
        labelJamOperasional.setText("Jam Operasional: -");
        if (cmbGarage.getItemCount()!=0) {

            OperationalTime opTime = ((Garage) cmbGarage.getSelectedItem()).getOperationalTime();
            labelJamOperasional.setText(
                    "Jam Operasional: " + (opTime.getDay() + ", " + opTime.getOpenHourTime() + " - " + opTime.getCloseHourTime()));
        }
    }

    public void initDateTime(){

        JTextField dateField = dateTimePicker.getDatePicker().getComponentDateTextField();
        JTextField timeField = dateTimePicker.getTimePicker().getComponentTimeTextField();
        LocalDateTime dt = LocalDateTime.now();
        String dateNow =  DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(dt);
        dateField.setText(dateNow);
        String timeNow = dt.format(DateTimeFormatter.ofPattern("hh:mma")).toLowerCase();
        timeField.setText(timeNow);
    }

    public String getDateTime(){

        return "";
    }

    private void loadArea(){
        try{
            DAOArea daoArea = new DAOArea();
            List<Area> areas =  daoArea.getAll();
            System.out.println("Debug : " + areas);
            for (Area area: areas){
                cmbArea.addItem(area);
                System.out.println("Debug : " + (Area) cmbArea.getSelectedItem());
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
//============================================================
    private void loadGarage(){
        try{
            DAOGarage daoGarage = new DAOGarage();
            List<Garage> garages =  daoGarage.getUnparkedGarageByUser(this.user);
            for (Garage garage: garages){
                cmbKendaraan.addItem(garage);
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
        stopButton.setEnabled(false);
    }

    private void disableStopButton(){
        stopButton.setEnabled(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
