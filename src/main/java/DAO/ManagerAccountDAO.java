package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ManagerAccount;
import utils.DBContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class ManagerAccountDAO {

    DBContext dbContext = new DBContext();

    // Xác thực user bằng username và password
    public ManagerAccount authenticateUser(String username, String password) {
        String sql = "SELECT * FROM ManagerAccount WHERE userName = ? AND password = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashSHA256(password));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy user bằng email (dùng cho Google Login)
    public ManagerAccount getUserByEmail(String email) {
        String sql = "SELECT * FROM ManagerAccount WHERE email = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tạo user mới từ Google
    public void createUserFromGoogle(String email, String fullName) {
        String sql = "INSERT INTO ManagerAccount (email, fullName, role) VALUES (?, ?, 'user')";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy vai trò của user (Staff, Admin)
    public String getUserRole(int userId) {
        String sql = "SELECT role FROM ManagerAccount WHERE ManagerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "user";
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Chuyển `ResultSet` thành `ManagerAccount`
    private ManagerAccount mapResultSetToUser(ResultSet rs) throws SQLException {
        ManagerAccount user = new ManagerAccount();
        user.setManagerID(rs.getInt("managerID"));
        user.setUsername(rs.getString("userName"));
        user.setFullName(rs.getString("fullName"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        return user;
    }

    /**
     * Khoa thêm cho UC ManageStaff Lấy danh sách tài khoản theo vai trò.
     *
     * @param role Vai trò cần lấy (ví dụ: "Staff").
     * @return Danh sách tài khoản phù hợp.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> getAccountsByRole(String role) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role "
                + "FROM ManagerAccount WHERE role = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    //LienXuanThinh
    // Lấy danh sách tất cả tài khoản
    public List<ManagerAccount> getAllAccounts() throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role, status "
                + "FROM ManagerAccount";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ManagerAccount account = mapResultSetToAccount(rs);
                listAccounts.add(account);
            }
        }
        return listAccounts;
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<ManagerAccount> searchAccounts(String keyword) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role, status "
                + "FROM ManagerAccount "
                + "WHERE username LIKE ? OR email LIKE ? OR fullName LIKE ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);

            try ( ResultSet rs = stmt.executeQuery()) {
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
     *
     * @param role Vai trò nhân viên ("Staff").
     * @param keyword Từ khóa tìm kiếm.
     * @return Danh sách tài khoản nhân viên phù hợp.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> searchAccountsByRole(String role, String keyword) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role "
                + "FROM ManagerAccount "
                + "WHERE role = ? AND (username LIKE ? OR email LIKE ? OR fullName LIKE ?)";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, role);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    // Lấy danh sách tài khoản bị khóa
    public List<ManagerAccount> getLockedAccounts() {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role, status "
                + "FROM ManagerAccount WHERE status = 0"; // Chỉ lấy tài khoản bị khóa

        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listAccounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listAccounts;
    }

    // Cập nhật trạng thái khóa/mở khóa tài khoản
    public void updateLockStatus(int managerID, boolean newStatus) throws SQLException {
        String sql = "UPDATE ManagerAccount SET status = ? WHERE managerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStatus ? 1 : 0); // 1 = Mở khoá, 0 = Khoá
            stmt.setInt(2, managerID);
            stmt.executeUpdate();
        }
    }

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

        account.setStatus(rs.getInt("status"));
        return account;
    }

}
