package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnrollmentDB extends ConnectDB {

    public boolean insertEnrollment(int userID, int courseID) {
        String sql = "INSERT INTO enroll (User_ID, Course_ID) VALUES (?, ?)";
        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, userID);
            pstm.setInt(2, courseID);
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }
}
