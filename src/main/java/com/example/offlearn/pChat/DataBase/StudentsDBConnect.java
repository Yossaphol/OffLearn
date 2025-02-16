package com.example.offlearn.pChat.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentsDBConnect {
    private final String url = "jdbc:mysql://localhost:3306/studentdb?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "1234";

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addStudent(String studentName) {
        String checkQuery = "SELECT COUNT(*) FROM studentdb.studentlist WHERE name = ?";
        String insertQuery = "INSERT INTO studentdb.studentlist (name) VALUES (?)";

        try (Connection conn = connectDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, studentName);
            var rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Student already exists. Skipping insertion.");
                return;
            }

            insertStmt.setString(1, studentName);
            insertStmt.executeUpdate();
            System.out.println("Insert to database complete");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
