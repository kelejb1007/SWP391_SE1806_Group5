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
import model.Novel;
import utils.DBContext;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String sql = "SELECT n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName AS author, COALESCE(AVG(r.score), 0) AS averageRating "
                + "FROM Novel n "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "LEFT JOIN Rating r ON n.novelID = r.novelID "
                + "WHERE n.novelStatus = 'active' AND n.novelID = ? "
                + "GROUP BY n.novelID, n.novelName, n.userID, n.imageURL, n.novelDescription, n.totalChapter, n.publishedDate, n.novelStatus, ua.fullName";
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
                novel.setAverageRating(resultSet.getDouble("averageRating")); // Lấy từ resultSet
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
