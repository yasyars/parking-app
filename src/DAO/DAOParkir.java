package DAO;

import Helper.DbConnection;
import Model.*;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOParkir {
    Connection CONN =DbConnection.getConnection();
    private final String readQuery = "SELECT * FROM parkir";
    private final String getByUserQuery = "SELECT * FROM parkir WHERE id_user = ?";
    private final String parkQuery = "INSERT INTO parkir (id_user,id_area,id_garage, plat_no, waktu_masuk) values (?,?,?,?,?)";
    private final String unparkQuery = "DELETE FROM parkir WHERE id_user = ?";

    public DAOParkir(){};

    public void park(Parkir parkir){
        PreparedStatement stm = null;
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        try{
            stm = CONN.prepareStatement(parkQuery);
            stm.setInt(1,parkir.getUser().getId());
            stm.setInt(2,parkir.getArea().getId());
            stm.setInt(3,parkir.getGarage().getId());
            stm.setString(4,parkir.getKendaraan().getNoPlat());
            stm.setString(5,parkir.getStartTime());
            stm.execute();
            parkir.getKendaraan().setIsParked(1);
            try{
                daoKendaraan.setIsParked(parkir.getKendaraan());
            }catch (Exception e){
                System.out.println("Error DAOParkir 1: "+ e);
            }
        }catch(HeadlessException | SQLException e){
            System.out.println("Error DAOParkir 2: " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException k){
                System.out.println("Error DAOParkir 3: " +k.getMessage());
            }
        }
    }

    public void unpark(Parkir parkir){
        PreparedStatement stm = null;
        DAOKendaraan daoKendaraan = new DAOKendaraan();
        try{
            stm = CONN.prepareStatement(unparkQuery);
            stm.setInt(1,parkir.getUser().getId());
            stm.execute();
            parkir.getKendaraan().setIsParked(0);
            try{
                daoKendaraan.setIsParked(parkir.getKendaraan());
            }catch (Exception e){
                System.out.println("Error DAOParkir 4: "+ e);
            }
        }catch(HeadlessException | SQLException e){
            System.out.println("Error DAOParkir 5: " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException k){
                System.out.println("Error DAOParkir 6: " +k.getMessage());
            }
        }
    }

    public Parkir getByUser(User user) {

        Parkir parkir = null;
        PreparedStatement stm = null;
        try{

            stm = CONN.prepareStatement(getByUserQuery);
            stm.setInt(1, user.getId());
            ResultSet res = stm.executeQuery();

            if (res.next()){
                try{
                    parkir = new Parkir();
                    DAOGarage daoGarage = new DAOGarage();
                    DAOKendaraan daoKendaraan = new DAOKendaraan();
                    DAOCustomer daoCustomer = new DAOCustomer();
                    DAOArea daoArea = new DAOArea();

                    parkir.setId(res.getInt("id_parkir"));
                    parkir.setUser(daoCustomer.getById(res.getInt("id_user")));
                    parkir.setArea(daoArea.getById(res.getInt("id_area")));
                    parkir.setGarage(daoGarage.getById(res.getInt("id_garage")));
                    parkir.setKendaraan(daoKendaraan.getByPlat(res.getString("plat_no")));
                    parkir.setStartTime(res.getString("waktu_masuk"));
                }catch (Exception error){
                    System.out.println("Error DAOParkir 7: " +error.getMessage());
                }
            }

        }catch(HeadlessException | SQLException e){
            System.out.println("Error DAOParkir 8: " +e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException sqle){
                System.out.println("Error DAOParkir 9: "+sqle.getMessage());
            }
        }

        return parkir;


    }

}
