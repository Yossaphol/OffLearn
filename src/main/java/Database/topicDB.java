package Database;

import client.inbox.gChat.topicContent;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.sql.*;

public class topicDB {
    private static final Dotenv nev = Dotenv.load();
    private static final String url = nev.get("DB_URL");
    private static final String user = nev.get("DB_USER");
    private static final String  password = nev.get("DB_PASS");


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

    public VBox showTopicFromDB(){

        VBox vbox = new VBox(20);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT topicID, posterName, topic_messages, time_stamp, favourite_count FROM topicdb");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/inbox/topicContent.fxml"));
                VBox messageBox = loader.load();

                topicContent controller = loader.getController();
                controller.setTopicId(rs.getInt("topicID"));
                controller.setName(rs.getString("posterName"));
                controller.setMessages(rs.getString("topic_messages"));
                controller.setTime(rs.getString("time_stamp"));
                controller.setFavourite_count(rs.getInt("favourite_count"));


                vbox.getChildren().addFirst(messageBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vbox;
    }

    public void updateFavCount(int topicId) {
        String sql = "UPDATE topicdb SET favourite_count = favourite_count + 1 WHERE topicID = ?";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void decreseFavCount(int topicId) {
        String sql = "UPDATE topicdb SET favourite_count = favourite_count - 1 WHERE topicID = ?";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
