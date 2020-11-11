package ViewController;

import Model.Customer;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ParkirForm extends JFrame {
    private Customer user;
    private JPanel parkirPanel;
    private DateTimePicker dateTimePicker;
    private JTable table1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton startButton;
    private JButton stopButton;

    public ParkirForm(Customer user){
        add(parkirPanel);
        setSize(500,500);
        this.user = user;


        JTextField dateField = dateTimePicker.getDatePicker().getComponentDateTextField();
        JTextField timeField = dateTimePicker.getTimePicker().getComponentTimeTextField();

        try {

            System.out.println("Debug: Date = "+dateField.getText());
            System.out.println("Debug: Time = "+timeField.getText());

        }catch (Exception error){
            JOptionPane.showMessageDialog(null,"Fail to load parkir form", "Load Failed", 2);
        }

    };

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
