package DAO;
import Helper.DbConnection;
import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOTransaksiUser {
    Connection CONN =(Connection) DbConnection.getConnection();
    private final String readQuery = "SELECT * FROM transaksi_parkir";
    private final String getByIdQuery = "SELECT * FROM area_parkir WHERE id_area = ?";
    protected final String getByUser = "SELECT * FROM transaksi_parkir WHERE id_user=?";


    public DAOTransaksiUser(){};

    public List<TransaksiUser> getByUser(Customer user) throws Exception {
        List<TransaksiUser> ltu = new ArrayList<>();
        PreparedStatement stm = null;

        try {
            stm = CONN.prepareStatement(getByUser);
            stm.setInt(1, user.getId());
            stm.execute();
            ResultSet res = stm.executeQuery();

            while (res.next()) {
                TransaksiUser transaksiUser = new TransaksiUser();
                try {
                    DAOArea dArea = new DAOArea();
                    transaksiUser.setArea(dArea.getById(res.getInt(3)));
                    DAOGarage daoGarage = new DAOGarage();
                    transaksiUser.setGarage(daoGarage.getById(res.getInt(4)));
                    transaksiUser.setStartTime(res.getString(5));
                    transaksiUser.setEndTime(res.getString(6));
                    transaksiUser.setDuration(res.getString(7));
                    transaksiUser.setTotalTransaction(res.getInt(8));
                    transaksiUser.setOwner(user);
                    ltu.add(transaksiUser);
                } catch (Exception error) {
                    System.out.println("Error : " + error.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }

        return ltu;
    }


    public List<TransaksiUser> getAll() {
        List<TransaksiUser> listOfTuser = null;
        try{
            listOfTuser = new ArrayList<TransaksiUser>();


            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                TransaksiUser transaksiUser = new TransaksiUser();
                try{
                    DAOArea dArea = new DAOArea();
                    transaksiUser.setArea(dArea.getById(res.getInt(3)));
                    DAOGarage daoGarage = new DAOGarage();
                    transaksiUser.setGarage(daoGarage.getById(res.getInt(4)));
                    transaksiUser.setStartTime(res.getString(5));
                    transaksiUser.setEndTime(res.getString(6));
                    transaksiUser.setDuration(res.getString(7));
                    transaksiUser.setTotalTransaction(res.getInt(8));
                }catch (Exception error){
                    System.out.println("Error : " +error.getMessage());
                }
                listOfTuser.add(transaksiUser);
            }
        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());
        }

        return listOfTuser;


    }

}

