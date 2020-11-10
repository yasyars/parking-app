package DAO;

import Helper.DbConnection;
import java.awt.*;
import java.sql.*;
import Model.*;
import java.util.ArrayList;
import java.util.List;

public class DAOKendaraan {
    Connection CONN =(Connection) DbConnection.getConnection();
    private final String insertQuery = "INSERT INTO kendaraan (plat_no,tipe_kendaraan) values (?,?)";
    private final String readQuery = "SELECT * FROM kendaraan";

    public DAOKendaraan(){};

    public List<Kendaraan> getAll(){
        List<Kendaraan> listOfKendaraan = null;
        try{
            listOfKendaraan = new ArrayList<Kendaraan>();

            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                Kendaraan kendaraan = new Kendaraan();
                kendaraan.setNoPlat(res.getString(1));
                try{
                    kendaraan.setTipe(res.getString(2));
                    kendaraan.setOwner(res.getInt(3));
                }catch (Exception error){
                    System.out.println("Error : " +error.getMessage());
                }
                listOfKendaraan.add(kendaraan);
            }
        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());
        }

        return listOfKendaraan;
    }

    public void insert(Kendaraan kendaraan){
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1, kendaraan.getNoPlat());
            stm.setString(2, kendaraan.getTipe());

            stm.execute();
        }catch (HeadlessException | SQLException e){
            System.out.println("Error : " +e.getMessage());

        }finally{
            try{
                stm.close();
            }catch(SQLException k){
                System.out.println("Error : " +k.getMessage());
            }
        }

    };


}
