package ViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Helper.DbConnection;
import Model.User;

public class UserForm extends JFrame implements ActionListener{
    private JTextField nama;
    private JTextField alamat;
    private JLabel email;
    private JLabel labelDataPengguna;
    private JPanel panelUser;
    private JButton editBtn;
    private JButton backBtn;
    private User user;

    public UserForm(String mail) {
        add(panelUser);
        setTitle("Aplikasi Parking Subcription");
        setSize(500, 500);

        labelDataPengguna.setVerticalTextPosition(JLabel.TOP);
        labelDataPengguna.setHorizontalTextPosition(JLabel.CENTER);
        nama.setEditable(false);
        alamat.setEditable(false);
        nama.setBorder(null);
        alamat.setBorder(null);

        showData(mail);
        backBtn.addActionListener(this);
        editBtn.addActionListener(this);
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

    public void showData(String mail) {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM `parking`.`user` WHERE `email`='"+mail+"'";


        try {
            ps = DbConnection.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                setNama(rs.getString("nama"));
                setAlamat(rs.getString("alamat"));
                setEmail(rs.getString("email"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String lastName = nama.getText();
        String lastAlamat = alamat.getText();
        if (e.getSource() == backBtn) {
            MenuUser mu = new MenuUser(this.user);
            dispose();
            mu.setVisible(true);
            mu.setMailLabel(getEmail());
            mu.setLocationRelativeTo(null);
            nama.setEditable(false);
            alamat.setEditable(false);
        } else { // if source is editBtn
            if (editBtn.getText() == "Edit"){
                nama.setEditable(true);
                alamat.setEditable(true);
                nama.grabFocus();
                editBtn.setText("Save");
                backBtn.setText("Cancel");
            } else {// if editBtn == Save
                try {
                    PreparedStatement ps;
                    ResultSet rs;
                    String query = "UPDATE `parking`.`user` SET `nama`=?, `alamat` =? WHERE `email`='" + getEmail() + "'";

                    ps = DbConnection.getConnection().prepareStatement(query);
                    ps.setString(1, getNama());
                    ps.setString(2, getAlamat());

                    ps.executeUpdate();
                    ps.close();

                    JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
                    editBtn.setText("Edit");
                    backBtn.setText("Back");
                    nama.setEditable(false);
                    alamat.setEditable(false);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }

//    public void actionPerformed(ActionEvent e) {
//        nama.setEditable(true);
//        alamat.setEditable(true);
//        editBtn.setText("Save");
//        backBtn.setText("Cancel");
//    }
}
