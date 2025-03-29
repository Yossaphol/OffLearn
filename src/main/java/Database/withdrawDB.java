package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class withdrawDB extends ConnectDB {

    // ดึงข้อมูลบัญชีธนาคารจากฐานข้อมูล
    public static withdraw getWithdrawInfo(String userId) {
        String query = "SELECT accountNumber, accountFName, accountLName, bankName FROM offlearn.bookbank WHERE user_id = ?";

        try (Connection conn = new withdrawDB().connectToDB(); // ✅ เรียกผ่าน instance
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);  // ใช้ user_id แทน username
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

    // อัปเดตข้อมูลบัญชีธนาคาร
    public boolean updateWithdrawInfo(String username, String accountNumber, String accountFName, String accountLName, String bankName) {
        String query = "UPDATE offlearn.bookbank SET accountNumber = ?, accountFName = ?, accountLName = ?, bankName = ? WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountFName);
            pstmt.setString(3, accountLName);
            pstmt.setString(4, bankName);
            pstmt.setString(5, username);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // true ถ้าอัปเดตสำเร็จ
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
