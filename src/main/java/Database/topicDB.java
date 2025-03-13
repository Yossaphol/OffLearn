package Database;

import Student.inbox.gChat.topicContent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.sql.*;

public class topicDB extends ConnectDB{

    public void saveTopic(String messages, String posterName){
        String sql = "INSERT INTO topicdb (topic_messages, posterName, time_stamp) VALUES (?, ?, NOW())";
        try (Connection conn = this.connectToDB();
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

        String sql = """
            SELECT t.topicID, t.posterName, t.topic_messages, t.time_stamp, t.favourite_count, u.Profile
            FROM topicdb t
            JOIN user u ON t.posterName = u.username
        """;

        try (Connection conn = this.connectToDB();
             PreparedStatement ptmt = conn.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()) {

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/inbox/topicContent.fxml"));
                VBox messageBox = loader.load();

                topicContent controller = loader.getController();
                controller.setTopicId(rs.getInt("topicID"));
                controller.setName(rs.getString("posterName"));
                controller.setMessages(rs.getString("topic_messages"));
                controller.setTime(rs.getString("time_stamp"));
                controller.setFavourite_count(rs.getInt("favourite_count"));

                if (rs.getString("Profile") == null){
                    controller.setProfile("/img/Profile/user.png");
                } else {
                    System.out.println(rs.getString("Profile"));
                    controller.setProfile(rs.getString("Profile"));
                }


                vbox.getChildren().addFirst(messageBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vbox;
    }

    public void updateFavCount(int topicId) {
        String sql = "UPDATE topicdb SET favourite_count = favourite_count + 1 WHERE topicID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void decreseFavCount(int topicId) {
        String sql = "UPDATE topicdb SET favourite_count = favourite_count - 1 WHERE topicID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
