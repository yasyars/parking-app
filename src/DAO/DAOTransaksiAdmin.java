package DAO;

import Helper.DbConnection;
import Model.Area;
import Model.Kendaraan;
import Model.TransaksiAdmin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOTransaksiAdmin {
    Connection CONN =(Connection) DbConnection.getConnection();
    private final String readQuery = "SELECT * FROM transaksi_parkir";
    private final String getByIdQuery = "SELECT * FROM area_parkir WHERE id_area = ?";


    public DAOTransaksiAdmin(){};

    public List<TransaksiAdmin> getAll() {
        List<TransaksiAdmin> listOfTransaksi = null;
        try{
            listOfTransaksi = new ArrayList<TransaksiAdmin>();


            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                TransaksiAdmin transaksiAdmin = new TransaksiAdmin();
                try{
                    DAOCustomer daoUser = new DAOCustomer();
                    transaksiAdmin.setUser(daoUser.getById(res.getInt(2)));
                    DAOArea dArea = new DAOArea();
                    transaksiAdmin.setArea(dArea.getById(res.getInt(3)));
                    DAOGarage daoGarage = new DAOGarage();
                    transaksiAdmin.setGarage(daoGarage.getById(res.getInt(4)));
                    transaksiAdmin.setDuration(res.getString(7));
                    transaksiAdmin.setTotalTransaction(res.getInt(8));
                }catch (Exception error){
                    System.out.println("Error : " +error.getMessage());
                }
                listOfTransaksi.add(transaksiAdmin);
            }
        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());
        }

        return listOfTransaksi;


    }
}
