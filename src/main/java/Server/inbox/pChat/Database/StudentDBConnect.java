package Server.inbox.pChat.Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;

public class StudentDBConnect {
    private static final Dotenv env = Dotenv.load();
    private final String url = env.get("DB_URL");
    private final String user = env.get("DB_USER");
    private final String password = env.get("DB_PASS");

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


    public int getStudentID(String studentName) {
        String query = "SELECT StudentID FROM studentlist WHERE name = ?";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("StudentID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // ถ้าไม่เจอ
    }

}
