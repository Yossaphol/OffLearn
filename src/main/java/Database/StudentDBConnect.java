package Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public class StudentDBConnect extends ConnectDB {

    public ArrayList<String> getStudentNames() {
        ArrayList<String> studentNames = new ArrayList<>();
        String query = "SELECT name FROM offlearn.studentlist";

        try (Connection conn = this.connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                studentNames.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }

    public int getStudentID(String studentName) {
        String query = "SELECT StudentID FROM offlearn.studentlist WHERE name = ?";
        int studentId = -1;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                studentId = rs.getInt("StudentID");
            } else {
                System.out.println("Student not found: " + studentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentId;
    }

    public byte[] getStudentProfilePicture(String username) {
        String query = "SELECT profile FROM offlearn.user WHERE username = ?";
        byte[] profilePicture = null;

        try (Connection conn = this.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String imageUrl = rs.getString("profile");
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        URL url = new URL(imageUrl);
                        try (InputStream in = url.openStream()) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int n;
                            while ((n = in.read(buffer)) != -1) {
                                baos.write(buffer, 0, n);
                            }
                            profilePicture = baos.toByteArray();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving student profile picture: " + e.getMessage());
        }
        return profilePicture;
    }
}