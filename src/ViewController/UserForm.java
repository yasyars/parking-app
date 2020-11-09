package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.DAOUser;
import Helper.DbConnection;
import Model.Customer;
import Model.User;

public class UserForm extends JFrame{
    private JTextField nama;
    private JTextField alamat;
    private JLabel email;
    private JLabel labelDataPengguna;
    private JPanel panelUser;
    private JButton editBtn;
    private JButton backBtn;
    private JComboBox subsCombo;
    private Customer user = new Customer();

    public UserForm(Customer user) {
        this.user = user;
        DAOUser daoUser = new DAOUser();

        add(panelUser);
        setTitle("Aplikasi Parking Subcription");
        setSize(500, 500);

        labelDataPengguna.setVerticalTextPosition(JLabel.TOP);
        labelDataPengguna.setHorizontalTextPosition(JLabel.CENTER);
        nama.setEditable(false);
        alamat.setEditable(false);
        nama.setBorder(null);
        alamat.setBorder(null);
        subsCombo.setEditable(false);

        showData(user);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuUser mu = new MenuUser(user);
                dispose();
                mu.setVisible(true);
                mu.setMailLabel(getEmail());
                mu.setLocationRelativeTo(null);
                nama.setEditable(false);
                alamat.setEditable(false);
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editBtn.getText() == "Edit") {
                    nama.setEditable(true);
                    alamat.setEditable(true);
                    nama.grabFocus();
                    editBtn.setText("Save");
                    backBtn.setText("Cancel");
                }else{
                    try {
                        user.setName(getNama());
                        user.setAddress(getAlamat());
                        daoUser.update(user);

                        editBtn.setText("Edit");
                        backBtn.setText("Back");
                        nama.setEditable(false);
                        alamat.setEditable(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
    }

    public void setSubsCombo(String subs){
        this.subsCombo.setSelectedItem(subs);
    }

    public String getSubsCombo(){
        return String.valueOf(subsCombo.getSelectedItem());
    }
    public void setNama(String nama) {
        this.nama.setText(nama);
    }

    public String getNama(){
        return this.nama.getText();
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public String getEmail() {
        return this.email.getText();
    }

    public void setAlamat(String alamat) {
        this.alamat.setText(alamat);
    }

    public String getAlamat(){
        return this.alamat.getText();
    }

    public void showData(User user) {
        try{
            setNama(user.getName());
            setAlamat(user.getAddress());
            setEmail(user.getEmail());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Fail to get user data" + e);
        }
    }

}
