package ViewController;

import DAO.DAOArea;
import DAO.DAOKendaraan;
import Model.Area;
import Model.Customer;
import Model.Kendaraan;
import Model.Transaction;
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
    private Customer user;
    private Transaction parkir;

    public ParkirForm(Customer user){
        add(parkirPanel);
        setSize(500,500);
        setLocationRelativeTo(null);
        this.user = user;
        parkir = new Transaction();
        parkir.setUser(user);

        loadData();



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

    }

    public void loadData(){

        this.initDateTime();
        this.loadArea();
        this.loadKendaraan();


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


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
