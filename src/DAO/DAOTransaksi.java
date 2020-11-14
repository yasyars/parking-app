package DAO;

import Helper.DbConnection;
import Model.*;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOTransaksi {
    Connection CONN =DbConnection.getConnection();
    private final String readQuery = "SELECT * FROM transaksi_parkir";
    private final String getByUserQuery = "SELECT * FROM transaksi_parkir WHERE id_user =?";
    private final String parkQuery = "INSERT INTO transaksi_parkir (id_user,id_area,id_garage, waktu_masuk, waktu_keluar, durasi, total_harga,plat_no) values (?,?,?,?,?,?,?,?)";

    private final String getByIdQuery = "SELECT * FROM area_parkir WHERE id_area = ?";


    public DAOTransaksi(){};

    public List<Transaksi> getAll() {
        List<Transaksi> listOfTransaksi = null;
        try{
            listOfTransaksi = new ArrayList<Transaksi>();


            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                Transaksi transaksi = new Transaksi();
                try{
                    DAOCustomer daoUser = new DAOCustomer();
                    transaksi.setUser(daoUser.getById(res.getInt(2)));
                    DAOArea dArea = new DAOArea();
                    transaksi.setArea(dArea.getById(res.getInt(3)));
                    DAOGarage daoGarage = new DAOGarage();
                    transaksi.setGarage(daoGarage.getById(res.getInt(4)));
                    transaksi.setDuration(res.getInt(7));
                    transaksi.setTotalTransaction(res.getInt(8));
                }catch (Exception error){
                    System.out.println("Error : " +error.getMessage());
                }
                listOfTransaksi.add(transaksi);
            }
        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());
        }

        return listOfTransaksi;
    }

    public void insert(Transaksi transaksi){
        PreparedStatement stm = null;

        try{
            stm = CONN.prepareStatement(parkQuery);
            stm.setInt(1,transaksi.getUser().getId());
            stm.setInt(2,transaksi.getArea().getId());
            stm.setInt(3,transaksi.getGarage().getId());
            stm.setString(4,transaksi.getStartTime());
            stm.setString(5,transaksi.getEndTime());
            stm.setInt(6, transaksi.getDuration());
            stm.setDouble(7, transaksi.getTotalTransaction());
            stm.setString(8,transaksi.getKendaraan().getNoPlat());
            stm.execute();

        }catch(HeadlessException | SQLException e){
            System.out.println("Error DAOTransaksi insert: " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException k){
                System.out.println("Error DAOParkir 3: " +k.getMessage());
            }
        }
    }

    public List<Transaksi> getByUser(Customer user){
        List<Transaksi> lg = new ArrayList<>();
        PreparedStatement stm = null;
        DAOArea daoArea = new DAOArea();
        DAOGarage daoGarage = new DAOGarage();
        DAOKendaraan daoKendaraan = new DAOKendaraan();

        try {
            stm = CONN.prepareStatement(getByUserQuery);
            stm.setInt(1, user.getId());
            stm.execute();
            ResultSet res = stm.executeQuery();

            while (res.next()) {
                Transaksi transaksi = new Transaksi();
                transaksi.setId(res.getInt("id_transaksi"));
                transaksi.setUser(user);
                try{
                    transaksi.setArea(daoArea.getById(res.getInt("id_area")));
                    transaksi.setGarage(daoGarage.getById(res.getInt("id_garage")));
                    transaksi.setKendaraan(daoKendaraan.getByPlat(res.getString("plat_no")));
                    transaksi.setStartTime(res.getString("waktu_masuk"));
                    transaksi.setEndTime(res.getString("waktu_keluar"));
                    transaksi.setDuration(res.getInt("durasi"));
                    transaksi.setTotalTransaction(res.getDouble("total_harga"));
                    lg.add(transaksi);

                }catch (Exception e){
                    System.out.println("Error DAOTransaksi: " + e);
                }


            }
        } catch (SQLException e) {
            System.out.println("Error DAOKendaraan 10: " + e.getMessage());
        }
        return lg;
    }






}
