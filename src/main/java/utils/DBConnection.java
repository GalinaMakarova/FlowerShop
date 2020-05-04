package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String DATABASE = "h2db";
    private static final String DATABASE_URL = "jdbc:h2:~/" + DATABASE;
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String USER = "sys";
    private static final String PASSWORD = "123";

    public void getConnection() {
        System.out.print("Driver connection: ");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(JDBC_DRIVER + " registered");

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            System.out.print("DB connection: ");
            System.out.println(connection);
            Statement statement = connection.createStatement();
            String SQL = "select 1 from dual";
            statement.execute(SQL);
            System.out.println("DB connected");
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
}