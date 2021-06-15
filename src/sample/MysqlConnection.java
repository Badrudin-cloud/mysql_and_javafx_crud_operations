package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    Connection con;

    public void connect() throws SQLException {
        String user = "root";
        String db_url = "jdbc:mysql://localhost:3308/ca183";
        String pass = "";
        con = DriverManager.getConnection(db_url, user, pass);
    }



}
