package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDB extends ConnectDB{
    public void saveCourse(int cat, String courseName, int userId, String desc, int price){
        String sql = "INSERT INTO course (Cat_ID, courseName, User_ID, courseDescription, price) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql);
        ){
            pstm.setInt(1, cat);
            pstm.setString(2, courseName);
            pstm.setInt(3, userId);
            pstm.setString(4, desc);
            pstm.setInt(5, price);
            pstm.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
