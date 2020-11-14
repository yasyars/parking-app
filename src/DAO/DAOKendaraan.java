package DAO;

import Helper.DbConnection;
import java.awt.*;
import java.sql.*;
import Model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    protected final String getByPlat = "SELECT * FROM kendaraan WHERE plat_no = ?";
    protected final String setIsParked = "UPDATE kendaraan SET is_parked = ? WHERE plat_no=?";


    public DAOKendaraan() {
    }

    public List<Kendaraan> getAll() throws Exception {
        List<Kendaraan> lk;
        try {
            lk = new ArrayList<Kendaraan>();
            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()) {
                if (res.getString(2).equals("Mobil")){
                    Mobil mo = new Mobil();
                    mo.setNoPlat(res.getString(1));

                    try {
                        DAOCustomer daoUser = new DAOCustomer();
                        mo.setOwner(daoUser.get(res.getInt(3)));
                    } catch (Exception error){ }
                    lk.add(mo);
                } else if (res.getString(2).equals("Motor")){
                    Motor mo = new Motor();
                    mo.setNoPlat(res.getString(1));
                    DAOCustomer daoUser = new DAOCustomer();
                    try {
                        mo.setOwner(daoUser.get(res.getInt(3)));
                        lk.add(mo);
                    } catch (Exception error){ }
                    lk.add(mo);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error : " + e.getMessage());
        }

        return lk;
    }

    public <T extends Kendaraan> void insert(T t) throws Exception {
        PreparedStatement stm = null;
        try {
            stm = CONN.prepareStatement(insertQuery);
            //System.out.println(t.getNoPlat());
            stm.setString(1, t.getNoPlat());
            stm.setString(2, t.getClass().getSimpleName());
            stm.setInt(3, t.getOwner().getId());
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

    public List<Kendaraan> getUnparkedKendaraanByUser(Customer user) throws Exception {
        List<Kendaraan> lg = new ArrayList<Kendaraan>();
        PreparedStatement stm = null;

        try {
            stm = CONN.prepareStatement(getUnparked);
            stm.setInt(1, user.getId());

            ResultSet res = stm.executeQuery();
            System.out.println("Debug stm: "+ stm);

            while (res.next()) {
                //Kendaraan kendaraan = new Kendaraan();

                if (res.getString("tipe_kendaraan").equals("Mobil")) {
                    Mobil mo = new Mobil();
                    //kendaraan.setTipe(res.getString("tipe_kendaraan"));
                    mo.setIsParked(res.getInt("is_parked"));
                    mo.setOwner(user);
                    try {
                        mo.setNoPlat(res.getString("plat_no"));
                    } catch (Exception e) {
                        throw new Exception("setPlat" + e.getMessage());
                    }
                    lg.add(mo);
                } else {
                    Motor mo = new Motor();
                    mo.setIsParked(res.getInt("is_parked"));
                    mo.setOwner(user);
                    try {
                        mo.setNoPlat(res.getString("plat_no"));
                    } catch (Exception e) {
                        throw new Exception("setPlat" + e.getMessage());
                    }
                    lg.add(mo);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error : " + e.getMessage());
        }

        return lg;
    }

    public Kendaraan getByPlat(String plat) throws Exception{
        PreparedStatement stm = null;
        //Kendaraan kendaraan = new Kendaraan();

        try{
            stm = CONN.prepareStatement(getByPlat);
            stm.setString(1, plat);
            ResultSet res = stm.executeQuery();
            res.next();

            DAOCustomer daoCustomer = new DAOCustomer();
            if (res.getString("tipe_kendaraan").equals("Mobil")) {
                //kendaraan.setTipe(res.getString("tipe_kendaraan"));
                Mobil mo = new Mobil();
                mo.setIsParked(res.getInt("is_parked"));
                mo.setOwner(daoCustomer.getById(res.getInt("id_user")));
                try {
                    mo.setNoPlat(plat);
                } catch (Exception e) {
                    throw new Exception("Error DAOKendaraan 6: " + e);
                }
                return mo;
            } else{
                Motor mo = new Motor();
                mo.setIsParked(res.getInt("is_parked"));
                mo.setOwner(daoCustomer.getById(res.getInt("id_user")));
                try {
                    mo.setNoPlat(plat);
                } catch (Exception e) {
                    throw new Exception("Error DAOKendaraan 6: " + e);
                }
                return mo;
            }
        }catch (SQLException e){
            throw new Exception("Error DAOKendaraan 7: " + e);
        }
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
                //Kendaraan kendaraan = new Kendaraan();
                if (res.getString("tipe_kendaraan").equals("Mobil")) {
                    try {
                        Mobil mo = new Mobil();
                        mo.setNoPlat(res.getString("plat_no"));
                        //kendaraan.setTipe(res.getString("tipe_kendaraan"));
                        mo.setIsParked(res.getInt("is_parked"));
                        mo.setOwner(user);
                        lg.add(mo);
                    } catch (Exception error) {
                        throw new Exception("Error DAOKendaraan 8: " + error.getMessage());
                    }
                } else {
                    try {
                        Motor mo = new Motor();
                        mo.setNoPlat(res.getString("plat_no"));
                        mo.setIsParked(res.getInt("is_parked"));
                        mo.setOwner(user);
                        lg.add(mo);
                    } catch (Exception error) {
                        throw new Exception("Error DAOKendaraan 8: " + error.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error DAOKendaraan 9: " + e.getMessage());
        }

        return lg;
    }


    public <T extends Kendaraan> void setIsParked(T t) throws Exception{
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(setIsParked);

            stm.setInt(1, t.getIsParked());
            stm.setString(2, t.getNoPlat());
            stm.execute();
        }catch (HeadlessException | SQLException e){
            throw new Exception("Error DAOKendaraan 10: "+ e.getMessage());
        }finally {
            try {
                stm.close();
            } catch (SQLException k) {
                throw new Exception("Error DAOKendaraan 11: " + k.getMessage());
            }
        }
    }

}
