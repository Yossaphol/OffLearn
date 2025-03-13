package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class chapterDB extends ConnectDB{
    public void saveChapter(int course_id, String chapName, String desc){
        String sql = "INSERT INTO chapter (Course_ID, chapterName, chapterDescription) VALUES (?, ?, ?)";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
        ){
            pstm.setInt(1, course_id);
            pstm.setString(2, chapName);
            pstm.setString(3, desc);
            pstm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
