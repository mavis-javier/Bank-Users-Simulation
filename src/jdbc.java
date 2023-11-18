// this is a class for database connection

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/banksim"; // TODO: change current DB
    private static final String USERNAME = "root";
    private static final String PASSWORD = "310872250";

    static Connection connection = null;

    public static PreparedStatement preparedStatement = null;
    public static PreparedStatement preparedStatement2 = null;
    public static ResultSet resultSet = null;

    public static void main(String[] args) {

        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            System.out.println("Connected to the database!");

            // *************************DB operation in Here*******************************
            // insert(004, 280); //NOTE: Duplicate insetrtion not allowed
            getCheckingBalance("test");
           // writeBalance(2, 0);
            // getBalance(2);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close resources in the finally block
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                    System.out.println("connection closed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void insert(int Acc_ID, int balance) {
        try {
            // SQL query to insert a tuple
            String sqlInsert = "INSERT INTO account (Account_ID, Balance) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsert);

            // Set values for the parameters in the SQL query
            preparedStatement.setInt(1, Acc_ID);
            preparedStatement.setInt(2, balance);

            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " row(s) affected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void getCheckingBalance(String urName) {
        try {

            // SQL query to select BALANCE based on Account_ID
            
            String sqlQuery = "SELECT C.BALANCE FROM CHECKING_ACCOUNT C, USERS U WHERE C.CHECKING_ACCOUNT_ID = U.CHECKING_ACCOUNT_ID AND U.USERNAME = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);

            // Set value for the parameter in the SQL query
            preparedStatement.setString(1, urName);

            // Execute the select statement
            resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int balance = resultSet.getInt("BALANCE");
                System.out.println("Balance for Account_ID " + urName + ": " + balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void writeBalance(int Acc_ID, int Bal) {
        try {
            // SQL query to select BALANCE based on Account_ID
            String sqlQuery =   "UPDATE ACCOUNT SET BALANCE = ? WHERE Account_ID = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);

           // Set values for the parameters in the SQL query
            preparedStatement.setInt(1, Bal);
            preparedStatement.setInt(2, Acc_ID);

           // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " row(s) affected.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}