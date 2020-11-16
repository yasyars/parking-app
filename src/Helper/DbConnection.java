package Helper;

import java.sql.*;

public class DbConnection {
    static Connection con;
    private static final String CONN = "jdbc:mysql://localhost:3306/parking?useTimezone=true&serverTimezone=UTC";
    private static final String UNAME = "root";
    private static final String PASS = "admin";

    public static Connection getConnection(){
        if (con == null){
            try{
                con = DriverManager.getConnection(CONN, UNAME,PASS);
                System.out.println("Database successfully connected");
            }catch(SQLException e){
                System.out.println("Fail to connect to database" + e);
            }
        }
        return con;
    }
}
