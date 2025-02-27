package Server.inbox.pChat.Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class TeacherDBConnect {
    private static final Dotenv env = Dotenv.load();
    private final String url = env.get("DB_URL");
    private final String user = env.get("DB_USER");
    private final String password = env.get("DB_PASS");

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addTeacher(String teacherName, String ip, int port) {
        String checkQuery = "SELECT COUNT(*) FROM teacherlist WHERE name = ?";
        String insertQuery = "INSERT INTO teacherlist (name, IP, port) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE teacherlist SET IP = ?, port = ? WHERE name = ?";

        try (Connection conn = connectDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, teacherName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, ip);
                    updateStmt.setInt(2, port);
                    updateStmt.setString(3, teacherName);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, teacherName);
                    insertStmt.setString(2, ip);
                    insertStmt.setInt(3, port);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(String teacherName, String ip, int port) {
        String checkQuery = "SELECT COUNT(*) FROM studentlist WHERE name = ?";
        String insertQuery = "INSERT INTO studentlist (name, IP, port) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE studentlist SET IP = ?, port = ? WHERE name = ?";

        try (Connection conn = connectDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, teacherName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, ip);
                    updateStmt.setInt(2, port);
                    updateStmt.setString(3, teacherName);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, teacherName);
                    insertStmt.setString(2, ip);
                    insertStmt.setInt(3, port);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTeacherId(String teacherName) {
        String query = "SELECT TeacherID FROM teacherlist WHERE name = ?";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, teacherName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TeacherID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
