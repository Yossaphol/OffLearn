package Database;

import java.sql.*;
import java.util.ArrayList;

public class PlaylistDB extends ConnectDB{
    
    ArrayList<String[]> playlist = new ArrayList<>();

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public ArrayList<String[]> getChaptersByCourseID(int courseID) {
        String sql = "SELECT Chapter_ID, chapterName FROM chapter WHERE Course_ID = ?";

        ArrayList<String[]> chapters = new ArrayList<>();

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] row = new String[2];
                row[0] = String.valueOf(rs.getInt("Chapter_ID"));
                row[1] = rs.getString("chapterName");
                chapters.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chapters;
    }


}
