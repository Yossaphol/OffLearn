package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChoicesDB extends ConnectDB{
    public void saveChoices(int questionID, String text) {
        String query = "INSERT INTO choices (Question_id, text) VALUES (?, ?)";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, questionID);
            pstmt.setString(2, text);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error saving message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
