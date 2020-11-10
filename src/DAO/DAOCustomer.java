package DAO;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Helper.DbConnection;
import Model.*;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DAOCustomer extends DAOUser{

    private final String insertQuery = "INSERT INTO user (nama,alamat, email, password, is_admin, subscription) values (?,?,?,?,?,?)";


    public DAOCustomer(){};

    @Override
    public Customer get(int id){
        Customer user = new Customer();
        PreparedStatement stm = null;
        try{
            stm = CONN.prepareStatement(getQuery);
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();
            try{
                user.setId(res.getInt("id_user"));
                user.setName(res.getString("nama"));
                user.setAddress(res.getString("alamat"));
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("password"));
                user.setAdmin(res.getInt("is_admin"));
                user.setSubscription(res.getString("subscription"));

            }catch (Exception error){
                JOptionPane.showMessageDialog(null,"Email atau password salah!");
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error : " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException sqle){
                System.out.println("Error : "+sqle.getMessage());
            }
        }
        return user;
    }

    public void insert(Customer user){
        PreparedStatement stm = null;
        try {
            Customer c = (Customer) user;
            stm = CONN.prepareStatement(this.insertQuery);
            stm.setString(1, user.getName());
            stm.setString(2,user.getAddress());
            stm.setString(3,user.getEmail());
            stm.setString(4,user.getPassword());
            stm.setInt(5, user.isAdmin());
            stm.setString(6,(user.getSubscription()));
            stm.execute();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error input data: " +e);
        }finally {
            try{
                stm.close();
            }catch(SQLException e){
                System.out.println("Error : " + e.getMessage());
            }
        }
    }

    public void update(Customer c){
        try {
            PreparedStatement ps;

            ps = DbConnection.getConnection().prepareStatement(updateQuery);

            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setInt(3, c.getId());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public Customer login(String email, char[] pass){

        String password = User.md5Spring(String.valueOf(pass));

        Customer user = new Customer();
        PreparedStatement stm = null;
        ResultSet res;

        try{
            stm = CONN.prepareStatement(loginQuery);
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setInt(3, this.isAdmin);

            res = stm.executeQuery();
            System.out.println("Debug : " +stm);
            if (res.next()){
                try{
                    user.setId(res.getInt("id_user"));
                    user.setName(res.getString("nama"));
                    user.setAddress(res.getString("alamat"));
                    user.setEmail(res.getString("email"));
                    user.setPassword(res.getString("password"));
                    user.setAdmin(res.getInt("is_admin"));
                    user.setSubscription(res.getString("subscription"));
                }catch (Exception e){
                    System.out.println("Error "+ e);
                }
            }else{
                System.out.println("Error login");
            }
        }catch(HeadlessException| SQLException e){
            JOptionPane.showMessageDialog(null,"Error : " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException sqle){
                System.out.println("Error : "+sqle.getMessage());
            }
        }
        System.out.println("Debug : " +
                user.getId() + user.getPassword());
        return user;
    }



}
