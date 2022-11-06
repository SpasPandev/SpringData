package _06_Remove_Villain;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static final String user = "root";
    private static final String password = "123321";

    public static void main(String[] args) throws SQLException, IOException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db",properties);

        System.out.println("Enter villain's id: ");

        Scanner scanner = new Scanner(System.in);
        int villainID = scanner.nextInt();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name FROM villains\n" +
                "WHERE id = ?;");
        preparedStatement.setInt(1, villainID);

        ResultSet resultSet = preparedStatement.executeQuery();

        String villainName;

        if (resultSet.next()) {

            villainName = resultSet.getString("name");

            preparedStatement = connection
                .prepareStatement("SELECT COUNT(minion_id) AS 'minionsCount' FROM minions_villains\n" +
                "WHERE villain_id = ?;");

            preparedStatement.setInt(1, villainID);

            ResultSet newResSet = preparedStatement.executeQuery();
            newResSet.next();

            int minionsCount = newResSet.getInt("minionsCount");

            preparedStatement = connection
                    .prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?;");
            preparedStatement.setInt(1, villainID);
            preparedStatement.execute();

            System.out.printf("%s was deleted\n", villainName);
            System.out.printf("%d minions released\n", minionsCount);

            preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ?;");
            preparedStatement.setInt(1, villainID);
            preparedStatement.execute();
        }
        else {
            System.out.println("No such villain was found");
        }

        System.out.println();

    }
}