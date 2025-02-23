package inbox.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class topicDB {
    private final String url = "jdbc:mysql://localhost:3306/studentdb?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "1234";

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void saveTopic(String messages, String posterName){
        String sql = "INSERT INTO topicdb (topic_messages, posterName, time_stamp) VALUES (?, ?, NOW())";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, messages);
            pstmt.setString(2, posterName);
            pstmt.executeUpdate();
            System.out.println("Topic saved successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
