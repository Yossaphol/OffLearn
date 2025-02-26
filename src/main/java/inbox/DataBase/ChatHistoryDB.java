
package inbox.DataBase;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class ChatHistoryDB {
        private static final Dotenv nev = Dotenv.load();
        private static final String url = nev.get("DB_URL");
        private static final String user = nev.get("DB_USER");
        private static final String  password = nev.get("DB_PASS");

        private static Connection connectDB() throws SQLException {
            return DriverManager.getConnection(url, user, password);
        }

        public List<String[]> getSentMessages(int studentId, int teacherId) {
            List<String[]> messages = new ArrayList<>();
            String query = "SELECT message_text, timestamp FROM messages WHERE sender_id = ? AND receiver_id = ? ORDER BY timestamp ASC";

            try (Connection conn = connectDB();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, studentId);
                pstmt.setInt(2, teacherId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    messages.add(new String[]{rs.getString("message_text"), rs.getString("timestamp")});
                }

            } catch (SQLException e) {
                System.err.println("Error fetching sent messages: " + e.getMessage());
            }
            return messages;
        }

        public List<String[]> getReceivedMessages(int studentId, int teacherId) {
            List<String[]> messages = new ArrayList<>();
            String query = "SELECT message_text, timestamp FROM messages WHERE sender_id = ? AND receiver_id = ? ORDER BY timestamp ASC";

            try (Connection conn = connectDB();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, teacherId);
                pstmt.setInt(2, studentId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    messages.add(new String[]{rs.getString("message_text"), rs.getString("timestamp")});
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
                e.printStackTrace();
            }
        }


    }

