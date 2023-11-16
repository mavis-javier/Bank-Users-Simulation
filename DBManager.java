import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public Subject login(String username, String password) {
        Subject out = null;

        try {
            Statement sql = connection.createStatement();
            ResultSet qResults = sql.executeQuery(String.format("SELECT USERNAME, CLEARANCE FROM USERS" +
                                                                "WHERE USERNAME = \"%s\"" +
                                                                "AND PASSWORD = \"%s\";", username, password));

            if(qResults.last()) {
                out = new Subject(qResults.getString("USERNAME"),
                                  SecLevel.values()[qResults.getInt("CLEARANCE")]);
                if(qResults.getRow() != 1) {
                    System.out.println("Warning: Duplicate users in DB");
                }
            }   //else incorrect login credentials, and out stays null
                
            sql.close();
        }
        catch(SQLException e) {
            System.out.println("Error with DB during login");
        }

        return out;
    }

    public Collection<Option> getOptions() {
        LinkedList<Option> out = new LinkedList<Option>();

        try {
            Statement sql = connection.createStatement();
            ResultSet qResults = sql.executeQuery("SELECT NAME, CLASSIFICATION FROM OPTIONS;");

            do {
                Option temp = new Option();
                temp.setName(qResults.getString("NAME"));
                temp.setClassification(SecLevel.values()[qResults.getInt("CLASSIFICATION")]);

                out.add(temp);
            } while(qResults.next());

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

