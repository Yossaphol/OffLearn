package Database;

import Student.dashboard.CourseScore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreDB extends ConnectDB{

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public void saveToDB(CourseDB courseDB, int userID, int chapterID, int score, int quizID) {
        int courseID = courseDB.getCourseIDByChapterID(chapterID);
        String checkQuery = "SELECT COUNT(*) FROM studentscore WHERE User_ID = ? AND Chapter_ID = ?";
        String insertQuery = "INSERT INTO studentscore (User_ID, Course_ID, Chapter_ID, studentscore, Quiz_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.connectToDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, userID);
            checkStmt.setInt(2, chapterID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, userID);
                    insertStmt.setInt(2, courseID);
                    insertStmt.setInt(3, chapterID);
                    insertStmt.setInt(4, score);
                    insertStmt.setInt(5, quizID);
                    insertStmt.executeUpdate();
                    System.out.println("บันทึกคะแนน " + score + " สำหรับ User " + userID + " ใน Chapter " + chapterID);
                }
            } else {
                System.out.println("คะแนนของ User " + userID + " ใน Chapter " + chapterID + " ถูกบันทึกไปแล้ว");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculateScorePercentage(int userID, int chapterID) {
        String userScoreQuery = "SELECT studentscore FROM studentscore WHERE User_ID = ? AND Chapter_ID = ?";
        String totalScoreQuery = "SELECT COUNT(*) AS total, AVG(studentscore) AS averageScore FROM studentscore WHERE Chapter_ID = ?";

        double userScore = 0.0;
        double averageScore = 0.0;
        int totalParticipants = 0;

        try (Connection conn = this.connectToDB()) {
            try (PreparedStatement userScoreStmt = conn.prepareStatement(userScoreQuery)) {
                userScoreStmt.setInt(1, userID);
                userScoreStmt.setInt(2, chapterID);
                ResultSet userScoreRs = userScoreStmt.executeQuery();

                if (userScoreRs.next()) {
                    userScore = userScoreRs.getDouble("studentscore");
                }
            }

            try (PreparedStatement totalScoreStmt = conn.prepareStatement(totalScoreQuery)) {
                totalScoreStmt.setInt(1, chapterID);
                ResultSet totalScoreRs = totalScoreStmt.executeQuery();

                if (totalScoreRs.next()) {
                    totalParticipants = totalScoreRs.getInt("total");
                    averageScore = totalScoreRs.getDouble("averageScore");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        double percentage = 0.0;
        if (totalParticipants > 0) {
            percentage = (userScore / averageScore) * 100;
        }

        return percentage;
    }


    public double calculatePercentageAboveMinScore(int teacherID) {
        String query = """
            SELECT COUNT(s.studentscore) AS totalStudents,
                   SUM(CASE WHEN s.studentscore > q.minscore THEN 1 ELSE 0 END) AS aboveMin
            FROM studentscore s
            JOIN quiz q ON s.Chapter_ID = q.Chapter_ID
            JOIN chapter ch ON q.Chapter_ID = ch.Chapter_ID
            JOIN course c ON ch.Course_ID = c.Course_ID
            WHERE c.User_ID = ?
        """;

        int totalStudents = 0;
        int aboveMinCount = 0;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, teacherID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalStudents = rs.getInt("totalStudents");
                aboveMinCount = rs.getInt("aboveMin");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (totalStudents > 0) ? ((double) aboveMinCount / totalStudents) * 100 : 0.0;
    }

    public double calculatePercentageBelowMinScore(int teacherID) {
        String query = """
            SELECT COUNT(s.studentscore) AS totalStudents,
                   SUM(CASE WHEN s.studentscore <= q.minscore THEN 1 ELSE 0 END) AS belowMin
            FROM studentscore s
            JOIN quiz q ON s.Chapter_ID = q.Chapter_ID
            JOIN chapter ch ON q.Chapter_ID = ch.Chapter_ID
            JOIN course c ON ch.Course_ID = c.Course_ID
            WHERE c.User_ID = ?
        """;

        int totalStudents = 0;
        int belowMinCount = 0;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, teacherID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalStudents = rs.getInt("totalStudents");
                belowMinCount = rs.getInt("belowMin");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (totalStudents > 0) ? ((double) belowMinCount / totalStudents) * 100 : 0.0;
    }

    public int countStudentsBelowAverage(int teacherID) {
        String query = """
        WITH avg_score AS (
            SELECT AVG(s.studentscore) AS avgScore
            FROM studentscore s
            JOIN chapter ch ON s.Chapter_ID = ch.Chapter_ID
            JOIN course c ON ch.Course_ID = c.Course_ID
            WHERE c.User_ID = ?
        )
        SELECT SUM(CASE WHEN s.studentscore < (SELECT avgScore FROM avg_score) THEN 1 ELSE 0 END) AS belowAvg
        FROM studentscore s
        JOIN chapter ch ON s.Chapter_ID = ch.Chapter_ID
        JOIN course c ON ch.Course_ID = c.Course_ID
        WHERE c.User_ID = ?;
    """;

        int belowAvgCount = 0;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, teacherID);
            pstmt.setInt(2, teacherID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                belowAvgCount = rs.getInt("belowAvg");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return belowAvgCount;
    }

    public Map<Integer, Integer> getScoreDistribution(int quizId) {
        Map<Integer, Integer> scoreMap = new HashMap<>();
        String query = "SELECT studentscore, COUNT(*) AS count FROM studentscore WHERE Quiz_ID = ? GROUP BY studentscore ORDER BY studentscore";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int score = rs.getInt("studentscore");
                int count = rs.getInt("count");
                scoreMap.put(score, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreMap;
    }

    public int getTotalScore(int userID) {
        String query = "SELECT SUM(studentscore) AS totalScore FROM offlearn.studentscore WHERE User_ID = ?";
        int totalScore = 0;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalScore = rs.getInt("totalScore"); // ดึงค่าผลรวมของคะแนนทั้งหมด
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalScore;
    }

    public ArrayList<CourseScore> getTopThreeCoursesByScore(int userID) {
        String query = """
        SELECT c.Course_ID, c.courseName, SUM(s.studentscore) AS totalScore
        FROM studentscore s
        JOIN chapter ch ON s.Chapter_ID = ch.Chapter_ID
        JOIN course c ON ch.Course_ID = c.Course_ID
        WHERE s.User_ID = ?
        GROUP BY c.Course_ID, c.courseName
        ORDER BY totalScore DESC
        LIMIT 3;
    """;

        ArrayList<CourseScore> topCourses = new ArrayList<>();

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String courseName = rs.getString("courseName");
                int totalScore = rs.getInt("totalScore");

                topCourses.add(new CourseScore(courseName, totalScore));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topCourses;
    }



}
