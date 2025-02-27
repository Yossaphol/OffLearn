package client.inbox.DataBase;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class StudentsDBConnect {
    private static final Dotenv nev = Dotenv.load();
    private static final String url = nev.get("DB_URL");
    private static final String user = nev.get("DB_USER");
    private static final String  password = nev.get("DB_PASS");

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

    public int getStudentID(String studentName) {
        String query = "SELECT StudentID FROM studentdb.studentlist WHERE name = ?";
        int studentId = -1;

        try (Connection conn = connectDB();
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

        System.out.println("Returning StudentID: " + studentId); // Debugging
        return studentId;
    }

}
