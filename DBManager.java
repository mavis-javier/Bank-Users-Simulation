import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
   private static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
   private static final String USER = "username";
   private static final String PASS = "password";

   private Connection connection;

   public DBManager() {
       try {
           connection = DriverManager.getConnection(DB_URL, USER, PASS);
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   public Connection getConnection() {
       return connection;
   }

   public void closeConnection() {
       try {
           connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
}

