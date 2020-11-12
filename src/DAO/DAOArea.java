package DAO;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import Helper.*;

import javax.swing.*;

public class DAOArea {
    Connection CONN =(Connection) DbConnection.getConnection();

    private final String insertQuery = "INSERT INTO area_parkir (nama_area) values (?)";
    private final String readQuery = "SELECT * FROM area_parkir";
    private final String updateQuery = "UPDATE area_parkir SET nama_area = ? WHERE id_area = ?";
    private final String deleteQuery = "DELETE FROM area_parkir WHERE id_area = ?";
    private final String getByIdQuery = "SELECT * FROM area_parkir WHERE id_area = ?";


    public DAOArea(){};

    public Area getById(int idArea) throws Exception{
        Area area = new Area();
        PreparedStatement stm = null;
        try{

            stm = CONN.prepareStatement(getByIdQuery);
            stm.setInt(1, idArea);
            ResultSet res = stm.executeQuery();
            res.next();

            try{
                area.setId(res.getInt("id_area"));
                area.setName(res.getString("nama_area"));

            }catch (Exception error){
                throw new Exception("Error : " + error.getMessage());
            }
        }catch(HeadlessException| SQLException e){
            throw new Exception("Error : " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException sqle){
                System.out.println("Error : "+sqle.getMessage());
            }
        }

        return area;
    }
    public List<Area> getAll() throws Exception{
        List<Area> listOfArea = null;
        try{
            listOfArea = new ArrayList<Area>();

            Statement stm = CONN.createStatement();
            ResultSet res = stm.executeQuery(readQuery);

            while (res.next()){
                Area area = new Area();
                area.setId(res.getInt(1));
                try{
                    area.setName(res.getString(2));
                }catch (Exception error){
                    throw new Exception("Error: "+ error.getMessage());
                }
                listOfArea.add(area);
            }
        }catch (SQLException e){
            throw new Exception("Error: "+ e.getMessage());
        }

        return listOfArea;
    }

    public void insert(Area area) throws Exception{
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1, area.getName());
            stm.execute();
        }catch (HeadlessException | SQLException e){
            throw new Exception("Error: "+ e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException k){
                System.out.println("Error : " +k.getMessage());
            }
        }

    };
    public void update (Area area) throws Exception{
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(updateQuery);
            stm.setString(1, area.getName());
            stm.setInt(2, area.getId());
            stm.execute();
        }catch (HeadlessException | SQLException e){
            throw new Exception("Error: "+ e.getMessage());
        }finally {
            try {
                stm.close();
            } catch (SQLException k) {
                System.out.println("Error : " + k.getMessage());
            }
        }
    };

    public void delete(int id) throws Exception{
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(deleteQuery);
            stm.setInt(1, id);
            stm.execute();
        }catch (HeadlessException | SQLException e){
            throw new Exception("Error: "+ e.getMessage());

        }finally {
            try {
                stm.close();
            } catch (SQLException k) {
                System.out.println("Error : " + k.getMessage());
            }
        }
    };
}
