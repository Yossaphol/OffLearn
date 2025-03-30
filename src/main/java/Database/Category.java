package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category extends ConnectDB{

    ArrayList<String> catList = new ArrayList<>();

    public Category() {
        insertCatList();
    }

    public void insertCatList(){
        String sql = "SELECT catName FROM offlearn.category";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()){
                catList.add(rs.getString("catName"));
            }
            System.out.println();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getCatID(String name){
        String sql = "SELECT Cat_ID FROM offlearn.category WHERE catName = ?";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("Cat_ID");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public String getCatName(String id){
        String sql = "SELECT catname FROM offlearn.category WHERE Cat_ID = ?";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("catname");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<String> getCatList(){
        return this.catList;
    }

    public String getCategoryByCourseID(int courseID) {
        String sql = "SELECT cat.CatName FROM offlearn.course c " +
                "JOIN offlearn.category cat ON c.Cat_ID = cat.Cat_ID " +
                "WHERE c.Course_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("CatName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
