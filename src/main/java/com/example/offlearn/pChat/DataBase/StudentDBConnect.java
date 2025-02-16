package com.example.offlearn.pChat.DataBase;

import java.sql.*;
import java.util.ArrayList;

public class StudentDBConnect {
    private final String url = "jdbc:mysql://localhost:3306/studentdb?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "1234";

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public ArrayList<String> getStdName() {
        ArrayList<String> studentNames = new ArrayList<>();
        String query = "SELECT name FROM studentdb.studentlist";

        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                studentNames.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }


}
