package com.example.offlearn.pChat.DataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherDBConnect {
    private final String url = "jdbc:mysql://localhost:3306/studentdb?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "1234";

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addTeacher(String teacherName) {
        String query = "INSERT INTO studentdb.teacherlist (name) VALUES (?) ON DUPLICATE KEY UPDATE name=name";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacherName);
            pstmt.executeUpdate();
            System.out.println("Insert to data base complete");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
