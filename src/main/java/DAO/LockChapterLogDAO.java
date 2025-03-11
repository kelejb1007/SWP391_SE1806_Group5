package DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LockChapterLog;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class LockChapterLogDAO {

    private final DBContext db;

    public LockChapterLogDAO() {
        db = new DBContext();
    }

    //Admin-------------------------------------------------------------------------------------------------------------
    public boolean addLockLog(LockChapterLog ln) {
        String sql = "insert into LockChapterLog (managerID, chapterID, action, lockReason)\n"
                   + "values (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ln.getManagerID());
            statement.setInt(2, ln.getChapterID());
            statement.setString(3, ln.getAction());
            statement.setString(4, ln.getLockReason());
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(ChapterDAO.class.getName()).log(Level.SEVERE, null, e);
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
