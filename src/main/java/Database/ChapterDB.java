package Database;

import java.sql.*;
import java.util.ArrayList;

public class ChapterDB extends ConnectDB{
    ArrayList<String[]> chapterList = new ArrayList<>();

    public ChapterDB() {
        getAllChapter();
    }

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

}
