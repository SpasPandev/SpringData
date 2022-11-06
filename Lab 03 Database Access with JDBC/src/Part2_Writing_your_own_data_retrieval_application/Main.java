package Part2_Writing_your_own_data_retrieval_application;

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

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT user_name, first_name, last_name, COUNT(ug.game_id) AS games_count" +
                        " FROM users AS u " +
                        "JOIN users_games AS ug " +
                        "ON u.id = ug.user_id " +
                        "WHERE user_name LIKE ? " +
                        "GROUP BY u.id");

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        boolean isExist = true;

        while (resultSet.next()) {

            isExist = false;

            String user_name = resultSet.getString("user_name");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            Integer games_count = resultSet.getInt("games_count");
            System.out.printf("User: %s\n", user_name);
            System.out.printf("%s %s has played %d games\n", first_name, last_name, games_count);
        }

        if (isExist == true){
            System.out.println("No such user exist");
        }


    }
}
