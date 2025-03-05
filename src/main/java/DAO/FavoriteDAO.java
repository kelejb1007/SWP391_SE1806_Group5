package DAO;

import utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Favorite;
import model.Novel;

public class FavoriteDAO {

    private final DBContext db;

    public FavoriteDAO() {
        db = new DBContext();
    }

    public Favorite getFavoriteByNovelIdAndUserId(int novelId, int userId) {
        Favorite favorite = null;
        String sql = "SELECT * FROM Favorite WHERE novelID = ? AND userID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelId);
            statement.setInt(2, userId);
            rs = statement.executeQuery();
            if (rs.next()) {
                favorite = new Favorite();
                favorite.setFavoriteID(rs.getInt("favoriteID"));
                favorite.setUserID(rs.getInt("userID"));
                favorite.setNovelID(rs.getInt("novelID"));
                favorite.setIsFavorite(rs.getBoolean("isFavorite"));
            }
        } catch (SQLException e) {
            Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return favorite;
    }

    public boolean addFavorite(Favorite favorite) {
        String sql = "INSERT INTO Favorite (userID, novelID, isFavorite) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, favorite.getUserID());
            statement.setInt(2, favorite.getNovelID());
            statement.setBoolean(3, true);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(Favorite favorite) {
        String sql = "DELETE FROM Favorite WHERE userID = ? AND novelID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, favorite.getUserID());
            statement.setInt(2, favorite.getNovelID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        } 
    }

    // Lấy danh sách Novel yêu thích của người dùng, bao gồm tên tác giả
    public List<Novel> getFavoriteNovelsByUserId(int userId) {
        List<Novel> favoriteNovels = new ArrayList<>();
        String sql = "SELECT n.*, ua.userName AS authorName "
                + "FROM Favorite f "
                + "JOIN Novel n ON f.novelID = n.novelID "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "WHERE f.userID = ? AND n.novelStatus = 'active'";

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
                novel.setUserID(rs.getInt("userID"));
                novel.setImageURL(rs.getString("imageURL"));
                novel.setNovelDescription(rs.getString("novelDescription"));
                novel.setTotalChapter(rs.getInt("totalChapter"));
                if (rs.getTimestamp("publishedDate") != null) {
                    novel.setPublishedDate(rs.getTimestamp("publishedDate").toLocalDateTime());
                } else {
                    novel.setPublishedDate(null); // Hoặc đặt giá trị mặc định nếu cần
                }

                novel.setNovelStatus(rs.getString("novelStatus"));

                novel.setAuthor(rs.getString("authorName")); // Giả sử bạn thêm trường 'authorName' vào class Novel

                favoriteNovels.add(novel);
            }

        } catch (SQLException e) {
            Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
        } 

        return favoriteNovels;
    }

   public List<Novel> searchFavoriteNovelsByUserId(int userId, String query) {
        List<Novel> favoriteNovels = new ArrayList<>();
        String sql = "SELECT n.*, ua.userName AS authorName "
                + "FROM Favorite f "
                + "JOIN Novel n ON f.novelID = n.novelID "
                + "JOIN UserAccount ua ON n.userID = ua.userID "
                + "WHERE f.userID = ? AND n.novelStatus = 'active' AND n.novelName LIKE ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2, "%" + query + "%"); // Thêm điều kiện tìm kiếm theo tên tiểu thuyết
            rs = statement.executeQuery();

            while (rs.next()) {
                Novel novel = new Novel();
                novel.setNovelID(rs.getInt("novelID"));
                novel.setNovelName(rs.getString("novelName"));
                novel.setUserID(rs.getInt("userID"));
                novel.setImageURL(rs.getString("imageURL"));
                novel.setNovelDescription(rs.getString("novelDescription"));
                novel.setTotalChapter(rs.getInt("totalChapter"));
                if (rs.getTimestamp("publishedDate") != null) {
                    novel.setPublishedDate(rs.getTimestamp("publishedDate").toLocalDateTime());
                } else {
                    novel.setPublishedDate(null); // Hoặc đặt giá trị mặc định nếu cần
                }

                novel.setNovelStatus(rs.getString("novelStatus"));

                novel.setAuthor(rs.getString("authorName")); // Giả sử bạn thêm trường 'authorName' vào class Novel

                favoriteNovels.add(novel);
            }

        } catch (SQLException e) {
            Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return favoriteNovels;
    }
   
}
