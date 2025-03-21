package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends ConnectDB{

//    public User getUserInfo(String username) {
//        String query = "SELECT User_ID, Username, Fullname, Password, Profile, Email FROM offlearn.user WHERE Username = ?";
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



    public String loginConnect(String username, String password) {
        String query = "SELECT type FROM offlearn.user WHERE Username = ? AND Password = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean signupConnect(String firstname, String lastname, String username, String password, String email) {
        String query = "INSERT INTO offlearn.user (firstname, lastname, Username, Password, Email) VALUES (?, ?, ?, ?, ?)";

        if (isUsernameExist(username)) {
            return false;
        }
        if (isEmailExist(email)) {
            return false;
        }

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, email);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isUsernameExist(String username) {
        String query = "SELECT COUNT(*) FROM offlearn.user WHERE Username = ?";

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
        String query = "SELECT COUNT(*) FROM offlearn.user WHERE Email = ?";

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
        String query = "SELECT COUNT(*) FROM offlearn.user WHERE Username = ? AND Email = ?";

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
        String query = "UPDATE offlearn.user SET Password = ? WHERE Username = ? AND Email = ?";

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

    public String getProfile(String name) {
        String sql = "SELECT Profile FROM offlearn.user WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, name);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String profilePath = rs.getString("Profile");
                    return (profilePath != null) ? profilePath : "/img/Profile/user.png";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "/img/Profile/user.png";
    }

}

