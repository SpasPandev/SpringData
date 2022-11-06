package _03_Get_Minion_Names;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static final String user = "root";
    public static final String password = "123321";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.
                getConnection("jdbc:mysql://localhost:3306/minions_db",properties);

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name, age FROM minions\n" +
                        "JOIN minions_villains mv on minions.id = mv.minion_id\n" +
                        "WHERE villain_id = ?;");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter villain's id:");
        Integer villainId = scanner.nextInt();

        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        Integer counter = 1;

        boolean isExist = true;

        while (resultSet.next()){
            isExist = false;
            System.out.printf("%d. %s %s", counter++, resultSet.getString("name"), resultSet.getString("age"));
            System.out.println();
        }

        if (isExist)
        {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
        }

    }
}