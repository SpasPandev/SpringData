package _09_Increase_Age_Stored_Procedure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Main {

    private static final String user = "root";
    private static final String password = "123321";

    public static void main(String[] args) throws SQLException, IOException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        System.out.println("Enter minion id: ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int minionId = Integer.parseInt(bufferedReader.readLine());

        CallableStatement callableStatement = connection.prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.executeUpdate();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age FROM minions WHERE id = ?");
        preparedStatement.setInt(1, minionId);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));

    }
}
