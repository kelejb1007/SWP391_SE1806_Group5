package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
import model.ManageChapter;
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
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, c.chapterStatus, "
                + "n.novelName "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
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
                    chapter.setNovelName(rs.getString("novelName"));
                    list.add(chapter);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getChaptersByNovelId: " + e.getMessage(), e);
        }
        return list;
    }

    public boolean lockChapter(int chapterID, int managerID, String lockReason) {
        String sql = "UPDATE Chapter SET chapterStatus = 'locked' WHERE chapterID = ? AND chapterStatus = 'active'";
        String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, datetime, action, lockReason) VALUES (?, ?, GETDATE(), 'lock', ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                stmt.setInt(1, chapterID);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    logStmt.setString(3, lockReason != null && !lockReason.trim().isEmpty() ? lockReason : "No reason provided");
                    logStmt.executeUpdate();
                    conn.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} has been locked (status set to locked).", chapterID);
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
                + "WHERE c.chapterStatus = 'locked'";

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
                chapter.setLocked(true);
                list.add(chapter);
            }
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Number of locked chapters: {0}", list.size());
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getAllLockedChapters: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ManageChapter> getLockedChaptersByNovelId(int novelID) {
        List<ManageChapter> list = new ArrayList<>();
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "WHERE c.novelID = ? AND c.chapterStatus = 'locked'";

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
                    chapter.setLocked(true);
                    list.add(chapter);
                }
            }
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Number of locked chapters for novel ID {0}: {1}", new Object[]{novelID, list.size()});
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
        String currentStatus = getCurrentChapterStatus(chapterID);
        Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Current status of chapter ID {0}: {1}", new Object[]{chapterID, currentStatus});

        if (currentStatus == null) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "Chapter with ID {0} does not exist.", chapterID);
            return false;
        }

        if (!currentStatus.equalsIgnoreCase("locked")) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "Chapter with ID {0} cannot be unlocked because its status is {1} (expected: locked).", new Object[]{chapterID, currentStatus});
            return false;
        }

        String sql = "UPDATE Chapter SET chapterStatus = 'active' WHERE chapterID = ? AND chapterStatus = 'locked'";
        String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, datetime, action, lockReason) VALUES (?, ?, GETDATE(), 'unlock', ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                stmt.setInt(1, chapterID);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    logStmt.setString(3, "Chapter unlocked by staff");
                    logStmt.executeUpdate();
                    conn.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} has been unlocked (status set to active).", chapterID);
                    return true;
                } else {
                    conn.rollback();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "No chapter found with ID {0} to unlock or status is not locked.", chapterID);
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
    
    public boolean changeChapterStatus(int chapterID, String status) {
        String sql = "UPDATE Chapter\n"
                + "SET chapterStatus = ?\n"
                + "WHERE chapterID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, chapterID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(ChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean updateChapterLock(int chapterID, int managerID, boolean isLock, String reason) {
        String action = isLock ? "lock" : "unlock";
        String newStatus = isLock ? "locked" : "active";
        String sql = "UPDATE Chapter SET chapterStatus = ? WHERE chapterID = ?";
        String logSql = "INSERT INTO LockChapterLog (managerID, chapterID, datetime, action, lockReason) VALUES (?, ?, GETDATE(), ?, ?)";

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
                    logStmt.setString(4, reason != null && !reason.trim().isEmpty() ? reason : "No reason provided");
                    logStmt.executeUpdate();
                    conn.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} {1} successfully.", new Object[]{chapterID, action});
                    return true;
                }
                conn.rollback();
                Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "Failed to {0} chapter with ID {1}.", new Object[]{action, chapterID});
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in updateChapterLock: " + e.getMessage(), e);
        }
        return false;
    }

    public List<ManageChapter> getPendingChapters() {
        List<ManageChapter> list = new ArrayList<>();
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, c.chapterStatus, "
                + "n.novelName "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE c.chapterStatus = 'pending'";

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
                chapter.setPending(true);
                list.add(chapter);
            }
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Number of pending chapters: {0}", list.size());
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Error in getPendingChapters: " + e.getMessage(), e);
        }
        return list;
    }

    public boolean approveChapter(int chapterID, int managerID) {
        String sql = "UPDATE Chapter SET chapterStatus = 'active' WHERE chapterID = ? AND chapterStatus = 'pending'";
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql); PreparedStatement logStmt = connection.prepareStatement(
                    "INSERT INTO LockChapterLog (managerID, chapterID, datetime, action, lockReason) VALUES (?, ?, GETDATE(), 'approve', ?)")) {
                stmt.setInt(1, chapterID);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    logStmt.setString(3, "Chapter approved by staff");
                    logStmt.executeUpdate();
                    connection.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} has been approved (status set to active).", chapterID);
                    return true;
                } else {
                    connection.rollback();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "No chapter found with ID {0} to approve or not in pending status.", chapterID);
                }
            } catch (SQLException e) {
                connection.rollback();
                Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "SQL Error in approveChapter: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Connection Error in approveChapter: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean rejectChapter(int chapterID, int managerID, String rejectReason) {
        String sql = "UPDATE Chapter SET chapterStatus = 'rejected' WHERE chapterID = ? AND chapterStatus = 'pending'";
        try (Connection connection = db.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql); PreparedStatement logStmt = connection.prepareStatement(
                    "INSERT INTO LockChapterLog (managerID, chapterID, datetime, action, lockReason) VALUES (?, ?, GETDATE(), 'reject', ?)")) {
                stmt.setInt(1, chapterID);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logStmt.setInt(1, managerID);
                    logStmt.setInt(2, chapterID);
                    logStmt.setString(3, rejectReason != null && !rejectReason.trim().isEmpty() ? rejectReason : "No reason provided");
                    logStmt.executeUpdate();
                    connection.commit();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.INFO, "Chapter with ID {0} has been rejected (status set to rejected).", chapterID);
                    return true;
                } else {
                    connection.rollback();
                    Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.WARNING, "No chapter found with ID {0} to reject or not in pending status.", chapterID);
                }
            } catch (SQLException e) {
                connection.rollback();
                Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "SQL Error in rejectChapter: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, "Connection Error in rejectChapter: " + e.getMessage(), e);
        }
        return false;
    }
    
    public boolean updatePublicDate(int chapterID) {
        String sql = "UPDATE Chapter\n"
                + "SET publishedDate = SYSDATETIME()\n"
                + "WHERE chapterID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, chapterID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public boolean updateChapter(ManageChapter draft, int chapterID) {
        String sql = "UPDATE Novel SET "
                + "chapterName = ?, fileURL = ?,"
                + "where chapterID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, draft.getNovelName());
            statement.setString(2, draft.getFileURL());
            statement.setInt(3, chapterID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public ManageChapter getChapterById(int chapterID) {
        ManageChapter chapter = null;
        String sql = "SELECT chapterID, novelID, chapterNumber, chapterName, fileURL, publishedDate, chapterStatus FROM Chapter WHERE chapterID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, chapterID);
            rs = statement.executeQuery();
            if(rs.next()){
                chapter = new ManageChapter();
                chapter.setChapterID(rs.getInt("chapterID"));
                chapter.setNovelID(rs.getInt("novelID"));
                chapter.setChapterNumber(rs.getInt("chapterNumber"));
                chapter.setChapterName(rs.getString("chapterName"));
                chapter.setFileURL(rs.getString("fileURL"));
                // Chuyển đổi từ Timestamp sang LocalDateTime (nếu publishedDate có thể null)
                java.sql.Timestamp timestamp = rs.getTimestamp("publishedDate");
                chapter.setChapterStatus(rs.getString("chapterStatus"));
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return chapter;
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

        // 4. Kiểm tra getPendingChapters
        System.out.println("\n=== Testing getPendingChapters ===");
        List<ManageChapter> pendingChapters = chapterDAO.getPendingChapters();
        System.out.println("Pending chapters:");
        for (ManageChapter chapter : pendingChapters) {
            System.out.println(chapter);
        }

        // 5. Kiểm tra getCurrentChapterStatus
        System.out.println("\n=== Testing getCurrentChapterStatus ===");
        int chapterId = 1;
        String currentStatus = chapterDAO.getCurrentChapterStatus(chapterId);
        System.out.println("Current status for chapter ID " + chapterId + ": " + currentStatus);

        // 6. Kiểm tra lockChapter
        System.out.println("\n=== Testing lockChapter ===");
        chapterId = 1;
        int managerId = 1;
        String reason = "Test lock reason";
        boolean lockResult = chapterDAO.lockChapter(chapterId, managerId, reason);
        System.out.println("Lock chapter result: " + lockResult);

        // 7. Kiểm tra unlockChapter
        System.out.println("\n=== Testing unlockChapter ===");
        chapterId = 1;
        managerId = 1;
        boolean unlockResult = chapterDAO.unlockChapter(chapterId, managerId);
        System.out.println("Unlock chapter result: " + unlockResult);

        // 8. Kiểm tra approveChapter
        System.out.println("\n=== Testing approveChapter ===");
        chapterId = 19; // Sử dụng chapter pending
        managerId = 1;
        boolean approveResult = chapterDAO.approveChapter(chapterId, managerId);
        System.out.println("Approve chapter result: " + approveResult);

        // 9. Kiểm tra rejectChapter
        System.out.println("\n=== Testing rejectChapter ===");
        chapterId = 20; // Sử dụng chapter pending
        managerId = 1;
        String rejectReason = "Test reject reason";
        boolean rejectResult = chapterDAO.rejectChapter(chapterId, managerId, rejectReason);
        System.out.println("Reject chapter result: " + rejectResult);
    }
}