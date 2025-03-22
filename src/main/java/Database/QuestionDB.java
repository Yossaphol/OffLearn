package Database;

import java.sql.*;

public class QuestionDB extends ConnectDB{
    public int saveQuestion(int quiz_id, String question, String corr, int point, String imgUrl) {
        String sql = "INSERT INTO question (Quiz_ID, questionText, correctAns, point, question_img) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstm.setInt(1, quiz_id);
            pstm.setString(2, question);
            pstm.setString(3, corr);
            pstm.setInt(4, point);
            pstm.setString(5, imgUrl);
            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public void deleteQuestion(int questionID) {
        String sql = "DELETE FROM question WHERE Question_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, questionID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
