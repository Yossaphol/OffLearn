package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class QuizDB extends ConnectDB{
    public int saveQuiz(int chapterID, String quizName, int minScore, String level) {
        String sql = "INSERT IGNORE INTO quiz (Chapter_ID, header, minScore, level) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstm.setInt(1, chapterID);
            pstm.setString(2, quizName);
            pstm.setInt(3, minScore);
            pstm.setString(4, level);
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

    public int getLatestQuizID() {
        String sql = "SELECT Quiz_ID FROM quiz ORDER BY Quiz_ID DESC LIMIT 1";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("Quiz_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public void deleteQuiz(int quizID) {
        String sql = "DELETE FROM quiz WHERE Quiz_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, quizID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
