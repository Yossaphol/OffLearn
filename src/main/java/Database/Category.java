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
        String sql = "SELECT catName FROM studentdb.category";

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
        String sql = "SELECT Cat_ID FROM studentdb.category WHERE catName = ?";

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

    public ArrayList<String> getCatList(){
        return this.catList;
    }

}
