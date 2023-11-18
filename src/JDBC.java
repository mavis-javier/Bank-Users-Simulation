// this is a class for database connection

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

    // jdbc URL, username, and password of MySQL server
    private static final String jdbc_URL = "jdbc:mysql://localhost:3306/banksim"; // TODO: change current DB
    private static final String USERNAME = "root";
    private static final String PASSWORD = "310872250";

    static Connection connection = null;

    public static PreparedStatement preparedStatement = null;
    public static PreparedStatement preparedStatementUpdate = null;
    public static PreparedStatement preparedStatementInsert = null;
    public static ResultSet resultSet = null;
    public static ResultSet resultSet2 = null;
    public static int tranSnumber; // for logging on receipt
    public static int pre_Bal, new_Bal;
    public static String account_type;

    public static void main(String[] args) {

        try {
            // Register the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(jdbc_URL, USERNAME, PASSWORD);

            System.out.println("Connected to the database!");

            // TODO: get switch() for options?

            // *************************DB operation in Here*******************************
            // insert(004, 280); //NOTE: Duplicate insetrtion not allowed

            // getCheckingBalance("test");
            getSavingBalance("usr2");
            getCheckingBalance("usr2");

            writeCKBalance("usr2", 20);
            writeCKBalance("usr2", 60);
            writeCKBalance("usr4", 10);
            // writeSVBalance("usr2", 100);
            // getALLReceipt();
            getUserReceipt("usr4");
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

    public static void connect() {
        try {
            // Register the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(jdbc_URL, USERNAME, PASSWORD);

            System.out.println("Connected to the database!");
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Subject login(String username, String password) {
        Subject out = null;

        try {
            Statement sql = connection.createStatement();
            ResultSet qResults = sql.executeQuery(String.format("SELECT USERNAME, CLEARANCE FROM USERS" +
                                                                "WHERE USERNAME = \'%s\'" +
                                                                "AND PASSWORD = \'%s\';", username, password));

            if(qResults.last()) {
                out = new Subject(qResults.getString("USERNAME"),
                                  SecLevel.values()[qResults.getInt("CLEARANCE")]);
            }   //else incorrect login credentials, and out stays null

            sql.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return out;
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
                System.out.println("User " + urName + "----- " + " Checking Account Balance : " + balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void getSavingBalance(String urName) {
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
                System.out.println("User " + urName + "----- " + " Saving Account Balance : " + balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void writeCKBalance(String urName, int amount) {
        try {

            account_type = "Saving";
            tranSnumber++; // increment transaction number

            // get old balance
            // Execute the select statement to get the updated balance
            String selectQuery1 = "SELECT C.Balance FROM Checking_Account C " +
                    "JOIN Users U ON C.Checking_Account_ID = U.Checking_Account_ID " +
                    "WHERE U.Username = ?";
            preparedStatement = connection.prepareStatement(selectQuery1);
            preparedStatement.setString(1, urName);

            // Execute the select statement
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pre_Bal = resultSet.getInt("Balance");
            }

            /****************************** */

            // Execute the update statement
            /************ */
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

            // Calculate new balance if needed
            new_Bal = pre_Bal + amount;

            String RCquery = "INSERT INTO RECEIPT (Transaction_Number, Username, Previous_Balance, Transaction_Amount, New_Balance,Account_Type) "
                    +
                    "VALUES (?, ?, ?, ?, ?,?)";
            preparedStatementInsert = connection.prepareStatement(RCquery);
            preparedStatementInsert.setInt(1, tranSnumber);
            preparedStatementInsert.setString(2, urName);
            preparedStatementInsert.setInt(3, pre_Bal);
            preparedStatementInsert.setInt(4, amount);
            preparedStatementInsert.setInt(5, new_Bal);
            preparedStatementInsert.setString(6, account_type);

            // Execute the insert statement
            int rowsInserted = preparedStatementInsert.executeUpdate();

            System.out.println("Update: " + rowsAffected + " row(s) affected.");
            System.out.println("Insert: " + rowsInserted + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void writeSVBalance(String urName, int amount) {
        try {
            account_type = "Saving";
            tranSnumber++; // increment transaction number

            // get old balance
            // Execute the select statement to get the updated balance
            String selectQuery1 = "SELECT S.Balance FROM Savings_Account S " +
                    "JOIN Users U ON S.Savings_Account_ID = U.Savings_Account_ID " +
                    "WHERE U.Username = ?";
            preparedStatement = connection.prepareStatement(selectQuery1);
            preparedStatement.setString(1, urName);

            // Execute the select statement
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pre_Bal = resultSet.getInt("Balance");
            }

            /********************** */

            // SQL query to update balance in Savings_Account based on Username
            String updateQuery = "UPDATE Savings_Account S " +
                    "JOIN Users U ON S.Savings_Account_ID = U.Savings_Account_ID " +
                    "SET S.Balance = S.Balance + ? " +
                    "WHERE U.Username = ?";
            preparedStatementUpdate = connection.prepareStatement(updateQuery);
            preparedStatementUpdate.setInt(1, amount);
            preparedStatementUpdate.setString(2, urName);

            // Execute the update statement
            int rowsAffected = preparedStatementUpdate.executeUpdate();

            // Calculate new balance if needed
            new_Bal = pre_Bal + amount;

            // create receipt
            String RCquery = "INSERT INTO RECEIPT (Transaction_Number, Username, Previous_Balance, Transaction_Amount, New_Balance,Account_Type) "
                    +
                    "VALUES (?, ?, ?, ?, ?,?)";
            preparedStatementInsert = connection.prepareStatement(RCquery);
            preparedStatementInsert.setInt(1, tranSnumber);
            preparedStatementInsert.setString(2, urName);
            preparedStatementInsert.setInt(3, pre_Bal);
            preparedStatementInsert.setInt(4, amount);
            preparedStatementInsert.setInt(5, new_Bal);
            preparedStatementInsert.setString(6, account_type);

            // Execute the insert statement
            int rowsInserted = preparedStatementInsert.executeUpdate();

            System.out.println("Update: " + rowsAffected + " row(s) affected.");
            System.out.println("Insert: " + rowsInserted + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void getALLReceipt() {
        try {
            String selectQuery = "SELECT * FROM RECEIPT";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int transactionNumber = resultSet.getInt("Transaction_Number");
                String username = resultSet.getString("Username");
                int previousBalance = resultSet.getInt("Previous_Balance");
                int transactionAmount = resultSet.getInt("Transaction_Amount");
                int newBalance = resultSet.getInt("New_Balance");
                String accountType = resultSet.getString("Account_Type");

                
                System.out.println("******Receipt ALL users ******** ");
                System.out.println("Transaction Number: " + transactionNumber);
                System.out.println("Username: " + username);
                System.out.println("Previous Balance: " + previousBalance);
                System.out.println("Transaction Amount: " + transactionAmount);
                System.out.println("New Balance: " + newBalance);
                System.out.println("Account Type: " + accountType);
                System.out.println("----------------------------------");
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void getUserReceipt(String urname) {
        try {

            String selectQuery = "SELECT * FROM RECEIPT WHERE Username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, urname);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and print the values
            while (resultSet.next()) {
                int transactionNumber = resultSet.getInt("Transaction_Number");
                int previousBalance = resultSet.getInt("Previous_Balance");
                int transactionAmount = resultSet.getInt("Transaction_Amount");
                int newBalance = resultSet.getInt("New_Balance");
                String accountType = resultSet.getString("Account_Type");

                System.out.println("******Receipt for user******** "+urname);
                System.out.println("Transaction Number: " + transactionNumber);
                System.out.println("Username: " + urname);
                System.out.println("Previous Balance: " + previousBalance);
                System.out.println("Transaction Amount: " + transactionAmount);
                System.out.println("New Balance: " + newBalance);
                System.out.println("Account Type: " + accountType);
                System.out.println("----------------------------------");
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}