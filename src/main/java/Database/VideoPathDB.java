package Database;

import Student.learningPage.VideoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoPathDB extends ConnectDB {

    // Fetch a single video by Chapter_ID (1:1 relationship)
    public VideoItem getVideoByChapterID(int chapterID) {
        String sql = "SELECT Videopath_ID, nameTitle, path, titlePaht, playTime, `order`, `like` " + // dislike is missing
                "FROM offlearn.videopath WHERE Chapter_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, chapterID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int videoID = rs.getInt("Videopath_ID");
                String title = rs.getString("nameTitle");
                String path = rs.getString("path");
                String thumbnail = rs.getString("titlePaht");
                String playTime = rs.getString("playTime");
                int order = rs.getInt("order");
                int likeCount = rs.getInt("like");

                return new VideoItem(videoID, chapterID, title, path, thumbnail, playTime, order, likeCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // No video means quiz chapter
    }

    @Override
    public void saveToDB() {
        
    }

    @Override
    public void deleteFromDB() {

    }
}