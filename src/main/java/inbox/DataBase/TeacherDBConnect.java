package inbox.DataBase;

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

    public String getTeacherIP(String teacherName) {
        String query = "SELECT IP FROM studentdb.teacherlist WHERE name = ?";
        String ip = "";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacherName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ip = rs.getString("IP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public int getTeacherPort(String teacherName) {
        String query = "SELECT Port FROM studentdb.teacherlist WHERE name = ?";
        int port = 0;

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacherName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                port = rs.getInt("Port");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return port;
    }

    public int getTeacherId(String teacherName) {
        String query = "SELECT TeacherID FROM studentdb.teacherlist WHERE name = ?";
        int teacherId = -1;

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacherName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                teacherId = rs.getInt("TeacherID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherId;
    }

}
