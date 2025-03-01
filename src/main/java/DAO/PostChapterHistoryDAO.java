package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
import utils.DBContext;

/**
 *
 * @author Grok 3 - xAI
 */
public class PostChapterHistoryDAO {

    private final DBContext db;
    private static final Logger LOGGER = Logger.getLogger(PostChapterHistoryDAO.class.getName());

    public PostChapterHistoryDAO() {
        db = new DBContext();
    }

    /**
     * Lấy lịch sử đăng chapter cho một novel cụ thể với phân trang
     *
     * @param novelID ID của novel
     * @param page Trang hiện tại
     * @param limit Số bản ghi trên mỗi trang
     * @return Danh sách các Chapter
     */
    public List<Chapter> getChapterPostingHistoryByNovel(int novelID, int page, int limit) {
        List<Chapter> historyList = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE c.novelID = ? "
                + "ORDER BY c.publishedDate DESC "
                + "LIMIT ? OFFSET ?";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, novelID);
            ps.setInt(2, limit);
            ps.setInt(3, (page - 1) * limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Chapter history = new Chapter(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus")
                );
                historyList.add(history);
            }
            LOGGER.log(Level.INFO, "Fetched chapter posting history for novel ID: {0} (page {1}, limit {2}), found {3} records",
                    new Object[]{novelID, page, limit, historyList.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching chapter posting history for novel ID: " + novelID, e);
            e.printStackTrace();
        }
        return historyList;
    }

    /**
     * Lấy lịch sử đăng chapter cho một user cụ thể với phân trang
     *
     * @param userID ID của user
     * @param page Trang hiện tại
     * @param limit Số bản ghi trên mỗi trang
     * @return Danh sách các Chapter
     */
    public List<Chapter> getChapterPostingHistoryByUser(int userID, int page, int limit) {
        List<Chapter> historyList = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE n.userID = ? "
                + "ORDER BY c.publishedDate DESC "
                + "LIMIT ? OFFSET ?";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ps.setInt(2, limit);
            ps.setInt(3, (page - 1) * limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Chapter history = new Chapter(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus")
                );
                historyList.add(history);
            }
            LOGGER.log(Level.INFO, "Fetched chapter posting history for user ID: {0} (page {1}, limit {2}), found {3} records",
                    new Object[]{userID, page, limit, historyList.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching chapter posting history for user ID: " + userID, e);
            e.printStackTrace();
        }
        return historyList;
    }

    // Giữ nguyên các phương thức cũ (không phân trang) để backward compatibility, nhưng có thể xóa nếu không cần
    public List<Chapter> getChapterPostingHistoryByNovel(int novelID) {
        List<Chapter> historyList = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE c.novelID = ? "
                + "ORDER BY c.publishedDate DESC";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, novelID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Chapter history = new Chapter(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus")
                );
                historyList.add(history);
            }
            LOGGER.log(Level.INFO, "Fetched chapter posting history for novel ID: {0}, found {1} records",
                    new Object[]{novelID, historyList.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching chapter posting history for novel ID: " + novelID, e);
            e.printStackTrace();
        }
        return historyList;
    }

    public List<Chapter> getChapterPostingHistoryByUser(int userID) {
        List<Chapter> historyList = new ArrayList<>();
        String query = "SELECT c.chapterID, c.novelID, n.novelName, c.chapterNumber, c.chapterName, c.fileURL, "
                + "c.publishedDate, c.chapterStatus "
                + "FROM Chapter c "
                + "JOIN Novel n ON c.novelID = n.novelID "
                + "WHERE n.userID = ? "
                + "ORDER BY c.publishedDate DESC";

        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Chapter history = new Chapter(
                        rs.getInt("chapterID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("chapterNumber"),
                        rs.getString("chapterName"),
                        rs.getString("fileURL"),
                        rs.getTimestamp("publishedDate").toLocalDateTime(),
                        rs.getString("chapterStatus")
                );
                historyList.add(history);
            }
            LOGGER.log(Level.INFO, "Fetched chapter posting history for user ID: {0}, found {1} records",
                    new Object[]{userID, historyList.size()});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching chapter posting history for user ID: " + userID, e);
            e.printStackTrace();
        }
        return historyList;
    }

    // Thêm phương thức đếm tổng số chapter trong lịch sử theo novel
    public int getTotalChapterHistoryByNovel(int novelID) {
        String query = "SELECT COUNT(*) FROM Chapter WHERE novelID = ?";
        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, novelID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting chapter history for novel ID: " + novelID, e);
            e.printStackTrace();
        }
        return 0;
    }

    // Thêm phương thức đếm tổng số chapter trong lịch sử theo user
    public int getTotalChapterHistoryByUser(int userID) {
        String query = "SELECT COUNT(*) FROM Chapter c JOIN Novel n ON c.novelID = n.novelID WHERE n.userID = ?";
        try ( Connection connection = db.getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting chapter history for user ID: " + userID, e);
            e.printStackTrace();
        }
        return 0;
    }
}
