package Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;

public class StudentDBConnect extends ConnectDB {

    public ArrayList<String> getStudentNames() {
        ArrayList<String> studentNames = new ArrayList<>();
        String query = "SELECT name FROM offlearn.studentlist";

        try (Connection conn = this.connectToDB();
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

    public int getStudentID(String studentName) {
        String query = "SELECT StudentID FROM offlearn.studentlist WHERE name = ?";
        int studentId = -1;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                studentId = rs.getInt("StudentID");
            } else {
                System.out.println("Student not found: " + studentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentId;
    }
}
