package Database;

import java.sql.*;

public class ChapterDB extends ConnectDB{
    public int saveChapter(int course_id, String chapName, String desc){
        String sql = "INSERT INTO chapter (Course_ID, chapterName, chapterDescription) VALUES (?, ?, ?)";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            pstm.setInt(1, course_id);
            pstm.setString(2, chapName);
            pstm.setString(3, desc);
            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return - 1;
    }

    public void deleteChapter(int chapterId) {
        String sql = "DELETE FROM chapter WHERE chapter_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
        ) {
            pstm.setInt(1, chapterId);
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
