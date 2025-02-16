package com.example.offlearn.pChat.DataBase;

import java.sql.*;
import java.util.ArrayList;

public class TeacherDBConnect {
    private final String url = "jdbc:mysql://localhost:3306/studentdb?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "1234";

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public ArrayList<String> getTeacherName(){
        ArrayList<String> teacherNames = new ArrayList<>();
        String query = "SELECT name FROM studentdb.teacherlist";

        try (Connection conn = connectDB();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query)){

            while (rs.next()) {
                teacherNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherNames;
    }
}
