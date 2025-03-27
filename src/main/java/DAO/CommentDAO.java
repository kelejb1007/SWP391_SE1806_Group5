/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Tai Vo
 */
import model.Comment;
import utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.Date;


public class CommentDAO {
    private final DBContext db;

    public CommentDAO() {
        db = new DBContext();
    }

    public Comment getCommentById(int commentId) {
        Comment comment = null;
        String sql = "SELECT * FROM Comment WHERE commentID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setUserID(rs.getInt("userID"));
                comment.setNovelID(rs.getInt("novelID"));
                comment.setContent(rs.getString("commentContent"));
                comment.setCommentDate(rs.getTimestamp("commentDate") != null ? rs.getTimestamp("commentDate").toLocalDateTime() : null);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comment;
    }

  
    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO Comment (userID, novelID, commentContent, commentDate) VALUES (?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comment.getUserID());
            stmt.setInt(2, comment.getNovelID());
            stmt.setString(3, comment.getContent());
             stmt.setTimestamp(4, Timestamp.valueOf(comment.getCommentDate())); // Chuyển đổi LocalDateTime thành Timestamp
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


    public boolean deleteComment(int commentId, int userId) {

        String sql = "DELETE FROM Comment WHERE commentID = ? AND userID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
             stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


    public boolean updateComment(int commentId, int userId, String newContent) { // command: cập nhật bình luận
        String sql = "UPDATE Comment SET commentContent = ? WHERE commentID = ? AND userID = ?";
        try ( Connection connection = db.getConnection();  PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newContent);
            stmt.setInt(2, commentId);
            stmt.setInt(3, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }}

 


    public List<Comment> getCommentsByNovelId(int novelId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.commentID, c.userID, u.fullName, c.novelID, c.commentContent, c.commentDate " +
                 "FROM Comment c JOIN UserAccount u ON c.userID = u.userID " +
                 "WHERE c.novelID = ? ORDER BY c.commentDate DESC";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, novelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setUserID(rs.getInt("userID"));
                comment.setNovelID(rs.getInt("novelID"));
                comment.setFullName(rs.getString("fullName"));
                comment.setContent(rs.getString("commentContent"));
                comment.setCommentDate(rs.getTimestamp("commentDate") != null ? rs.getTimestamp("commentDate").toLocalDateTime() : null);
                comments.add(comment);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comments;
    }
    
   
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();

  String sql = "SELECT c.commentID, c.userID, u.fullName, c.novelID, c.commentContent, c.commentDate " +
                 "FROM Comment c JOIN UserAccount u ON c.userID = u.userID " +
                 "ORDER BY c.commentDate DESC";
       
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setUserID(rs.getInt("userID"));
                comment.setFullName(rs.getString("fullName"));
                comment.setNovelID(rs.getInt("novelID"));
                comment.setContent(rs.getString("commentContent"));
                comment.setCommentDate(rs.getTimestamp("commentDate") != null ? rs.getTimestamp("commentDate").toLocalDateTime() : null);
                comments.add(comment);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comments;
    }



    public boolean deleteCommentByStaff(int commentId) {
        String sql = "DELETE FROM Comment WHERE commentID = ?";
        try ( Connection connection = db.getConnection();  PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    
  
}
