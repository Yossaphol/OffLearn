package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends ConnectDB{

//    public User getUserInfo(String username) {
//        String query = "SELECT User_ID, Username, Fullname, Password, Profile, Email FROM studentdb.user WHERE Username = ?";
//
//        try (Connection conn = this.connectToDB();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, username);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                int userID = rs.getInt("User_ID");
//                String user = rs.getString("Username");
//                String fullname = rs.getString("Fullname");
//                String email = rs.getString("Email");
//                String password = rs.getString("Password");
//                byte[] profile = rs.getBytes("Profile");
//                return new User(userID, user, fullname, email, password, profile);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



    public boolean loginConnect(String username, String password){
        String query = "SELECT Username, Password FROM studentdb.user WHERE Username = ? AND Password = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signupConnect(String fullname, String username, String password, String email) {
        String query = "INSERT INTO studentdb.user (Fullname, Username, Password, Email) VALUES (?, ?, ?, ?)";

        if (isUsernameExist(username)) {
            return false;
        }
        if (isEmailExist(email)) {
            return false;
        }

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, fullname);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isUsernameExist(String username) {
        String query = "SELECT COUNT(*) FROM studentdb.user WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean isEmailExist(String email) {
        String query = "SELECT COUNT(*) FROM studentdb.user WHERE Email = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserValid(String username, String email) {
        String query = "SELECT COUNT(*) FROM studentdb.user WHERE Username = ? AND Email = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePasswordConnect(String email, String username, String newPassword) {
        String query = "UPDATE studentdb.user SET Password = ? WHERE Username = ? AND Email = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

