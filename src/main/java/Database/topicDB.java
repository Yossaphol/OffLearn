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

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement("SELECT topicID, posterName, topic_messages, time_stamp, favourite_count FROM topicdb");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/Teacher.inbox/topicContent.fxml"));
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
