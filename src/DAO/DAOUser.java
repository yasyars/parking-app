package DAO;

import Helper.DbConnection;
import Model.*;
import ViewController.Login;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DAOUser {
    Connection CONN = DbConnection.getConnection();

    protected final String loginQuery = "SELECT * FROM parking.user WHERE email = ? AND password =? AND is_admin=?";
    protected final String insertQuery = "INSERT INTO user (nama,alamat, email, password, is_admin) values (?,?,?,?,?)";
    protected final String updateQuery = "UPDATE user SET nama = ?, alamat = ? WHERE id_user = ?";
    protected final String getQuery = "SELECT * FROM parking.user WHERE id_user = ?";
    protected final static String getUserByEmail = "SELECT * FROM parking.user WHERE email = ?";
    protected int isAdmin;

    public DAOUser(){}

    public void setIsAdmin(int admin){
        this.isAdmin = admin;
    }
    public int getIsAdmin(){
        return isAdmin;
    }

    public User get(int id) throws Exception{
        User user = null;
        PreparedStatement stm = null;
        ResultSet res = null;
        try{
            stm = CONN.prepareStatement(getQuery);
            stm.setInt(1, id);
            res = stm.executeQuery();
            try{
                user.setId(res.getInt("id_user"));
                user.setName(res.getString("nama"));
                user.setAddress(res.getString("alamat"));
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("password"));
                user.setAdmin(res.getInt("is_admin"));

            }catch (Exception error){
            }
        }catch(HeadlessException| SQLException e){
            throw new Exception("Error : " + e.getMessage());
        }finally{
            try { res.close(); } catch (Exception ex) { /* ignored */ }
            try { stm.close(); } catch (Exception ex) { /* ignored */ }
            try { CONN.close(); } catch (Exception ex) { /* ignored */ }

        }
        return user;
    }

    public void insert(User user) throws Exception{
        PreparedStatement stm = null;
        try {
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1, user.getName());
            stm.setString(2,user.getAddress());
            stm.setString(3,user.getEmail());
            stm.setString(4,user.getPassword());
            stm.setInt(5, user.isAdmin());
            stm.execute();
        }catch (SQLException e){
			throw new Exception("Error : " + e.getMessage());
        }finally {
            try{
                stm.close();
            }catch(SQLException e){
                System.out.println("Error : " + e.getMessage());
            }
        }
    }


    public User login(String email, char[] pass) throws Exception{

        String password = User.md5Spring(String.valueOf(pass));

        User user = new User();
        PreparedStatement stm = null;
        ResultSet res;

        try{
            stm = CONN.prepareStatement(loginQuery);
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setInt(3, this.isAdmin);

            res = stm.executeQuery();
            //System.out.println("Debug : " +stm);
            if (res.next()){
                try{
                    user.setId(res.getInt("id_user"));
                    user.setName(res.getString("nama"));
                    user.setAddress(res.getString("alamat"));
                    user.setEmail(res.getString("email"));
                    user.setPassword(res.getString("password"));
                    user.setAdmin(res.getInt("is_admin"));
                }catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }else{
                System.out.println("Error login");
            }
        }catch(HeadlessException| SQLException e){
			throw new Exception("Error : " + e.getMessage());
        }finally{
            try{
                stm.close();
            }catch(SQLException sqle){
                //System.out.println("Error : "+sqle.getMessage());
            }
        }
//        System.out.println("Debug : " +
//        user.getId() + user.getPassword());
        return user;
    }

    public static boolean isEmailUsed(String email){
        PreparedStatement ps;
        ResultSet rs;
        boolean mailUsed = false;

        try{
            ps = DbConnection.getConnection().prepareStatement(getUserByEmail);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()){
                mailUsed = true;
            }

        } catch (SQLException sqle){
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return mailUsed;
    }

}
