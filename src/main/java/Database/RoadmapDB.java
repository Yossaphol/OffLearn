package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoadmapDB extends ConnectDB {

    ArrayList<String[]> roadmapList = new ArrayList<>();

    public RoadmapDB() {
        // Constructor
    }

    public void insertRoadmapList(String catID) {
        roadmapList.clear();

        String sql = "SELECT RoadMap_ID, name, RMDescription FROM offlearn.roadmap WHERE Cat_ID = ?";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, catID);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String[] roadmapData = new String[3];
                roadmapData[0] = rs.getString("RoadMap_ID");
                roadmapData[1] = rs.getString("name");
                roadmapData[2] = rs.getString("RMDescription");
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
