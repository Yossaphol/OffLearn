package Database;

import java.sql.*;
import java.util.ArrayList;

public class OrderRoadmapDB extends ConnectDB {

    public ArrayList<String[]> getCourseListByRoadmapID(String roadmapID) {
        ArrayList<String[]> courseList = new ArrayList<>();

        String sql = "SELECT c.courseName, c.courseDescription, o.order " +
                "FROM offlearn.orderroadmap o " +
                "JOIN offlearn.course c ON o.Course_ID = c.Course_ID " +
                "WHERE o.RoadMap_ID = ? " +
                "ORDER BY o.order ASC";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, roadmapID);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String[] courseDetails = new String[3];
                courseDetails[0] = rs.getString("courseName");
                courseDetails[1] = rs.getString("courseDescription");
                courseDetails[2] = String.valueOf(rs.getInt("order"));

                courseList.add(courseDetails);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }
}
