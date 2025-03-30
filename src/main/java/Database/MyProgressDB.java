package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyProgressDB extends ConnectDB {

    public double loadChapterProgress(String chapID, String userID) {
        String sql = "SELECT myprogress FROM offlearn.myprogress WHERE Chapter_ID = ? AND User_ID = ?";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, chapID);
            pstm.setString(2, userID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getDouble("myprogress");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public double loadChapterLasttime(String chapID, String userID) {
        String sql = "SELECT lasttime FROM offlearn.myprogress WHERE Chapter_ID = ? AND User_ID = ?";

        try {
            Connection conn = this.connectToDB();
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, chapID);
            pstm.setString(2, userID);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getDouble("lasttime");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public double insertChapterProgress(String chapID, String userID, double progress, double lasttime) {
        String sql = "INSERT INTO offlearn.myprogress (User_ID, Chapter_ID, myprogress,lasttime) VALUES (?,?,?,?)";
        String sqlu = "UPDATE offlearn.myprogress SET Lasttime = ?, myprogress = ? WHERE User_ID = ? AND Chapter_ID = ?";

        if (loadChapterLasttime(chapID, userID) >= lasttime) {
            return 0;
        }

        try {
            Connection conn = this.connectToDB();

            if(loadChapterLasttime(chapID, userID) == -1){
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setString(1, userID);
                pstm.setString(2, chapID);
                pstm.setDouble(3, progress);
                pstm.setString(4, String.valueOf(lasttime));
                int rs = pstm.executeUpdate();
                return rs;
            }
            PreparedStatement pstm = conn.prepareStatement(sqlu);
            pstm.setString(1, String.valueOf(lasttime));
            pstm.setDouble(2, progress);
            pstm.setString(3, userID);
            pstm.setString(4, chapID);

            int rs = pstm.executeUpdate();
            return rs;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
