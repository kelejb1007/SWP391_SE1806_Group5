/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Novel> getAllActiveNovels(String s) {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.imageURL, n.totalChapter, n.publishedDate, u.fullName, COALESCE(ROUND(AVG(r.score), 2), 0) AS averageRating, COUNT(v.novelID) AS viewCount\n"
                + "FROM Novel n \n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "LEFT JOIN Rating r ON n.novelID = r.novelID\n"
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID\n"
                + "WHERE novelStatus = '" + s + "'\n"
                + "GROUP BY n.novelID, n.novelName, n.imageURL, n.totalChapter, n.publishedDate, u.fullName";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Novel m = new Novel(rs.getInt("novelID"), rs.getString("novelName"), rs.getString("imageURL"), rs.getInt("totalChapter"),
                        (rs.getTimestamp("publishedDate") != null ? rs.getTimestamp("publishedDate").toLocalDateTime() : null),
                        rs.getString("fullName"), rs.getDouble("averageRating"), rs.getInt("viewCount"));
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, rs);
        }
        return list;
    }


public List<Novel> getLockedNovels() {
        List<Novel> list = new ArrayList<>();
        String sql = "WITH LatestLock AS (\n" +
                     "    SELECT novelID, MAX(datetime) AS latestLockDate\n" +
                     "    FROM LockNovelLog\n" +
                     "    WHERE action = 'lock'\n" +
                     "    GROUP BY novelID )\n" +
                     
                     "SELECT \n" +
                     "    n.novelID, n.novelName, n.totalChapter, n.publishedDate,\n" +
                     "	  u.fullName as author, m.fullName as staffName,\n" +
                     "    ll.datetime ,ll.lockReason\n" +
                     "FROM LatestLock l\n" +
                     "JOIN LockNovelLog ll ON (l.novelID = ll.novelID AND l.latestLockDate = ll.datetime) OR (l.novelID = ll.novelID AND l.latestLockDate is null AND ll.datetime is null)\n" +
                     "JOIN ManagerAccount m ON ll.managerID = m.managerID\n" +
                     "JOIN Novel n ON ll.novelID = n.novelID\n" +
                     "JOIN UserAccount u ON n.UserID = u.UserID \n" +
                     "WHERE n.novelStatus = 'inactive' \n" +
                     "ORDER BY ll.datetime DESC;";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
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
                m.setDatetime(rs.getTimestamp("datetime") != null? rs.getTimestamp("datetime").toLocalDateTime() : null);
                m.setLockReason(rs.getString("lockReason"));
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, rs);
        }
        return list;
    }
    
// Use for staff and user my novel
    public Novel getNovelByID(int novelID) {
        String sql = "SELECT n.novelID, n.novelName, n.imageURL, n.novelDescription, n.totalChapter, n.novelStatus, n.publishedDate, \n" +
                     "u.fullName as author, COALESCE(ROUND(AVG(r.score), 2), 0) AS averageRating, COUNT(v.novelID) AS viewCount, \n" +
                     "(SELECT STRING_AGG(g.genreName, ', ') " +
                     " FROM Genre_Novel gn2 " +
                     " JOIN Genre g ON gn2.genreID = g.genreID " +
                     " WHERE gn2.novelID = n.novelID) AS genres " +
                
                     "FROM Novel n\n" +
                     "JOIN UserAccount u ON n.UserID = u.UserID \n" +
                     "LEFT JOIN Rating r ON n.novelID = r.novelID\n" +
                     "LEFT JOIN Viewing v ON n.novelID = v.novelID\n" +
                     "WHERE n.novelID = ?\n" +
                     "GROUP BY n.novelID, n.novelName, n.imageURL, n.novelDescription, n.totalChapter, n.novelStatus, n.publishedDate, u.fullName";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Novel novel = new Novel();
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            rs = statement.executeQuery();

            while (rs.next()) {
                
                novel.setNovelID(rs.getInt("novelID"));
                novel.setNovelName(rs.getString("novelName"));
                novel.setImageURL(rs.getString("imageURL"));
                novel.setNovelDescription(rs.getString("novelDescription"));
                novel.setTotalChapter(rs.getInt("totalChapter"));
                novel.setNovelStatus(rs.getString("novelStatus"));
                novel.setPublishedDate(rs.getTimestamp("publishedDate") != null? rs.getTimestamp("publishedDate").toLocalDateTime() : null);
                novel.setAuthor(rs.getString("author"));
                novel.setAverageRating(rs.getDouble("averageRating")); 
                novel.setViewCount(rs.getInt("viewCount"));
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, rs);
        }
        return novel;
    }
    
    //---------------------------------------------------------------------------------------------------------------------

    //User - My Novel ---------------------------------------------------------------------------------------------------
    public List<Novel> getMyNovels(int userID) {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.imageURL, n.totalChapter, n.novelStatus, n.publishedDate, u.fullName, COALESCE(ROUND(AVG(r.score), 2), 0) AS averageRating, COUNT(v.novelID) AS viewCount\n"
                + "FROM Novel n \n"
                + "JOIN UserAccount u ON n.UserID = u.UserID \n"
                + "LEFT JOIN Rating r ON n.novelID = r.novelID\n"
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID\n"
                + "WHERE n.UserID = '" + userID +"'\n"
                + "GROUP BY n.novelID, n.novelName, n.imageURL, n.totalChapter, n.novelStatus, n.publishedDate, u.fullName";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
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
                list.add(m);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, rs);
        }
        return list;
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
        } finally {
            closeResources(connection, statement, resultSet);
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
        } finally {
            closeResources(connection, statement, resultSet);
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
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return list;
    }

    public List<Novel> getNovelsByRating() {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "WHERE n.novelStatus = 'active' "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName "
                + "ORDER BY averageRating DESC";
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
                novel.setAverageRating(resultSet.getDouble("averageRating")); // Lấy từ resultSet
                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return list;
    }

    public List<Novel> getNovelsByTimeUpdate() {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "WHERE n.novelStatus = 'active' "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName "
                + "ORDER BY n.publishedDate DESC";
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
                novel.setAverageRating(resultSet.getDouble("averageRating")); // Lấy từ resultSet
                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return list;
    }

    public List<Novel> getNovelsByPopularity() {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating, COUNT(v.novelID) AS viewCount "
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
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
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
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return novel;
    }

    public List<Novel> getNovelsSortedByFilter(String filterType, String genreName) {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, "
                + "n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, "
                + "COALESCE(AVG(r.score), 0) AS averageRating, COUNT(v.novelID) AS viewCount "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "LEFT JOIN Viewing v ON n.novelID = v.novelID ";

        // Nếu có genre, thêm JOIN và WHERE vào câu lệnh SQL
        if (genreName != null && !genreName.isEmpty() && !genreName.equals("all")) {
            sql += "JOIN Genre_Novel gn ON n.novelID = gn.novelID "
                    + "JOIN Genre g ON gn.genreID = g.genreID "
                    + "WHERE g.genreName = ? AND n.novelStatus = 'active' ";
        } else {
            sql += "WHERE n.novelStatus = 'active' ";
        }

        sql += "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, "
                + "n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName ";

        // Chọn cách sắp xếp
        if ("rating".equals(filterType)) {
            sql += "ORDER BY averageRating DESC";
        } else if ("time".equals(filterType)) {
            sql += "ORDER BY n.publishedDate DESC";
        } else if ("popular".equals(filterType)) {
            sql += "ORDER BY viewCount DESC"; // Sắp xếp theo lượt xem
        }

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            // Nếu có genre, truyền giá trị vào PreparedStatement
            if (genreName != null && !genreName.isEmpty() && !genreName.equals("all")) {
                statement.setString(1, genreName);
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
                novel.setViewCount(resultSet.getInt("viewCount")); // Thêm thông tin viewCount
                list.add(novel);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

// Lọc danh sách theo genre (sau khi đã lọc theo filter)
    public List<Novel> filterNovelsByGenre(List<Novel> novels, String genreName) {
        List<Novel> filteredList = new ArrayList<>();
        for (Novel novel : novels) {
            if (isNovelInGenre(novel.getNovelID(), genreName)) {
                filteredList.add(novel);
            }
        }
        return filteredList;
    }

// Kiểm tra xem một truyện có thuộc genre không
    private boolean isNovelInGenre(int novelID, String genreName) {
        String sql = "SELECT COUNT(*) FROM Genre_Novel gn "
                + "JOIN Genre g ON gn.genreID = g.genreID "
                + "WHERE gn.novelID = ? AND g.genreName = ?";

        try ( Connection connection = db.getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novelID);
            statement.setString(2, genreName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
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
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
     //------------------------------------------------------------------------------------------------------------------
