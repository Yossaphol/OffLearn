package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreDB extends ConnectDB{

    public void saveScore(CourseDB courseDB, int userID, int chapterID, int score) {

        int courseID =  courseDB.getCourseIDByChapterID(chapterID);
        String checkQuery = "SELECT COUNT(*) FROM studentscore WHERE User_ID = ? AND Chapter_ID = ?";
        String insertQuery = "INSERT INTO studentscore (User_ID, Course_ID, Chapter_ID, studentscore) VALUES (?, ?, ?, ?)";

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
                    insertStmt.executeUpdate();
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


}
