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

            // This code to add into database
            String addsql = "INSERT INTO guest (idguest, firstname, lastname) VALUES (?, ?, ?)";

            PreparedStatement addstatement = connection.prepareStatement(addsql);
            addstatement.setInt(1, 100);
            addstatement.setString(2, "Tima");
            addstatement.setString(3, "Yuss");

            int rows = addstatement.executeUpdate();
            System.out.println(rows);

            // This code to iterate through all guests
            String  sql = "SELECT * FROM guest";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            int count = 0;
            while(result.next()) {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                count++;
                System.out.println("Guest: " + firstname + " " + lastname);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Oops, error!");
            e.printStackTrace();
        }
    }
}
