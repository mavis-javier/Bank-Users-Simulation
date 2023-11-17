import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

public class DBManager {
    private static short count = 0;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/banksim?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

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
        Statement sql = null;
        String query = String.format("SELECT Username FROM Customer " +
                                     "WHERE Username = \'%s\' " +
                                     "AND Password = \'%s\';", username, password);
        ResultSet qResults = null;

        try {
            sql = connection.createStatement();
            qResults = sql.executeQuery(query);

            if(qResults.next()) {
                out = new Subject(qResults.getString("Username"),
                                    SecLevel.values()[1]);
                if(qResults.getRow() != 1) {
                    System.out.println("Warning: Duplicate users in DB");
                }
            }   //else incorrect login credentials, and out stays null
        }
        catch(SQLException e) {
            System.out.println("Error with DB during login");
        }
        finally {
            try {
                sql.close();    //This implicitly closes qResults if applicable
            }
            catch(NullPointerException n) {}    //sql was never successfully instantiated. Nothing to clean up
            catch(SQLException s) {
                System.out.println("Could not close sql Statement after login attempt. Potential memory leak");
            }
        }

        return out;
    }

    public Collection<Option> getOptions() {
        LinkedList<Option> out = new LinkedList<Option>();

        try {
            Statement sql = connection.createStatement();
            ResultSet qResults = sql.executeQuery("SELECT Name, Classification FROM Options;");

            do {
                Option temp = new Option();
                temp.setName(qResults.getString("Name"));
                temp.setClassification(SecLevel.values()[qResults.getInt("Classification")]);

                out.add(temp);
            } while(qResults.next());

            sql.close();
        }
        catch(SQLException e) {
            System.out.println("Error retrieving Options from DB");
        }

        return out;
    }

    public boolean insertAccount(int Acc_ID, int balance) {
        boolean out = false;

        return out;
    }

    public Account getAccount(int Acc_ID) {
        Account out = null;

        return out;
    }

    public boolean adjustBalance(int Acc_ID, int change) {
        boolean out = false;

        return out;
    }

    private void addAuditRow(int Acc_ID, int change) {

    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

