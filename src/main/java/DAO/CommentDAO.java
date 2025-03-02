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
                comment.setCommentDate(rs.getDate("commentDate"));
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
            stmt.setDate(4, new java.sql.Date(comment.getCommentDate().getTime()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    public static void main(String[] args) {
    // Tạo một đối tượng CommentDAO để thao tác với DB
    CommentDAO commentDAO = new CommentDAO();

    // Tạo một bình luận mới
    Comment newComment = new Comment();
    newComment.setUserID(1);  // Thay bằng userID hợp lệ từ database
    newComment.setNovelID(2); // Thay bằng novelID hợp lệ từ database
    newComment.setContent("Đây là bình luận test");
    newComment.setCommentDate(new Date()); // Thêm ngày hiện tại

    // Gọi hàm addComment để thêm bình luận vào database
    boolean isAdded = commentDAO.addComment(newComment);

    // Kiểm tra kết quả
    if (isAdded) {
        System.out.println("Binh luan da duoc them thanh cong!");
    } else {
        System.out.println("Them binh luan that bai!");
    }
}


    public boolean deleteComment(int commentId) {
        String sql = "DELETE FROM Comment WHERE commentID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<Comment> getCommentsByNovelId(int novelId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE novelID = ? ORDER BY commentDate DESC";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, novelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setUserID(rs.getInt("userID"));
                comment.setNovelID(rs.getInt("novelID"));
                comment.setContent(rs.getString("commentContent"));
                comment.setCommentDate(rs.getDate("commentDate"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comments;
    }
    
   
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comment ORDER BY commentDate DESC";
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setUserID(rs.getInt("userID"));
                comment.setNovelID(rs.getInt("novelID"));
                comment.setContent(rs.getString("commentContent"));
                comment.setCommentDate(rs.getDate("commentDate"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comments;
    }
    
  
}
