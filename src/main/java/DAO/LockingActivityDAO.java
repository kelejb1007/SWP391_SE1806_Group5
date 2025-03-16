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
import model.LockingActivity;
import utils.DBContext;

public class LockingActivityDAO {

    private final DBContext db;

    public LockingActivityDAO() {
        db = new DBContext();
    }

     public List<LockingActivity> getAllLockingActivities() {
        List<LockingActivity> list = new ArrayList<>();
        String sql = "SELECT la.logNID, la.novelID, n.novelName, la.managerID, m.fullName AS managerName, "
                   + "la.action, la.datetime, la.lockReason "
                   + "FROM LockNovelLog la "
                   + "JOIN Novel n ON la.novelID = n.novelID "
                   + "JOIN ManagerAccount m ON la.managerID = m.managerID "
                   + "ORDER BY la.datetime DESC";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            
            while (rs.next()) {
                LockingActivity activity = new LockingActivity(
                        rs.getInt("logNID"),
                        rs.getInt("novelID"),
                        rs.getString("novelName"),
                        rs.getInt("managerID"),
                        rs.getString("managerName"),
                        rs.getString("action"),
                        rs.getTimestamp("datetime").toLocalDateTime(),
                        rs.getString("lockReason")
                );
                list.add(activity);
            }
        } catch (SQLException e) {
            Logger.getLogger(LockingActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
      }

    public static void main(String[] args) {
        // Khởi tạo DAO
        LockingActivityDAO dao = new LockingActivityDAO();
        
        // Gọi phương thức lấy danh sách hoạt động khóa
        List<LockingActivity> activities = dao.getAllLockingActivities();

        // Kiểm tra danh sách có dữ liệu không
        if (activities.isEmpty()) {
            System.out.println("Không có hoạt động khóa nào trong cơ sở dữ liệu.");
        } else {
            // In danh sách hoạt động khóa
            System.out.println("Danh sách hoạt động khóa:");
            for (LockingActivity activity : activities) {
                System.out.println("ID: " + activity.getLogNID());
                System.out.println("Truyện: " + activity.getNovelName());
                System.out.println("Quản lý: " + activity.getManagerName());
                System.out.println("Hành động: " + activity.getAction());
                System.out.println("Thời gian: " + activity.getDatetime());
                System.out.println("Lý do: " + activity.getLockReason());
                System.out.println("-------------------------------");
            }
        }
    }


     

    public void addLockingActivity(LockingActivity activity) {
        String sql = "INSERT INTO LockNovelLog (novelID, managerID, action, datetime, lockReason) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, activity.getNovelID());
            statement.setInt(2, activity.getManagerID());
            statement.setString(3, activity.getAction());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(activity.getDatetime()));
            statement.setString(5, activity.getLockReason());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(LockingActivityDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(LockingActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
