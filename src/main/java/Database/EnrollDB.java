package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EnrollDB extends ConnectDB{

    public int[] countEnrollmentsForCurrentAndLastMonth(int userID) {
        String sql = "SELECT " +
                "SUM(CASE WHEN MONTH(Enroll_Date) = MONTH(CURRENT_DATE()) AND YEAR(Enroll_Date) = YEAR(CURRENT_DATE()) THEN 1 ELSE 0 END) AS this_month, " +
                "SUM(CASE WHEN MONTH(Enroll_Date) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(Enroll_Date) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH) THEN 1 ELSE 0 END) AS last_month " +
                "FROM enroll e " +
                "JOIN course c ON e.Course_ID = c.Course_ID " +
                "WHERE c.User_ID = ? " +
                "AND (MONTH(Enroll_Date) = MONTH(CURRENT_DATE()) OR MONTH(enroll_date) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH))";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new int[]{rs.getInt("this_month"), rs.getInt("last_month")};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new int[]{0, 0};
    }



}
