package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ManageChapter;
import utils.DBContext;

/**
 * 
 * @author Nguyen Ngoc Phat - CE180321
 */
public class ManageChapterDAO {

    private final DBContext db;

    public ManageChapterDAO() {
        db = new DBContext();
    }

    public List<ManageChapter> getChaptersByNovelId(int novelID, boolean isDescending) {
        List<ManageChapter> list = new ArrayList<>();
        String sql = "SELECT c.chapterID, c.novelID, c.chapterNumber, c.chapterName, c.fileURL, c.publishedDate, "
                + "COALESCE((SELECT MAX(action) FROM LockChapterLog l WHERE l.chapterID = c.chapterID), 'unlock') AS lockStatus "
                + "FROM Chapter c WHERE c.novelID = ? ORDER BY c.chapterNumber " + (isDescending ? "DESC" : "ASC");

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, novelID);
            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ManageChapter chapter = new ManageChapter();
                    chapter.setChapterID(rs.getInt("chapterID"));
                    chapter.setNovelID(rs.getInt("novelID"));
                    chapter.setChapterNumber(rs.getInt("chapterNumber"));
                    chapter.setChapterName(rs.getString("chapterName"));
                    chapter.setFileURL(rs.getString("fileURL"));

                    // Kiểm tra NULL trước khi chuyển đổi
                    Timestamp timestamp = rs.getTimestamp("publishedDate");
                    chapter.setPublishedDate(timestamp != null ? timestamp.toLocalDateTime() : null);

                    String lockStatus = rs.getString("lockStatus");
                    chapter.setLocked("lock".equalsIgnoreCase(lockStatus));

                    list.add(chapter);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Cập nhật trạng thái khóa chương
    public boolean updateChapterLock(int chapterID, int managerID, boolean isLock, String reason) {
        String action = isLock ? "lock" : "unlock";
        String sql = "INSERT INTO LockChapterLog (managerID, chapterID, lockDateTime, action, lockReason) "
                + "VALUES (?, ?, GETDATE(), ?, ?)";

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, managerID);
            statement.setInt(2, chapterID);
            statement.setString(3, action);

            // Kiểm tra nếu reason là NULL thì truyền chuỗi rỗng ""
            statement.setString(4, reason != null ? reason : "");

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
