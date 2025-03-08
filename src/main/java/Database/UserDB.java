package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends ConnectDB{

    public void testConnect(){
        String query = "SELECT User_ID, Fullname FROM studentdb.user";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("User_ID");
                String fullName = rs.getString("Fullname");

                System.out.println("ID: " + studentId + ", Name: " + fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

