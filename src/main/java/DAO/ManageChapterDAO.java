package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ManageChapter;
import model.Novel;
import utils.DBContext;

/**
 * @author Nguyen Ngoc Phat - CE180321
 */
public class ManageChapterDAO {

    private final DBContext db;

    public ManageChapterDAO() {
        db = new DBContext();
    }

    public List<ManageChapter> getChaptersByNovelId(int novelID) {
        List<ManageChapter> list = new ArrayList<>();
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "WHERE c.novelID = ? AND c.chapterStatus = 'active'";

        try (Connection connection = db.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novelID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ManageChapter chapter = new ManageChapter();
                    chapter.setChapterID(rs.getInt("chapterID"));
                    chapter.setNovelID(rs.getInt("novelID"));
                    chapter.setChapterNumber(rs.getInt("chapterNumber"));
                    chapter.setChapterName(rs.getString("chapterName"));
                    chapter.setFileURL(rs.getString("fileURL"));
                    Timestamp timestamp = rs.getTimestamp("publishedDate");
                    chapter.setPublishedDate(timestamp != null ? new java.util.Date(timestamp.getTime()) : null);
                    chapter.setChapterStatus(rs.getString("chapterStatus"));
                    list.add(chapter);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getChaptersByNovelId: " + e.getMessage(), e);
        }
        return list;
    }

    public boolean lockChapter(int chapterID, int managerID, String lockReason) {
        String sql = "UPDATE Chapter SET chapterStatus = 'inactive' WHERE chapterID = ? AND chapterStatus = 'active'";
        String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, dateTime, action, lockReason) VALUES (?, ?, GETDATE(), 'lock', ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                stmt.setInt(1, chapterID);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    logStmt.setString(3, lockReason != null ? lockReason : "No reason provided");
                    logStmt.executeUpdate();
                    conn.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} has been locked (status set to inactive).", chapterID);
                    return true;
                } else {
                    conn.rollback();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "No chapter found with ID {0} to lock or already locked.", chapterID);
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();
                Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "SQL Error in lockChapter: " + e.getMessage(), e);
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Connection Error in lockChapter: " + e.getMessage(), e);
            return false;
        }
    }

    public List<ManageChapter> getAllLockedChapters() {
        List<ManageChapter> list = new ArrayList<>();
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, c.chapterStatus, "
                + "n.novelName "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE c.chapterStatus = 'inactive'"; // Chỉ lấy chapter có chapterStatus = 'inactive'

        try (Connection connection = db.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                ManageChapter chapter = new ManageChapter();
                chapter.setChapterID(rs.getInt("chapterID"));
                chapter.setNovelID(rs.getInt("novelID"));
                chapter.setChapterNumber(rs.getInt("chapterNumber"));
                chapter.setChapterName(rs.getString("chapterName"));
                chapter.setFileURL(rs.getString("fileURL"));
                Timestamp timestamp = rs.getTimestamp("publishedDate");
                chapter.setPublishedDate(timestamp != null ? new java.util.Date(timestamp.getTime()) : null);
                chapter.setNovelName(rs.getString("novelName"));
                chapter.setChapterStatus(rs.getString("chapterStatus"));
                chapter.setLocked(true); // Đánh dấu là locked vì chapterStatus = 'inactive'
                list.add(chapter);
                System.out.println("Found locked chapter: " + chapter.getChapterID() + " - " + chapter.getChapterName());
            }
            System.out.println("Number of locked chapters in getAllLockedChapters: " + list.size());
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getAllLockedChapters: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ManageChapter> getLockedChaptersByNovelId(int novelID) {
        List<ManageChapter> list = new ArrayList<>();
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "WHERE c.novelID = ? AND c.chapterStatus = 'inactive'"; // Chỉ lấy chapter có chapterStatus = 'inactive'

        try (Connection connection = db.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novelID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ManageChapter chapter = new ManageChapter();
                    chapter.setChapterID(rs.getInt("chapterID"));
                    chapter.setNovelID(rs.getInt("novelID"));
                    chapter.setChapterNumber(rs.getInt("chapterNumber"));
                    chapter.setChapterName(rs.getString("chapterName"));
                    chapter.setFileURL(rs.getString("fileURL"));
                    Timestamp timestamp = rs.getTimestamp("publishedDate");
                    chapter.setPublishedDate(timestamp != null ? new java.util.Date(timestamp.getTime()) : null);
                    chapter.setChapterStatus(rs.getString("chapterStatus"));
                    chapter.setLocked(true); // Đánh dấu là locked vì chapterStatus = 'inactive'
                    list.add(chapter);
                    System.out.println("Found locked chapter for novel ID " + novelID + ": " + chapter.getChapterID() + " - " + chapter.getChapterName());
                }
                System.out.println("Total locked chapters for novel ID " + novelID + ": " + list.size());
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getLockedChaptersByNovelId: " + e.getMessage(), e);
        }
        return list;
    }

    public String getCurrentChapterStatus(int chapterID) {
        String sql = "SELECT chapterStatus FROM Chapter WHERE chapterID = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chapterID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("chapterStatus");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getCurrentChapterStatus: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean unlockChapter(int chapterID, int managerID) {
        // Kiểm tra trạng thái hiện tại của chapter
        String currentStatus = getCurrentChapterStatus(chapterID);
        Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Current status of chapter ID {0}: {1}", new Object[]{chapterID, currentStatus});

        if (currentStatus == null) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "Chapter with ID {0} does not exist.", chapterID);
            return false;
        }

        if (!currentStatus.equalsIgnoreCase("inactive")) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "Chapter with ID {0} cannot be unlocked because its status is {1} (expected: inactive).", new Object[]{chapterID, currentStatus});
            return false;
        }

        String sql = "UPDATE Chapter SET chapterStatus = 'active' WHERE chapterID = ? AND chapterStatus = 'inactive'";
        String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, dateTime, action, lockReason) VALUES (?, ?, GETDATE(), 'unlock', 'Chapter unlocked by staff')";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                stmt.setInt(1, chapterID);
                int rows = stmt.executeUpdate();
                Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Rows updated in Chapter table: {0}", rows);

                if (rows > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    int logRows = logStmt.executeUpdate();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Rows inserted into LockChapterLog: {0}", logRows);

                    conn.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} has been unlocked (status set to active).", chapterID);
                    return true;
                } else {
                    conn.rollback();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "No chapter found with ID {0} to unlock or status is not inactive.", chapterID);
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();
                Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "SQL Error in unlockChapter: " + e.getMessage(), e);
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Connection Error in unlockChapter: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateChapterLock(int chapterID, int managerID, boolean isLock, String reason) {
        String action = isLock ? "lock" : "unlock";
        String newStatus = isLock ? "inactive" : "active"; // Đồng bộ với logic lock/unlock
        String sql = "UPDATE Chapter SET chapterStatus = ? WHERE chapterID = ?";
        String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, dateTime, action, lockReason) VALUES (?, ?, GETDATE(), ?, ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                stmt.setString(1, newStatus);
                stmt.setInt(2, chapterID);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    logStmt.setString(3, action);
                    logStmt.setString(4, reason != null ? reason : "");
                    logStmt.executeUpdate();
                    conn.commit();
                    return true;
                }
                conn.rollback();
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in updateChapterLock: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean approveChapter(int chapterID, int managerID) {
        String sql = "UPDATE Chapter SET chapterStatus = 'approved' WHERE chapterID = ? AND chapterStatus != 'approved'";
        try (Connection connection = db.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, chapterID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, dateTime, action, lockReason) "
                        + "VALUES (?, ?, GETDATE(), 'approve', 'Chapter approved by staff')";
                try (PreparedStatement logStatement = connection.prepareStatement(logSql)) {
                    logStatement.setInt(1, managerID);
                    logStatement.setInt(2, chapterID);
                    logStatement.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in approveChapter: " + e.getMessage(), e);
        }
        return false;
    }

    public static void main(String[] args) {
        ManageChapterDAO chapterDAO = new ManageChapterDAO();

        // 1. Kiểm tra getChaptersByNovelId
        System.out.println("=== Testing getChaptersByNovelId ===");
        int novelId = 1;
        List<ManageChapter> chapters = chapterDAO.getChaptersByNovelId(novelId);
        System.out.println("Chapters for novel ID " + novelId + ":");
        for (ManageChapter chapter : chapters) {
            System.out.println(chapter);
        }

        // 2. Kiểm tra getAllLockedChapters
        System.out.println("\n=== Testing getAllLockedChapters ===");
        List<ManageChapter> lockedChapters = chapterDAO.getAllLockedChapters();
        System.out.println("All locked chapters:");
        for (ManageChapter chapter : lockedChapters) {
            System.out.println(chapter);
        }

        // 3. Kiểm tra getLockedChaptersByNovelId
        System.out.println("\n=== Testing getLockedChaptersByNovelId ===");
        novelId = 1;
        lockedChapters = chapterDAO.getLockedChaptersByNovelId(novelId);
        System.out.println("Locked chapters for novel ID " + novelId + ":");
        for (ManageChapter chapter : lockedChapters) {
            System.out.println(chapter);
        }

        // 4. Kiểm tra getCurrentChapterStatus
        System.out.println("\n=== Testing getCurrentChapterStatus ===");
        int chapterId = 1;
        String currentStatus = chapterDAO.getCurrentChapterStatus(chapterId);
        System.out.println("Current status for chapter ID " + chapterId + ": " + currentStatus);

        // 5. Kiểm tra lockChapter
        System.out.println("\n=== Testing lockChapter ===");
        chapterId = 1;
        int managerId = 1;
        String reason = "Test lock reason";
        boolean lockResult = chapterDAO.lockChapter(chapterId, managerId, reason);
        System.out.println("Lock chapter result: " + lockResult);

        // 6. Kiểm tra unlockChapter
        System.out.println("\n=== Testing unlockChapter ===");
        chapterId = 1;
        managerId = 1;
        boolean unlockResult = chapterDAO.unlockChapter(chapterId, managerId);
        System.out.println("Unlock chapter result: " + unlockResult);

        // 7. Kiểm tra approveChapter
        System.out.println("\n=== Testing approveChapter ===");
        chapterId = 1;
        managerId = 1;
        boolean approveResult = chapterDAO.approveChapter(chapterId, managerId);
        System.out.println("Approve chapter result: " + approveResult);
    }
}