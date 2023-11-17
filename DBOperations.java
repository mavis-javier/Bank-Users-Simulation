import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBOperations {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

    
    }

    public static void insert(){


        // SQL query to insert a tuple
            String sqlInsert = "INSERT INTO account (Account_ID, Balance) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsert);

            // Set values for the parameters in the SQL query
            preparedStatement.setInt(1, accountID);
            preparedStatement.setInt(2, balance);

            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " row(s) affected.");
    }

}


