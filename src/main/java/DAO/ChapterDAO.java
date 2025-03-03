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
import model.Chapter;
import utils.DBContext;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class ChapterDAO {
    private final DBContext db;

    public ChapterDAO() {
        db = new DBContext();
    }

    public List<Chapter> getChaptersByNovelId(int novelID, String sort) {
        List<Chapter> list = new ArrayList<>();
        String sql = "SELECT chapterID, novelID, chapterNumber, chapterName, fileURL, publishedDate, chapterStatus FROM Chapter WHERE novelID = ? AND chapterStatus ='active' ORDER BY chapterNumber " + (sort != null && sort.equals("desc") ? "DESC" : "ASC");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            rs = statement.executeQuery();

            while (rs.next()) {
                Chapter chapter = new Chapter();
                chapter.setChapterID(rs.getInt("chapterID"));
                chapter.setNovelID(rs.getInt("novelID"));
                chapter.setChapterNumber(rs.getInt("chapterNumber"));
                chapter.setChapterName(rs.getString("chapterName"));
                chapter.setFileURL(rs.getString("fileURL"));

                // Chuyển đổi từ Timestamp sang LocalDateTime (nếu publishedDate có thể null)
                java.sql.Timestamp timestamp = rs.getTimestamp("publishedDate");
                chapter.setChapterCreatedDate(timestamp != null ? timestamp.toLocalDateTime() : null);

                chapter.setChapterStatus(rs.getString("chapterStatus"));
                list.add(chapter);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return list;
    }


    public Chapter getChapterById(int chapterID) {
        Chapter chapter = null;
        String sql = "SELECT chapterID, novelID, chapterNumber, chapterName, fileURL, publishedDate, chapterStatus FROM Chapter WHERE chapterID = ? AND chapterStatus ='active' ";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, chapterID);
            rs = statement.executeQuery();
            if(rs.next()){
                chapter = new Chapter();
                chapter.setChapterID(rs.getInt("chapterID"));
                chapter.setNovelID(rs.getInt("novelID"));
                chapter.setChapterNumber(rs.getInt("chapterNumber"));
                chapter.setChapterName(rs.getString("chapterName"));
                chapter.setFileURL(rs.getString("fileURL"));
                // Chuyển đổi từ Timestamp sang LocalDateTime (nếu publishedDate có thể null)
                java.sql.Timestamp timestamp = rs.getTimestamp("publishedDate");
                chapter.setChapterCreatedDate(timestamp != null ? timestamp.toLocalDateTime() : null);
                chapter.setChapterStatus(rs.getString("chapterStatus"));
            }
        } catch (SQLException e) {
            Logger.getLogger(ChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return chapter;
    }

    public static void main(String[] args) {
        ChapterDAO c = new ChapterDAO();
        System.out.println(c.getChaptersByNovelId(1, "desc"));
    }
}
