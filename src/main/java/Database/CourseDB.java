package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDB extends ConnectDB{
    public void saveCourse(int cat, String courseName, int userId, String desc, int price) {
        String checkSql = "SELECT COUNT(*) FROM course WHERE courseName = ? AND User_ID = ?";
        String insertSql = "INSERT INTO course (Cat_ID, courseName, User_ID, courseDescription, price, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        ) {
            checkStmt.setString(1, courseName);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }

            insertStmt.setInt(1, cat);
            insertStmt.setString(2, courseName);
            insertStmt.setInt(3, userId);
            insertStmt.setString(4, desc);
            insertStmt.setInt(5, price);
            insertStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCourse(int cat, String courseName, int userId, String desc, int price, String imageUrl) {
        String checkSql = "SELECT COUNT(*) FROM course WHERE courseName = ? AND User_ID = ?";
        String insertSql = "INSERT INTO course (Cat_ID, courseName, User_ID, courseDescription, price, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = this.connectToDB();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        ) {
            checkStmt.setString(1, courseName);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }

            insertStmt.setInt(1, cat);
            insertStmt.setString(2, courseName);
            insertStmt.setInt(3, userId);
            insertStmt.setString(4, desc);
            insertStmt.setInt(5, price);
            insertStmt.setString(6, imageUrl);
            insertStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getCourseID(String name){
        String sql = "SELECT Course_ID FROM offlearn.course WHERE courseName = ? ";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("Course_ID");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
