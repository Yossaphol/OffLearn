package Database;

import Teacher.quiz.QuizItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentScoreDB extends ConnectDB {
    
    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public Integer getStudentScore(int userID, int quizID) {
        String query = """
        SELECT s.studentscore 
        FROM studentscore s
        WHERE s.User_ID = ? AND s.Quiz_ID = ?
    """;

        try (Connection connection = this.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, quizID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("studentscore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getTotalStudentScore(int userID, int courseID) {
        String query = "SELECT SUM(studentscore) AS totalScore FROM studentscore WHERE User_ID = ? AND Course_ID = ?";

        try (Connection connection = this.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("totalScore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }


    public int getCourseIdByCourseName(String courseName) {
        String sql = "SELECT Course_ID FROM course WHERE courseName = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int courseId = rs.getInt("Course_ID");

                if (!hasQuizChapters(courseId, conn)) {
                    return -2;
                }

                return courseId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //เช็คว่าคอร์สนั้นๆ มีควิซหรือไม่มี
    public boolean hasQuizChapters(int courseId, Connection conn) {
        String sql = "SELECT 1 FROM quiz WHERE Chapter_ID IN (SELECT Chapter_ID FROM chapter WHERE Course_ID = ?) LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public List<QuizItem> getFinishedQuizzes(int userID) {
        List<QuizItem> finishedQuizzes = new ArrayList<>();
        String query = """
        SELECT q.Quiz_ID, q.header, q.minScore, q.maxScore, q.level 
        FROM quiz q
        JOIN studentscore s ON q.Quiz_ID = s.Quiz_ID
        WHERE s.User_ID = ?
    """;

        try (Connection connection = this.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int quizID = rs.getInt("Quiz_ID");
                String header = rs.getString("header");
                int minScore = rs.getInt("minScore");
                int maxScore = rs.getInt("maxScore");
                String level = rs.getString("level");

                finishedQuizzes.add(new QuizItem(quizID, header, minScore, maxScore, level));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finishedQuizzes;
    }

    public List<QuizItem> getUndoneQuizzes(int userID) {
        List<QuizItem> undoneQuizzes = new ArrayList<>();
        String query = """
        SELECT q.Quiz_ID, q.header, q.minScore, q.maxScore, q.level
        FROM quiz q
        JOIN chapter ch ON q.Chapter_ID = ch.Chapter_ID
        JOIN enroll e ON ch.Course_ID = e.Course_ID
        WHERE e.User_ID = ? 
        AND q.Quiz_ID NOT IN (SELECT Quiz_ID FROM studentscore WHERE User_ID = ?)
    """;

        try (Connection connection = this.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int quizID = rs.getInt("Quiz_ID");
                String header = rs.getString("header");
                int minScore = rs.getInt("minScore");
                int maxScore = rs.getInt("maxScore");
                String level = rs.getString("level");

                undoneQuizzes.add(new QuizItem(quizID, header, minScore, maxScore, level));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return undoneQuizzes;
    }



}
