package inbox.DataBase;

import inbox.gChat.topicContent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.sql.*;

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

    public VBox showTopicFromDB(){

        VBox vbox = new VBox(20);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT posterName, topic_messages, time_stamp FROM topicdb");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inbox/topicContent.fxml"));
                VBox messageBox = loader.load();

                topicContent controller = loader.getController();
                controller.setName(rs.getString("posterName"));
                controller.setMessages(rs.getString("topic_messages"));
                controller.setTime(rs.getString("time_stamp"));

                vbox.getChildren().addFirst(messageBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vbox;
    }
}
