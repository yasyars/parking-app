package DAO;

import Helper.DbConnection;
import java.awt.*;
import java.sql.*;
import Model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOKendaraan {
    Connection CONN = (Connection) DbConnection.getConnection();

    protected final String insertQuery = "INSERT INTO kendaraan (plat_no, tipe_kendaraan, id_user) values (?,?,?)";
    protected final String readQuery = "SELECT * FROM kendaraan";
    protected final String deleteQuery = "DELETE FROM kendaraan WHERE plat_no = ?";
    protected static final String getPlatInTransc = "SELECT * FROM transaksi_parkir WHERE plat_no = ?";
    protected final String getByUser = "SELECT * FROM kendaraan WHERE id_user=?";
    protected final String getUnparked = "SELECT * FROM kendaraan WHERE id_user = ? AND is_parked=0";

    public DAOKendaraan() {
    }

    public List<Kendaraan> getAll() throws Exception {
        List<Kendaraan> lk = null;
        try {
            lk = new ArrayList<Kendaraan>();

            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()) {
                Kendaraan kendaraan = new Kendaraan();
                try {
                    kendaraan.setTipe(res.getString(2));
                    kendaraan.setNoPlat(res.getString(1));
                    DAOCustomer daoUser = new DAOCustomer();
                    //Customer c = daoUser.get(res.getInt(3));
                    kendaraan.setOwner(daoUser.get(res.getInt(3)));
                } catch (Exception error) {
                }
                lk.add(kendaraan);
            }
        } catch (SQLException e) {
            throw new Exception("Error : " + e.getMessage());
        }

        return lk;
    }

    ;

    public void insert(Kendaraan kendaraan) throws Exception {
        PreparedStatement stm = null;
        try {
            stm = CONN.prepareStatement(insertQuery);

            System.out.println(kendaraan.getOwner().getId());
            stm.setString(1, kendaraan.getNoPlat());
            stm.setString(2, kendaraan.getTipe());
            stm.setInt(3, kendaraan.getOwner().getId());
            stm.executeUpdate();

        } catch (HeadlessException | SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                stm.close();
            } catch (SQLException k) {
                throw new Exception(k.getMessage());
            }
        }
    }

    public void delete(String plat_no) throws Exception {
        PreparedStatement stm = null;
        System.out.println(plat_no);
        try {
            stm = CONN.prepareStatement(deleteQuery);
            stm.setString(1, plat_no);
            stm.execute();

        } catch (HeadlessException | SQLException e) {
            throw new Exception("Error : " + e.getMessage());
        } finally {
            try {
                stm.close();
            } catch (SQLException k) {
                throw new Exception("Error : " + k.getMessage());
            }
        }
    }

    public static boolean isInTransaction(String plat_no) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        boolean in = false;

        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(getPlatInTransc);
            ps.setString(1, plat_no);
            rs = ps.executeQuery();

            if (rs.next()) {
                in = true;
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return in;
    }

    public List<Kendaraan> getUnparkedKendaraanByUser(Customer user) {
        List<Kendaraan> lg = new ArrayList<>();
        PreparedStatement stm = null;

        try {
            stm = CONN.prepareStatement(getUnparked);
            stm.setInt(1, user.getId());

            ResultSet res = stm.executeQuery();
            System.out.println("Debug stm: "+ stm);

            while (res.next()) {
                Kendaraan kendaraan = new Kendaraan();


                System.out.println(kendaraan.getNoPlat());
                kendaraan.setTipe(res.getString("tipe_kendaraan"));
                kendaraan.setIsParked(res.getInt("is_parked"));
                kendaraan.setOwner(user);
                try{
                    kendaraan.setNoPlat(res.getString("plat_no"));
                }catch(Exception e){
                    System.out.println("setPlat" + e);
                }

                try {
//                    System.out.println("Debug: "+ res.getString("plat_no"));
//
//                    kendaraan.setNoPlat(res.getString("plat_no"));
//                    System.out.println(kendaraan.getNoPlat());
//                    kendaraan.setTipe(res.getString("tipe_kendaraan"));
//                    kendaraan.setIsParked(res.getInt("is_parked"));
//                    kendaraan.setOwner(user);
                    lg.add(kendaraan);
                } catch (Exception error) {
                    System.out.println("Error : " + error.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }

        return lg;
    }

    public List<Kendaraan> getByUser(Customer user) throws Exception {
        List<Kendaraan> lg = new ArrayList<>();
        PreparedStatement stm = null;

        try {
            stm = CONN.prepareStatement(getByUser);
            stm.setInt(1, user.getId());
            stm.execute();
            ResultSet res = stm.executeQuery();

            while (res.next()) {
                Kendaraan kendaraan = new Kendaraan();
                try {
                    kendaraan.setNoPlat(res.getString("plat_no"));
                    kendaraan.setTipe(res.getString("tipe_kendaraan"));
                    kendaraan.setIsParked(res.getInt("is_parked"));
                    kendaraan.setOwner(user);
                    lg.add(kendaraan);
                } catch (Exception error) {
                    System.out.println("Error : " + error.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }

        return lg;
    }

}
