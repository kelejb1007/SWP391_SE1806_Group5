package DAO;

import utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Novel;
import model.ReadingHistory;

public class ReadingHistoryDAO {

    private final DBContext db;

    public ReadingHistoryDAO() {
        db = new DBContext();
    }

    public List<Novel> getReadingHistoryNovels(int userId) {
        List<Novel> readingHistoryNovels = new ArrayList<>();
        String sql = "SELECT "
                + "n.novelID, "
                + "n.novelName, "
                + "n.imageURL, "
                + "n.novelDescription, "
                + "n.totalChapter, "
                + "COALESCE(c.chapterName, (SELECT TOP 1 chapterName FROM Chapter "
                + "WHERE novelID = n.novelID ORDER BY chapterNumber ASC)) AS lastChapterName, "
                + "COALESCE(c.chapterNumber, (SELECT TOP 1 chapterNumber FROM Chapter "
                + "WHERE novelID = n.novelID ORDER BY chapterNumber ASC)) AS lastChapterNumber, "
                + "rh.lastReadDate, "
                + "rh.chapterID AS lastReadChapterID, "
                + "COALESCE(AVG(r.score), 0) AS averageRating "
                + // Thêm điểm trung bình
                "FROM Novel n "
                + "LEFT JOIN ( "
                + "    SELECT novelID, chapterID, lastReadDate FROM ReadingHistory WHERE userID = ? "
                + ") rh ON n.novelID = rh.novelID "
                + "LEFT JOIN Chapter c ON rh.chapterID = c.chapterID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + // JOIN với Rating
                "GROUP BY n.novelID, n.novelName, n.imageURL, n.novelDescription, n.totalChapter, "
                + "c.chapterName, c.chapterNumber, rh.lastReadDate, rh.chapterID "
                + "ORDER BY CASE WHEN rh.lastReadDate IS NULL THEN 0 ELSE 1 END DESC, rh.lastReadDate DESC;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId); // Set the userID parameter

            rs = statement.executeQuery();

            while (rs.next()) {
                Novel novel = new Novel();
                novel.setNovelID(rs.getInt("novelID"));
                novel.setNovelName(rs.getString("novelName"));
                novel.setImageURL(rs.getString("imageURL"));
                novel.setNovelDescription(rs.getString("novelDescription"));
                novel.setTotalChapter(rs.getInt("totalChapter"));
                novel.setAverageRating(rs.getDouble("averageRating"));
                // Get the last read chapter name
                String lastChapterName = rs.getString("lastChapterName");
                novel.setLastChapterName(lastChapterName);

                // Get the last chapter number
                int lastChapterNumber = rs.getInt("lastChapterNumber");
                novel.setLastChapterNumber(lastChapterNumber);

                // Get the last read chapter ID
                Integer lastReadChapterID = (Integer) rs.getObject("lastReadChapterID"); //Get the lastReadChapterID
                if (lastReadChapterID == null) {
                    novel.setLastReadChapterID(1); // Default to 1
                } else {
                    novel.setLastReadChapterID(lastReadChapterID);
                }

                // Get the last read date
                LocalDateTime lastReadDate = null;
                if (rs.getTimestamp("lastReadDate") != null) {
                    lastReadDate = rs.getTimestamp("lastReadDate").toLocalDateTime();
                    novel.setLastReadDate(lastReadDate);
                } else {
                    novel.setLastReadDate(null);
                }

                readingHistoryNovels.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return readingHistoryNovels;
    }

    public boolean addOrUpdateReadingHistory(ReadingHistory history) {
        String sql = "MERGE INTO ReadingHistory AS target "
                + "USING (SELECT ? AS userID, ? AS novelID, ? AS chapterID) AS source "
                + "ON target.userID = source.userID AND target.novelID = source.novelID "
                + "WHEN MATCHED THEN "
                + "    UPDATE SET "
                + "        chapterID = CASE "
                + "            WHEN source.chapterID IS NOT NULL THEN source.chapterID "
                + "            ELSE target.chapterID "
                + // Giữ nguyên chapterID nếu source.chapterID là NULL
                "        END, "
                + "        lastReadDate = GETDATE() "
                + "WHEN NOT MATCHED THEN "
                + "    INSERT (userID, novelID, chapterID, lastReadDate) "
                + "    VALUES (?, ?, ?, GETDATE());";

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set parameters for source (checking existence)
            statement.setInt(1, history.getUserID());
            statement.setInt(2, history.getNovelID());
            if (history.getChapterID() != null) {
                statement.setInt(3, history.getChapterID());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }

            // Set parameters for INSERT operation
            statement.setInt(4, history.getUserID());
            statement.setInt(5, history.getNovelID());
            if (history.getChapterID() != null) {
                statement.setInt(6, history.getChapterID());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Get reading history by user and novel ID
    public ReadingHistory getReadingHistory(int userId, int novelId) {
        ReadingHistory history = null;
        String sql = "SELECT * FROM ReadingHistory WHERE userID = ? AND novelID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, novelId);
            rs = statement.executeQuery();

            if (rs.next()) {
                history = new ReadingHistory();
                history.setReadingID(rs.getInt("readingID"));
                history.setUserID(rs.getInt("userID"));
                history.setNovelID(rs.getInt("novelID"));

                // Handle nullable ChapterID
                int chapterId = rs.getInt("chapterID");
                if (!rs.wasNull()) {
                    history.setChapterID(chapterId);
                } else {
                    history.setChapterID(null); // Set to null if it's null in the database
                }

                // Handle LocalDateTime
                if (rs.getTimestamp("lastReadDate") != null) {
                    history.setLastReadDate(rs.getTimestamp("lastReadDate").toLocalDateTime());
                } else {
                    history.setLastReadDate(null);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return history;
    }

    // Add reading history
    public boolean addReadingHistory(ReadingHistory history) {
        String sql = "INSERT INTO ReadingHistory (userID, novelID, chapterID, lastReadDate) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, history.getUserID());
            statement.setInt(2, history.getNovelID());

            // Handle nullable ChapterID
            if (history.getChapterID() != null) {
                statement.setInt(3, history.getChapterID());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }

            // Handle LocalDateTime
            if (history.getLastReadDate() != null) {
                statement.setObject(4, history.getLastReadDate());
            } else {
                statement.setObject(4, LocalDateTime.now()); // Or handle null as needed
            }

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Update reading history
    public boolean updateReadingHistory(ReadingHistory history) {
        String sql = "UPDATE ReadingHistory SET chapterID = ?, lastReadDate = ? WHERE userID = ? AND novelID = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);

            // Handle nullable ChapterID
            if (history.getChapterID() != null) {
                statement.setInt(1, history.getChapterID());
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
            }

            // Handle LocalDateTime
            if (history.getLastReadDate() != null) {
                statement.setObject(2, history.getLastReadDate());
            } else {
                statement.setObject(2, LocalDateTime.now()); // Or handle null as needed
            }

            statement.setInt(3, history.getUserID());
            statement.setInt(4, history.getNovelID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Delete reading history (optional)
    public boolean deleteReadingHistory(int userId, int novelId) {
        String sql = "DELETE FROM ReadingHistory WHERE userID = ? AND novelID = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, novelId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
