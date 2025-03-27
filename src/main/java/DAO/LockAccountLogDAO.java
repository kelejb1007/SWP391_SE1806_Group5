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
import model.LockAccountLog;
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class LockAccountLogDAO {

    DBContext dbContext = new DBContext();

    public void logAccountLockAction(int managerID, int userID, String action, String lockReason) throws SQLException {
        String sql = "INSERT INTO LockAccountLog (managerID, userID, datetime, action, lockReason) VALUES (?, ?, GETDATE(), ?, ?)";

        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, managerID);
            stmt.setInt(2, userID);
            stmt.setString(3, action);
            stmt.setString(4, lockReason);
            stmt.executeUpdate();
        }
    }

    public List<LockAccountLog> getAllLockLogs() throws SQLException {
        List<LockAccountLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM LockAccountLog ORDER BY datetime DESC";

        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LockAccountLog log = new LockAccountLog(
                        rs.getInt("logAID"),
                        rs.getInt("managerID"),
                        rs.getInt("userID"),
                        rs.getTimestamp("datetime"),
                        rs.getString("action"),
                        rs.getString("lockReason")
                );
                logs.add(log);
            }
        }
        return logs;
    }

}
