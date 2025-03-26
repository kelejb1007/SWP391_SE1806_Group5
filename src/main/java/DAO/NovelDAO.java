/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Novel;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class NovelDAO {

    private final DBContext db;

    public NovelDAO() {
        db = new DBContext();
    }

    //Admin-------------------------------------------------------------------------------------------------------------
    public List<Novel> getNovelByStatus(String status) throws SQLException {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.imageURL, n.totalChapter, n.publishedDate, u.fullName, COALESCE(ROUND(AVG(r.score), 2), 0) AS averageRating, COUNT(v.novelID) AS viewCount,\n"
                + "(SELECT STRING_AGG(g.genreName, ', ') "
                + " FROM Genre_Novel gn2 "
                + " JOIN Genre g ON gn2.genreID = g.genreID\n"
                + " WHERE gn2.novelID = n.novelID) AS genres "
                + "FROM Novel n \n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "LEFT JOIN Rating r ON n.novelID = r.novelID\n"
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID\n"
                + "WHERE novelStatus = ?\n"
                + "GROUP BY n.novelID, n.novelName, n.imageURL, n.totalChapter, n.publishedDate, u.fullName\n"
                + "ORDER BY n.publishedDate DESC";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            rs = statement.executeQuery();
            while (rs.next()) {
                Novel m = new Novel(rs.getInt("novelID"), rs.getString("novelName"), rs.getString("imageURL"), rs.getInt("totalChapter"),
                        (rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").toLocalDateTime() : null),
                        rs.getString("fullName"), rs.getDouble("averageRating"), rs.getInt("viewCount"), rs.getString("genres"));
                m.setPublishDate2(new Date(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").getTime() : null));
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> getLockedNovels() throws SQLException {
        List<Novel> list = new ArrayList<>();
        String sql = "WITH LatestLock AS (\n"
                + "    SELECT novelID, MAX(datetime) AS latestLockDate\n"
                + "    FROM LockNovelLog\n"
                + "    WHERE action = 'lock'\n"
                + "    GROUP BY novelID )\n"
                + "SELECT \n"
                + "    n.novelID, n.novelName, n.totalChapter, n.publishedDate,\n"
                + "    u.fullName as author, m.fullName as staffName,\n"
                + "    ll.datetime ,ll.lockReason,\n"
                + "    (SELECT STRING_AGG(g.genreName, ', ') "
                + "    FROM Genre_Novel gn2 "
                + "    JOIN Genre g ON gn2.genreID = g.genreID\n"
                + "    WHERE gn2.novelID = n.novelID) AS genres "
                + "FROM LatestLock l\n"
                + "JOIN LockNovelLog ll ON (l.novelID = ll.novelID AND l.latestLockDate = ll.datetime) OR (l.novelID = ll.novelID AND l.latestLockDate is null AND ll.datetime is null)\n"
                + "JOIN ManagerAccount m ON ll.managerID = m.managerID\n"
                + "JOIN Novel n ON ll.novelID = n.novelID\n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "WHERE n.novelStatus = 'locked' \n"
                + "ORDER BY ll.datetime DESC;";

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Novel m = new Novel();
                m.setNovelID(rs.getInt("novelID"));
                m.setNovelName(rs.getString("novelName"));
                m.setTotalChapter(rs.getInt("totalChapter"));
                m.setPublishedDate(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").toLocalDateTime() : null);
                m.setAuthor(rs.getString("author"));
                m.setStaffName(rs.getString("staffName"));
                m.setDatetime(rs.getTimestamp("datetime") != null ? rs.getTimestamp("datetime").toLocalDateTime() : null);
                m.setLockReason(rs.getString("lockReason"));
                m.setGenres(rs.getString("genres"));
                m.setPublishDate2(new Date(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").getTime() : null));
                m.setLockDate2(new Date(rs.getTimestamp("datetime") != null ? rs.getTimestamp("datetime").getTime() : null));
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

// Use for staff and user my novel
    public Novel getNovelByID(int novelID) throws SQLException {
        String sql = "SELECT n.novelID, n.novelName, n.imageURL, n.novelDescription, n.totalChapter, n.novelStatus, n.publishedDate, \n"
                + "u.fullName as author, COALESCE(ROUND(AVG(r.score), 2), 0) AS averageRating, COUNT(v.novelID) AS viewCount, \n"
                + "(SELECT STRING_AGG(g.genreName, ', ') "
                + " FROM Genre_Novel gn2 "
                + " JOIN Genre g ON gn2.genreID = g.genreID "
                + " WHERE gn2.novelID = n.novelID) AS genres "
                + "FROM Novel n\n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "LEFT JOIN Rating r ON n.novelID = r.novelID\n"
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID\n"
                + "WHERE n.novelID = ?\n"
                + "GROUP BY n.novelID, n.novelName, n.imageURL, n.novelDescription, n.totalChapter, n.novelStatus, n.publishedDate, u.fullName";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        Novel novel = new Novel();
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            rs = statement.executeQuery();

            if (rs.next()) {
                novel.setNovelID(rs.getInt("novelID"));
                novel.setNovelName(rs.getString("novelName"));
                novel.setImageURL(rs.getString("imageURL"));
                novel.setNovelDescription(rs.getString("novelDescription"));
                novel.setTotalChapter(rs.getInt("totalChapter"));
                novel.setNovelStatus(rs.getString("novelStatus"));
                novel.setPublishedDate(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setAuthor(rs.getString("author"));
                novel.setAverageRating(rs.getDouble("averageRating"));
                novel.setViewCount(rs.getInt("viewCount"));
                novel.setGenres(rs.getString("genres"));
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return novel;
    }

    public boolean changeNovelStatus(int novelID, String status) throws SQLException {
        String sql = "UPDATE Novel\n"
                + "SET novelStatus = ?\n"
                + "WHERE novelID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, novelID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean updatePublicDate(int novelID) {
        String sql = "UPDATE Novel\n"
                + "SET publishedDate = SYSDATETIME()\n"
                + "WHERE novelID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    //---------------------------------------------------------------------------------------------------------------------
    //User - My Novel ---------------------------------------------------------------------------------------------------
    public List<Novel> getMyNovels(int userID) throws SQLException {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.imageURL, n.totalChapter, n.novelStatus, n.publishedDate, u.fullName, "
                + "COALESCE(ROUND(AVG(r.score), 2), 0) AS averageRating, COUNT(v.novelID) AS viewCount,\n"
                + "(SELECT STRING_AGG(g.genreName, ', ') "
                + " FROM Genre_Novel gn2 "
                + " JOIN Genre g ON gn2.genreID = g.genreID "
                + " WHERE gn2.novelID = n.novelID) AS genres "
                + "FROM Novel n \n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "LEFT JOIN Rating r ON n.novelID = r.novelID\n"
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID\n"
                + "WHERE n.UserID = ? AND n.novelStatus IN ('active')\n"
                + "GROUP BY n.novelID, n.novelName, n.imageURL, n.totalChapter, n.novelStatus, n.publishedDate, u.fullName";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            rs = statement.executeQuery();
            while (rs.next()) {
                Novel m = new Novel();
                m.setNovelID(rs.getInt("novelID"));
                m.setNovelName(rs.getString("novelName"));
                m.setImageURL(rs.getString("imageURL"));
                m.setTotalChapter(rs.getInt("totalChapter"));
                m.setNovelStatus(rs.getString("novelStatus"));
                m.setPublishedDate(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").toLocalDateTime() : null);
                m.setAuthor(rs.getString("fullName"));
                m.setAverageRating(rs.getDouble("averageRating"));
                m.setViewCount(rs.getInt("viewCount"));
                m.setGenres(rs.getString("genres"));
                m.setPublishDate2(new Date(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").getTime() : null));
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> getDeletedNovels(int userID) throws SQLException {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.totalChapter, n.novelStatus, n.publishedDate, ll.datetime, ll.lockReason\n"
                + "FROM Novel n \n"
                + "LEFT JOIN LockNovelLog ll ON ll.novelID = n.novelID\n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "WHERE n.UserID = ? AND n.novelStatus IN ('locked', 'deleted')";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            rs = statement.executeQuery();
            while (rs.next()) {
                Novel m = new Novel();
                m.setNovelID(rs.getInt("novelID"));
                m.setNovelName(rs.getString("novelName"));
                m.setTotalChapter(rs.getInt("totalChapter"));
                m.setNovelStatus(rs.getString("novelStatus"));
                m.setPublishedDate(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").toLocalDateTime() : null);             
                m.setPublishDate2(new Date(rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").getTime() : null));
                m.setLockDate2(rs.getTimestamp("datetime") != null ? new Date(rs.getTimestamp("datetime").getTime()) : null);
                m.setLockReason(rs.getString("lockReason"));
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int addNovel(Novel novel) {
        String sql = "INSERT INTO Novel (novelName, userID, imageURL, novelDescription, totalChapter, publishedDate, novelStatus)\n"
                + "VALUES (?, ?, ?, ?, ?, NULL, ?)";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        int n;
        int novelID = -1;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, novel.getNovelName());
            statement.setInt(2, novel.getUserID());
            statement.setString(3, novel.getImageURL());
            statement.setString(4, novel.getNovelDescription());
            statement.setInt(5, novel.getTotalChapter());
            statement.setString(6, novel.getNovelStatus());
            n = statement.executeUpdate();
            if (n > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    novelID = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return novelID;
    }

    public boolean updateNovel(Novel draft, int novelID) {
        String sql = "UPDATE Novel SET "
                + "novelName = ?, imageURL = ?,"
                + "novelDescription = ?, totalChapter = ?"
                + "where novelID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, draft.getNovelName());
            statement.setString(2, draft.getImageURL());
            statement.setString(3, draft.getNovelDescription());
            statement.setInt(4, draft.getTotalChapter());
            statement.setInt(5, novelID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean deleteNovel(int novelID) {
        String sql = "UPDATE Novel SET "
                + "novelStatus = 'deleted' "
                + "where novelID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean checkNovelName(String novelName) {
        String sql = "SELECT novelID FROM Novel where novelName = ?";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, novelName);
            rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean checkNovelNameForUpdate(String novelName, int novelID) {
        String sql = "SELECT novelID FROM Novel where novelName = ?";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, novelName);
            rs = statement.executeQuery();
            if (rs.next()) {
                if (rs.getInt("novelID") != novelID) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    //User, Guest-------------------------------------------------------------------------------------------------------------
    public List<Novel> getAllNovels() {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "WHERE n.novelStatus = 'active' "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating")); // Lấy từ resultSet
                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> searchNovels(String query) {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "WHERE n.novelStatus = 'active' AND n.novelName LIKE ? "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null
                        ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating")); // Lấy từ resultSet
                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> getNovelsByGenre(String genreName) {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "JOIN Genre_Novel gn ON n.novelID = gn.novelID "
                + "JOIN Genre g ON gn.genreID = g.genreID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "WHERE n.novelStatus = 'active' AND g.genreName = ? "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, genreName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null
                        ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating")); // Lấy từ resultSet
                // novel.setGenreName(resultSet.getString("genreName")); //No genreName in Novel Model
                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> getTop10NovelsByMonthlyRating() {
        List<Novel> list = new ArrayList<>();
        String sql = "WITH MonthlyRatings AS (\n"
                + "    SELECT \n"
                + "        r.novelID,\n"
                + "        AVG(r.score) AS avgRating,\n"
                + "        COUNT(r.ratingID) AS ratingCount\n"
                + "    FROM Rating r\n"
                + "    JOIN Novel n ON r.novelID = n.novelID\n"
                + "    WHERE MONTH(r.ratingDate) = MONTH(GETDATE())\n"
                + "        AND YEAR(r.ratingDate) = YEAR(GETDATE())\n"
                + "    GROUP BY r.novelID\n"
                + "),\n"
                + "GenreList AS (\n"
                + "    SELECT\n"
                + "        gn.novelID,\n"
                + "        STRING_AGG(g.genreName, ', ') AS genres\n"
                + "    FROM Genre_Novel gn\n"
                + "    JOIN Genre g ON gn.genreID = g.genreID\n"
                + "    GROUP BY gn.novelID\n"
                + ")\n"
                + "SELECT TOP 10 \n"
                + "    n.novelID,\n"
                + "    n.novelName,\n"
                + "    n.userID,\n"
                + "    n.imageURL,\n"
                + "    n.novelDescription,\n"
                + "    n.totalChapter,\n"
                + "    n.publishedDate,\n"
                + "    n.novelStatus,\n"
                + "    ua.fullName AS author,\n"
                + "    mr.avgRating AS averageRating,\n"
                + "    COALESCE(gl.genres, '') AS genres\n"
                + // Use COALESCE to handle cases where a novel has no genres
                "FROM MonthlyRatings mr\n"
                + "JOIN Novel n ON mr.novelID = n.novelID\n"
                + "JOIN UserAccount ua ON n.userID = ua.userID\n"
                + "LEFT JOIN GenreList gl ON n.novelID = gl.novelID\n"
                + "WHERE n.novelStatus = 'active'\n"
                + "ORDER BY mr.avgRating DESC, mr.ratingCount DESC;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null
                        ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating"));

                // Lấy danh sách genreName
                String genreNamesStr = resultSet.getString("genres");
                List<String> genreNames = new ArrayList<>();
                if (genreNamesStr != null && !genreNamesStr.isEmpty()) {
                    String[] genres = genreNamesStr.split(", "); // Chia chuỗi thành mảng
                    for (String genre : genres) {
                        genreNames.add(genre.trim()); // Thêm từng genre vào danh sách
                    }
                }
                novel.setGenreNames(genreNames); // Set danh sách genreName vào novel

                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> getNovelsByTimeUpdate() {
        List<Novel> list = new ArrayList<>();
        String sql = "WITH GenreList AS (\n"
                + "    SELECT\n"
                + "        gn.novelID,\n"
                + "        STRING_AGG(g.genreName, ', ') AS genres\n"
                + "    FROM Genre_Novel gn\n"
                + "    JOIN Genre g ON gn.genreID = g.genreID\n"
                + "    GROUP BY gn.novelID\n"
                + ")\n"
                + "SELECT TOP 6 n.novelID, n.novelName, n.novelDescription, n.imageURL, ua.userName AS author, n.totalChapter, MAX(c.publishedDate) AS latestChapterDate, MAX(c.chapterID) AS latestChapterID, COALESCE(gl.genres, '') AS genres, \n"
                + "       (SELECT chapterName FROM Chapter WHERE chapterID = MAX(c.chapterID)) AS lastChapterName, \n"
                + "       (SELECT chapterNumber FROM Chapter WHERE chapterID = MAX(c.chapterID)) AS lastChapterNumber\n"
                + "FROM Novel n\n"
                + "JOIN UserAccount ua ON n.userID = ua.userID\n"
                + "JOIN Chapter c ON n.novelID = c.novelID\n"
                + "LEFT JOIN GenreList gl ON n.novelID = gl.novelID\n"
                + "WHERE n.novelStatus = 'active' and c.chapterStatus = 'active'\n"
                + "GROUP BY n.novelID, n.novelName, n.imageURL, ua.userName, n.totalChapter, n.novelDescription, gl.genres\n"
                + "ORDER BY latestChapterDate DESC";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setChapterID(resultSet.getInt("latestChapterID")); // Lấy chapterID mới nhất
                novel.setLatestChapterDate(resultSet.getTimestamp("latestChapterDate") != null ? resultSet.getTimestamp("latestChapterDate").toLocalDateTime() : null); // Thêm dòng này nếu bạn có trường latestChapterDate trong Novel model

                // Lấy danh sách genreName
                String genreNamesStr = resultSet.getString("genres");
                List<String> genreNames = new ArrayList<>();
                if (genreNamesStr != null && !genreNamesStr.isEmpty()) {
                    String[] genres = genreNamesStr.split(", "); // Chia chuỗi thành mảng
                    for (String genre : genres) {
                        genreNames.add(genre.trim()); // Thêm từng genre vào danh sách
                    }
                }
                novel.setGenreNames(genreNames); // Set danh sách genreName vào novel

                // Lấy chapterName mới nhất
                novel.setLastChapterName(resultSet.getString("lastChapterName"));

                // Lấy chapterNumber mới nhất
                novel.setLastChapterNumber(resultSet.getInt("lastChapterNumber"));

                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Novel> getTop12NovelsByMonthlyViews() {
        List<Novel> list = new ArrayList<>();
        String sql = "WITH MonthlyViews AS (\n"
                + "    SELECT \n"
                + "        v.novelID, \n"
                + "        COUNT(v.viewID) AS viewCount\n"
                + "    FROM Viewing v\n"
                + "    WHERE \n"
                + "        v.viewDate >= DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1) -- Ngày đầu tháng\n"
                + "        AND v.viewDate < DATEADD(MONTH, 1, DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1)) -- Ngày đầu tháng sau\n"
                + "    GROUP BY v.novelID\n"
                + ")\n"
                + "SELECT TOP 12 \n"
                + "    n.novelID,\n"
                + "    n.novelName,\n"
                + "    u.fullName AS author,\n"
                + "    u.imageUML ,n.novelDescription,\n"
                + "    n.imageURL,\n"
                + "    COALESCE(STRING_AGG(g.genreName, ', '), '') AS genres, -- Gộp thể loại thành chuỗi\n"
                + "    COALESCE(mv.viewCount, 0) AS totalViews\n"
                + "FROM Novel n\n"
                + "LEFT JOIN MonthlyViews mv ON n.novelID = mv.novelID\n"
                + "LEFT JOIN UserAccount u ON n.userID = u.userID\n"
                + "LEFT JOIN Genre_Novel gn ON n.novelID = gn.novelID\n"
                + "LEFT JOIN Genre g ON gn.genreID = g.genreID\n"
                + "WHERE n.novelStatus = 'active'\n"
                + "GROUP BY n.novelID, n.novelName, u.fullName, n.novelDescription, n.imageURL, u.imageUML , mv.viewCount\n"
                + "ORDER BY totalViews DESC;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setImageURL(resultSet.getString("imageURL"));

                // Lấy danh sách genreName
                String genreNamesStr = resultSet.getString("genres");
                List<String> genreNames = new ArrayList<>();
                if (genreNamesStr != null && !genreNamesStr.isEmpty()) {
                    String[] genres = genreNamesStr.split(", "); // Chia chuỗi thành mảng
                    for (String genre : genres) {
                        genreNames.add(genre.trim()); // Thêm từng genre vào danh sách
                    }
                }
                novel.setGenreNames(genreNames); // Set danh sách genreName vào novel
                novel.setAuthorImage(resultSet.getString("imageUML"));
                novel.setViewCount(resultSet.getInt("totalViews"));

                list.add(novel);

            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return list;
    }

    public static void main(String[] args) {
        NovelDAO n = new NovelDAO();
        List<Novel> list = n.getNovelsByTimeUpdate();
        System.out.println(list);
    }

    public List<Novel> getNovelsByPopularity() {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT TOP 3 n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating, COUNT(v.novelID) AS viewCount "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID "
                + "WHERE n.novelStatus = 'active' "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName "
                + "ORDER BY viewCount DESC"; // Sắp xếp theo viewCount giảm dần
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null
                        ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating"));
                novel.setViewCount(resultSet.getInt("viewCount")); // Thêm dòng này
                list.add(novel);

            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return list;
    }

    public Novel getNovelById(int novelID) {
        Novel novel = null;
        List<String> genreNames = new ArrayList<>(); // Danh sách tên thể loại
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, "
                + "n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, "
                + "COALESCE(AVG(r.score), 0) AS averageRating, COUNT(DISTINCT v.viewID) AS viewCount, "
                + "(SELECT STRING_AGG(g.genreName, ', ') "
                + " FROM Genre_Novel gn2 "
                + " JOIN Genre g ON gn2.genreID = g.genreID "
                + " WHERE gn2.novelID = n.novelID) AS genres "
                + // Subquery để loại bỏ trùng
                "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID "
                + "WHERE n.novelStatus = 'active' AND n.novelID = ? "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, "
                + "n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null
                        ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating"));
                novel.setViewCount(resultSet.getInt("viewCount"));

                // Lấy danh sách genreName
                String genreNamesStr = resultSet.getString("genres");
                if (genreNamesStr != null && !genreNamesStr.isEmpty()) {
                    String[] genres = genreNamesStr.split(", "); // Chia chuỗi thành mảng
                    for (String genre : genres) {
                        genreNames.add(genre.trim()); // Thêm từng genre vào danh sách
                    }
                }
                novel.setGenreNames(genreNames); // Set danh sách genreName vào novel

                // Tính ratingCount bằng RatingDAO
                RatingDAO ratingDAO = new RatingDAO();
                int ratingCount = ratingDAO.getRatingCount(novel.getNovelID());
                novel.setRatingCount(ratingCount);

            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return novel;
    }

    public List<Novel> getNovelsSortedByFilter(String filterType, List<String> genreNames) {
        List<Novel> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, "
                + "n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, "
                + "COALESCE(AVG(r.score), 0) AS averageRating, "
                + "((SELECT COUNT(*) FROM Viewing v WHERE v.novelID = n.novelID) + "
                + "(SELECT COUNT(*) FROM Rating r2 WHERE r2.novelID = n.novelID)) AS popularityScore, "
                + "MAX(c.publishedDate) AS latestChapterDate "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Chapter c ON n.novelID = c.novelID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
        );

        sql.append("WHERE n.novelStatus = 'active' ");

        if (genreNames != null && !genreNames.isEmpty() && !genreNames.contains("all")) {
            sql.append("AND n.novelID IN (")
                    .append("SELECT gn.novelID FROM Genre_Novel gn JOIN Genre g ON gn.genreID = g.genreID ")
                    .append("WHERE g.genreName IN (")
                    .append(String.join(",", Collections.nCopies(genreNames.size(), "?")))
                    .append(")) "); // Bỏ HAVING để lấy novel có ít nhất 1 genre
        }

        sql.append("GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, ")
                .append("n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName ");

        switch (filterType) {
            case "rating":
                sql.append("ORDER BY averageRating DESC");
                break;
            case "time":
                sql.append("ORDER BY COALESCE(MAX(c.publishedDate), n.publishedDate) DESC");
                break;
            case "popular":
                sql.append("ORDER BY popularityScore DESC");
                break;
            default:
                break;
        }

        System.out.println("SQL Query: " + sql.toString());

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int parameterIndex = 1;
            if (genreNames != null && !genreNames.isEmpty() && !genreNames.contains("all")) {
                for (String genre : genreNames) {
                    statement.setString(parameterIndex++, genre);
                    System.out.println("Genre Param " + (parameterIndex - 1) + ": " + genre);
                }
                // Không cần set Genre Count Param nữa vì đã bỏ HAVING
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Novel novel = new Novel();
                novel.setNovelID(resultSet.getInt("novelID"));
                novel.setNovelName(resultSet.getString("novelName"));
                novel.setUserID(resultSet.getInt("userID"));
                novel.setImageURL(resultSet.getString("imageURL"));
                novel.setNovelDescription(resultSet.getString("novelDescription"));
                novel.setTotalChapter(resultSet.getInt("totalChapter"));
                novel.setPublishedDate(resultSet.getTimestamp("publishedDate") != null
                        ? resultSet.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setNovelStatus(resultSet.getString("novelStatus"));
                novel.setAuthor(resultSet.getString("author"));
                novel.setAverageRating(resultSet.getDouble("averageRating"));
                novel.setPopularityScore(resultSet.getInt("popularityScore"));
                novel.setLatestChapterDate(resultSet.getTimestamp("latestChapterDate") != null
                        ? resultSet.getTimestamp("latestChapterDate").toLocalDateTime() : null);
                list.add(novel);
                System.out.println("Found Novel: " + novel.getNovelID() + " - " + novel.getNovelName());
            }
            System.out.println("Novels retrieved: " + list.size());
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //------------------------------------------------------------------------------------------------------------------
// Helper method to close resources
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();

            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class
                    .getName()).log(Level.SEVERE, null, e);
        }
    }

}
     //------------------------------------------------------------------------------------------------------------------
