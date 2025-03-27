package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChoicesDB extends ConnectDB{
    public void saveChoices(int questionID, String text) {
        String checkQuery = "SELECT 1 FROM choices WHERE Question_id = ? AND text = ? LIMIT 1";
        String insertQuery = "INSERT INTO choices (Question_id, text) VALUES (?, ?)";

        try (Connection conn = this.connectToDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setInt(1, questionID);
            checkStmt.setString(2, text);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
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


    public String getLastChoice(int questionID) {
        String getLastChoiceQuery = "SELECT text FROM choices WHERE Question_ID = ? ORDER BY Choice_ID DESC LIMIT 1";

        try (Connection conn = this.connectToDB();
             PreparedStatement getStmt = conn.prepareStatement(getLastChoiceQuery)) {

            getStmt.setInt(1, questionID);
            try (ResultSet rs = getStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("text");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteLastChoice(int questionID) {
        String getLastChoiceQuery = "SELECT Choice_ID FROM choices WHERE Question_ID = ? ORDER BY Choice_ID DESC LIMIT 1";
        String deleteLastChoiceQuery = "DELETE FROM choices WHERE Choice_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement getStmt = conn.prepareStatement(getLastChoiceQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteLastChoiceQuery)) {

            getStmt.setInt(1, questionID);
            try (ResultSet rs = getStmt.executeQuery()) {
                if (rs.next()) {
                    int choiceID = rs.getInt("Choice_ID");

                    deleteStmt.setInt(1, choiceID);
                    deleteStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
