package Database;

import java.sql.*;

public class ChapterDB extends ConnectDB{
    public int saveChapter(int course_id, String chapName, String desc, String chapImg, String chapMaterial){
        String sql = "INSERT INTO chapter (Course_ID, chapterName, chapterDescription, chapter_image, chapter_material) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            pstm.setInt(1, course_id);
            pstm.setString(2, chapName);
            pstm.setString(3, desc);
            pstm.setString(4, chapImg);
            pstm.setString(5, chapMaterial);
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

    public int getCurrentChapterId() {
        String sql = "SELECT chapter_ID FROM chapter ORDER BY chapter_ID DESC LIMIT 1";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("chapter_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
