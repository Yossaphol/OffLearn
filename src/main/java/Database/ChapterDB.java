package Database;

import java.sql.*;
import java.util.ArrayList;

public class ChapterDB extends ConnectDB{
    ArrayList<String[]> chapterList = new ArrayList<>();

    public ChapterDB() {
        getAllChapter();
    }

    public int saveChapter(int course_id, String chapName, String desc, String chapVideo, String chapMaterial, double playtime){
        String sql = "INSERT INTO chapter (Course_ID, chapterName, chapterDescription, chapter_video, chapter_material, chapter_playtime) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            pstm.setInt(1, course_id);
            pstm.setString(2, chapName);
            pstm.setString(3, desc);
            pstm.setString(4, chapVideo);
            pstm.setString(5, chapMaterial);
            pstm.setDouble(6, playtime);
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

    public boolean updateChapter(int chapterId, String chapName, String desc, String chapVideo, String chapMaterial, double playtime) {
        if (!isChapterExists(chapterId)) {
            return false;
        }

        String sql = "UPDATE chapter SET chapterName = ?, chapterDescription = ?, chapter_video = ?, chapter_material = ?, chapter_playtime WHERE Chapter_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, chapName);
            pstm.setString(2, desc);
            pstm.setString(3, chapVideo);
            pstm.setString(4, chapMaterial);
            pstm.setInt(5, chapterId);
            pstm.setDouble(6, playtime);

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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

    public void getAllChapter() {
        String sql = "SELECT chapterName,Chapter_ID FROM offlearn.chapter";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();


            while (rs.next()){
                String[] tmpList = new String[2];
                tmpList[0] = (String.valueOf(rs.getString("Chapter_ID")));
                tmpList[1] = (rs.getString("chapterName"));
                chapterList.add(tmpList);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Object.getChapterList().get(index)[index]
    public ArrayList<String[]> getChapterList(){
        return this.chapterList;
    }

    public boolean isChapterExists(int chapterId) {
        String sql = "SELECT COUNT(*) FROM chapter WHERE Chapter_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, chapterId);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<String[]> getChaptersByCourseID(String courseID) { // some test
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
