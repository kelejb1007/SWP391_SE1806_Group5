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
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
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
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Logger.getLogger(GenreDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return genre;
    }
public static void main(String[] args) {
        GenreDAO ge = new GenreDAO();
        List<Genre> list = ge.getAllGenres();
        System.out.println(list);
    }
}
