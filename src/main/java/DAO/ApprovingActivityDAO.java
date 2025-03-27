package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ApprovingActivity;
import utils.DBContext;

public class ApprovingActivityDAO {

    private final DBContext db;

    public ApprovingActivityDAO() {
        db = new DBContext();
    }

    public List<ApprovingActivity> getAllApprovingActivities() {
        List<ApprovingActivity> list = new ArrayList<>();
        String sql = "SELECT cs.submissionCID AS approveID, cs.chapterID AS novelID, "
                   + "n.novelName, cs.managerID, m.fullName AS managerName, "
                   + "cs.status AS action, cs.approvalDate AS datetime, cs.reasonRejected AS approveReason "
                   + "FROM ChapterSubmission cs "
                   + "JOIN Chapter c ON cs.chapterID = c.chapterID "
                   + "JOIN Novel n ON c.novelID = n.novelID "
                   + "JOIN ManagerAccount m ON cs.managerID = m.managerID "
                   + "ORDER BY cs.approvalDate DESC";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                ApprovingActivity activity = new ApprovingActivity(
                        rs.getInt("approveID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("managerID"),
                        rs.getString("managerName"),
                        rs.getString("action"),
                        rs.getTimestamp("datetime") != null ? rs.getTimestamp("datetime").toLocalDateTime() : null,
                        rs.getString("approveReason")
                );
                list.add(activity);
            }
        } catch (SQLException e) {
            Logger.getLogger(ApprovingActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
    public static void main(String[] args) {
        ApprovingActivityDAO dao = new ApprovingActivityDAO();
        List<ApprovingActivity> activities = dao.getAllApprovingActivities();
        
        if (activities.isEmpty()) {
            System.out.println("Không có hoạt động phê duyệt nào.");
        } else {
            System.out.println("Danh sách hoạt động phê duyệt:");
            for (ApprovingActivity activity : activities) {
                System.out.println("ID: " + activity.getApproveID()
                        + ", Chapter ID: " + activity.getNovelID()
                        + ", Tiểu thuyết: " + activity.getNovelName()
                        + ", Quản lý: " + activity.getManagerName()
                        + ", Hành động: " + activity.getAction()
                        + ", Thời gian: " + (activity.getDatetime() != null ? activity.getDatetime() : "N/A")
                        + ", Lý do: " + (activity.getApproveReason() != null ? activity.getApproveReason() : "N/A"));
            }
        }
    }

    public void addApprovingActivity(ApprovingActivity activity) {
        String sql = "INSERT INTO ChapterSubmission (chapterID, managerID, status, approvalDate, reasonRejected) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, activity.getNovelID());
            statement.setInt(2, activity.getManagerID());
            statement.setString(3, activity.getAction());
            if (activity.getDatetime() != null) {
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(activity.getDatetime()));
            } else {
                statement.setNull(4, java.sql.Types.TIMESTAMP);
            }
            statement.setString(5, activity.getApproveReason());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ApprovingActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources(connection, statement, null);
        }
    }

    private void closeResources(Connection connection, PreparedStatement statement, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ApprovingActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
