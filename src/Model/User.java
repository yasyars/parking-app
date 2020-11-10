package Model;

import Helper.Validasi;
import ViewController.Login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class User {
    private int id;
    private String name;
    private String address;
    private String email;
    private String password;
    private int isAdmin;


    public User(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws  Exception {
        if (Validasi.isEmpty(name)){
            throw new Exception("Nama user tidak boleh kosong!");
        }else{
            this.name = name;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String hashedPassword){
        this.password = hashedPassword;

    }

    public void setPassword(char[] rawPassword) {

        String pass = md5Spring(String.valueOf(rawPassword));
        this.password = pass;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }

    public static String md5Spring(String text) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(text.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b: hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }

    public static boolean isPasswordValid(String password){
        boolean isValid = true;
        String validasiLabel ="";
        String passLabel ="";
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";

        if (password.length() < 6 || !password.matches(upperCaseChars) || !password.matches(lowerCaseChars) || !password.matches(numbers)){
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

    public static boolean isEmailValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (pat.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }

}
