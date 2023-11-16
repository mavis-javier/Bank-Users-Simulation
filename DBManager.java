import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class DBManager {
    private static short count = 0;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
    private static final String USER = "username";
    private static final String PASS = "password";

    private Connection connection;

    public DBManager() {
        if(count != 0) {
            System.out.println("Blocked attempt to connect DBManager #" + count+1);
            return;
        }

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Option> getOptions() {
        LinkedList<Option> out = new LinkedList<Option>();

        try {
            Statement sql = connection.createStatement();
            ResultSet qResults = sql.executeQuery("SELECT * FROM OPTIONS");

            while(qResults.next()) {
                Option temp = new Option();
                temp.setName(qResults.getString("Name"));
                temp.setClassification(SecLevel.values()[qResults.getInt("classification")]);

                out.add(temp);
            }

            sql.close();
        }
        catch(SQLException e) {
            System.out.println("Error retrieving Options from DB");
        }

        return out;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

