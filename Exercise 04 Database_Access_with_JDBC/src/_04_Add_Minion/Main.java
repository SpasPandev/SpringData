package _04_Add_Minion;

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

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db",properties);

        System.out.println("Enter minion's name age and town: ");

        BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
        String[] minionInput = reader.readLine().split(" ");

        String minionName = minionInput[0];
        Integer minionAge = Integer.parseInt(minionInput[1]);
        String minionTown = minionInput[2];

        System.out.println("Enter villain name: ");
        String villainName = reader.readLine();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name FROM towns WHERE name LIKE ?");

        preparedStatement.setString(1, minionTown);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {

            preparedStatement = connection
                    .prepareStatement("INSERT INTO towns (name) VALUES (?);");

            preparedStatement.setString(1, minionTown);

            preparedStatement.execute();

            System.out.printf("Town %s was added to the database.\n", minionTown);
        }

        preparedStatement = connection
                .prepareStatement("SELECT name FROM villains WHERE name LIKE ?");

        preparedStatement.setString(1, villainName);

        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {

            preparedStatement = connection
                    .prepareStatement("INSERT INTO villains (name, evilness_factor) VALUES (?, 'evil');");

            preparedStatement.setString(1, villainName);

            preparedStatement.execute();

            System.out.printf("Villain %s was added to the database.\n", villainName);
        }

        preparedStatement = connection
                .prepareStatement("INSERT INTO minions (name, age, town_id) VALUES (?, ?, (SELECT id FROM towns WHERE towns.name like ?));");

        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setString(3, minionTown);
        preparedStatement.execute();

        preparedStatement = connection
                .prepareStatement("INSERT INTO minions_villains (minion_id, villain_id) " +
                        "VALUES ((SELECT id FROM minions WHERE minions.name like ?), (SELECT id FROM villains WHERE villains.name like ?));");

        preparedStatement.setString(1, minionName);
        preparedStatement.setString(2, villainName);
        preparedStatement.execute();

        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);


        System.out.println();

    }
}
