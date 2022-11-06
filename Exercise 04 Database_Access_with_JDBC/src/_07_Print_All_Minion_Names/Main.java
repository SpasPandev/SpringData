package _07_Print_All_Minion_Names;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Properties;

public class Main {

    private static final String user = "root";
    private static final String password = "123321";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        ArrayDeque<String> arrayDeque = new ArrayDeque<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name FROM minions");

        while (resultSet.next()) {

            arrayDeque.offer(resultSet.getString("name"));
        }

        int counter = 0;

        while (arrayDeque.size() > 0) {

            if (counter % 2 == 0) {
                System.out.println(arrayDeque.removeFirst());
            }
            else {
                System.out.println(arrayDeque.removeLast());
            }

            counter++;
        }

        System.out.println();

    }
}
