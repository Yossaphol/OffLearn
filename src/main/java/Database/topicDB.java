package Database;

import Student.inbox.gChat.topicContent;
import a_Session.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class topicDB extends ConnectDB {

    private static Map<Integer, Boolean> userFavorites = new HashMap<>();
    private static Map<Integer, Integer> favoriteCounts = new HashMap<>();

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public boolean isTopicFavorited(int topicId) {
        String currentUser = SessionManager.getInstance().getUsername();
        String sql = "SELECT COUNT(*) FROM user_favorites WHERE topicID = ? AND username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            pstmt.setString(2, currentUser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                boolean isFavorited = rs.getInt(1) > 0;
                userFavorites.put(topicId, isFavorited);
                return isFavorited;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userFavorites.getOrDefault(topicId, false);
    }

    public void setTopicFavorite(int topicId, boolean favorited) {
        String currentUser = SessionManager.getInstance().getUsername();
        userFavorites.put(topicId, favorited);

        if (favorited) {
            String insertSql = "INSERT INTO user_favorites (topicID, username) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE username = username";
            try (Connection conn = this.connectToDB();
                 PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, topicId);
                pstmt.setString(2, currentUser);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            String deleteSql = "DELETE FROM user_favorites WHERE topicID = ? AND username = ?";
            try (Connection conn = this.connectToDB();
                 PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, topicId);
                pstmt.setString(2, currentUser);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getFavoriteCount(int topicId) {
        String sql = "SELECT favourite_count FROM topicdb WHERE topicID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("favourite_count");
                favoriteCounts.put(topicId, count);
                return count;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return favoriteCounts.getOrDefault(topicId, 0);
    }

    public void setFavoriteCount(int topicId, int count) {
        favoriteCounts.put(topicId, count);
        String sql = "UPDATE topicdb SET favourite_count = ? WHERE topicID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, count);
            pstmt.setInt(2, topicId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateFavCount(int topicId) {
        setTopicFavorite(topicId, true);
        int currentCount = getFavoriteCount(topicId);
        setFavoriteCount(topicId, currentCount + 1);
    }

    public void decreseFavCount(int topicId) {
        setTopicFavorite(topicId, false);
        int currentCount = getFavoriteCount(topicId);
        if (currentCount > 0) {
            setFavoriteCount(topicId, currentCount - 1);
        }
    }

    public void initializeFavoriteCount(int topicId, int count) {
        if (!favoriteCounts.containsKey(topicId)) {
            setFavoriteCount(topicId, count);
        }
    }

    public void saveToDB(String messages, String posterName){
        String sql = "INSERT INTO topicdb (topic_messages, posterName, time_stamp, favourite_count) VALUES (?, ?, NOW(), 0)";
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
            ORDER BY t.time_stamp DESC
        """;

        try (Connection conn = this.connectToDB();
             PreparedStatement ptmt = conn.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()) {

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/inbox/topicContent.fxml"));
                VBox messageBox = loader.load();

                int topicId = rs.getInt("topicID");
                topicContent controller = loader.getController();
                controller.setTopicId(topicId);
                controller.setName(rs.getString("posterName"));
                controller.setMessages(rs.getString("topic_messages"));
                controller.setTime(rs.getString("time_stamp"));
                controller.setFavourite_count(rs.getInt("favourite_count"));

                if (rs.getString("Profile") == null){
                    controller.setProfile("/img/Profile/user.png");
                } else {
                    controller.setProfile(rs.getString("Profile"));
                }

                vbox.getChildren().addFirst(messageBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vbox;
    }


}