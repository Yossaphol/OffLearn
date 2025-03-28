package Database;

import java.sql.*;
import java.util.ArrayList;

public class PlaylistDB extends ConnectDB{
    ArrayList<String[]> playlist = new ArrayList<>();
    public ArrayList<String[]> getChaptersByCourseID(String courseID) {
        ArrayList<String[]> chapters = new ArrayList<>();
        String sql = "SELECT Chapter_ID, chapterName FROM chapter WHERE Course_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, courseID);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String[] chapter = new String[2];
                chapter[0] = rs.getString("Chapter_ID");   // ID
                chapter[1] = rs.getString("chapterName");  // Title
                chapters.add(chapter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chapters;
    }

}
