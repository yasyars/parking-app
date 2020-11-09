package DAO;

import Helper.DbConnection;
import Model.*;
import ViewController.Login;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private final String loginQuery = "SELECT * FROM parking.user WHERE email = ? AND password =? AND is_admin=?";
    private final String insertQuery = "INSERT INTO user (nama,alamat, email, password, is_admin, subscription) values (?,?,?,?,?,?)";
    private final String updateQuery = "UPDATE user SET nama = ?, alamat = ? WHERE id_user = ?";
    private final String getQuery = "SELECT * FROM parking.user WHERE id_user = ?";
    private final static String getUserByEmail = "SELECT * FROM parking.user WHERE email = ?";
    private int isAdmin;

    public DAOUser(){}

    public void setIsAdmin(int admin){
        this.isAdmin = admin;
    }
    public int getIsAdmin(){
        return isAdmin;
    }

    public User get(int id){
        User user = null;
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
            }catch (Exception error){
                JOptionPane.showMessageDialog(null,"Email atau password salah!");
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

        return user;
    }

    public void insert(User user){
        PreparedStatement stm = null;
        try {
            Customer c = (Customer) user;
            stm = CONN.prepareStatement(insertQuery);
            stm.setString(1, c.getName());
            stm.setString(2,c.getAddress());
            stm.setString(3,c.getEmail());
            stm.setString(4,c.getPassword());
            stm.setInt(5, c.isAdmin());
            stm.setString(6, c.getSubscription());
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

    public void update(User user){
        try {
            PreparedStatement ps;
            Customer c = (Customer) user;

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

    public User login(String email, char[] pass){

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
            System.out.println("Debug : " +stm);
            if (res.next()){
                try{
                    user.setId(res.getInt("id_user"));
                    user.setName(res.getString("nama"));
                    user.setAddress(res.getString("alamat"));
                    user.setEmail(res.getString("email"));
                    user.setPassword(res.getString("password"));
                    user.setAdmin(res.getInt("is_admin"));
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
