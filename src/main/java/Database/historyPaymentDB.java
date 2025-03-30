package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class historyPaymentDB extends ConnectDB {
    public boolean insertHistoryPayment(String accountNumber, String accountName, String bankName, double amount, int userId) {
        String query = "INSERT INTO offlearn.historypayment (acctNo, acctName, bankName, amount, User_ID,time_stamp) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountName);
            pstmt.setString(3, bankName);
            pstmt.setDouble(4, amount);
            pstmt.setInt(5, userId);


            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
