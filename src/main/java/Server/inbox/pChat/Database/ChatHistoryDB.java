package Server.inbox.pChat.Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHistoryDB {
    private static final Dotenv env = Dotenv.load();
    private static final String url = env.get("DB_URL");
    private static final String user = env.get("DB_USER");
    private static final String password = env.get("DB_PASS");

    private static Connection connectDB() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<String> getSentMessages(int studentId, int teacherId) {
        List<String> messages = new ArrayList<>();
        String query = "SELECT message_text FROM messages WHERE sender_id = ? AND receiver_id = ? ORDER BY message_id ASC";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, teacherId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                messages.add(rs.getString("message_text"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching sent messages: " + e.getMessage());
        }
        return messages;
    }

    public List<String> getReceivedMessages(int studentId, int teacherId) {
        List<String> messages = new ArrayList<>();
        String query = "SELECT message_text FROM messages WHERE sender_id = ? AND receiver_id = ? ORDER BY message_id ASC";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, teacherId);
            pstmt.setInt(2, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                messages.add(rs.getString("message_text"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching received messages: " + e.getMessage());
        }
        return messages;
    }

    public void saveChatMessage(int senderId, String senderType, int receiverId, String receiverType, String message) {
        String query = "INSERT INTO messages (sender_id, sender_type, receiver_id, receiver_type, message_text) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, senderId);
            pstmt.setString(2, senderType);
            pstmt.setInt(3, receiverId);
            pstmt.setString(4, receiverType);
            pstmt.setString(5, message);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error saving message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getAllMessages(int teacherId, int studentId) {
        List<Map<String, String>> messages = new ArrayList<>();
        String query = "SELECT sender_id, message_text FROM messages " +
                "WHERE (sender_id = ? AND receiver_id = ?) " +
                "OR (sender_id = ? AND receiver_id = ?) " +
                "ORDER BY timestamp ASC";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, teacherId);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, studentId);
            pstmt.setInt(4, teacherId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> messageData = new HashMap<>();
                messageData.put("sender_id", String.valueOf(rs.getInt("sender_id")));
                messageData.put("message_text", rs.getString("message_text"));
                messages.add(messageData);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching messages: " + e.getMessage());
        }
        return messages;
    }


}
