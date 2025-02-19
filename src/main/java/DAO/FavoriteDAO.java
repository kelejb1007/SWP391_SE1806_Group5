package DAO;

import utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Favorite;

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
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
            }
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
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
            }
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
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Logger.getLogger(FavoriteDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
       public static void main(String[] args) {
        FavoriteDAO favoriteDAO = new FavoriteDAO();

        // 1. Test getFavoriteByNovelIdAndAccountId
        System.out.println("--- Test getFavoriteByNovelIdAndAccountId ---");
        int novelId = 8;
        int accountId = 1;
        Favorite favorite = favoriteDAO.getFavoriteByNovelIdAndUserId(novelId, accountId);
        if (favorite != null) {
            System.out.println("Favorite found: " + favorite.getFavoriteID() + ", Account ID: " + favorite.getUserID() + ", Novel ID: " + favorite.getNovelID() + ", Is Favorite: " + favorite.isIsFavorite());
        } else {
            System.out.println("Favorite not found for Novel ID: " + novelId + " and Account ID: " + accountId);
        }
        
        // 2. Test addFavorite
        System.out.println("\n--- Test addFavorite ---");
        Favorite newFavorite = new Favorite();
        newFavorite.setUserID(1);
        newFavorite.setNovelID(8); // Chọn novelID khác để kiểm tra thêm
        newFavorite.setIsFavorite(true);
        boolean addSuccess = favoriteDAO.addFavorite(newFavorite);
        if (addSuccess) {
            System.out.println("Favorite added successfully.");
            
            // kiểm tra lại xem đã thêm vào DB chưa
           Favorite checkFavorite = favoriteDAO.getFavoriteByNovelIdAndUserId(3,1);
          if (checkFavorite != null) {
                System.out.println("Favorite check found: " + checkFavorite.getFavoriteID() + ", Account ID: " + checkFavorite.getUserID() + ", Novel ID: " + checkFavorite.getNovelID() + ", Is Favorite: " + checkFavorite.isIsFavorite());
            } else {
                System.out.println("Favorite check not found for Novel ID: " + 3 + " and Account ID: " + 1);
            }

        } else {
            System.out.println("Failed to add favorite.");
        }


        
    }


        
}