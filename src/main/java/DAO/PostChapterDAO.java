package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
import utils.DBContext;

/**
 *
 * 
 * @author Nguyen Ngoc Phat - CE180321
 */
public class PostChapterDAO {

    private final DBContext db;

    public PostChapterDAO() {
        db = new DBContext();
    }

    /**
     * Insert a new chapter into an existing novel and return its generated ID
     *
     * @param chapter The chapter object containing details
     * @return ID of the newly inserted chapter, or -1 if failed
     */
    public int postChapter(Chapter chapter) {
        String sql = "INSERT INTO Chapter (novelID, chapterNumber, chapterName, fileURL, publishedDate, chapterStatus) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, chapter.getNovelID());  // Novel ID (phải tồn tại trong bảng Novel)
            statement.setInt(2, chapter.getChapterNumber());
            statement.setString(3, chapter.getChapterName());
            statement.setString(4, chapter.getFileURL());

            // Convert LocalDateTime to SQL Timestamp
            statement.setTimestamp(5, chapter.getChapterCreatedDate() != null
                    ? Timestamp.valueOf(chapter.getChapterCreatedDate()) : null);

            statement.setString(6, "pending");  // Trạng thái chương

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                // Lấy ID của chương vừa được tạo
                try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PostChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1; // Trả về -1 nếu thất bại
    }

    public static void main(String[] args) {
        PostChapterDAO dao = new PostChapterDAO();
        Chapter newChapter = new Chapter(0, 1, 5, "New Chapter", "https://example.com/chapter5.pdf", LocalDateTime.now(), "pending");
        int chapterID = dao.postChapter(newChapter);
        System.out.println("New Chapter ID: " + chapterID);
    }
}
