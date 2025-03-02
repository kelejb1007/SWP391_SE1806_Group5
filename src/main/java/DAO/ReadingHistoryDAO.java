package DAO;

import utils.DBContext;
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
import model.Novel;
import model.ReadingHistory;

public class ReadingHistoryDAO {

    private final DBContext db;

    public ReadingHistoryDAO() {
        db = new DBContext();
    }

 public List<Novel> getReadingHistoryNovels1(int userId) {
    List<Novel> readingHistoryNovels = new ArrayList<>();
    String sql = "SELECT "
            + "    n.novelID, "
            + "    n.novelName, "
            + "    n.imageURL, "
            + "    n.novelDescription, "
            + "    n.totalChapter, "
            + "    COALESCE(lastChapter.chapterNumber, 1) AS lastChapterNumber, "
            + "    COALESCE(lastChapter.chapterName, 'Chapter 1') AS lastChapterName, "
            + "    COALESCE(AVG(r.score), 0) AS averageRating, "
            + "    rh.lastReadDate, "
            + "    rh.chapterID AS lastReadChapterID "
            + "FROM ReadingHistory rh "
            + "JOIN UserAccount u ON rh.userID = u.userID "
            + "JOIN Novel n ON rh.novelID = n.novelID "
            + "LEFT JOIN Rating r ON n.novelID = r.novelID "
            + "OUTER APPLY ( "
            + "    SELECT TOP 1 ch.chapterID, ch.chapterNumber, ch.chapterName "
            + "    FROM Chapter ch "
            + "    WHERE ch.novelID = n.novelID "
            + "    ORDER BY ch.chapterNumber DESC "
            + ") lastChapter "
            + "WHERE u.userID = ? "
            + "GROUP BY n.novelID, n.novelName, n.imageURL, n.novelDescription, n.totalChapter, "
            + "         lastChapter.chapterID, lastChapter.chapterNumber, lastChapter.chapterName, rh.lastReadDate, rh.chapterID "
            + "ORDER BY rh.lastReadDate DESC;";

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

    try {
        connection = db.getConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        rs = statement.executeQuery();

        while (rs.next()) {
            Novel novel = new Novel();
            novel.setNovelID(rs.getInt("novelID"));
            novel.setNovelName(rs.getString("novelName"));
            novel.setImageURL(rs.getString("imageURL"));
            novel.setNovelDescription(rs.getString("novelDescription"));
            novel.setTotalChapter(rs.getInt("totalChapter"));
            novel.setAverageRating(rs.getDouble("averageRating"));
            novel.setLastChapterName(rs.getString("lastChapterName"));
            novel.setLastChapterNumber(rs.getInt("lastChapterNumber"));

         /*   Integer lastReadChapterID = rs.getInt("lastReadChapterID");
             if (rs.wasNull()) {
                 novel.setLastReadChapterID(1); // Default value
              } else {
                 novel.setLastReadChapterID(lastReadChapterID);
              }*/

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
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    return readingHistoryNovels;
}

 
    public List<Novel> getReadingHistoryNovels(int userId) {
    List<Novel> readingHistoryNovels = new ArrayList<>();
    String sql =
        "WITH\n" +
        "  ReadProgress AS (\n" +
        "    SELECT\n" +
        "      userID,\n" +
        "      novelID,\n" +
        "      COUNT(DISTINCT chapterID) AS process\n" +
        "    FROM ReadingHistory\n" +
        "    WHERE\n" +
        "      userID = ?\n" +
        "    GROUP BY\n" +
        "      userID,\n" +
        "      novelID\n" +
        "  ),\n" +
        "  LatestRead AS (\n" +
        "    SELECT\n" +
        "      rh.userID,\n" +
        "      rh.novelID,\n" +
        "      MAX(rh.lastReadDate) AS lastReadDate\n" +
        "    FROM ReadingHistory rh\n" +
        "    WHERE\n" +
        "      rh.userID = ?\n" +
        "    GROUP BY\n" +
        "      rh.userID,\n" +
        "      rh.novelID\n" +
        "  )\n" +
        "SELECT\n" +
        "  u.userID,\n" +
        "  u.userName,\n" +
        "  n.novelID,\n" +
        "  n.novelName,\n" +
        "  n.imageURL,\n" +
        "  n.totalChapter,\n" +
        "  c.chapterID,\n" +
        "  c.chapterNumber,\n" +
        "  c.chapterName,\n" +
        "  COALESCE(rp.process, 0) AS process,\n" +
        "  COALESCE(AVG(r.score), 0) AS averageRating,\n" +
        "  rh.lastReadDate\n" +
        "FROM LatestRead lr\n" +
        "JOIN ReadingHistory rh\n" +
        "  ON lr.userID = rh.userID\n" +
        "  AND lr.novelID = rh.novelID\n" +
        "  AND lr.lastReadDate = rh.lastReadDate\n" +
        "JOIN UserAccount u\n" +
        "  ON rh.userID = u.userID\n" +
        "JOIN Novel n\n" +
        "  ON rh.novelID = n.novelID\n" +
        "JOIN Chapter c\n" +
        "  ON rh.chapterID = c.chapterID\n" +
        "LEFT JOIN Rating r\n" +
        "  ON n.novelID = r.novelID\n" +
        "LEFT JOIN ReadProgress rp\n" +
        "  ON rh.novelID = rp.novelID\n" +
        "WHERE n.novelStatus ='active'"+
        "GROUP BY\n" +
        "  u.userID,\n" +
        "  u.userName,\n" +
        "  n.novelID,\n" +
        "  n.novelName,\n" +
        "  n.imageURL,\n" +
        "  n.totalChapter,\n" +
        "  c.chapterID,\n" +
        "  c.chapterNumber,\n" +
        "  c.chapterName,\n" +
        "  rh.lastReadDate,\n" +
        "  rp.process\n" +
        "ORDER BY\n" +
        "  rh.lastReadDate DESC,\n" +
        "  c.chapterNumber;";

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

    try {
        connection = db.getConnection();
        statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, userId);

        rs = statement.executeQuery();

        while (rs.next()) {
            Novel novel = new Novel();
            novel.setNovelID(rs.getInt("novelID"));
            novel.setNovelName(rs.getString("novelName"));
            novel.setImageURL(rs.getString("imageURL"));
            novel.setTotalChapter(rs.getInt("totalChapter"));
            novel.setChapterID(rs.getInt("chapterID")); // Thêm chapterID
            novel.setLastChapterName(rs.getString("chapterName"));
            novel.setLastChapterNumber(rs.getInt("chapterNumber"));
            novel.setProcess(rs.getInt("process")); // Đặt giá trị process
            novel.setAverageRating(rs.getDouble("averageRating"));

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
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ReadingHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    return readingHistoryNovels;
}


     public boolean updateLastReadDate(ReadingHistory history) {
        String sql = "UPDATE ReadingHistory SET lastReadDate = ? WHERE userID = ? AND novelID = ? AND chapterID = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);

            // Handle LocalDateTime
            statement.setObject(1, LocalDateTime.now());

            statement.setInt(2, history.getUserID());
            statement.setInt(3, history.getNovelID());
            statement.setInt(4, history.getChapterID());

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

     public ReadingHistory getReadingHistory(int userID, int novelID, int chapterID) {
   String sql = "SELECT UserID, NovelID, ChapterID, LastReadDate FROM ReadingHistory WHERE userID = ? AND novelID = ? AND chapterID = ?";
   try (Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
       ps.setInt(1, userID);
       ps.setInt(2, novelID);
       ps.setInt(3, chapterID);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           ReadingHistory history = new ReadingHistory();
           history.setUserID(rs.getInt("userID"));
           history.setNovelID(rs.getInt("novelID"));
           history.setChapterID(rs.getInt("chapterID"));
           history.setLastReadDate(rs.getObject("LastReadDate", LocalDateTime.class));
           return history;
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }
   return null;}
    
    // Add reading history
  public boolean addReadingHistory(ReadingHistory history) {
    String sql = "INSERT INTO ReadingHistory (userID, novelID, chapterID, lastReadDate) VALUES (?, ?, ?, ?)";
    Connection connection = null;
    PreparedStatement statement = null;

    try {
        connection = db.getConnection();

        // Handle nullable ChapterID
        Integer chapterIDToInsert = history.getChapterID();
        if (chapterIDToInsert == null) {
            // Get the first chapterID for the novelID
            String getFirstChapterSQL = "SELECT MIN(chapterID) FROM Chapter WHERE novelID = ?";
            PreparedStatement getFirstChapterStatement = connection.prepareStatement(getFirstChapterSQL);
            getFirstChapterStatement.setInt(1, history.getNovelID());
            ResultSet rs = getFirstChapterStatement.executeQuery();
            if (rs.next()) {
                chapterIDToInsert = rs.getInt(1);
                if (rs.wasNull()) {
                    // Novel doesn't have any chapters, handle accordingly
                    System.out.println("NovelID " + history.getNovelID() + " has no chapters.");
                    return false; // Or throw an exception, depending on your requirements
                }
            } else {
                 // Novel doesn't have any chapters, handle accordingly
                 System.out.println("NovelID " + history.getNovelID() + " has no chapters.");
                 return false; // Or throw an exception, depending on your requirements
            }
            rs.close();
            getFirstChapterStatement.close();
        }

        statement = connection.prepareStatement(sql);
        statement.setInt(1, history.getUserID());
        statement.setInt(2, history.getNovelID());
        statement.setInt(3, chapterIDToInsert); // Use the retrieved or original chapterID


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
