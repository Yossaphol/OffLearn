package Database;

import Teacher.quiz.QuizItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizDB extends ConnectDB{
    public int saveQuiz(int chapterID, String quizName, int minScore, int max, String level) {
        String checkSql = "SELECT Quiz_ID FROM quiz WHERE Chapter_ID = ? AND header = ?";
        String insertSql = "INSERT INTO quiz (Chapter_ID, header, minScore, level, maxScore) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement checkPstm = conn.prepareStatement(checkSql)
        ) {
            checkPstm.setInt(1, chapterID);
            checkPstm.setString(2, quizName);
            try (ResultSet rs = checkPstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            try (PreparedStatement insertPstm = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertPstm.setInt(1, chapterID);
                insertPstm.setString(2, quizName);
                insertPstm.setInt(3, minScore);
                insertPstm.setString(4, level);
                insertPstm.setInt(5, max);
                insertPstm.executeUpdate();

                try (ResultSet generatedKeys = insertPstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
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

    public boolean isQuizExists(int quizID) {
        String sql = "SELECT 1 FROM quiz WHERE Quiz_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, quizID);
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public QuizItem getQuizById(int quizID) {
        String sql = "SELECT * FROM quiz WHERE Quiz_ID = ?";
        QuizItem quizItem = null;

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, quizID);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String header = rs.getString("header");
                    int minScore = rs.getInt("minScore");
                    String level = rs.getString("level");
                    int maxScore = rs.getInt("maxScore");

                    quizItem = new QuizItem(quizID, header, minScore, maxScore, level);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizItem;
    }

    public List<QuizItem> getAllQuizzes() {
        String sql = "SELECT * FROM quiz";
        List<QuizItem> quizList = new ArrayList<>();

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                int quizID = rs.getInt("Quiz_ID");
                String header = rs.getString("header");
                int minScore = rs.getInt("minScore");
                String level = rs.getString("level");
                int maxScore = rs.getInt("maxScore");

                quizList.add(new QuizItem(quizID, header, minScore, maxScore, level));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizList;
    }

    public String getCourseNameByQuizID(int quizID) {
        String sql = "SELECT c.courseName " +
                "FROM course c " +
                "JOIN chapter ch ON c.Course_ID = ch.Course_ID " +
                "JOIN quiz q ON ch.Chapter_ID = q.Chapter_ID " +
                "WHERE q.Quiz_ID = ?";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, quizID);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("courseName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isQuizAvailableForChapter(int chapterId) {
        String sql = "SELECT Quiz_ID FROM quiz WHERE Chapter_ID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chapterId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true if a quiz exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }





}
