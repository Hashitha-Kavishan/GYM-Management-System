package lk.ijse.gymmanagmentsystem.model;

import lk.ijse.gymmanagmentsystem.dto.Customer;
import lk.ijse.gymmanagmentsystem.dto.Payment;
import lk.ijse.gymmanagmentsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PaymentModel {
    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Payment> getAll() throws SQLException {
        List<Payment> data = new ArrayList<>();

        String sql = "SELECT * FROM payment";
        ResultSet resultSet = CrudUtil.execute(sql);


        while (resultSet.next()) {
            data.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)

            ));
        }
        return data;
    }
    public static void saveAll(List<Payment> payments) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            con.setAutoCommit(false);

            String sql = "INSERT INTO Payment VALUES (?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);

            for (Payment p : payments) {
                stm.setString(1, p.getId());
                stm.setString(2, p.getDate());
                stm.setDouble(3, p.getAmount());
                stm.setString(4, p.getMemberId());


                stm.addBatch();
            }

            int[] counts = stm.executeBatch();
            con.commit();

            System.out.println("Saved " + counts.length + " payment to database.");
        } catch (SQLException ex) {
            System.out.println("Error saving payment to database: " + ex.getMessage());
            throw ex;
        }
    }
}
