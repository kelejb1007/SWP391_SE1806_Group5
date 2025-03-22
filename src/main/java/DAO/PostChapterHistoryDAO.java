package DAO;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
import model.ChapterSubmission;
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

    public List<ChapterSubmission> getSubmisstionHistory(int userID) {
        List<ChapterSubmission> list = new ArrayList<>();
        String sql = "SELECT cs.submissionCID, cs.chapterID, cs.userID, cs.managerID, cs.draftID, cs.submissionDate, \n"
                + "cs.approvalDate, type, status, cs.reasonRejected, c.chapterName, c.chapterNumber, n.novelID, n.novelName\n"
                + "FROM ChapterSubmission cs\n"
                + "JOIN Chapter c ON c.chapterID = cs.chapterID\n"
                + "JOIN Novel n ON c.novelID = n.novelID\n"
                + "WHERE cs.userID = ?\n"
                + "ORDER BY submissionDate DESC";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            rs = statement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            while (rs.next()) {
                ChapterSubmission cs = new ChapterSubmission();
                cs.setSubmissionCID(rs.getInt("submissionCID"));
                cs.setChapterID(rs.getInt("chapterID"));
                cs.setUserID(rs.getInt("userID"));
                cs.setManagerID(rs.getInt("managerID"));
                cs.setDraftID(rs.getInt("draftID"));
                cs.setSubmissionDate(rs.getTimestamp("submissionDate") != null ? rs.getTimestamp("submissionDate").toLocalDateTime().format(formatter) : null);
                cs.setApprovalDate(rs.getTimestamp("approvalDate") != null ? rs.getTimestamp("approvalDate").toLocalDateTime().format(formatter) : null);
                cs.setType(rs.getString("type"));
                cs.setStatus(rs.getString("status"));
                cs.setReasonRejected(rs.getString("reasonRejected"));
                cs.setChapterName(rs.getString("chapterName"));
                cs.setChapterNumber(rs.getString("chapterNumber"));
                cs.setNovelID(rs.getInt("novelID"));
                cs.setNovelName(rs.getString("novelName"));
                list.add(cs);
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
