/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ChapterSubmission;
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class ChapterSubmissionDAO {
    private final DBContext db;

    public ChapterSubmissionDAO() {
        db = new DBContext();
    }
////////////user--------------------------------------------------------------

    public boolean addPostingSubmission(int chapterID, int userID, String type) {
        String sql = "INSERT INTO ChapterSubmission (chapterID, userID, status, approvalDate, type)\n"
                + "VALUES (?, ?, 'pending', NULL, ?)";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, chapterID);
            statement.setInt(2, userID);
            statement.setString(3, type);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public boolean addUpdatingSubmission(int chapterID, int userID, String type, int draftID) {
        String sql = "INSERT INTO ChapterSubmission (chapterID, userID, status, approvalDate, type, draftID)\n"
                + "VALUES (?, ?, 'pending', NULL, ?, ?)";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, chapterID);
            statement.setInt(2, userID);
            statement.setString(3, type);
            statement.setInt(4, draftID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public List<ChapterSubmission> getAllSubmisstion() {
        List<ChapterSubmission> list = new ArrayList<>();
        String sql = "SELECT ns.submissionCID, ns.chapterID, ns.userID, ns.managerID, ns.draftID, ns.submissionDate, \n"
                + "ns.approvalDate, ns.type, ns.status, ns.reasonRejected, n.chapterName, us.userName\n"
                + "FROM ChapterSubmission ns\n"
                + "JOIN Chapter n ON n.chapterID = ns.chapterID\n"
                + "JOIN UserAccount us ON us.userID = ns.userID\n"
                + "WHERE ns.status = 'pending'\n"
                + "ORDER BY submissionDate DESC";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            while (rs.next()) {
                ChapterSubmission ns = new ChapterSubmission();
                ns.setSubmissionCID(rs.getInt("submissionCID"));
                ns.setChapterID(rs.getInt("chapterID"));
                ns.setUserID(rs.getInt("userID"));
                ns.setManagerID(rs.getInt("managerID"));
                ns.setDraftID(rs.getInt("draftID"));
                ns.setSubmissionDate(rs.getTimestamp("submissionDate") != null ? rs.getTimestamp("submissionDate").toLocalDateTime().format(formatter) : null);
                ns.setApprovalDate(rs.getTimestamp("approvalDate") != null ? rs.getTimestamp("approvalDate").toLocalDateTime().format(formatter) : null);
                ns.setType(rs.getString("type"));
                ns.setStatus(rs.getString("status"));
                ns.setReasonRejected(rs.getString("reasonRejected"));
                ns.setChapterName(rs.getString("chapterName"));
                ns.setUserName(rs.getString("userName"));
                list.add(ns);
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
    
    public boolean updateSubmission(ChapterSubmission cs) {
        String sql = "UPDATE ChapterSubmission SET \n"
                + "managerID = ?, status= ?, approvalDate = SYSDATETIME() ,reasonRejected = ? \n"
                + "where submissionCID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, cs.getManagerID());
            statement.setString(2, cs.getStatus());
            statement.setString(3, cs.getReasonRejected());
            statement.setInt(4, cs.getSubmissionCID());
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(ManageChapterDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
