// this is a class for database connection

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

              // TODO: get switch()  for options?
          

            // *************************DB operation in Here*******************************
            // insert(004, 280); //NOTE: Duplicate insetrtion not allowed
           
        
            getCheckingBalance("test");
            getSavingBalance("usr2");
            getCheckingBalance("usr2");
            
            writeCKBalance("usr2", 20);
            writeSVBalance("usr2", -50);
           // writeSVBalance("usr2", 40);
            System.out.println("After new balance written");
            getCheckingBalance("usr2");
            getSavingBalance("usr2");
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

    public static Subject login(String username, String password) {
        Subject out = null;

        try {
            Statement sql = connection.createStatement();
            ResultSet qResults = sql.executeQuery(String.format("SELECT Username, Clearance FROM Users" +
                                                                "WHERE Username = \'%s\'" +
                                                                "AND Password = \'%s\';", username, password));

            if(qResults.next()) {
                out = new Subject(qResults.getString("Username"),
                                  SecLevel.values()[qResults.getInt("Clearance")]);
            }   //else incorrect login credentials, and out stays null

            sql.close();
        }
        catch(SQLException e) {
            System.out.println("Error with DB during login");
        }

        return out;
    }


    public static void insert(int Acc_ID, int balance) {
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

    public static void getCheckingBalance(String urName) {
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
                System.out.println( "User "+ urName + "----- " + " Checking Account Balance : "+ balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public static void getSavingBalance(String urName) {
        try {

            // SQL query to select BALANCE based on Account_ID
            
            String sqlQuery = "SELECT S.BALANCE FROM SAVINGS_ACCOUNT S, USERS U WHERE S.SAVINGS_ACCOUNT_ID = U.SAVINGS_ACCOUNT_ID AND U.USERNAME = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);

            // Set value for the parameter in the SQL query
            preparedStatement.setString(1, urName);

            // Execute the select statement
            resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int balance = resultSet.getInt("BALANCE");
                System.out.println( "User "+ urName + "----- " + " Saving Account Balance : "+ balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void writeCKBalance(String urName, int amount) {
        try {
            // SQL query to update balance in Checking_Account based on Username
            String updateQuery = "UPDATE Checking_Account C " +
                                 "JOIN USERS U ON C.CHECKING_ACCOUNT_ID = U.CHECKING_ACCOUNT_ID " +
                                 "SET C.Balance = C.Balance + ? " +
                                 "WHERE U.Username = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
    
            // Set values for the parameters in the SQL query
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, urName);
    
            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();
    
            System.out.println(rowsAffected + " row(s) affected.");
    
            // Execute the select statement to get the updated balance
            String selectQuery = "SELECT C.Balance FROM Checking_Account C " +
                                 "JOIN USERS U ON C.CHECKING_ACCOUNT_ID = U.CHECKING_ACCOUNT_ID " +
                                 "WHERE U.Username = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, urName);
    
            // Execute the select statement
            resultSet = preparedStatement.executeQuery();
    
            // Process the result set
            while (resultSet.next()) {
                int newBalance = resultSet.getInt("Balance");
                System.out.println("User " + urName + " - New Checking Account Balance: " + newBalance);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void writeSVBalance(String urName, int amount) {
        try {
            // SQL query to update balance in Savings_Account based on Username
            String updateQuery = "UPDATE Savings_Account S " +
                                 "JOIN Users U ON S.Savings_Account_ID = U.Savings_Account_ID " +
                                 "SET S.Balance = S.Balance + ? " +
                                 "WHERE U.Username = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
    
            // Set values for the parameters in the SQL update query
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, urName);
    
            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();
    
            System.out.println(rowsAffected + " row(s) affected.");
    
            // Execute the select statement to get the updated balance
            String selectQuery = "SELECT S.Balance FROM Savings_Account S " +
                                 "JOIN Users U ON S.Savings_Account_ID = U.Savings_Account_ID " +
                                 "WHERE U.Username = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, urName);
    
            // Execute the select statement
            resultSet = preparedStatement.executeQuery();
    
            // Process the result set
            while (resultSet.next()) {
                int newBalance = resultSet.getInt("Balance");
                System.out.println("User " + urName + " - New Saving Account Balance: " + newBalance);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
