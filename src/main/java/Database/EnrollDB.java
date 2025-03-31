package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnrollDB extends ConnectDB{

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

//    return int[] index 0 คือ ยอด enroll เดือนนี้ index 1 คือ ยอด enroll เดือนก่อน
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

//    return top 3 คอร์สที่มียอด enroll สูงสุด
    public Map<String, Integer> getTop3CoursesByEnrollment(int userID) {
        Map<String, Integer> topCourses = new LinkedHashMap<>();

        String sql = "SELECT c.courseName, COUNT(e.Enroll_ID) AS enroll_count " +
                "FROM enroll e " +
                "JOIN course c ON e.Course_ID = c.Course_ID " +
                "WHERE c.User_ID = ? " +
                "GROUP BY c.Course_ID " +
                "ORDER BY enroll_count DESC " +
                "LIMIT 3";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                topCourses.put(rs.getString("courseName"), rs.getInt("enroll_count"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topCourses;
    }

//    return ชื่อคอร์ส กับ รายได้คอร์์สนั้น จาก คอร์สทั้งหมดที่เราสร้างไว้
    public Map<String, Integer> getCourseRevenue(int userID) {
        Map<String, Integer> courseRevenue = new LinkedHashMap<>();

        String sql = "SELECT c.courseName, c.price * COUNT(e.Enroll_ID) AS revenue " +
                "FROM course c " +
                "LEFT JOIN enroll e ON c.Course_ID = e.Course_ID " +
                "WHERE c.User_ID = ? " +
                "GROUP BY c.Course_ID";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courseRevenue.put(rs.getString("courseName"), rs.getInt("revenue"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseRevenue;
    }

    // return รายได้ทั้งหมดในเดือนนี้
    public int getTotalRevenueForCurrentMonth(int userID) {
        String sql = "SELECT COALESCE(SUM(c.price), 0) AS total_revenue " +
                "FROM course c " +
                "JOIN enroll e ON c.Course_ID = e.Course_ID " +
                "WHERE c.User_ID = ? " +
                "AND MONTH(e.Enroll_Date) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(e.Enroll_Date) = YEAR(CURRENT_DATE())";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_revenue");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

//    return ยอด enroll ทั้งหมด
    public int getTotalEnrollments(int userID) {
        String sql = "SELECT COUNT(e.Enroll_ID) AS total_enrollments " +
                "FROM enroll e " +
                "JOIN course c ON e.Course_ID = c.Course_ID " +
                "WHERE c.User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_enrollments");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

//    return    index 0 คือ ยอดรวมทั้งหมด index 1 คือ ยอด enroll ทั้งหมด
    public int[] getRevenueAndEnrollCountByCourseId(int courseId) {
        String sql = "SELECT COALESCE(c.price * COUNT(e.Enroll_ID), 0) AS revenue, " +
                "COUNT(e.Enroll_ID) AS enroll_count " +
                "FROM course c " +
                "LEFT JOIN enroll e ON c.Course_ID = e.Course_ID " +
                "WHERE c.Course_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new int[]{rs.getInt("revenue"), rs.getInt("enroll_count")};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new int[]{0, 0};
    }


    public List<String> getEnrolledCourseNames(int userID) {
        List<String> courseNames = new ArrayList<>();

        String sql = "SELECT c.courseName " +
                "FROM enroll e " +
                "JOIN course c ON e.Course_ID = c.Course_ID " +
                "WHERE e.User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courseNames.add(rs.getString("courseName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    public List<String> getStudentsByCourseName(String courseName) {
        List<String> studentNames = new ArrayList<>();

        String sql = "SELECT u.userName " +
                "FROM enroll e " +
                "JOIN user u ON e.User_ID = u.User_ID " +
                "JOIN course c ON e.Course_ID = c.Course_ID " +
                "WHERE c.courseName = ? " +
                "AND u.type = 'student'";

        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentNames.add(rs.getString("userName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }



}
