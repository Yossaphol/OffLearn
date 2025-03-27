package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoadmapDB extends ConnectDB {

    ArrayList<String[]> roadmapList = new ArrayList<>();

    public RoadmapDB() {
        // Constructor เปล่าไว้ก่อน
    }

    public void insertRoadmapList(String catID) {
        roadmapList.clear(); // เคลียร์รายการเก่าก่อนจะโหลดใหม่

        String sql = "SELECT RoadMap_ID, name, RMDescription FROM offlearn.roadmap WHERE Cat_ID = ?";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, catID);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String[] roadmapData = new String[3];
                roadmapData[0] = rs.getString("RoadMap_ID"); // RoadmapID
                roadmapData[1] = rs.getString("name");         // ชื่อ Roadmap
                roadmapData[2] = rs.getString("RMDescription"); // รายละเอียดของ Roadmap
                roadmapList.add(roadmapData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getRoadmapList() {
        return roadmapList;
    }
}
