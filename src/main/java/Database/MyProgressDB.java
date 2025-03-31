package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyProgressDB extends ConnectDB {

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

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

    public double sumChapterProgress(String CourseID, String chapID, String userID) {
        String sqlC = "SELECT COUNT(*) FROM offlearn.chapter WHERE Course_ID = ?";
        String sql = "SELECT Chapter_ID FROM offlearn.chapter WHERE Course_ID = ?";
        String sqlS = "SELECT myprogress FROM offlearn.myprogress WHERE User_ID = ? AND Chapter_ID = ?";
        double totalProgress = 0;
        double tmpS = 0;
        int tmpC = 0;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmS = conn.prepareStatement(sqlS);
             PreparedStatement pstmC = conn.prepareStatement(sqlC);
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstmC.setString(1, CourseID);

            pstm.setString(1, CourseID);

            ResultSet rc = pstmC.executeQuery();
            ResultSet r = pstm.executeQuery();

            while (r.next()) {
                pstmS.setString(1, userID);
                pstmS.setString(2, r.getString("Chapter_ID"));
                ResultSet r1 = pstmS.executeQuery();
                System.out.println(r.getString("Chapter_ID"));
                if (r1.next()) {
                    System.out.println(r1.getDouble("myprogress"));
                    tmpS += r1.getDouble("myprogress");
                }
            }
            if (rc.next()) {
                tmpC = rc.getInt(1);
            }
            System.out.println("S "+tmpS + " C" + tmpC);
            if ( tmpS == 0 || tmpC == 0) {
                return 0;
            }
            totalProgress = tmpS / tmpC;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(totalProgress);
        return totalProgress;
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

    public double saveToDB(String chapID, String userID, double progress, double lasttime) {
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
