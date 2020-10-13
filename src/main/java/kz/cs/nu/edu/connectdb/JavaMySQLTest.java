package kz.cs.nu.edu.connectdb;

import java.sql.*;

public class JavaMySQLTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotelmanagement";
        String username = "root";
        String password = "951753qwerty";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");

            String sql = "SELECT * FROM guest";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;

            while(result.next()) {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                count++;
                System.out.println("Guest:" + firstname + " " + lastname);
            }
        } catch (SQLException e) {
            System.out.println("Oops, error!");
            e.printStackTrace();
        }
    }
}
