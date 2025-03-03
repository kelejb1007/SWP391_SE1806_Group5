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

    public boolean addNovelSubmission(int novelID, int userID) {
        String sql = "INSERT INTO NovelSubmission (novelID, userID, status)\n"
                + "VALUES (?, ?, 'pending')";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            statement.setInt(2, userID);
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
        String sql = "SELECT submissionNID, novelID, userID, managerID, submissionDate, approvalDate, status, reasonRejected"
                + "FROM NovelSubmission "
                + "ORDER BY submissionDate DESC";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                NovelSubmission ns = new NovelSubmission();
                ns.setSubmissionNID(rs.getInt("submissionNID"));
                ns.setSubmissionNID(rs.getInt("novelID"));
                ns.setSubmissionNID(rs.getInt("userID"));
                ns.setSubmissionNID(rs.getInt("managerID"));
                ns.setSubmissionDate(rs.getTimestamp("submissionDate") != null ? rs.getTimestamp("submissionDate").toLocalDateTime() : null);
                ns.setSubmissionDate(rs.getTimestamp("approvalDate") != null ? rs.getTimestamp("approvalDate").toLocalDateTime() : null);
                ns.setStatus(rs.getString("status"));
                ns.setReasonRejected(rs.getString("reasonRejected"));
                list.add(ns);
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

}
