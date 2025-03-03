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
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class ViewingDAO {
    private final DBContext db;

    public ViewingDAO() {
        db = new DBContext();
    }

   // Tạo một Viewing mới (chỉ cần novelID)
    public boolean createViewing(int novelID) {
        String sql = "INSERT INTO Viewing (novelID) VALUES (?)";
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ViewingDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ViewingDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return rowsInserted > 0;
    }

    // Lấy số lượt xem hiện tại của một novel
    public int getViewsCount(int novelID) {
        String sql = "SELECT count(viewID) AS viewCount FROM Viewing WHERE novelID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int views = 0;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            rs = statement.executeQuery();

            if (rs.next()) {
                views = rs.getInt("viewCount");
            }
        } catch (SQLException e) {
            Logger.getLogger(ViewingDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return views;
    }

    public static void main(String[] args) {
        ViewingDAO v = new ViewingDAO();
        int a = v.getViewsCount(8);
        System.out.println(a);
    }
}
