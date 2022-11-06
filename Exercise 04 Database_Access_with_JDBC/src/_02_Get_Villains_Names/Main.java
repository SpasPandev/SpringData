package _02_Get_Villains_Names;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static final String user = "root";
    private static final String password = "123321";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name, COUNT(DISTINCT mv.minion_id) AS minionCount FROM villains\n" +
                        "JOIN minions_villains mv on villains.id = mv.villain_id\n" +
                        "GROUP BY villain_id\n" +
                        "HAVING minionCount > ?\n" +
                        "ORDER BY minionCount DESC;");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter minions number:");
        Integer minionsNumber = scanner.nextInt();

        preparedStatement.setInt(1, minionsNumber);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next())
        {
            System.out.println(resultSet.getString("name") + " " + resultSet.getString("minionCount"));
        }


    }
}