package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LockChapterLog;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class LockChapterLogDAO {

    private final DBContext db;
    private static final Logger LOGGER = Logger.getLogger(LockChapterLogDAO.class.getName());

    public LockChapterLogDAO() {
        db = new DBContext();
    }

    /**
     * Lấy danh sách các chapter bị khóa cho một novel cụ thể
     *
     * @param novelID ID của novel
     * @return Danh sách các LockChapterLog
     */
    public List<LockChapterLog> getLockedChaptersByNovel(int novelID) {
        List<LockChapterLog> lockedChapters = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus, l.logID, l.action, l.lockReason, l.lockDate "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "JOIN LockChapterLog l ON c.chapterID = l.chapterID "
                + "WHERE c.novelID = ? AND l.action = 'lock' "
                + "ORDER BY l.lockDate DESC";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, novelID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LockChapterLog lockedChapter = new LockChapterLog(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(), // Chuyển Timestamp thành LocalDateTime
                        rs.getString("chapterStatus"),
                        rs.getInt("logID"),
                        rs.getString("action"),
                        rs.getString("lockReason"),
                        rs.getTimestamp("lockDate")
                );
                lockedChapters.add(lockedChapter);
            }
            LOGGER.log(Level.INFO, "Fetched locked chapters for novel ID: {0}, found {1} records",
                    new Object[]{novelID, lockedChapters.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching locked chapters for novel ID: " + novelID, e);
            e.printStackTrace();
        }
        return lockedChapters;
    }

    /**
     * Lấy danh sách các chapter bị khóa cho một user cụ thể
     *
     * @param userID ID của user
     * @return Danh sách các LockChapterLog
     */
    public List<LockChapterLog> getLockedChaptersByUser(int userID) {
        List<LockChapterLog> lockedChapters = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus, l.logID, l.action, l.lockReason, l.lockDate "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "JOIN LockChapterLog l ON c.chapterID = l.chapterID "
                + "WHERE n.userID = ? AND l.action = 'lock' "
                + "ORDER BY l.lockDate DESC";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LockChapterLog lockedChapter = new LockChapterLog(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(), // Chuyển Timestamp thành LocalDateTime
                        rs.getString("chapterStatus"),
                        rs.getInt("logID"),
                        rs.getString("action"),
                        rs.getString("lockReason"),
                        rs.getTimestamp("lockDate")
                );
                lockedChapters.add(lockedChapter);
            }
            LOGGER.log(Level.INFO, "Fetched locked chapters for user ID: {0}, found {1} records",
                    new Object[]{userID, lockedChapters.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching locked chapters for user ID: " + userID, e);
            e.printStackTrace();
        }
        return lockedChapters;
    }

    /**
     * Lấy tất cả các chapter bị khóa trong hệ thống
     *
     * @return Danh sách các LockChapterLog
     */
    public List<LockChapterLog> getAllLockedChapters() {
        List<LockChapterLog> lockedChapters = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus, l.logID, l.action, l.lockReason, l.lockDate "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "JOIN LockChapterLog l ON c.chapterID = l.chapterID "
                + "WHERE l.action = 'lock' "
                + "ORDER BY l.lockDate DESC";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LockChapterLog lockedChapter = new LockChapterLog(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(), // Chuyển Timestamp thành LocalDateTime
                        rs.getString("chapterStatus"),
                        rs.getInt("logID"),
                        rs.getString("action"),
                        rs.getString("lockReason"),
                        rs.getTimestamp("lockDate")
                );
                lockedChapters.add(lockedChapter);
            }
            LOGGER.log(Level.INFO, "Fetched all locked chapters, found {0} records", lockedChapters.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all locked chapters", e);
            e.printStackTrace();
        }
        return lockedChapters;
    }

    // Thêm phương thức đếm tổng số chapter bị khóa
    public int getTotalLockedChapters() {
        String query = "SELECT COUNT(*) FROM LockChapterLog WHERE action = 'lock'";
        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting total locked chapters", e);
            e.printStackTrace();
        }
        return 0;
    }

    // Thêm phương thức đếm tổng số chapter bị khóa theo novel
    public int getTotalLockedChaptersByNovel(int novelID) {
        String query = "SELECT COUNT(*) FROM LockChapterLog l "
                + "JOIN Chapter c ON l.chapterID = c.chapterID "
                + "WHERE c.novelID = ? AND l.action = 'lock'";
        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, novelID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting locked chapters for novel ID: " + novelID, e);
            e.printStackTrace();
        }
        return 0;
    }

    // Thêm phương thức đếm tổng số chapter bị khóa theo user
    public int getTotalLockedChaptersByUser(int userID) {
        String query = "SELECT COUNT(*) FROM LockChapterLog l "
                + "JOIN Chapter c ON l.chapterID = c.chapterID "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE n.userID = ? AND l.action = 'lock'";
        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting locked chapters for user ID: " + userID, e);
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật phương thức getLockedChaptersByNovel với pagination
    public List<LockChapterLog> getLockedChaptersByNovel(int novelID, int page, int limit) {
        List<LockChapterLog> lockedChapters = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus, l.logID, l.action, l.lockReason, l.lockDate "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "JOIN LockChapterLog l ON c.chapterID = l.chapterID "
                + "WHERE c.novelID = ? AND l.action = 'lock' "
                + "ORDER BY l.lockDate DESC "
                + "LIMIT ? OFFSET ?";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, novelID);
            ps.setInt(2, limit);
            ps.setInt(3, (page - 1) * limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LockChapterLog lockedChapter = new LockChapterLog(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus"),
                        rs.getInt("logID"),
                        rs.getString("action"),
                        rs.getString("lockReason"),
                        rs.getTimestamp("lockDate")
                );
                lockedChapters.add(lockedChapter);
            }
            LOGGER.log(Level.INFO, "Fetched locked chapters for novel ID: {0} (page {1}, limit {2}), found {3} records",
                    new Object[]{novelID, page, limit, lockedChapters.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching locked chapters for novel ID: " + novelID, e);
            e.printStackTrace();
        }
        return lockedChapters;
    }

    // Cập nhật phương thức getLockedChaptersByUser với pagination
    public List<LockChapterLog> getLockedChaptersByUser(int userID, int page, int limit) {
        List<LockChapterLog> lockedChapters = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus, l.logID, l.action, l.lockReason, l.lockDate "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "JOIN LockChapterLog l ON c.chapterID = l.chapterID "
                + "WHERE n.userID = ? AND l.action = 'lock' "
                + "ORDER BY l.lockDate DESC "
                + "LIMIT ? OFFSET ?";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ps.setInt(2, limit);
            ps.setInt(3, (page - 1) * limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LockChapterLog lockedChapter = new LockChapterLog(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus"),
                        rs.getInt("logID"),
                        rs.getString("action"),
                        rs.getString("lockReason"),
                        rs.getTimestamp("lockDate")
                );
                lockedChapters.add(lockedChapter);
            }
            LOGGER.log(Level.INFO, "Fetched locked chapters for user ID: {0} (page {1}, limit {2}), found {3} records",
                    new Object[]{userID, page, limit, lockedChapters.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching locked chapters for user ID: " + userID, e);
            e.printStackTrace();
        }
        return lockedChapters;
    }

    // Cập nhật phương thức getAllLockedChapters với pagination
    public List<LockChapterLog> getAllLockedChapters(int page, int limit) {
        List<LockChapterLog> lockedChapters = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus, l.logID, l.action, l.lockReason, l.lockDate "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "JOIN LockChapterLog l ON c.chapterID = l.chapterID "
                + "WHERE l.action = 'lock' "
                + "ORDER BY l.lockDate DESC "
                + "LIMIT ? OFFSET ?";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, (page - 1) * limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LockChapterLog lockedChapter = new LockChapterLog(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus"),
                        rs.getInt("logID"),
                        rs.getString("action"),
                        rs.getString("lockReason"),
                        rs.getTimestamp("lockDate")
                );
                lockedChapters.add(lockedChapter);
            }
            LOGGER.log(Level.INFO, "Fetched all locked chapters (page {0}, limit {1}), found {2} records",
                    new Object[]{page, limit, lockedChapters.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all locked chapters", e);
            e.printStackTrace();
        }
        return lockedChapters;
    }
}
