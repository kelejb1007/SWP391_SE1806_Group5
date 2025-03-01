package DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import model.Chapter;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class PostChapterDAO {
    private final DBContext db;
    private static final Logger LOGGER = Logger.getLogger(PostChapterDAO.class.getName());
    private static final String BASE_DIR = "chapters"; // Sử dụng đường dẫn tương đối cố định

    public PostChapterDAO(ServletContext context) {
        db = new DBContext();
        
        // Log thông tin về thư mục gốc
        LOGGER.log(Level.INFO, "Base directory path: {0}", BASE_DIR);
        
        // Đảm bảo thư mục gốc "chapters" tồn tại trong context thực tế
        String realPath = context.getRealPath(BASE_DIR);
        File baseDirFile = new File(realPath != null ? realPath : BASE_DIR);
        if (!baseDirFile.exists()) {
            boolean created = baseDirFile.mkdirs();
            LOGGER.log(Level.INFO, "Base directory created: {0}", created);
        }
        
        // Kiểm tra quyền ghi
        LOGGER.log(Level.INFO, "Base directory can write: {0}", baseDirFile.canWrite());
    }

    /**
     * Tạo thư mục cho novel dựa trên tên
     * @param novelName Tên của novel
     * @return Đường dẫn tương đối đến thư mục hoặc null nếu thất bại
     */
    public String createNovelDirectory(String novelName) {
        String safeName = novelName.replaceAll("[^a-zA-Z0-9-_]", "_"); // Làm sạch tên, loại bỏ ký tự nguy hiểm
        String dirPath = Paths.get(BASE_DIR, safeName).toString(); // "chapters/The_Mystic_World"
        
        LOGGER.log(Level.INFO, "Creating novel directory at: {0}", dirPath);
        
        // Lấy đường dẫn thực tế để tạo thư mục
        String realPath = getRealPath(dirPath);
        File directory = new File(realPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            LOGGER.log(Level.INFO, "Novel directory created: {0}", created);
            
            if (!created) {
                LOGGER.log(Level.SEVERE, "Failed to create directory: {0}", dirPath);
                return null;
            }
        }
        
        return dirPath; // Trả về đường dẫn tương đối (không thay thế \ bằng / vì đã là định dạng chuẩn)
    }
    
    /**
     * Lấy đường dẫn đến file chapter
     * @param novelName Tên novel
     * @param chapterNumber Số chapter
     * @return Đường dẫn tương đối file hoặc null nếu không thể tạo thư mục
     */
    public String getChapterFilePath(String novelName, int chapterNumber) {
        String dirPath = createNovelDirectory(novelName);
        if (dirPath == null) {
            return null;
        }
        
        // Tạo đường dẫn file với định dạng "ch{NUMBER}.txt"
        String filePath = Paths.get(dirPath, "ch" + chapterNumber + ".txt").toString();
        LOGGER.log(Level.INFO, "Chapter file path (relative): {0}", filePath);
        return filePath; // Trả về đường dẫn tương đối
    }
    
    /**
     * Lưu nội dung chapter vào file
     * @param filePath Đường dẫn file tương đối
     * @param content Nội dung cần lưu
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean saveChapterContent(String filePath, String content) {
        LOGGER.log(Level.INFO, "Saving content to file: {0}", filePath);
        
        // Lấy đường dẫn thực tế để ghi file
        String realPath = getRealPath(filePath);
        File parent = new File(realPath).getParentFile();
        if (!parent.exists()) {
            LOGGER.log(Level.INFO, "Parent directory doesn't exist, creating: {0}", parent.getAbsolutePath());
            boolean parentCreated = parent.mkdirs();
            LOGGER.log(Level.INFO, "Parent directory created: {0}", parentCreated);
        }
        
        try (FileWriter writer = new FileWriter(realPath)) {
            writer.write(content);
            LOGGER.log(Level.INFO, "Content successfully written to file");
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file: {0}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lưu nội dung chapter từ InputStream (upload file)
     * @param filePath Đường dẫn file tương đối
     * @param inputStream Stream dữ liệu
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean saveChapterFile(String filePath, InputStream inputStream) {
        LOGGER.log(Level.INFO, "Saving uploaded file to: {0}", filePath);
        
        // Lấy đường dẫn thực tế để ghi file
        String realPath = getRealPath(filePath);
        File parent = new File(realPath).getParentFile();
        if (!parent.exists()) {
            LOGGER.log(Level.INFO, "Parent directory doesn't exist, creating: {0}", parent.getAbsolutePath());
            boolean parentCreated = parent.mkdirs();
            LOGGER.log(Level.INFO, "Parent directory created: {0}", parentCreated);
        }
        
        try {
            Files.copy(inputStream, Paths.get(realPath), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.log(Level.INFO, "File successfully saved");
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving uploaded file: {0}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int postChapter(Chapter chapter) {
        if (chapter == null || chapter.getNovelID() <= 0) {
            LOGGER.log(Level.WARNING, "Invalid chapter or novel ID.");
            return -1;
        }

        String getMaxChapterQuery = "SELECT MAX(chapterNumber) AS maxId FROM Chapter WHERE novelID = ?";
        try (Connection connection = db.getConnection(); PreparedStatement maxStmt = connection.prepareStatement(getMaxChapterQuery)) {
            maxStmt.setInt(1, chapter.getNovelID());

            int nextChapterNumber = 1; // Mặc định chapterNumber bắt đầu từ 1
            try (ResultSet rs = maxStmt.executeQuery()) {
                if (rs.next() && !rs.wasNull()) {
                    nextChapterNumber = rs.getInt("maxId") + 1;
                }
            }

            // Tạo đường dẫn file chapter với định dạng mới (tương đối)
            String novelName = chapter.getNovelName() != null ? chapter.getNovelName() : "Unknown";
            LOGGER.log(Level.INFO, "Creating file path for novel: {0}, chapter: {1}", new Object[]{novelName, nextChapterNumber});
            
            String filePath = getChapterFilePath(novelName, nextChapterNumber);
            
            if (filePath == null) {
                LOGGER.log(Level.SEVERE, "Failed to create directory for novel: {0}", novelName);
                return -1;
            }

            String insertChapterQuery = "INSERT INTO Chapter (novelID, chapterNumber, chapterName, fileURL, publishedDate, chapterStatus) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement insertStmt = connection.prepareStatement(insertChapterQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, chapter.getNovelID());
                insertStmt.setInt(2, nextChapterNumber);
                insertStmt.setString(3, chapter.getChapterName());
                insertStmt.setString(4, filePath); // Lưu đường dẫn tương đối: "chapters/The_Mystic_World/ch2.txt"

                insertStmt.setTimestamp(5, chapter.getChapterCreatedDate() != null
                        ? Timestamp.valueOf(chapter.getChapterCreatedDate())
                        : Timestamp.valueOf(LocalDateTime.now()));

                insertStmt.setString(6, chapter.getChapterStatus() != null ? chapter.getChapterStatus() : "pending");

                int rowsInserted = insertStmt.executeUpdate();
                LOGGER.log(Level.INFO, "Rows inserted: {0}", rowsInserted);

                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            LOGGER.log(Level.INFO, "Generated chapter ID: {0}", generatedId);
                            return generatedId;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error posting chapter", e);
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu thất bại
    }

    public int getNextChapterNumber(int novelId) {
        String query = "SELECT COALESCE(MAX(chapterNumber), 0) + 1 FROM Chapter WHERE novelID = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, novelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int nextNumber = rs.getInt(1);
                LOGGER.log(Level.INFO, "Next chapter number for novel ID {0}: {1}", new Object[]{novelId, nextNumber});
                return nextNumber;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching next chapter number", e);
            e.printStackTrace();
        }
        return 1;
    }

    public boolean updateChapterFilePath(int chapterID, String filePath) {
        String updateQuery = "UPDATE Chapter SET fileURL = ? WHERE chapterID = ?";
        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(updateQuery)) {
            ps.setString(1, filePath); // Sử dụng đường dẫn tương đối
            ps.setInt(2, chapterID);
            int updated = ps.executeUpdate();
            LOGGER.log(Level.INFO, "Chapter file path updated: {0} rows for chapter ID {1}", new Object[]{updated, chapterID});
            return updated > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating file path for chapter ID: " + chapterID, e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Chuyển đổi đường dẫn tương đối thành đường dẫn thực tế
     * @param relativePath Đường dẫn tương đối (ví dụ: "chapters/The_Mystic_World/ch1.txt")
     * @return Đường dẫn thực tế trên hệ thống
     */
    private String getRealPath(String relativePath) {
        String realPath = null;
        try {
            realPath = new File(relativePath).getCanonicalPath(); // Lấy đường dẫn tuyệt đối thực tế
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error getting real path for: {0}", relativePath);
            e.printStackTrace();
        }
        if (realPath == null) {
            // Nếu không lấy được canonical path, thử dùng ServletContext
            ServletContext context = null; // Bạn cần truyền context vào constructor hoặc phương thức nếu cần
            if (context != null) {
                realPath = context.getRealPath(relativePath);
            }
            if (realPath == null) {
                realPath = relativePath; // Sử dụng đường dẫn tương đối như fallback
            }
        }
        return realPath;
    }
}