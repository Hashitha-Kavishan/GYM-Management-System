package lk.ijse.gymmanagmentsystem.model;

import lk.ijse.gymmanagmentsystem.dto.Customer;
import lk.ijse.gymmanagmentsystem.dto.Instructor;
import lk.ijse.gymmanagmentsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InstructorModel {
    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<Instructor> getAll() throws SQLException {
        List<Instructor> data = new ArrayList<>();

        String sql = "SELECT * FROM instructor";
        ResultSet resultSet = CrudUtil.execute(sql);


        while (resultSet.next()) {
            data.add(new Instructor(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    public static void saveAll(List<Instructor> instructor) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            con.setAutoCommit(false);

            String sql = "INSERT INTO Instructor VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);

            for (Instructor i : instructor) {
                stm.setString(1, i.getId());
                stm.setString(2, i.getName());
                stm.setString(3, i.getAddress());
                stm.setString(4, i.getEmail());
                stm.setString(5, i.getMobileNumber());

                stm.addBatch();
            }

            int[] counts = stm.executeBatch();
            con.commit();

            System.out.println("Saved " + counts.length + " Instructor to database.");
        } catch (SQLException ex) {
            System.out.println("Error saving instructor to database: " + ex.getMessage());
            throw ex;
        }
    }
}
