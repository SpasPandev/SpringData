package _08_Increase_Minions_Age;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class Main {

    private static final String user = "root";
    private static final String password = "123321";

    public static void main(String[] args) throws SQLException, IOException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        System.out.println("Enter minions Ids: ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int[] idsInput = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        Arrays.stream(idsInput).forEach(id -> {
            try {
                PreparedStatement preparedStatement = connection
                        .prepareStatement("UPDATE minions\n" +
                                "SET name = LOWER(name), age = age + 1\n" +
                                "WHERE id IN (?);");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
            catch (SQLException trowables) {
                trowables.printStackTrace();
            }
        });

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name, age FROM minions");

        while (resultSet.next()) {

            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }

    }
}
