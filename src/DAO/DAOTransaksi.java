package DAO;

import Helper.DbConnection;
import Model.Transaksi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOTransaksi {
    Connection CONN =DbConnection.getConnection();
    private final String readQuery = "SELECT * FROM transaksi_parkir";
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
                    transaksi.setDuration(res.getString(7));
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
}
