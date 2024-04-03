package lk.ijse.gymmanagmentsystem.model;

import lk.ijse.gymmanagmentsystem.dto.Customer;
import lk.ijse.gymmanagmentsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomerModel {
    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Customer> getAll() throws SQLException {
        List<Customer> data = new ArrayList<>();

        String sql = "SELECT * FROM member";
        ResultSet resultSet = CrudUtil.execute(sql);


        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            ));
        }
        return data;
    }
    public static void saveAll(List<Customer> customers) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            con.setAutoCommit(false);

            String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);

            for (Customer c : customers) {
                stm.setString(1, c.getId());
                stm.setString(2, c.getName());
                stm.setString(3, c.getMobileNumber());
                stm.setString(4, c.getAddress());
                stm.setString(5, c.getAge());
                stm.setString(6, c.getEmail());
                stm.setString(7, c.getGender());
                stm.setDate(8, java.sql.Date.valueOf(c.getJoinDate()));
                stm.setString(9, c.getMembershipType());

                stm.addBatch();
            }

            int[] counts = stm.executeBatch();
            con.commit();

            System.out.println("Saved " + counts.length + " customers to database.");
        } catch (SQLException ex) {
            System.out.println("Error saving customers to database: " + ex.getMessage());
            throw ex;
        }
    }
}
