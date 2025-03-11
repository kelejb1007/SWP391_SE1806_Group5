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
import model.LockNovelLog;
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class LockNovelLogDAO {

    private final DBContext db;

    public LockNovelLogDAO() {
        db = new DBContext();
    }

    //Admin-------------------------------------------------------------------------------------------------------------
    public boolean addLockLog(LockNovelLog ln) throws SQLException{
        String sql = "insert into LockNovelLog (managerID, novelID, action, lockReason)\n"
                   + "values (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ln.getManagerID());
            statement.setInt(2, ln.getNovelID());
            statement.setString(3, ln.getAction());
            statement.setString(4, ln.getLockReason());
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        } 
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------

    // Helper method to close resources
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}

