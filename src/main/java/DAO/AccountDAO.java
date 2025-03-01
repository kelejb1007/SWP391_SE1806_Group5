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
import model.ManagerAccount;
import utils.DBContext;

/**
 *
 * @author Admin LienXuanThinh_ce182117
 */

public class AccountDAO {

    DBContext dbContext = new DBContext();

    /**
     * Lấy tất cả các tài khoản quản trị từ bảng ManagerAccount.
     * @return Danh sách ManagerAccount.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> getAllAccounts() throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role " +
                     "FROM ManagerAccount";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ManagerAccount account = mapResultSetToAccount(rs);
                listAccounts.add(account);
            }
        }
        return listAccounts;
    }

    /**
     * Tìm kiếm tài khoản quản trị theo từ khóa (so sánh với username, email, fullName).
     * @param keyword Từ khóa tìm kiếm.
     * @return Danh sách ManagerAccount thỏa điều kiện tìm kiếm.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> searchAccounts(String keyword) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role " +
                     "FROM ManagerAccount " +
                     "WHERE username LIKE ? OR email LIKE ? OR fullName LIKE ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    /**
     * Ánh xạ ResultSet sang đối tượng ManagerAccount.
     * @param rs Kết quả truy vấn.
     * @return Đối tượng ManagerAccount.
     * @throws SQLException Nếu có lỗi truy xuất dữ liệu.
     */
    private ManagerAccount mapResultSetToAccount(ResultSet rs) throws SQLException {
        ManagerAccount account = new ManagerAccount();
        account.setManagerID(rs.getInt("managerID"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setCreationDate(rs.getTimestamp("creationDate"));
        account.setFullName(rs.getString("fullName"));
        account.setEmail(rs.getString("email"));
        account.setNumberPhone(rs.getString("numberPhone"));
        account.setGender(rs.getString("gender"));
        account.setCanLock(rs.getBoolean("canLock"));
        account.setCanApprove(rs.getBoolean("canApprove"));
        account.setRole(rs.getString("role"));
        return account;
    }

    /** Khoa thêm cho UC ManageStaff
     * Lấy danh sách tài khoản theo vai trò.
     * @param role Vai trò cần lấy (ví dụ: "Staff").
     * @return Danh sách tài khoản phù hợp.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> getAccountsByRole(String role) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role " +
                     "FROM ManagerAccount WHERE role = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, role);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    /**
     * Tìm kiếm tài khoản nhân viên theo từ khóa.
     * @param role Vai trò nhân viên ("Staff").
     * @param keyword Từ khóa tìm kiếm.
     * @return Danh sách tài khoản nhân viên phù hợp.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> searchAccountsByRole(String role, String keyword) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role " +
                     "FROM ManagerAccount " +
                     "WHERE role = ? AND (username LIKE ? OR email LIKE ? OR fullName LIKE ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, role);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    public ManagerAccount getAccountById(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean registerUser(ManagerAccount newAccount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isUserExist(String username, String email) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
