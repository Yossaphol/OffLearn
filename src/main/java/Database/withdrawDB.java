package Database;

import a_Session.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class withdrawDB extends ConnectDB {
    public static withdraw getWithdrawInfo(String userId) {
        String query = "SELECT accountNumber, accountFName, accountLName, bankName FROM offlearn.bookbank WHERE user_id = ?";

        try (Connection conn = new withdrawDB().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                withdraw withdrawInfo = new withdraw();
                withdrawInfo.setAccountNumber(rs.getString("accountNumber"));
                withdrawInfo.setAccountFName(rs.getString("accountFName"));
                withdrawInfo.setAccountLName(rs.getString("accountLName"));
                withdrawInfo.setBankName(rs.getString("bankName"));
                return withdrawInfo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateWithdrawInfo(String accountNumber, String accountFName, String accountLName, String bankName, int User_ID) {
        String query = "UPDATE offlearn.bookbank SET accountNumber = ?, accountFName = ?, accountLName = ?, bankName = ? WHERE User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountFName);
            pstmt.setString(3, accountLName);
            pstmt.setString(4, bankName);
            pstmt.setInt(5, User_ID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
