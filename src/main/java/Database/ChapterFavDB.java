package Database;

import java.sql.*;

public class ChapterFavDB extends ConnectDB {

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    // Create or Update reaction
    public void setReaction(int userId, int chapterId, boolean isLike, boolean isDislike) {
        String sqlCheck = "SELECT Chapterfav_ID FROM chapterfav WHERE Chapter_ID = ? AND User_ID = ?";
        String sqlInsert = "INSERT INTO chapterfav (Chapter_ID, User_ID, fav, dis) VALUES (?, ?, ?, ?)";
        String sqlUpdate = "UPDATE chapterfav SET fav = ?, dis = ? WHERE Chapter_ID = ? AND User_ID = ?";

        try (Connection conn = this.connectToDB();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setInt(1, chapterId);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Update
                try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                    updateStmt.setBoolean(1, isLike);
                    updateStmt.setBoolean(2, isDislike);
                    updateStmt.setInt(3, chapterId);
                    updateStmt.setInt(4, userId);
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert
                try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                    insertStmt.setInt(1, chapterId);
                    insertStmt.setInt(2, userId);
                    insertStmt.setBoolean(3, isLike);
                    insertStmt.setBoolean(4, isDislike);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete reaction
    public void deleteFromDB(int userId, int chapterId) {
        String sql = "DELETE FROM chapterfav WHERE Chapter_ID = ? AND User_ID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chapterId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get current reaction by user
    public boolean[] getUserReaction(int userId, int chapterId) {
        boolean[] reaction = new boolean[2]; // [0] = fav, [1] = dis
        String sql = "SELECT fav, dis FROM chapterfav WHERE Chapter_ID = ? AND User_ID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chapterId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reaction[0] = rs.getBoolean("fav");
                reaction[1] = rs.getBoolean("dis");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reaction;
    }

    // Get total likes and dislikes
    public int[] getChapterReactionTotals(int chapterId) {
        int[] totals = new int[2]; // [0] = total likes, [1] = total dislikes
        String sql = "SELECT SUM(CASE WHEN fav = 1 THEN 1 ELSE 0 END) as totalFav, " +
                "SUM(CASE WHEN dis = 1 THEN 1 ELSE 0 END) as totalDis " +
                "FROM chapterfav WHERE Chapter_ID = ?";
        try (Connection conn = this.connectToDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chapterId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totals[0] = rs.getInt("totalFav");
                totals[1] = rs.getInt("totalDis");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totals;
    }

    public void toggleReaction(int userId, int chapterId, boolean isLike) {
        boolean[] current = getUserReaction(userId, chapterId);
        boolean liked = current[0];
        boolean disliked = current[1];

        if (isLike) {
            if (liked) {
                // User already liked → unset like
                deleteFromDB(userId, chapterId);
            } else {
                // Set like, unset dislike
                setReaction(userId, chapterId, true, false);
            }
        } else {
            if (disliked) {
                // User already disliked → unset dislike
                deleteFromDB(userId, chapterId);
            } else {
                // Set dislike, unset like
                setReaction(userId, chapterId, false, true);
            }
        }
    }


}
