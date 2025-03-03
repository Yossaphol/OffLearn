package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHistoryDB extends ConnectDB {

    public void saveChatMessage(int senderId, String senderType, int receiverId, String receiverType, String message) {
        String query = "INSERT INTO messages (sender_id, sender_type, receiver_id, receiver_type, message_text) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.connectToDB();
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
        String query = "SELECT sender_id, message_text, timestamp FROM messages " +
                "WHERE (sender_id = ? AND receiver_id = ?) " +
                "OR (sender_id = ? AND receiver_id = ?) " +
                "ORDER BY timestamp ASC";

        try (Connection conn = this.connectToDB();
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
                messageData.put("timestamp", rs.getString("timestamp"));
                messages.add(messageData);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching messages: " + e.getMessage());
        }
        return messages;
    }
}
