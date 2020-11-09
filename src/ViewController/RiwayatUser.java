package ViewController;

import Model.User;

import javax.swing.*;

public class RiwayatUser extends JFrame{
    private JPanel riwayatUserPanel;
    private User user;

    public RiwayatUser(User user){
        this.user = user;

        add(riwayatUserPanel);
        setTitle("Riwayat Transaksi Pengguna");
        setSize(500,500);

    }
}
