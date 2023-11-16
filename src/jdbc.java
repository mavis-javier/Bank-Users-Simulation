// this is a class for database connection

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbc {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bankingsim";     //TODO: change current DB
    private static final String USERNAME = "root";
    private static final String PASSWORD = "310872250";

       


    public static void main(String[] args) {
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; 

        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            System.out.println("Connected to the database!");
            //String sqlQuery = "SELECT * FROM account";
            String sqlQuery = "SELECT * FROM account";
            preparedStatement = connection.prepareStatement(sqlQuery);

            // Execute the query and get the result set
            resultSet = preparedStatement.executeQuery();

            //Check if valid result is retuned
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No data found in the result set.");
            }
            

        // Process the result set
        while (resultSet.next()) {
            // Access columns by name or index, for example:
            int id = resultSet.getInt("Account_ID");
            int balance = resultSet.getInt("Balance");
            // Process other columns as needed
            System.out.println("ID: " + id + ", balance: " + balance);
        }
           

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
}