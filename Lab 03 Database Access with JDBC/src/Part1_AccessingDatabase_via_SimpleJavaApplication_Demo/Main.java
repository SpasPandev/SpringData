package Part1_AccessingDatabase_via_SimpleJavaApplication_Demo;

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

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", properties);

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT first_name, last_name FROM employees WHERE salary > ?");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter salary:");
        String salary = scanner.nextLine();

        preparedStatement.setDouble(1, Double.parseDouble(salary));

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
        }

    }
}
