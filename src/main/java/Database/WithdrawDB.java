package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WithdrawDB extends ConnectDB {


    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public static withdraw getWithdrawInfo(int userId) {
        String query = "SELECT accountNumber, accountFName, accountLName, bankName FROM offlearn.bookbank WHERE User_ID = ?";

        try (Connection conn = new WithdrawDB().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(userId));
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

    public boolean updateWithdrawInfo(String accountNumber, String accountFName, String accountLName, String bankName, int User_ID,withdraw withdrawInfo) {
        String query = "UPDATE offlearn.bookbank SET accountNumber = ?, accountFName = ?, accountLName = ?, bankName = ? WHERE User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountFName);
            pstmt.setString(3, accountLName);
            pstmt.setString(4, bankName);
            pstmt.setInt(5, User_ID);
            withdrawInfo.setAccountNumber(accountNumber);
            withdrawInfo.setAccountFName(accountFName);
            withdrawInfo.setAccountLName(accountLName);
            withdrawInfo.setBankName(bankName);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveToDB(String accountNumber, String accountFName, String accountLName, String bankName, int User_ID,withdraw withdrawInfo) {
        String query = "INSERT INTO offlearn.bookbank (accountNumber, accountFName, accountLName, bankName, User_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountFName);
            pstmt.setString(3, accountLName);
            pstmt.setString(4, bankName);
            pstmt.setInt(5, User_ID);
            withdrawInfo = new withdraw();
            withdrawInfo.setAccountNumber(accountNumber);
            withdrawInfo.setAccountFName(accountFName);
            withdrawInfo.setAccountLName(accountLName);
            withdrawInfo.setBankName(bankName);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getAvailableBalance(int userId) {
        String query = """
        SELECT 
            (SELECT SUM(c.price * (SELECT COUNT(*) FROM offlearn.enroll e WHERE e.Course_ID = c.Course_ID))
             FROM offlearn.course c WHERE c.User_ID = ?) 
            - COALESCE((SELECT SUM(hp.amount) FROM offlearn.historypayment hp WHERE hp.User_ID = ?), 0)
            AS availableBalance;
        """;

        try (Connection conn = new WithdrawDB().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("availableBalance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


    public boolean withdrawAmount(int userId, String acctNo, String acctName, String bankName, double amount) {
        double availableBalance = getAvailableBalance(userId);
        if (amount > availableBalance) {
            System.out.println("ยอดเงินไม่พอสำหรับการถอน!");
            return false;
        }

        String query = """
        INSERT INTO offlearn.historypayment (acctNo, acctName, bankName, amount, User_ID, time_stamp)
        VALUES (?, ?, ?, ?, ?, NOW())
        """;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, acctNo);
            pstmt.setString(2, acctName);
            pstmt.setString(3, bankName);
            pstmt.setDouble(4, amount);
            pstmt.setInt(5, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
