package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestionDB extends ConnectDB{
    public void saveQuestion(String question, String corr, int point){
        String sql = "INSERT INTO question (Quiz_ID ,questionText, correct_ans, point) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ){
                    pstm.setInt(1, 1);
                    pstm.setString(2, question);
                    pstm.setString(3 , corr);
                    pstm.setInt(4, point);
                    pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
