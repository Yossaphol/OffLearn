package Database;

import a_Session.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyCourseDB extends ConnectDB {
    private ArrayList<MyCourse>  allMyCourse = new ArrayList<>();

    public MyCourseDB() { getAllMyCourse(); }

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    private void getAllMyCourse() {
        String sql = "SELECT Course_ID FROM enroll WHERE User_ID = ?";
        String sqlCourse = "SELECT * FROM course WHERE Course_ID = ?";

        try (Connection conn = this.connectToDB();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sqlCourse)) {

            pstmt.setInt(1, Integer.parseInt(SessionManager.getInstance().getUserID()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                pstmt2.setInt(1, rs.getInt("Course_ID"));
                ResultSet tmp = pstmt2.executeQuery();
                while (tmp.next()){
                    String Course_ID = String.valueOf(tmp.getInt("Course_ID"));
                    String Cat_ID = String.valueOf(tmp.getInt("Cat_ID"));
                    String courseName = tmp.getString("courseName");
                    String courseDescription = tmp.getString("courseDescription");
                    String price = tmp.getString("price");
                    String image = tmp.getString("image");
                    String User_ID = tmp.getString("User_ID");
                    String Course_Code = tmp.getString("Course_Code");
                    MyCourse myCourse = new MyCourse(Course_ID, Cat_ID, courseName, courseDescription, price, image, User_ID, Course_Code);
                    allMyCourse.add(myCourse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MyCourse> getallMyCourse() { return allMyCourse; }


}
