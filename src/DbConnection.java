import java.sql.*;

public class DbConnection {

        private static final String CONN = "jdbc:mysql://localhost:3306/parking?useTimezone=true&serverTimezone=UTC";
        private static final String UNAME = "root";
        private static final String PASS = "";
        //private static final String CONN = "jdbc:mysql://localhost:3306/parking";


        public static Connection getConnection() throws SQLException {
      //          System.out.println("Connected to database");
                return DriverManager.getConnection(CONN, UNAME, PASS);
        }
}
