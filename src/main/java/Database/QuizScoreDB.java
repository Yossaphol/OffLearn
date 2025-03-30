package Database;

import Teacher.quiz.QuizItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizScoreDB extends ConnectDB {

    public Integer getUserQuizScore(int userId, int quizId) {
        String sql = "SELECT quizscore FROM quizscore WHERE User_ID = ? AND Quiz_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, userId);
            pstm.setInt(2, quizId);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quizscore");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<QuizItem> getUserDoneQuizzes(int userId) {
        String sql = "SELECT q.Quiz_ID, q.header, q.minScore, q.maxScore, q.level " +
                "FROM quiz q " +
                "JOIN chapter ch ON q.Chapter_ID = ch.Chapter_ID " +
                "JOIN enroll e ON ch.Course_ID = e.Course_ID " +
                "JOIN quizscore qs ON q.Quiz_ID = qs.Quiz_ID " +
                "WHERE e.User_ID = ? AND qs.User_ID = ?";

        List<QuizItem> quizList = new ArrayList<>();

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, userId);
            pstm.setInt(2, userId);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int quizID = rs.getInt("Quiz_ID");
                    String header = rs.getString("header");
                    int minScore = rs.getInt("minScore");
                    int maxScore = rs.getInt("maxScore");
                    String level = rs.getString("level");

                    quizList.add(new QuizItem(quizID, header, minScore, maxScore, level));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizList;
    }

    public List<QuizItem> getUserUndoneQuizzes(int userId) {
        String sql = "SELECT q.Quiz_ID, q.header, q.minScore, q.maxScore, q.level " +
                "FROM quiz q " +
                "JOIN chapter ch ON q.Chapter_ID = ch.Chapter_ID " +
                "JOIN enroll e ON ch.Course_ID = e.Course_ID " +
                "WHERE e.User_ID = ? " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM quizscore qs " +
                "    WHERE qs.User_ID = ? AND qs.Quiz_ID = q.Quiz_ID " +
                ")";

        List<QuizItem> quizList = new ArrayList<>();

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, userId);
            pstm.setInt(2, userId);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int quizID = rs.getInt("Quiz_ID");
                    String header = rs.getString("header");
                    int minScore = rs.getInt("minScore");
                    int maxScore = rs.getInt("maxScore");
                    String level = rs.getString("level");

                    quizList.add(new QuizItem(quizID, header, minScore, maxScore, level));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizList;
    }
}
