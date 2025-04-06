/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Notification;
import utils.DBContext;

/**
 *
 * @author $ LienXuanThinh - CE182117
 */
public class NotificationDAO {

    private final Connection conn;
    private static final Logger LOGGER = Logger.getLogger(NotificationDAO.class.getName());

    public NotificationDAO() throws SQLException {
        conn = new DBContext().getConnection();  // Get connection from DBContext
    }

    // Add notification for a user
    public void addNotification(int userId, int novelId, String chapterName) {
        String sql = "INSERT INTO Notification (UserID, NovelID, ChapterName, CreatedAt, IsRead) VALUES (?, ?, ?, ?, ?)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, novelId);
            stmt.setString(3, chapterName);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, 0);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.log(Level.INFO, "Notification inserted successfully for user: {0}", userId);
            } else {
                LOGGER.log(Level.WARNING, "Failed to insert notification for user: {0}", userId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding notification for user: " + userId, e);
        }
    }

    // Retrieve notifications for a user by userId
    public List<Notification> getNotificationsByUserId(int userId) throws SQLException {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.ID, n.UserID, n.NovelID, n.ChapterName, n.IsRead, n.CreatedAt, nv.novelName "
                + "FROM Notification n "
                + "INNER JOIN Novel nv ON n.NovelID = nv.novelID "
                + "WHERE n.UserID = ? ORDER BY n.CreatedAt DESC";
        System.out.println("Executing SQL: " + sql + " with UserID: " + userId);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DBContext dbContext = new DBContext();

        try {
            conn = dbContext.getConnection();
            if (conn == null) {
                LOGGER.log(Level.SEVERE, "Unable to get database connection.");
                throw new SQLException("Database connection failed.");
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                try {
                    // Retrieve data from ResultSet and create Notification object
                    Notification n = new Notification(
                            rs.getInt("UserID"),
                            rs.getInt("NovelID"),
                            rs.getString("ChapterName"),
                            rs.getBoolean("IsRead"),
                            rs.getString("novelName") // Retrieve novel name
                    );
                    n.setId(rs.getInt("ID"));
                    n.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    list.add(n);
                } catch (SQLException colEx) {
                    LOGGER.log(Level.SEVERE, "Error reading column data for UserID: " + userId, colEx);
                    throw colEx;
                }
            }

            if (!found) {
                System.out.println("No notifications found for UserID: " + userId);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL error when retrieving notifications for UserID: " + userId, e);
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing ResultSet", e);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing PreparedStatement", e);
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing Connection", e);
            }
        }

        return list;
    }

    // Delete a notification
    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM Notification WHERE ID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
