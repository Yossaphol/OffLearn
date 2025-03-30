package Database;

import a_Session.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends ConnectDB{

    //setting page
    public User getUserInfo(String username) {
        String query = "SELECT Firstname, Lastname, Username, Email, Password, Profile, description FROM offlearn.user WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstname = rs.getString("Firstname");
                String lastname = rs.getString("Lastname");
                String user = rs.getString("Username");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String profile = rs.getString("Profile");
                String description = rs.getString("description");
                return new User(firstname, lastname, user, email, password, profile, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserInfo(String username, String firstname, String lastname, String email) {
        String query = "UPDATE offlearn.user SET Firstname = ?, Lastname = ?, Email = ? WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, email);
            pstmt.setString(4, username);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserInfoes(String username, String firstname, String lastname, String email, String description) {
        String query = "UPDATE offlearn.user SET firstname = ?, lastname = ?, email = ? , description = ? WHERE username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, email);
            pstmt.setString(5, username);
            pstmt.setString(4, description);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfileImage(String username, String profilePath) {
        String query = "UPDATE offlearn.user SET Profile = ? WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, profilePath);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getOldPasswordFromDB(String username) {
        String query = "SELECT Password FROM offlearn.user WHERE Username = ?";

        try (Connection conn = connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //update password
    public boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE offlearn.user SET Password = ? WHERE Username = ?";

        try (Connection conn = connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //login page
    public String loginConnect(String username, String password) {
        String query = "SELECT type,User_ID FROM offlearn.user WHERE Username = ? AND Password = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                SessionManager.getInstance().setUserID(rs.getString("User_ID"));
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //signup page
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
    //check in signup page that doesn't have same username in db
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
    //check in signup page that doesn't have same email in db
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
    //forgotpassword page
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
//forgotpassword page
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

    //searchbarController and gchat user profile
    public String getProfile(String username) {
        String sql = "SELECT Profile FROM offlearn.user WHERE Username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, username);

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

    public int getUserId(String username) {
        String query = "SELECT User_ID FROM offlearn.user WHERE username = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("User_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String[] getUserNameProfileAndSpecByCourseID(int courseID) {
        String sql = "SELECT u.username, u.profile, u.description FROM offlearn.course c " +
                "JOIN offlearn.user u ON c.User_ID = u.User_ID " +
                "WHERE c.Course_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("Username");
                String profile = rs.getString("Profile");
                String specification = rs.getString("description");

                if (profile == null || profile.isEmpty()) {
                    profile = "/img/Profile/user.png";
                }

                if (specification == null) {
                    specification = "-";
                }

                return new String[]{username, profile, specification};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDescByUserID(int userId) {
        String query = "SELECT description FROM offlearn.user WHERE User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

