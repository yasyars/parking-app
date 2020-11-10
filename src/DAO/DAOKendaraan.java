package DAO;

import Helper.DbConnection;
import java.awt.*;
import java.sql.*;
import Model.*;
import java.util.ArrayList;
import java.util.List;

public class DAOKendaraan {
    Connection CONN =(Connection) DbConnection.getConnection();

    private final String insertQuery = "INSERT INTO kendaraan (plat_no,tipe_kendaraan,id_user) values (?,?,?)";
    private final String readQuery = "SELECT * FROM kendaraan";
    private final String updateQuery = "UPDATE kendaraan SET  tipe_kendaraan = ?, id_user = ? WHERE plat_no = ?";
    private final String deleteQuery = "DELETE FROM kendaraan WHERE plat_no = ?";
//    private final String

    public List<Kendaraan> getAll(){
        List<Kendaraan> lk = null;
        try{
            lk = new ArrayList<Kendaraan>();

            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                Kendaraan kendaraan = new Kendaraan();

                try{
                    kendaraan.setNoPlat(res.getString(1));
                    kendaraan.setTipe(res.getString(2));
                    DAOCustomer daoUser = new DAOCustomer();
                    kendaraan.setOwner(daoUser.get(res.getInt(3)));

                }catch (Exception error){
                    System.out.println("Error : " +error.getMessage());
                }
                lk.add(kendaraan);
            }
        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());
        }

        return lk;
    };

    public void insert(Kendaraan kendaraan){
        PreparedStatement stm  = null;
        try{
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1,kendaraan.getNoPlat());
            stm.setString(2,kendaraan.getTipe());
            stm.setInt(3,kendaraan.getOwner().getId());
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

    public void update(Kendaraan kendaraan){
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1,kendaraan.getNoPlat());
            stm.setString(2,kendaraan.getTipe());
            stm.setInt(3,kendaraan.getOwner().getId());
            stm.execute();;

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

    public void delete(int plat_no){
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(deleteQuery);
            stm.setInt(1, plat_no);
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
    }

//    public List<Kendaraan> getByUser(Customer user){
//        List<Kendaraan> lg = new ArrayList<>();
//        try{
//            Statement stm = CONN.createStatement();
//            ResultSet res = stm.executeQuery(readQuery);
//
//            while (res.next()){
//                Garage garage = new Garage();
//                garage.setId(res.getInt(1));
//                try{
//                    garage.setName(res.getString(2));
//                    OperationalTime time = new OperationalTime();
//                    time.setDay(res.getString("hari_operasional"));
//                    time.setOpenHour(res.getString("waktu_buka"));
//                    time.setCloseHour(res.getString("waktu_tutup"));
//                    garage.setOperationalTime(time);
//                    garage.setArea(Integer.parseInt(res.getString("id_area")));
//                    garage.setTarifMotor(res.getDouble("tarif_motor"));
//                    garage.setTarifMobil(res.getDouble("tarif_mobil"));
//                }catch (Exception error){
//                    System.out.println("Error : " +error.getMessage());
//                }
//                lg.add(garage);
//            }
//        }catch (SQLException e){
//            System.out.println("Error : " +e.getMessage());
//        }
//
//        return lg;
//    }
}
