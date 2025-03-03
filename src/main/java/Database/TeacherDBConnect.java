package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherDBConnect extends ConnectDB {

    public ArrayList<String> getTeacherNames() {
        ArrayList<String> teacherNames = new ArrayList<>();
        String query = "SELECT name FROM teacherlist";

        try (Connection conn = this.connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                teacherNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherNames;
    }

    public Map<String, String> getTeacherInfo(String teacherName) {
        String query = "SELECT IP, Port FROM teacherlist WHERE name = ?";
        Map<String, String> teacherInfo = new HashMap<>();

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacherName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                teacherInfo.put("IP", rs.getString("IP"));
                teacherInfo.put("Port", String.valueOf(rs.getInt("Port")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherInfo;
    }

    public int getTeacherId(String teacherName) {
        String query = "SELECT TeacherID FROM teacherlist WHERE name = ?";

        try (Connection conn = this.connectToDB();
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

    public void addOrUpdateUser(String table, String name, String ip, int port) {
        String checkQuery = "SELECT COUNT(*) FROM " + table + " WHERE name = ?";
        String insertQuery = "INSERT INTO " + table + " (name, IP, port) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE " + table + " SET IP = ?, port = ? WHERE name = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, ip);
                    updateStmt.setInt(2, port);
                    updateStmt.setString(3, name);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, name);
                    insertStmt.setString(2, ip);
                    insertStmt.setInt(3, port);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
