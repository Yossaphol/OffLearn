package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChoicesDB extends ConnectDB{
    public void saveChoices(int questionID, String text) {
        String checkQuery = "SELECT COUNT(*) FROM choices WHERE Question_id = ? AND text = ?";
        String insertQuery = "INSERT INTO choices (Question_id, text) VALUES (?, ?)";

        try (Connection conn = this.connectToDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setInt(1, questionID);
            checkStmt.setString(2, text);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return;
                }
            }

            insertStmt.setInt(1, questionID);
            insertStmt.setString(2, text);
            insertStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error saving choice: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
