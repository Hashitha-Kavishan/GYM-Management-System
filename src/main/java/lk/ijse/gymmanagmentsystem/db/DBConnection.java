package lk.ijse.gymmanagmentsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DBConnection {
        private static DBConnection dbConnection;
        private Connection con;

        private DBConnection() throws SQLException {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gymmanagement",
                    "root",
                    "1234"
            );
        }

        public static DBConnection getInstance() throws SQLException {
        /*if(dbConnection == null) {
            dbConnection = new DBConnection();
            return dbConnection;
        } else {
            return dbConnection;
        }*/
            return (null == dbConnection) ? dbConnection = new DBConnection()
                    : dbConnection;
        }
        public Connection getConnection() {
            return con;
        }
    }


