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
import model.Genre;
import utils.DBContext;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class GenreDAO {

    private final DBContext db;

    public GenreDAO() {
        db = new DBContext();
    }

    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genre";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rs.getInt("genreID"));
                genre.setGenreName(rs.getString("genreName"));
                genres.add(genre);
            }
        } catch (SQLException e) {
            Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return genres;
    }

    public Genre getGenreById(int genreId) {
        Genre genre = null;
        String sql = "SELECT * FROM Genre WHERE genreID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, genreId);
            rs = statement.executeQuery();
            if (rs.next()) {
                genre = new Genre();
                genre.setGenreID(rs.getInt("genreID"));
                genre.setGenreName(rs.getString("genreName"));
            }
        } catch (SQLException e) {
            Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return genre;
    }

   // Phương thức thêm một Genre mới
    public void addGenre(Genre genre) {
        String sql = "INSERT INTO Genre (genreName) VALUES (?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, genre.getGenreName());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
    }
    
    // Phương thức xóa một Genre theo genreID
    public void deleteGenre(int genreId) {
        String sql = "DELETE FROM Genre WHERE genreID = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, genreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
    }

    
    
    
    
    ///Trung---------------------------------------------------------------------------
    public boolean addGenreNovel(int genreID, int novelID) {
        String sql = "INSERT INTO Genre_Novel (genreID, novelID)\n"
                + "VALUES (?, ?)";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, genreID);
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

    public String getGenreByNovelID(int novelID) {
        String sql = "SELECT STRING_AGG(g.genreName, ', ') "
                + " FROM Genre_Novel gn2 "
                + " JOIN Genre g ON gn2.genreID = g.genreID "
                + " WHERE gn2.novelID = ?";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        String genres = "";
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            rs = statement.executeQuery();

            if (rs.next()) {
                genres = rs.getString(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return genres;
    }

    public boolean deleteGenreNovel(int novelID) {
        String sql = "DELETE FROM Genre_Novel \n"
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

//    public int getGenreID(String genreName) {
//        String sql = "SELECT genreID FROM Genre WHERE genreName = ?";
//        Connection connection;
//        PreparedStatement statement;
//        ResultSet rs;
//        int genreID = -1;
//        try {
//            connection = db.getConnection();
//            statement = connection.prepareStatement(sql);
//            statement.setString(1, genreName);
//            rs = statement.executeQuery();
//            if (rs.next()) {
//                genreID = rs.getInt("genreID");
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return genreID;
//    }
    public static void main(String[] args) {
        GenreDAO ge = new GenreDAO();
        List<Genre> list = ge.getAllGenres();
        System.out.println(list);
    }
}
