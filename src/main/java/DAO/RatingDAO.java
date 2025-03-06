/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Rating;
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class RatingDAO {
    private final DBContext db;

    public RatingDAO() {
        db = new DBContext();
    }

    public boolean addRate(Rating rate) {
    String sql = "INSERT INTO Rating (userID, novelID, score) VALUES (?, ?, ?)";
    Connection connection = null;
    PreparedStatement statement = null;

    try {
        connection = db.getConnection(); // Lấy Connection từ DBContext
        statement = connection.prepareStatement(sql);

        statement.setInt(1, rate.getUserID());
        statement.setInt(2, rate.getNovelID());
        statement.setInt(3, rate.getScore());

        System.out.println("Executing SQL: " + sql + " with parameters: " + rate.getUserID() + ", " + rate.getNovelID() + ", " + rate.getScore());
        int rowsAffected = statement.executeUpdate();

        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    } catch (SQLException e) {
        Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, "Error adding rate", e);
        e.printStackTrace(); // In stack trace để xem chi tiết lỗi
        return false;
    } finally {
        // Đảm bảo đóng statement và connection trong khối finally
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, "Error closing connection", e);
        }
    }
}

    public Rating getRateByNovelIdAndUserId(int novelId, int userId) {
        String sql = "SELECT * FROM Rating WHERE novelID = ? AND userID = ?";
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
                Rating rate = new Rating();
                rate.setRatingID(rs.getInt("ratingID"));
                rate.setUserID(rs.getInt("userID"));
                rate.setNovelID(rs.getInt("novelID"));
                rate.setScore(rs.getInt("score"));
               
                return rate;
            }
        } catch (SQLException e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace(); // In stack trace để xem chi tiết lỗi
        }
        return null;
    }

     public boolean updateRate(Rating rate) {
    String sql = "UPDATE Rating SET score = ? WHERE ratingID = ?";
    Connection connection = null;
    PreparedStatement statement = null;

    try {
        System.out.println("RateDAO.updateRate() called with rate: " + rate); // In thông tin rate
        connection = db.getConnection(); // Lấy Connection từ DBContext
        System.out.println("RateDAO.updateRate(): Connection obtained: " + connection); // In connection

        statement = connection.prepareStatement(sql);
        statement.setInt(1, rate.getScore());
        statement.setInt(2, rate.getRatingID());

        System.out.println("Executing SQL: " + sql + " with parameters: score=" + rate.getScore() + ", ratingID=" + rate.getRatingID()); // In câu lệnh SQL

        int rowsAffected = statement.executeUpdate();
        System.out.println("RatingDAO.updateRate(): Rows affected: " + rowsAffected); // In số hàng bị ảnh hưởng

        return rowsAffected > 0;
    } catch (SQLException e) {
        Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
        System.err.println("RateDAO.updateRate(): SQLException: " + e.getMessage()); // In thông báo lỗi SQLException
        e.printStackTrace(); // In stack trace để xem chi tiết lỗi
        return false;
    } finally {
        // Đảm bảo đóng statement và connection trong khối finally
        try {
            if (statement != null) {
                System.out.println("RateDAO.updateRate(): Closing statement"); // In thông báo đóng statement
                statement.close();
            }
            if (connection != null) {
                System.out.println("RateDAO.updateRate(): Closing connection"); // In thông báo đóng connection
                connection.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            System.err.println("RatingDAO.updateRate(): Error closing resources: " + e.getMessage()); // In thông báo lỗi đóng tài nguyên
        }
    }
}

    public boolean deleteRate(int ratingID) {
        String sql = "DELETE FROM Rating WHERE ratingID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ratingID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
             e.printStackTrace(); // In stack trace để xem chi tiết lỗi
            return false;
        } 
    }

    public double calculateAverageRating(int novelId) {
        String sql = "SELECT AVG(score) FROM Rating WHERE novelID = ?";
         Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
             statement = connection.prepareStatement(sql);
            statement.setInt(1, novelId);
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
             e.printStackTrace(); // In stack trace để xem chi tiết lỗi
        } 
        return 0.0;
    }

    public int getRatingCount(int novelId) {
       String sql = "SELECT COUNT(*) FROM Rating WHERE novelID = ?";
         Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
             statement = connection.prepareStatement(sql);
            statement.setInt(1, novelId);
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, e);
             e.printStackTrace(); // In stack trace để xem chi tiết lỗi
        } 
        return 0;
    }
    public static void main(String[] args) {
        RatingDAO r = new RatingDAO();
        int ratingCount = 0;
        ratingCount = r.getRatingCount(9);
        System.out.println(ratingCount);
    }
}
