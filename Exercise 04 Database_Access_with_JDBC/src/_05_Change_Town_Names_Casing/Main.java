package _05_Change_Town_Names_Casing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    private static final String user = "root";
    private static final String password = "123321";

    public static void main(String[] args) throws SQLException, IOException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",properties);

        System.out.println("Enter country name: ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String countryName = bufferedReader.readLine();

        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE towns\n" +
                        "SET name = UPPER(name)\n" +
                        "WHERE country LIKE ?;");

        preparedStatement.setString(1, countryName);

        int count = preparedStatement.executeUpdate();

        preparedStatement = connection
                .prepareStatement("SELECT name FROM towns\n" +
                        "WHERE country LIKE ?;");

        preparedStatement.setString(1, countryName);

        List<String> allNames = new ArrayList<>();

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            allNames.add(resultSet.getString("name"));
        }

        if (count > 0) {
            System.out.printf("%d town names were affected.\n", allNames.size());
            System.out.println(allNames);
        }
        else {
            System.out.println("No town names were affected");
        }

        System.out.println();

    }
}
