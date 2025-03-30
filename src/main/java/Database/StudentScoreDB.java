package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentScoreDB extends ConnectDB {

    public Integer getStudentScore(int userID, int courseID) {
        String query = "SELECT studentscore FROM studentscore WHERE User_ID = ? AND Course_ID = ?";

        try (Connection connection = this.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("studentscore");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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



}
