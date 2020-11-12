package DAO;


import Helper.DbConnection;

import java.awt.*;
import java.sql.*;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public class DAOGarage {

    Connection CONN =(Connection) DbConnection.getConnection();

    private final String insertQuery = "INSERT INTO garage (nama_garage,hari_operasional,waktu_buka, waktu_tutup, id_area,tarif_motor,tarif_mobil) values (?,?,?,?,?,?,?)";
    private final String readQuery = "SELECT * FROM garage";
    private final String updateQuery = "UPDATE garage SET nama_garage = ? , hari_operasional = ?, waktu_buka = ?, waktu_tutup = ?, id_area =?  ,tarif_motor=?, tarif_mobil=? WHERE id_garage = ?";
    private final String deleteQuery = "DELETE FROM garage WHERE id_garage = ?";
    private final String getByIdQuery = "SELECT * FROM garage WHERE id_garage = ?";

    public Garage getById(int id){
        Garage garage = new Garage();
        PreparedStatement stm = null;
        try{

            stm = CONN.prepareStatement(getByIdQuery);
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();
            res.next();

            try{
                garage.setId(res.getInt("id_garage"));
                garage.setName(res.getString("nama_garage"));
                OperationalTime time = new OperationalTime();
                time.setDay(res.getString("hari_operasional"));
                time.setOpenHour(res.getString("waktu_buka"));
                time.setCloseHour(res.getString("waktu_tutup"));
                garage.setOperationalTime(time);
                garage.setArea(Integer.parseInt(res.getString("id_area")));
                garage.setTarifMotor(res.getDouble("tarif_motor"));
                garage.setTarifMobil(res.getDouble("tarif_mobil"));

            }catch (Exception error){
                System.out.println("Error : " +error.getMessage());
            }
        }catch(HeadlessException| SQLException e){
            System.out.println("Error : " +e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException sqle){
                System.out.println("Error : "+sqle.getMessage());
            }
        }

        return garage;
    }



    public List<Garage> getAll(){
        List<Garage> lg = null;
        try{
            lg = new ArrayList<Garage>();

            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                Garage garage = new Garage();
                garage.setId(res.getInt(1));
                try{
                    garage.setName(res.getString(2));
                    OperationalTime time = new OperationalTime();
                    time.setDay(res.getString("hari_operasional"));
                    time.setOpenHour(res.getString("waktu_buka"));
                    time.setCloseHour(res.getString("waktu_tutup"));
                    garage.setOperationalTime(time);
                    garage.setArea(Integer.parseInt(res.getString("id_area")));
                    garage.setTarifMotor(res.getDouble("tarif_motor"));
                    garage.setTarifMobil(res.getDouble("tarif_mobil"));
                }catch (Exception error){
                    System.out.println("Error : " +error.getMessage());
                }
                lg.add(garage);
            }
        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());
        }

        return lg;
    };

    public void insert(Garage garage){
        PreparedStatement stm  = null;
        try{
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1,garage.getName());
            stm.setString(2, garage.getOperationalTime().getDay());
            stm.setString(3, garage.getOperationalTime().getOpenHour());
            stm.setString(4, garage.getOperationalTime().getCloseHour());
            stm.setInt(5, garage.getArea());
            stm.setDouble(6,garage.getTarifMotor());
            stm.setDouble(7,garage.getTarifMobil());
            stm.execute();
        }catch(HeadlessException | SQLException e){
            System.out.println("Error : " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException k){
                System.out.println("Error : " +k.getMessage());
            }
        }
    };

    public void update(Garage garage){
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(updateQuery);
            stm.setString(1, garage.getName());
            stm.setString(2, garage.getOperationalTime().getDay());
            stm.setString(3,garage.getOperationalTime().getOpenHour());
            stm.setString(4,garage.getOperationalTime().getCloseHour());
            stm.setInt(5, garage.getArea());
            stm.setDouble(6,garage.getTarifMotor());
            stm.setDouble(7,garage.getTarifMobil());
            stm.setInt(8, garage.getId());

            stm.execute();

        }catch (HeadlessException | SQLException e){
            System.out.println("Error : " + e.getMessage());
        }finally {
            try {
                stm.close();
            } catch (SQLException k) {
                System.out.println("Error : " + k.getMessage());
            }
        }
    };
    public void delete(int id){
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(deleteQuery);
            stm.setInt(1, id);
            stm.execute();

        }catch (HeadlessException | SQLException e){
            System.out.println("Error : " +e.getMessage());
        }finally {
            try{
                stm.close();
            }catch (SQLException k){
                System.out.println("Error : " + k.getMessage());
            }
        }
    };


}
