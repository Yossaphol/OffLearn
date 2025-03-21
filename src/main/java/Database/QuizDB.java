package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuizDB extends ConnectDB{
    public void saveQuiz(int chapterID, String quizName, int minScore, String level){
        String sql = "INSERT INTO quiz (Chapter_ID, header, minScore, level) VALUES (?, ?, ?. ? )";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
        ){
            pstm.setInt(1, chapterID);
            pstm.setString(2, quizName);
            pstm.setInt(3, minScore);
            pstm.setString(4, level);
            pstm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
