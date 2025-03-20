/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NovelSubmission;
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class NovelSubmissionDAO {

    private final DBContext db;

    public NovelSubmissionDAO() {
        db = new DBContext();
    }
////////////user--------------------------------------------------------------

    public boolean addPostingSubmission(NovelSubmission ns) {
        String sql = "INSERT INTO NovelSubmission (novelID, userID, status, approvalDate, type)\n"
                + "VALUES (?, ?, 'pending', NULL, ?)";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ns.getNovelID());
            statement.setInt(2, ns.getUserID());
            statement.setString(3, ns.getType());
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean addUpdatingSubmission(NovelSubmission ns) {
        String sql = "INSERT INTO NovelSubmission (novelID, userID, status, approvalDate, type, draftID)\n"
                + "VALUES (?, ?, 'pending', NULL, ?, ?)";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ns.getNovelID());
            statement.setInt(2, ns.getUserID());
            statement.setString(3, ns.getType());
            statement.setInt(4, ns.getDraftID());
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public List<NovelSubmission> getSubmisstionHistory(int userID) {
        List<NovelSubmission> list = new ArrayList<>();
        String sql = "SELECT ns.submissionNID, ns.novelID, ns.userID, ns.managerID, ns.draftID, ns.submissionDate, \n"
                + "ns.approvalDate, type, status, ns.reasonRejected, n.novelName\n"
                + "FROM NovelSubmission ns\n"
                + "JOIN Novel n ON n.novelID = ns.novelID\n"
                + "WHERE ns.userID = ?\n"
                + "ORDER BY submissionDate DESC";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            rs = statement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            while (rs.next()) {
                NovelSubmission ns = new NovelSubmission();
                ns.setSubmissionNID(rs.getInt("submissionNID"));
                ns.setNovelID(rs.getInt("novelID"));
                ns.setUserID(rs.getInt("userID"));
                ns.setManagerID(rs.getInt("managerID"));
                ns.setDraftID(rs.getInt("draftID"));
                ns.setSubmissionDate(rs.getTimestamp("submissionDate") != null ? rs.getTimestamp("submissionDate").toLocalDateTime().format(formatter) : null);
                ns.setApprovalDate(rs.getTimestamp("approvalDate") != null ? rs.getTimestamp("approvalDate").toLocalDateTime().format(formatter) : null);
                ns.setType(rs.getString("type"));
                ns.setStatus(rs.getString("status"));
                ns.setReasonRejected(rs.getString("reasonRejected"));
                ns.setNovelName(rs.getString("novelName"));
                list.add(ns);
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //staff---------------------------------------------------------------------------------------
    public List<NovelSubmission> getAllSubmisstion() throws SQLException {
        List<NovelSubmission> list = new ArrayList<>();
        String sql = "SELECT ns.submissionNID, ns.novelID, ns.userID, ns.managerID, ns.draftID, ns.submissionDate, \n"
                + "ns.approvalDate, ns.type, ns.status, ns.reasonRejected, n.novelName, us.userName\n"
                + "FROM NovelSubmission ns\n"
                + "JOIN Novel n ON n.novelID = ns.novelID\n"
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
                NovelSubmission ns = new NovelSubmission();
                ns.setSubmissionNID(rs.getInt("submissionNID"));
                ns.setNovelID(rs.getInt("novelID"));
                ns.setUserID(rs.getInt("userID"));
                ns.setManagerID(rs.getInt("managerID"));
                ns.setDraftID(rs.getInt("draftID"));
                ns.setSubmissionDate(rs.getTimestamp("submissionDate") != null ? rs.getTimestamp("submissionDate").toLocalDateTime().format(formatter) : null);
                ns.setApprovalDate(rs.getTimestamp("approvalDate") != null ? rs.getTimestamp("approvalDate").toLocalDateTime().format(formatter) : null);
                ns.setType(rs.getString("type"));
                ns.setStatus(rs.getString("status"));
                ns.setReasonRejected(rs.getString("reasonRejected"));
                ns.setNovelName(rs.getString("novelName"));
                ns.setUserName(rs.getString("userName"));
                list.add(ns);
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public boolean updateSubmission(NovelSubmission ns) {
        String sql = "UPDATE NovelSubmission SET \n"
                + "managerID = ?, status= ?, approvalDate = SYSDATETIME() ,reasonRejected = ? \n"
                + "where submissionNID = ?";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ns.getManagerID());
            statement.setString(2, ns.getStatus());
            statement.setString(3, ns.getReasonRejected());
            statement.setInt(4, ns.getSubmissionNID());
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean checkPending(int NovelID) {
        String sql = "SELECT submissionNID FROM NovelSubmission where novelID = ? AND status = 'pending'";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, NovelID);
            rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

}
